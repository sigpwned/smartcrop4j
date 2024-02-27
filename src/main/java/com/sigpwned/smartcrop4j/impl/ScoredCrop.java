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
