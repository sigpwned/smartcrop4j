package com.sigpwned.smartcrop4j.impl;

import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import com.sigpwned.smartcrop4j.Crop;
import com.sigpwned.smartcrop4j.CropBoost;
import com.sigpwned.smartcrop4j.CropResult;
import com.sigpwned.smartcrop4j.SmartCropper;
import com.sigpwned.smartcrop4j.impl.util.Boosting;
import com.sigpwned.smartcrop4j.impl.util.Composition;
import com.sigpwned.smartcrop4j.impl.util.EdgeDetection;
import com.sigpwned.smartcrop4j.impl.util.MoreImageData;
import com.sigpwned.smartcrop4j.impl.util.Saturation;
import com.sigpwned.smartcrop4j.impl.util.SkinColoring;
import com.sigpwned.smartcrop4j.util.BufferedImages;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
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
    final float[] od = output.data;
    // TODO What on earth is this doing? Bug?
    for (int i = 0; i < output.width; i += PIXEL_STRIDE) {
      od[i + BO] = 0;
    }
    for (CropBoost boost : configuration.getBoosts()) {
      Boosting.boost(output, boost.getX(), boost.getY(), boost.getWidth(), boost.getHeight(),
          boost.getWeight());
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


  private void foo(BufferedImage image, int aspectWidth, int aspectHeight) {
    // Determine the scale of the largest crop that fits within the image and has the given aspect ratio
    float scale = Math.min(image.getWidth() / (float) aspectWidth,
        image.getHeight() / (float) aspectHeight);

    // Determine the size of the crop
    int cropWidth = (int) (aspectWidth * scale);
    int cropHeight = (int) (aspectHeight * scale);

    List<CropBoost> boosts = configuration.getBoosts();

    // For performance reasons, we don't want to analyze the image at full resolution in case it's
    // very large. In practice, we don't need to analyze an image larger than 256x256 pixels.
    // Therefore, we'll downscale the image if it's larger than 256x256 pixels.
    float prescale = Math.min(Math.max(256.0f / image.getWidth(), 256.0f / image.getHeight()),
        1.0f);
    if (prescale < 1.0f) {
      // If prescale is less than 1, it means that at least one of the dimensions of the image
      // (width or height) is greater than 256 pixels. Here's the reasoning:
      //
      // The ratios 256.0f / imageWidth and 256.0f / imageHeight compute how much the image would
      // need to be scaled down for the width or height, respectively, to be exactly 256 pixels. If
      // either dimension of the image is greater than 256 pixels, the corresponding ratio will be
      // less than 1.
      //
      // The Math.max(256.0f / imageWidth, 256.0f / imageHeight) part of the expression ensures that
      // the larger of these two scaling factors is used. This is because the aim is to scale the
      // image down just enough so that neither dimension exceeds 256 pixels, maintaining the
      // image's aspect ratio.
      //
      // Finally, the Math.min(..., 1.0f) part ensures that the scaling factor does not exceed 1,
      // meaning the image should not be scaled up if both dimensions are already less than or equal
      // to 256 pixels.
      //
      // Therefore, if prescale is less than 1, it indicates that scaling down is necessary for at
      // least one dimension of the image to fit within a 256-pixel size constraint, implying that
      // either imageWidth or imageHeight (or both) is larger than 256 pixels.
      BufferedImage x = BufferedImages.scaled(image, (int) (image.getWidth() * prescale),
          (int) (image.getHeight() * prescale), BufferedImage.TYPE_INT_ARGB);

      cropWidth = (int) (cropWidth * prescale);
      cropHeight = (int) (cropHeight * prescale);

      float finalPrescale = prescale;
      boosts = boosts.stream().map(
          b -> new CropBoost((int) (b.getX() * finalPrescale), (int) (b.getY() * finalPrescale),
              (int) (b.getWidth() * finalPrescale), (int) (b.getHeight() * finalPrescale),
              b.getWeight())).collect(toList());
    } else {
      // If prescale is 1, it means that both dimensions of the image are less than or equal to 256.
      // In this case, we don't need to scale the image down, so we set prescale to 1.
      prescale = 1.0f;
    }

    // Analyze the image
    ImageData input = ImageData.fromBufferedImage(image);

    ImageData output = new ImageData(input.width, input.height);

    EdgeDetection.edgeDetect(input, output);
    SkinColoring.skinDetect(input, output, configuration.getSkinColor(),
        configuration.getSkinThreshold(), configuration.getSkinBrightnessMin(),
        configuration.getSkinBrightnessMax());
    Saturation.saturationDetect(input, output, configuration.getSaturationThreshold(),
        configuration.getSaturationBrightnessMin(), configuration.getSaturationBrightnessMax());
    applyBoosts(output);

    ImageData scoreOutput = MoreImageData.scaledDown(output, configuration.getScoreDownSample());

    ScoredCrop topCrop = generateCrops(input.width, input.height).stream().map(
            c -> new ScoredCrop(c.getX(), c.getY(), c.getWidth(), c.getHeight(),
                score(scoreOutput, c, configuration.getScoreDownSample())))
        .max(Comparator.reverseOrder()).orElse(null);


  }

  private List<Crop> generateCrops(int width, int height) {
    List<Crop> result = new ArrayList<>();
    int minDimension = Math.min(width, height);
    int cropWidth = options.cropWidth || minDimension;
    int cropHeight = options.cropHeight || minDimension;
    for (float scale = configuration.getMaxScale(); scale >= configuration.getMinScale();
        scale -= configuration.getScaleStep()) {
      final int cwd = (int) (cropWidth * scale);
      final int chd = (int) (cropHeight * scale);
      for (int y = 0; y + chd <= height; y += configuration.getStep()) {
        for (int x = 0; x + cwd <= width; x += configuration.getStep()) {
          result.add(new Crop(x, y, cwd, chd));
        }
      }
    }
    return unmodifiableList(result);
  }

  public CropScore score(ImageData output, Crop crop, int downSample) {
    float detail = 0.0f;
    float saturation = 0.0f;
    float skin = 0.0f;
    float boost = 0.0f;

    float[] od = output.data;
    int outputHeightDownSample = output.height * downSample;
    int outputWidthDownSample = output.width * downSample;
    int outputWidth = output.width;

    for (int y = 0; y < outputHeightDownSample; y += downSample) {
      for (int x = 0; x < outputWidthDownSample; x += downSample) {
        int pos = (y / downSample) * outputWidth + (x / downSample) * 4;

        float importance = Composition.calculateImportance(crop, x, y,
            configuration.getOutsideImportance(), configuration.getEdgeRadius(),
            configuration.getEdgeWeight(), configuration.isRuleOfThirds());

        float detailOfPosition = od[pos + 1] / 255.0f;

        skin += (od[pos] / 255.0f) * (detailOfPosition + configuration.getSkinBias()) * importance;
        detail += detailOfPosition * importance;
        saturation +=
            (od[pos + 2] / 255.0f) * (detailOfPosition + configuration.getSaturationBias())
                * importance;
        boost += (od[pos + 3] / 255.0f) * importance;
      }
    }

    final float total =
        (detail * configuration.getDetailWeight() + skin * configuration.getSkinWeight()
            + saturation * configuration.getSaturationWeight()
            + boost * configuration.getBoostWeight()) / (crop.getWidth() * crop.getHeight());

    return new CropScore(detail, saturation, skin, boost, total);
  }
}
