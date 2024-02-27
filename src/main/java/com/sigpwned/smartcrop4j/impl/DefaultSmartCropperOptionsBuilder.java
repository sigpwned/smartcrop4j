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

import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Objects;

public class DefaultSmartCropperOptionsBuilder {

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

  public DefaultSmartCropperOptionsBuilder() {
  }

  public DefaultSmartCropperOptionsBuilder(DefaultSmartCropperOptions that) {
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

  public DefaultSmartCropperOptionsBuilder setDetailWeight(float detailWeight) {
    this.detailWeight = detailWeight;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSkinColor(float[] skinColor) {
    this.skinColor = skinColor;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSkinBias(float skinBias) {
    this.skinBias = skinBias;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSkinBrightnessMin(float skinBrightnessMin) {
    this.skinBrightnessMin = skinBrightnessMin;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSkinBrightnessMax(float skinBrightnessMax) {
    this.skinBrightnessMax = skinBrightnessMax;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSkinThreshold(float skinThreshold) {
    this.skinThreshold = skinThreshold;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSkinWeight(float skinWeight) {
    this.skinWeight = skinWeight;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSaturationBrightnessMin(
      float saturationBrightnessMin) {
    this.saturationBrightnessMin = saturationBrightnessMin;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSaturationBrightnessMax(
      float saturationBrightnessMax) {
    this.saturationBrightnessMax = saturationBrightnessMax;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSaturationThreshold(float saturationThreshold) {
    this.saturationThreshold = saturationThreshold;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSaturationBias(float saturationBias) {
    this.saturationBias = saturationBias;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setSaturationWeight(float saturationWeight) {
    this.saturationWeight = saturationWeight;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setScoreDownSample(int scoreDownSample) {
    this.scoreDownSample = scoreDownSample;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setCropSearchStep(int cropSearchStep) {
    this.cropSearchStep = cropSearchStep;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setScaleStep(float scaleStep) {
    this.scaleStep = scaleStep;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setMinScale(float minScale) {
    this.minScale = minScale;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setMaxScale(float maxScale) {
    this.maxScale = maxScale;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setEdgeRadius(float edgeRadius) {
    this.edgeRadius = edgeRadius;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setEdgeWeight(float edgeWeight) {
    this.edgeWeight = edgeWeight;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setOutsideImportance(float outsideImportance) {
    this.outsideImportance = outsideImportance;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setBoostWeight(float boostWeight) {
    this.boostWeight = boostWeight;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setRuleOfThirdsWeight(float ruleOfThirdsWeight) {
    this.ruleOfThirdsWeight = ruleOfThirdsWeight;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setPrescale(boolean prescale) {
    this.prescale = prescale;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setPrescaleSize(int prescaleSize) {
    this.prescaleSize = prescaleSize;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setPrescaleAlgorithm(Object prescaleAlgorithm) {
    this.prescaleAlgorithm = prescaleAlgorithm;
    return this;
  }

  public DefaultSmartCropperOptionsBuilder setDebug(boolean debug) {
    this.debug = debug;
    return this;
  }

  public DefaultSmartCropperOptions build() {
    return new DefaultSmartCropperOptions(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DefaultSmartCropperOptionsBuilder)) {
      return false;
    }
    DefaultSmartCropperOptionsBuilder that = (DefaultSmartCropperOptionsBuilder) o;
    return Float.compare(getDetailWeight(), that.getDetailWeight()) == 0
        && Float.compare(getSkinBias(), that.getSkinBias()) == 0
        && Float.compare(getSkinBrightnessMin(), that.getSkinBrightnessMin()) == 0
        && Float.compare(getSkinBrightnessMax(), that.getSkinBrightnessMax()) == 0
        && Float.compare(getSkinThreshold(), that.getSkinThreshold()) == 0
        && Float.compare(getSkinWeight(), that.getSkinWeight()) == 0
        && Float.compare(getSaturationBrightnessMin(), that.getSaturationBrightnessMin())
        == 0
        && Float.compare(getSaturationBrightnessMax(), that.getSaturationBrightnessMax())
        == 0 && Float.compare(getSaturationThreshold(), that.getSaturationThreshold()) == 0
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
    return "DefaultSmartCropperConfigurationBuilder{" +
        "detailWeight=" + detailWeight +
        ", skinColor=" + Arrays.toString(skinColor) +
        ", skinBias=" + skinBias +
        ", skinBrightnessMin=" + skinBrightnessMin +
        ", skinBrightnessMax=" + skinBrightnessMax +
        ", skinThreshold=" + skinThreshold +
        ", skinWeight=" + skinWeight +
        ", saturationBrightnessMin=" + saturationBrightnessMin +
        ", saturationBrightnessMax=" + saturationBrightnessMax +
        ", saturationThreshold=" + saturationThreshold +
        ", saturationBias=" + saturationBias +
        ", saturationWeight=" + saturationWeight +
        ", scoreDownSample=" + scoreDownSample +
        ", cropSearchStep=" + cropSearchStep +
        ", scaleStep=" + scaleStep +
        ", minScale=" + minScale +
        ", maxScale=" + maxScale +
        ", edgeRadius=" + edgeRadius +
        ", edgeWeight=" + edgeWeight +
        ", outsideImportance=" + outsideImportance +
        ", boostWeight=" + boostWeight +
        ", ruleOfThirdsWeight=" + ruleOfThirdsWeight +
        ", prescale=" + prescale +
        ", prescaleSize=" + prescaleSize +
        ", prescaleAlgorithm=" + prescaleAlgorithm +
        ", debug=" + debug +
        '}';
  }
}
