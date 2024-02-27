package com.sigpwned.smartcrop4j;

import static com.sigpwned.smartcrop4j.util.Validation.requireNonNegative;
import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;
import static com.sigpwned.smartcrop4j.util.Validation.requireUnit;

public class CropBoost {

  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final float weight;

  /**
   * Create a new crop boost.
   *
   * @param x      the x-coordinate of the region to boost
   * @param y      the y-coordinate of the region to boost
   * @param width  the width of the region to boost
   * @param height the height of the region to boost
   * @param weight the weight of the boost, 0.0 to 1.0
   */
  public CropBoost(int x, int y, int width, int height, float weight) {
    this.x = requireNonNegative(x);
    this.y = requireNonNegative(y);
    this.width = requirePositive(width);
    this.height = requirePositive(height);
    this.weight = requireUnit(weight);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public float getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CropBoost)) {
      return false;
    }
    CropBoost cropBoost = (CropBoost) o;
    return getX() == cropBoost.getX() &&
        getY() == cropBoost.getY() &&
        getWidth() == cropBoost.getWidth() &&
        getHeight() == cropBoost.getHeight() &&
        Double.compare(cropBoost.getWeight(), getWeight()) == 0;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = getX();
    result = 31 * result + getY();
    result = 31 * result + getWidth();
    result = 31 * result + getHeight();
    temp = Double.doubleToLongBits(getWeight());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "CropBoost{" +
        "x=" + x +
        ", y=" + y +
        ", width=" + width +
        ", height=" + height +
        ", weight=" + weight +
        '}';
  }
}
