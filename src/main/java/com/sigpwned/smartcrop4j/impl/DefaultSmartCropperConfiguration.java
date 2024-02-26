package com.sigpwned.smartcrop4j.impl;

import java.awt.RenderingHints;

public class DefaultSmartCropperConfiguration {

  public static final DefaultSmartCropperConfiguration DEFAULT = new DefaultSmartCropperConfiguration();

  private final float detailWeight = 0.2f;

  /**
   * [ 0.78, 0.57, 0.44 ]
   */
  private final float[] skinColor = new float[]{0.78f, 0.57f, 0.44f};
  private final float skinBias = 0.01f;
  private final float skinBrightnessMin = 0.2f;
  private final float skinBrightnessMax = 1.0f;
  private final float skinThreshold = 0.8f;
  private final float skinWeight = 1.8f;
  private final float saturationBrightnessMin = 0.05f;
  private final float saturationBrightnessMax = 0.9f;
  private final float saturationThreshold = 0.4f;
  private final float saturationBias = 0.2f;
  private final float saturationWeight = 0.1f;
  // Step * minscale rounded down to the next power of two should be good
  private final int scoreDownSample = 8;
  private final int cropSearchStep = 8;
  private final float scaleStep = 0.1f;
  private final float minScale = 1.0f;
  private final float maxScale = 1.0f;
  private final float edgeRadius = 0.4f;
  private final float edgeWeight = -20.0f;
  private final float outsideImportance = -0.5f;
  private final float boostWeight = 100.0f;
  private final boolean ruleOfThirds = true;
  private final boolean prescale = true;
  private final int prescaleSize = 256;
  private final Object prescaleAlgorithm = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
  private final boolean debug = false;


  public DefaultSmartCropperConfiguration() {
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

  public boolean isRuleOfThirds() {
    return ruleOfThirds;
  }

  public boolean isPrescale() {
    return prescale;
  }

  public boolean isDebug() {
    return debug;
  }

  public int getPrescaleSize() {
    return prescaleSize;
  }

  public Object getPrescaleAlgorithm() {
    return prescaleAlgorithm;
  }
}
