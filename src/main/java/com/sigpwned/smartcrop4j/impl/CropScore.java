package com.sigpwned.smartcrop4j.impl;

import java.util.Objects;

public class CropScore implements Comparable<CropScore> {

  private final float detail;
  private final float saturation;
  private final float skin;
  private final float boost;
  private final float total;

  public CropScore(float detail, float saturation, float skin, float boost, float total) {
    this.detail = detail;
    this.saturation = saturation;
    this.skin = skin;
    this.boost = boost;
    this.total = total;
  }

  public float getDetail() {
    return detail;
  }

  public float getSaturation() {
    return saturation;
  }

  public float getSkin() {
    return skin;
  }

  public float getBoost() {
    return boost;
  }

  public float getTotal() {
    return total;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CropScore)) {
      return false;
    }
    CropScore cropScore = (CropScore) o;
    return Float.compare(getDetail(), cropScore.getDetail()) == 0
        && Float.compare(getSaturation(), cropScore.getSaturation()) == 0
        && Float.compare(getSkin(), cropScore.getSkin()) == 0
        && Float.compare(getBoost(), cropScore.getBoost()) == 0
        && Float.compare(getTotal(), cropScore.getTotal()) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDetail(), getSaturation(), getSkin(), getBoost(), getTotal());
  }

  @Override
  public String toString() {
    return "CropScore{" +
        "detail=" + detail +
        ", saturation=" + saturation +
        ", skin=" + skin +
        ", boost=" + boost +
        ", total=" + total +
        '}';
  }

  @Override
  public int compareTo(CropScore that) {
    return Float.compare(this.getTotal(), that.getTotal());
  }
}
