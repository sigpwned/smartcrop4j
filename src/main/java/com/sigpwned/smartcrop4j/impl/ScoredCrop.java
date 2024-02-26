package com.sigpwned.smartcrop4j.impl;

import static java.util.Objects.requireNonNull;

import com.sigpwned.smartcrop4j.Crop;
import java.util.Objects;

public class ScoredCrop extends Crop implements Comparable<ScoredCrop> {

  private final CropScore score;

  public ScoredCrop(int x, int y, int width, int height, CropScore score) {
    super(x, y, width, height);
    this.score = requireNonNull(score);
  }

  public CropScore getScore() {
    return score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ScoredCrop)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ScoredCrop that = (ScoredCrop) o;
    return Objects.equals(getScore(), that.getScore());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getScore());
  }

  @Override
  public String toString() {
    return "ScoredCrop{" +
        "score=" + score +
        "} " + super.toString();
  }

  @Override
  public int compareTo(ScoredCrop that) {
    return this.getScore().compareTo(that.getScore());
  }
}
