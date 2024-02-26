package com.sigpwned.smartcrop4j.util;

public final class Validation {

  private Validation() {
  }

  /**
   * Checks that the given value is not negative.
   *
   * @param value the value to check
   * @return the value
   * @throws IllegalArgumentException if the value is negative
   */
  public static int requireNonNegative(int value) {
    if (value < 0) {
      throw new IllegalArgumentException("value must be non-negative: " + value);
    }
    return value;
  }

  /**
   * Checks that the given value is not negative.
   *
   * @param value the value to check
   * @return the value
   * @throws IllegalArgumentException if the value is negative
   */
  public static float requireNonNegative(float value) {
    if (value < 0.0f) {
      throw new IllegalArgumentException("value must be non-negative: " + value);
    }
    return value;
  }

  /**
   * Checks that the given value is positive.
   *
   * @param value the value to check
   * @return the value
   * @throws IllegalArgumentException if the value is positive
   */
  public static int requirePositive(int value) {
    if (value <= 0) {
      throw new IllegalArgumentException("value must be positive: " + value);
    }
    return value;
  }

  /**
   * Checks that the given value is positive.
   *
   * @param value the value to check
   * @return the value
   * @throws IllegalArgumentException if the value is positive
   */
  public static float requirePositive(float value) {
    if (value <= 0.0f) {
      throw new IllegalArgumentException("value must be positive: " + value);
    }
    return value;
  }

  /**
   * Checks that the given value is not infinite and not NaN.
   *
   * @param value the value to check
   * @return the value
   * @throws IllegalArgumentException if the value is infinite or NaN.
   */
  public static float requireFinite(float value) {
    if (Float.isInfinite(value) || Float.isNaN(value)) {
      throw new IllegalArgumentException("value must be finite: " + value);
    }
    return value;
  }

  /**
   * Checks that the given value is between 0 and 1, inclusive.
   *
   * @param value the value to check
   * @return the value
   * @throws IllegalArgumentException if the value is not between 0 and 1, inclusive
   */
  public static float requireUnit(float value) {
    if (value < 0.0f || value > 1.0f) {
      throw new IllegalArgumentException("value must be in the range [0, 1]: " + value);
    }
    return value;
  }

  /**
   * Checks that the given color either (i) is null, or (ii) has 3 components, and each component is
   * between 0 and 1, inclusive.
   *
   * @param color the color to check
   * @return the color
   * @throws IllegalArgumentException if the color is not null and does not have 3 components, or if
   *                                  any component is not between 0 and 1, inclusive
   */
  public static float[] requireUnitColor(float[] color) {
    if (color == null) {
      return null;
    }
    if (color.length != 3) {
      throw new IllegalArgumentException("color must have 3 components: " + color.length);
    }
    for (int i = 0; i < 3; i++) {
      if (color[i] < 0.0f || color[i] > 1.0f) {
        throw new IllegalArgumentException(
            "color component must be in the range [0, 1]: " + color[i]);
      }
    }
    return color;
  }
}
