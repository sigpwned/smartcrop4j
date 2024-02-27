/*-
 * =================================LICENSE_START==================================
 * smartcrop4j
 * ====================================SECTION=====================================
 * Copyright (C) 2024 Andy Boothe
 * ====================================SECTION=====================================
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ==================================LICENSE_END===================================
 */
package com.sigpwned.smartcrop4j.impl;

import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import com.sigpwned.smartcrop4j.Crop;
import com.sigpwned.smartcrop4j.CropBoost;
import com.sigpwned.smartcrop4j.SmartCropper;
import com.sigpwned.smartcrop4j.impl.util.Boosting;
import com.sigpwned.smartcrop4j.impl.util.Composition;
import com.sigpwned.smartcrop4j.impl.util.EdgeDetection;
import com.sigpwned.smartcrop4j.impl.util.MoreImageData;
import com.sigpwned.smartcrop4j.impl.util.Saturation;
import com.sigpwned.smartcrop4j.impl.util.SkinColoring;
import com.sigpwned.smartcrop4j.util.BufferedImages;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DefaultSmartCropper implements SmartCropper {

  private final DefaultSmartCropperOptions options;

  public DefaultSmartCropper() {
    this(DefaultSmartCropperOptions.create());
  }

  public DefaultSmartCropper(DefaultSmartCropperOptions options) {
    this.options = requireNonNull(options);
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
  private static final int AO = 3;

  public DefaultCropResult crop(BufferedImage originalImage, int aspectWidth, int aspectHeight,
      List<CropBoost> boosts) {
    // Validate our inputs
    originalImage = requireNonNull(originalImage);
    aspectWidth = requirePositive(aspectWidth);
    aspectHeight = requirePositive(aspectHeight);
    boosts = Optional.ofNullable(boosts).map(Collections::unmodifiableList)
        .orElseGet(Collections::emptyList);

    // TODO Is there any work to do here to handle tiny images?

    // Determine the scale of the largest crop that fits within the image and has the given aspect ratio
    float scale = Math.min(originalImage.getWidth() / (float) aspectWidth,
        originalImage.getHeight() / (float) aspectHeight);

    // Determine the size of the crop. This is in the original coordinate space.
    int cropWidth = (int) (aspectWidth * scale);
    int cropHeight = (int) (aspectHeight * scale);

    // For performance reasons, we don't want to analyze the image at full resolution in case it's
    // very large. In practice, we don't need to analyze an image larger than 256x256 pixels.
    // Therefore, we'll downscale the image if it's larger than 256x256 pixels.
    BufferedImage analyzeImage;
    float prescale;
    if (getOptions().isPrescale()) {
      prescale = Math.min(
          Math.max(getOptions().getPrescaleSize() / (float) originalImage.getWidth(),
              getOptions().getPrescaleSize() / (float) originalImage.getHeight()), 1.0f);
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
        analyzeImage = BufferedImages.scaled(originalImage,
            (int) (originalImage.getWidth() * prescale),
            (int) (originalImage.getHeight() * prescale), BufferedImage.TYPE_INT_ARGB,
            getOptions().getPrescaleAlgorithm());

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
        analyzeImage = originalImage;
      }
    } else {
      // We don't prescale in this case, so set prescale to 1.0f and use the original image for analysis.
      prescale = 1.0f;
      analyzeImage = originalImage;
    }

    // Analyze the image. These are in the prescaled coordinate space.
    ImageData input = ImageData.fromBufferedImage(analyzeImage);
    ImageData output = new ImageData(input.width, input.height);

    EdgeDetection.edgeDetect(input, output);
    SkinColoring.skinDetect(input, output, getOptions().getSkinColor(),
        getOptions().getSkinThreshold(), getOptions().getSkinBrightnessMin(),
        getOptions().getSkinBrightnessMax());
    Saturation.saturationDetect(input, output, getOptions().getSaturationThreshold(),
        getOptions().getSaturationBrightnessMin(),
        getOptions().getSaturationBrightnessMax());
    Boosting.applyBoosts(output, boosts);

    ScoredCrop topCrop = scoreCrops(output,
        Composition.generateCandidateCrops(input.width, input.height, cropWidth, cropHeight,
            getOptions().getMinScale(), getOptions().getMaxScale(),
            getOptions().getScaleStep(), getOptions().getCropSearchStep()),
        getOptions().getScoreDownSample()).stream()
        .max(Comparator.comparing(ScoredCrop::getScore)).orElseThrow();

    BufferedImage debugImage;
    if (getOptions().isDebug()) {
      debugImage = output.toBufferedImage();
    } else {
      debugImage = null;
    }

    return new DefaultCropResult(topCrop, debugImage);
  }

  public List<ScoredCrop> scoreCrops(ImageData output, List<Crop> crops, int downsample) {
    final ImageData downsampledOutput = MoreImageData.scaledDown(output, downsample);

    final float[] od = downsampledOutput.data;
    final int outputHeightDownSample = downsampledOutput.height * downsample;
    final int outputWidthDownSample = downsampledOutput.width * downsample;
    final int outputWidth = downsampledOutput.width;

    return crops.stream().map(c -> {
      // TODO It's odd. The crop and the output are in different coordinate spaces. The output has
      // been scaled down twice (!!), but the crop has only been scaled down once.

      float detail = 0.0f;
      float saturation = 0.0f;
      float skin = 0.0f;
      float boost = 0.0f;

      // osx = output standard x
      // osy = output standard y
      // osp = output standard pos
      // dsx = downscaled x
      // dsy = downscaled y
      // dsp = downscaled pos
      for (int osy = 0; osy < outputHeightDownSample; osy += downsample) {
        for (int osx = 0; osx < outputWidthDownSample; osx += downsample) {
          final int dsy = osy / downsample;
          final int dsx = osx / downsample;

          final int osp = (osy * outputWidthDownSample + osx) * PIXEL_STRIDE;
          final int dsp = (dsy * outputWidth + dsx) * PIXEL_STRIDE;

          final float ospImportance = Composition.calculatePointImportance(c, osx, osy,
              getOptions().getOutsideImportance(), getOptions().getEdgeRadius(),
              getOptions().getEdgeWeight(), getOptions().getRuleOfThirdsWeight());

          final float dspDetail = od[dsp + GO] / 255.0f;

          skin += (od[dsp + RO] / 255.0f) * (dspDetail + getOptions().getSkinBias())
              * ospImportance;
          detail += dspDetail * ospImportance;
          saturation +=
              (od[dsp + BO] / 255.0f) * (dspDetail + getOptions().getSaturationBias())
                  * ospImportance;
          boost += (od[dsp + AO] / 255.0f) * ospImportance;
        }
      }

      final float total =
          (detail * getOptions().getDetailWeight() + skin * getOptions().getSkinWeight()
              + saturation * getOptions().getSaturationWeight()
              + boost * getOptions().getBoostWeight()) / (c.getWidth() * c.getHeight());

      CropScore score = new CropScore(detail, saturation, skin, boost, total);

      return new ScoredCrop(c.getX(), c.getY(), c.getWidth(), c.getHeight(), score);
    }).collect(toList());
  }

  private DefaultSmartCropperOptions getOptions() {
    return options;
  }
}
