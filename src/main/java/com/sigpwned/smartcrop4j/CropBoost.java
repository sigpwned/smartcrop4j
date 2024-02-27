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
