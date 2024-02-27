package com.sigpwned.smartcrop4j.impl;

import java.awt.RenderingHints;

public class DefaultSmartCropperBuilder {

  private float detailWeight = 0.2f;
  private float[] skinColor = new float[]{0.78f, 0.57f, 0.44f};
  private float skinBias = 0.01f;
  private float skinBrightnessMin = 0.2f;
  private float skinBrightnessMax = 1.0f;
  private float skinThreshold = 0.4f;
  private float skinWeight = 1.8f;
  private float saturationBrightnessMin = 0.05f;
  private float saturationBrightnessMax = 0.9f;
  private float saturationThreshold = 0.4f;
  private float saturationBias = 0.2f;
  private float saturationWeight = 0.1f;
  private int scoreDownSample = 8;
  private int cropSearchStep = 8;
  private float scaleStep = 0.1f;
  private float minScale = 0.8f;
  private float maxScale = 1.0f;
  private float edgeRadius = 0.4f;
  private float edgeWeight = -20.0f;
  private float outsideImportance = -0.5f;
  private float boostWeight = 100.0f;
  private float ruleOfThirdsWeight = 5.0f;
  private boolean prescale = true;
  private int prescaleSize = 256;
  private Object prescaleAlgorithm = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
  private boolean debug = true;

  public DefaultSmartCropperBuilder() {
  }

  public DefaultSmartCropperBuilder(DefaultSmartCropper that) {
    setDetailWeight(that.getDetailWeight());
    setSkinColor(that.getSkinColor());
    setSkinBias(that.getSkinBias());
    setSkinBrightnessMin(that.getSkinBrightnessMin());
    setSkinBrightnessMax(that.getSkinBrightnessMax());
    setSkinThreshold(that.getSkinThreshold());
    setSkinWeight(that.getSkinWeight());
    setSaturationBrightnessMin(that.getSaturationBrightnessMin());
    setSaturationBrightnessMax(that.getSaturationBrightnessMax());
    setSaturationThreshold(that.getSaturationThreshold());
    setSaturationBias(that.getSaturationBias());
    setSaturationWeight(that.getSaturationWeight());
    setScoreDownSample(that.getScoreDownSample());
    setCropSearchStep(that.getCropSearchStep());
    setScaleStep(that.getScaleStep());
    setMinScale(that.getMinScale());
    setMaxScale(that.getMaxScale());
    setEdgeRadius(that.getEdgeRadius());
    setEdgeWeight(that.getEdgeWeight());
    setOutsideImportance(that.getOutsideImportance());
    setBoostWeight(that.getBoostWeight());
    setRuleOfThirdsWeight(that.getRuleOfThirdsWeight());
    setPrescale(that.isPrescale());
    setPrescaleSize(that.getPrescaleSize());
    setPrescaleAlgorithm(that.getPrescaleAlgorithm());
    setDebug(that.isDebug());
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

  public DefaultSmartCropperBuilder setDetailWeight(float detailWeight) {
    this.detailWeight = detailWeight;
    return this;
  }

  public DefaultSmartCropperBuilder setSkinColor(float[] skinColor) {
    this.skinColor = skinColor;
    return this;
  }

  public DefaultSmartCropperBuilder setSkinBias(float skinBias) {
    this.skinBias = skinBias;
    return this;
  }

  public DefaultSmartCropperBuilder setSkinBrightnessMin(float skinBrightnessMin) {
    this.skinBrightnessMin = skinBrightnessMin;
    return this;
  }

  public DefaultSmartCropperBuilder setSkinBrightnessMax(float skinBrightnessMax) {
    this.skinBrightnessMax = skinBrightnessMax;
    return this;
  }

  public DefaultSmartCropperBuilder setSkinThreshold(float skinThreshold) {
    this.skinThreshold = skinThreshold;
    return this;
  }

  public DefaultSmartCropperBuilder setSkinWeight(float skinWeight) {
    this.skinWeight = skinWeight;
    return this;
  }

  public DefaultSmartCropperBuilder setSaturationBrightnessMin(float saturationBrightnessMin) {
    this.saturationBrightnessMin = saturationBrightnessMin;
    return this;
  }

  public DefaultSmartCropperBuilder setSaturationBrightnessMax(float saturationBrightnessMax) {
    this.saturationBrightnessMax = saturationBrightnessMax;
    return this;
  }

  public DefaultSmartCropperBuilder setSaturationThreshold(float saturationThreshold) {
    this.saturationThreshold = saturationThreshold;
    return this;
  }

  public DefaultSmartCropperBuilder setSaturationBias(float saturationBias) {
    this.saturationBias = saturationBias;
    return this;
  }

  public DefaultSmartCropperBuilder setSaturationWeight(float saturationWeight) {
    this.saturationWeight = saturationWeight;
    return this;
  }

  public DefaultSmartCropperBuilder setScoreDownSample(int scoreDownSample) {
    this.scoreDownSample = scoreDownSample;
    return this;
  }

  public DefaultSmartCropperBuilder setCropSearchStep(int cropSearchStep) {
    this.cropSearchStep = cropSearchStep;
    return this;
  }

  public DefaultSmartCropperBuilder setScaleStep(float scaleStep) {
    this.scaleStep = scaleStep;
    return this;
  }

  public DefaultSmartCropperBuilder setMinScale(float minScale) {
    this.minScale = minScale;
    return this;
  }

  public DefaultSmartCropperBuilder setMaxScale(float maxScale) {
    this.maxScale = maxScale;
    return this;
  }

  public DefaultSmartCropperBuilder setEdgeRadius(float edgeRadius) {
    this.edgeRadius = edgeRadius;
    return this;
  }

  public DefaultSmartCropperBuilder setEdgeWeight(float edgeWeight) {
    this.edgeWeight = edgeWeight;
    return this;
  }

  public DefaultSmartCropperBuilder setOutsideImportance(float outsideImportance) {
    this.outsideImportance = outsideImportance;
    return this;
  }

  public DefaultSmartCropperBuilder setBoostWeight(float boostWeight) {
    this.boostWeight = boostWeight;
    return this;
  }

  public DefaultSmartCropperBuilder setRuleOfThirdsWeight(float ruleOfThirdsWeight) {
    this.ruleOfThirdsWeight = ruleOfThirdsWeight;
    return this;
  }

  public DefaultSmartCropperBuilder setPrescale(boolean prescale) {
    this.prescale = prescale;
    return this;
  }

  public DefaultSmartCropperBuilder setPrescaleSize(int prescaleSize) {
    this.prescaleSize = prescaleSize;
    return this;
  }

  public DefaultSmartCropperBuilder setPrescaleAlgorithm(Object prescaleAlgorithm) {
    this.prescaleAlgorithm = prescaleAlgorithm;
    return this;
  }

  public DefaultSmartCropperBuilder setDebug(boolean debug) {
    this.debug = debug;
    return this;
  }

  public DefaultSmartCropper buildDefaultSmartCropper() {
    return new DefaultSmartCropper(this);
  }
}