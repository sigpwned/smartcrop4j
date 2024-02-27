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

import java.util.Objects;

public class Crop {

  private final int x;
  private final int y;
  private final int width;
  private final int height;

  public Crop(int x, int y, int width, int height) {
    this.x = requireNonNegative(x);
    this.y = requireNonNegative(y);
    this.width = requirePositive(width);
    this.height = requirePositive(height);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Crop)) {
      return false;
    }
    Crop crop = (Crop) o;
    return getX() == crop.getX() && getY() == crop.getY() && getWidth() == crop.getWidth()
        && getHeight() == crop.getHeight();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public String toString() {
    return "Crop{" +
        "x=" + x +
        ", y=" + y +
        ", width=" + width +
        ", height=" + height +
        '}';
  }

  public boolean contains(int x, int y) {
    return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
  }
}
