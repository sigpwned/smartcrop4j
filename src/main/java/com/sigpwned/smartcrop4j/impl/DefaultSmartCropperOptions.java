package com.sigpwned.smartcrop4j.impl;

import static com.sigpwned.smartcrop4j.util.Validation.requireFinite;
import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;
import static com.sigpwned.smartcrop4j.util.Validation.requireUnit;
import static com.sigpwned.smartcrop4j.util.Validation.requireUnitColor;
import static java.util.Objects.requireNonNull;

import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Objects;

public class DefaultSmartCropperOptions {

  public static DefaultSmartCropperOptionsBuilder builder() {
    return new DefaultSmartCropperOptionsBuilder();
  }

  public static final DefaultSmartCropperOptions DEFAULT = builder().build();

  public static DefaultSmartCropperOptions create() {
    return DEFAULT;
  }

  private final float detailWeight;
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

  /* default */ DefaultSmartCropperOptions(DefaultSmartCropperOptionsBuilder builder) {
    this.detailWeight = requireFinite(builder.getDetailWeight());
    this.skinColor = requireUnitColor(requireNonNull(builder.getSkinColor()));
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
    if (getPrescaleAlgorithm() != RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        && getPrescaleAlgorithm() != RenderingHints.VALUE_INTERPOLATION_BILINEAR
        && getPrescaleAlgorithm() != RenderingHints.VALUE_INTERPOLATION_BICUBIC) {
      throw new IllegalArgumentException(
          "prescaleAlgorithm must be one of VALUE_INTERPOLATION_NEAREST_NEIGHBOR, VALUE_INTERPOLATION_BILINEAR, VALUE_INTERPOLATION_BICUBIC");
    }
    this.debug = builder.isDebug();
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

  public DefaultSmartCropperOptionsBuilder toBuilder() {
    return new DefaultSmartCropperOptionsBuilder(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DefaultSmartCropperOptions)) {
      return false;
    }
    DefaultSmartCropperOptions that = (DefaultSmartCropperOptions) o;
    return Float.compare(getDetailWeight(), that.getDetailWeight()) == 0
        && Float.compare(getSkinBias(), that.getSkinBias()) == 0
        && Float.compare(getSkinBrightnessMin(), that.getSkinBrightnessMin()) == 0
        && Float.compare(getSkinBrightnessMax(), that.getSkinBrightnessMax()) == 0
        && Float.compare(getSkinThreshold(), that.getSkinThreshold()) == 0
        && Float.compare(getSkinWeight(), that.getSkinWeight()) == 0
        && Float.compare(getSaturationBrightnessMin(), that.getSaturationBrightnessMin()) == 0
        && Float.compare(getSaturationBrightnessMax(), that.getSaturationBrightnessMax()) == 0
        && Float.compare(getSaturationThreshold(), that.getSaturationThreshold()) == 0
        && Float.compare(getSaturationBias(), that.getSaturationBias()) == 0
        && Float.compare(getSaturationWeight(), that.getSaturationWeight()) == 0
        && getScoreDownSample() == that.getScoreDownSample()
        && getCropSearchStep() == that.getCropSearchStep()
        && Float.compare(getScaleStep(), that.getScaleStep()) == 0
        && Float.compare(getMinScale(), that.getMinScale()) == 0
        && Float.compare(getMaxScale(), that.getMaxScale()) == 0
        && Float.compare(getEdgeRadius(), that.getEdgeRadius()) == 0
        && Float.compare(getEdgeWeight(), that.getEdgeWeight()) == 0
        && Float.compare(getOutsideImportance(), that.getOutsideImportance()) == 0
        && Float.compare(getBoostWeight(), that.getBoostWeight()) == 0
        && Float.compare(getRuleOfThirdsWeight(), that.getRuleOfThirdsWeight()) == 0
        && isPrescale() == that.isPrescale() && getPrescaleSize() == that.getPrescaleSize()
        && isDebug() == that.isDebug() && Arrays.equals(getSkinColor(), that.getSkinColor())
        && Objects.equals(getPrescaleAlgorithm(), that.getPrescaleAlgorithm());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getDetailWeight(), getSkinBias(), getSkinBrightnessMin(),
        getSkinBrightnessMax(), getSkinThreshold(), getSkinWeight(), getSaturationBrightnessMin(),
        getSaturationBrightnessMax(), getSaturationThreshold(), getSaturationBias(),
        getSaturationWeight(), getScoreDownSample(), getCropSearchStep(), getScaleStep(),
        getMinScale(), getMaxScale(), getEdgeRadius(), getEdgeWeight(), getOutsideImportance(),
        getBoostWeight(), getRuleOfThirdsWeight(), isPrescale(), getPrescaleSize(),
        getPrescaleAlgorithm(), isDebug());
    result = 31 * result + Arrays.hashCode(getSkinColor());
    return result;
  }

  @Override
  public String toString() {
    return "DefaultSmartCropperConfiguration{" + "detailWeight=" + detailWeight + ", skinColor="
        + Arrays.toString(skinColor) + ", skinBias=" + skinBias + ", skinBrightnessMin="
        + skinBrightnessMin + ", skinBrightnessMax=" + skinBrightnessMax + ", skinThreshold="
        + skinThreshold + ", skinWeight=" + skinWeight + ", saturationBrightnessMin="
        + saturationBrightnessMin + ", saturationBrightnessMax=" + saturationBrightnessMax
        + ", saturationThreshold=" + saturationThreshold + ", saturationBias=" + saturationBias
        + ", saturationWeight=" + saturationWeight + ", scoreDownSample=" + scoreDownSample
        + ", cropSearchStep=" + cropSearchStep + ", scaleStep=" + scaleStep + ", minScale="
        + minScale + ", maxScale=" + maxScale + ", edgeRadius=" + edgeRadius + ", edgeWeight="
        + edgeWeight + ", outsideImportance=" + outsideImportance + ", boostWeight=" + boostWeight
        + ", ruleOfThirdsWeight=" + ruleOfThirdsWeight + ", prescale=" + prescale
        + ", prescaleSize=" + prescaleSize + ", prescaleAlgorithm=" + prescaleAlgorithm + ", debug="
        + debug + '}';
  }
}
