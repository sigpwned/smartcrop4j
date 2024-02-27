package com.sigpwned.smartcrop4j.impl;

import static com.sigpwned.smartcrop4j.util.Validation.requireFinite;
import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;
import static com.sigpwned.smartcrop4j.util.Validation.requireUnit;
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
import com.sigpwned.smartcrop4j.util.Validation;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DefaultSmartCropper implements SmartCropper {

  public static DefaultSmartCropperBuilder builder() {
    return new DefaultSmartCropperBuilder();
  }

  public static DefaultSmartCropper create() {
    return builder().buildDefaultSmartCropper();
  }

  private final float detailWeight;

  /**
   * [ 0.78, 0.57, 0.44 ]
   */
  private final float[] skinColor;
  private final float skinBias;
  private final float skinBrightnessMin;
  private final float skinBrightnessMax;
  private final float skinThreshold;
  private final float skinWeight;
  private final float saturationBrightnessMin;
  private final float saturationBrightnessMax;
  private final float saturationThreshold;
  private final float saturationBias;
  private final float saturationWeight;
  // Step * minscale rounded down to the next power of two should be good
  private final int scoreDownSample;
  private final int cropSearchStep;
  private final float scaleStep;
  private final float minScale;
  private final float maxScale;
  private final float edgeRadius;
  private final float edgeWeight;
  private final float outsideImportance;
  private final float boostWeight;
  private final float ruleOfThirdsWeight;
  private final boolean prescale;
  private final int prescaleSize;
  private final Object prescaleAlgorithm;
  private final boolean debug;

  /* default */ DefaultSmartCropper(DefaultSmartCropperBuilder builder) {
    this.detailWeight = requireFinite(builder.getDetailWeight());
    this.skinColor = Validation.requireUnitColor(requireNonNull(builder.getSkinColor()));
    this.skinBias = requireFinite(builder.getSkinBias());
    this.skinBrightnessMin = requireUnit(builder.getSkinBrightnessMin());
    this.skinBrightnessMax = requireUnit(builder.getSkinBrightnessMax());
    if (getSkinBrightnessMin() > getSkinBrightnessMax()) {
      throw new IllegalArgumentException("skinBrightnessMin > skinBrightnessMax");
    }
    this.skinThreshold = requireFinite(builder.getSkinThreshold());
    this.skinWeight = requireFinite(builder.getSkinWeight());
    this.saturationBrightnessMin = requireUnit(builder.getSaturationBrightnessMin());
    this.saturationBrightnessMax = requireUnit(builder.getSaturationBrightnessMax());
    if (getSaturationBrightnessMin() > getSaturationBrightnessMax()) {
      throw new IllegalArgumentException("saturationBrightnessMin > saturationBrightnessMax");
    }
    this.saturationThreshold = requireFinite(builder.getSaturationThreshold());
    this.saturationBias = requireFinite(builder.getSaturationBias());
    this.saturationWeight = requireFinite(builder.getSaturationWeight());
    this.scoreDownSample = requirePositive(builder.getScoreDownSample());
    this.cropSearchStep = requirePositive(builder.getCropSearchStep());
    this.scaleStep = requirePositive(builder.getScaleStep());
    this.minScale = requireUnit(builder.getMinScale());
    this.maxScale = requireUnit(builder.getMaxScale());
    if (getMinScale() > getMaxScale()) {
      throw new IllegalArgumentException("minScale > maxScale");
    }
    this.edgeRadius = requirePositive(builder.getEdgeRadius());
    this.edgeWeight = requireFinite(builder.getEdgeWeight());
    this.outsideImportance = requireFinite(builder.getOutsideImportance());
    this.boostWeight = requireFinite(builder.getBoostWeight());
    this.ruleOfThirdsWeight = builder.getRuleOfThirdsWeight();
    this.prescale = builder.isPrescale();
    this.prescaleSize = requirePositive(builder.getPrescaleSize());
    this.prescaleAlgorithm = requireNonNull(builder.getPrescaleAlgorithm());
    this.debug = builder.isDebug();
  }

  public DefaultSmartCropperBuilder toBuilder() {
    return new DefaultSmartCropperBuilder(this);
  }

  public float getDetailWeight() {
    return detailWeight;
  }

  public float[] getSkinColor() {
    return skinColor;
  }

  public float getSkinBias() {
    return skinBias;
  }

  public float getSkinBrightnessMin() {
    return skinBrightnessMin;
  }

  public float getSkinBrightnessMax() {
    return skinBrightnessMax;
  }

  public float getSkinThreshold() {
    return skinThreshold;
  }

  public float getSkinWeight() {
    return skinWeight;
  }

  public float getSaturationBrightnessMin() {
    return saturationBrightnessMin;
  }

  public float getSaturationBrightnessMax() {
    return saturationBrightnessMax;
  }

  public float getSaturationThreshold() {
    return saturationThreshold;
  }

  public float getSaturationBias() {
    return saturationBias;
  }

  public float getSaturationWeight() {
    return saturationWeight;
  }

  public int getScoreDownSample() {
    return scoreDownSample;
  }

  public int getCropSearchStep() {
    return cropSearchStep;
  }

  public float getScaleStep() {
    return scaleStep;
  }

  public float getMinScale() {
    return minScale;
  }

  public float getMaxScale() {
    return maxScale;
  }

  public float getEdgeRadius() {
    return edgeRadius;
  }

  public float getEdgeWeight() {
    return edgeWeight;
  }

  public float getOutsideImportance() {
    return outsideImportance;
  }

  public float getBoostWeight() {
    return boostWeight;
  }

  public float getRuleOfThirdsWeight() {
    return ruleOfThirdsWeight;
  }

  public boolean isPrescale() {
    return prescale;
  }

  public int getPrescaleSize() {
    return prescaleSize;
  }

  public Object getPrescaleAlgorithm() {
    return prescaleAlgorithm;
  }

  public boolean isDebug() {
    return debug;
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

  private static final float RULE_OF_THIRDS_WEIGHT = 16.0f;

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
    if (isPrescale()) {
      prescale = Math.min(Math.max(getPrescaleSize() / (float) originalImage.getWidth(),
          getPrescaleSize() / (float) originalImage.getHeight()), 1.0f);
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
            getPrescaleAlgorithm());

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
    SkinColoring.skinDetect(input, output, getSkinColor(), getSkinThreshold(),
        getSkinBrightnessMin(), getSkinBrightnessMax());
    Saturation.saturationDetect(input, output, getSaturationThreshold(),
        getSaturationBrightnessMin(), getSaturationBrightnessMax());
    Boosting.applyBoosts(output, boosts);

    ScoredCrop topCrop = scoreCrops(output,
        Composition.generateCandidateCrops(input.width, input.height, cropWidth, cropHeight,
            getMinScale(), getMaxScale(), getScaleStep(), getCropSearchStep()),
        getScoreDownSample()).stream().max(Comparator.comparing(ScoredCrop::getScore))
        .orElseThrow();

    System.out.println("Selected " + topCrop);

    BufferedImage debugImage;
    if (isDebug()) {
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
              getOutsideImportance(), getEdgeRadius(), getEdgeWeight(), getRuleOfThirdsWeight());

          final float dspDetail = od[dsp + GO] / 255.0f;

          skin += (od[dsp + RO] / 255.0f) * (dspDetail + getSkinBias()) * ospImportance;
          detail += dspDetail * ospImportance;
          saturation += (od[dsp + BO] / 255.0f) * (dspDetail + getSaturationBias()) * ospImportance;
          boost += (od[dsp + AO] / 255.0f) * ospImportance;
        }
      }

      final float total =
          (detail * getDetailWeight() + skin * getSkinWeight() + saturation * getSaturationWeight()
              + boost * getBoostWeight()) / (c.getWidth() * c.getHeight());

      CropScore score = new CropScore(detail, saturation, skin, boost, total);

      ScoredCrop result = new ScoredCrop(c.getX(), c.getY(), c.getWidth(), c.getHeight(), score);

      System.out.println(result);

      return result;
    }).collect(toList());
  }
}
