package com.sigpwned.smartcrop4j.impl;

public class DefaultSmartCropperConfiguration {

  private final int width = 0;
  private final int height = 0;
  private final float aspect = 0.0f;
  private final int cropWidth = 0;
  private final int cropHeight = 0;
  private final float detailWeight = 0.2f;
  /**
   * [ 0.78, 0.57, 0.44 ]
   */
  private final float[] skinColor = new float[]{ 0.78f, 0.57f, 0.44f };
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
  private final int step = 8;
  private final float scaleStep = 0.1f;
  private final float minScale = 1.0f;
  private final float maxScale = 1.0f;
  private final float edgeRadius = 0.4f;
  private final float edgeWeight = -20.0f;
  private final float outsideImportance = -0.5f;
  private final float boostWeight = 100.0f;
  private final boolean ruleOfThirds = true;
  private final boolean prescale = true;
  // private final  imageOperations;
  // private final canvasFactory;
  private final boolean debug = false;

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public float getAspect() {
    return aspect;
  }

  public int getCropWidth() {
    return cropWidth;
  }

  public int getCropHeight() {
    return cropHeight;
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

  public int getStep() {
    return step;
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
}
