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

import com.sigpwned.smartcrop4j.Crop;
import com.sigpwned.smartcrop4j.CropResult;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class DefaultCropResult extends CropResult {

  private final BufferedImage debugImage;


  public DefaultCropResult(Crop topCrop) {
    this(topCrop, null);
  }

  public DefaultCropResult(Crop topCrop, BufferedImage debugImage) {
    super(topCrop);
    this.debugImage = debugImage;
  }

  public BufferedImage getDebugImage() {
    return debugImage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DefaultCropResult)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    DefaultCropResult that = (DefaultCropResult) o;
    return Objects.equals(getDebugImage(), that.getDebugImage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getDebugImage());
  }

  @Override
  public String toString() {
    return "DefaultCropResult{" +
        "debugImage=" + debugImage +
        "} " + super.toString();
  }
}
