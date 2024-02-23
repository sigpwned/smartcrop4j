package com.sigpwned.smartcrop4j.impl;

import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;

import com.sigpwned.smartcrop4j.Crop;
import com.sigpwned.smartcrop4j.CropResult;
import com.sigpwned.smartcrop4j.SmartCropper;
import com.sigpwned.smartcrop4j.impl.util.MoreImageData;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DefaultSmartCropper implements SmartCropper {

  private final DefaultSmartCropperConfiguration configuration;

  @Override
  public CropResult crop(BufferedImage image) {
    return null;
  }

  public DefaultSmartCropper(DefaultSmartCropperConfiguration configuration) {
    this.configuration = requireNonNull(configuration);
  }


  private void applyBoosts(ImageData output) {
    float[] od = output.getData();
    for (int i = 0; i < output.getWidth(); i += PIXEL_STRIDE) {
      od[i + BO] = 0;
    }
    for (int i = 0; i < options.boost.length; i++) {
      applyBoost(options.boost[i], options, output);
    }
  }

  /**
   * four elements per pixel
   */
  private static final int PIXEL_STRIDE = 4;

  /**
   * red offset
   */
  private static final int RO = 0;

  /**
   * green offset
   */
  private static final int GO = 1;

  /**
   * blue offset
   */
  private static final int BO = 2;

  /**
   * alpha offset
   */
  private static final float AO = 3;

  private static final float RULE_OF_THIRDS_WEIGHT = 16.0f;


  private float importance(Crop crop, int x, int y) {
    if (!crop.contains(x, y)) {
      return options.outsideImportance;
    }
    float nx = (x - crop.getX()) / (float) crop.getWidth();
    float ny = (y - crop.getY()) / (float) crop.getHeight();
    float px = Math.abs(0.5f - nx) * 2.0f;
    float py = Math.abs(0.5f - ny) * 2.0f;
    // Distance from edge
    float dx = Math.max(px - 1.0f + options.edgeRadius, 0.0f);
    float dy = Math.max(py - 1.0f + options.edgeRadius, 0.0f);
    float d = (dx * dx + dy * dy) * options.edgeWeight;
    float s = 1.41f - (float) Math.sqrt(px * px + py * py);
    if (options.ruleOfThirds) {
      s += Math.max(0.0f, s + d + 0.5f) * 1.2f * (thirds(px) + thirds(py));
    }
    return s + d;
  }

  private List<Crop> generateCrops(int width, int height) {
    List<Crop> result = new ArrayList<>();
    int minDimension = Math.min(width, height);
    int cropWidth = options.cropWidth || minDimension;
    int cropHeight = options.cropHeight || minDimension;
    for (float scale = options.maxScale; scale >= options.minScale; scale -= options.scaleStep) {
      final int cwd = (int) (cropWidth * scale);
      final int chd = (int) (cropHeight * scale);
      for (int y = 0; y + chd <= height; y += options.step) {
        for (int x = 0; x + cwd <= width; x += options.step) {
          result.add(new Crop(x, y, cwd, chd));
        }
      }
    }
    return unmodifiableList(result);
  }

  private CropResult analyse(ImageData input) {
    Object result = null;
    ImageData output = new ImageData(input.getWidth(), input.getHeight());

    edgeDetect(input, output);
    skinDetect(input, output);
    saturationDetect(input, output);
    applyBoosts(output);

    var scoreOutput = MoreImageData.scaled(output, options.scoreDownSample);

    var topScore = Float.NEGATIVE_INFINITY;
    Crop topCrop = null;
    List<Crop> crops = generateCrops(input.getWidth(), input.getHeight());

    for (int i = 0, iLen = crops.length; i < iLen; i++) {
      var crop = crops[i];
      crop.score = score(options, scoreOutput, crop);
      if (crop.score.total > topScore) {
        topCrop = crop;
        topScore = crop.score.total;
      }
    }

    result.topCrop = topCrop;

    if (options.debug && topCrop) {
      result.crops = crops;
      result.debugOutput = output;
      result.debugOptions = options;
      // Create a copy which will not be adjusted by the post scaling of smartcrop.crop
      result.debugTopCrop = extend({}, result.topCrop);
    }
    return result;
  }

  float score(ImageData output, Crop crop) {
    var result = {detail:0, saturation:0, skin:0, boost:0, total:0
    };

    var od = output.data;
    var downSample = options.scoreDownSample;
    var invDownSample = 1 / downSample;
    var outputHeightDownSample = output.height * downSample;
    var outputWidthDownSample = output.width * downSample;
    var outputWidth = output.width;

    for (var y = 0; y < outputHeightDownSample; y += downSample) {
      for (var x = 0; x < outputWidthDownSample; x += downSample) {
        var p = (~~(y * invDownSample) * outputWidth + ~~(x * invDownSample)) * 4;
        var i = importance(options, crop, x, y);
        var detail = od[p + 1] / 255;

        result.skin += (od[p] / 255) * (detail + options.skinBias) * i;
        result.detail += detail * i;
        result.saturation += (od[p + 2] / 255) * (detail + options.saturationBias) * i;
        result.boost += (od[p + 3] / 255) * i;
      }
    }

    result.total = (result.detail * options.detailWeight + result.skin * options.skinWeight
        + result.saturation * options.saturationWeight + result.boost * options.boostWeight) / (
        crop.width * crop.height);
    return result;
  }

}
