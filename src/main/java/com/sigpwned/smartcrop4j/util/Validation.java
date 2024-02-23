package com.sigpwned.smartcrop4j.util;

public final class Validation {

  private Validation() {
  }

  public static int requireNonNegative(int value) {
    if (value < 0) {
      throw new IllegalArgumentException("value must be non-negative: " + value);
    }
    return value;
  }

  public static float requireNonNegative(float value) {
    if (value < 0.0f) {
      throw new IllegalArgumentException("value must be non-negative: " + value);
    }
    return value;
  }

  public static int requirePositive(int value) {
    if (value <= 0) {
      throw new IllegalArgumentException("value must be positive: " + value);
    }
    return value;
  }

  public static float requirePositive(float value) {
    if (value <= 0.0f) {
      throw new IllegalArgumentException("value must be positive: " + value);
    }
    return value;
  }

  public static double requireFinite(double value) {
    if (Double.isInfinite(value) || Double.isNaN(value)) {
      throw new IllegalArgumentException("value must be finite: " + value);
    }
    return value;
  }

  public static float requireUnit(float value) {
    if (value < 0.0f || value > 1.0f) {
      throw new IllegalArgumentException("value must be in the range [0, 1]: " + value);
    }
    return value;
  }
}
