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

import com.sigpwned.smartcrop4j.impl.DefaultCropResult;
import com.sigpwned.smartcrop4j.util.Validation;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Uses the content of an image to recommend a good crop of same.
 */
public interface SmartCropper {

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image       the image to crop
   * @param aspectRatio the aspect ratio of the crop, width:height
   * @return the recommended crop
   */
  default DefaultCropResult crop(BufferedImage image, float aspectRatio) {
    return crop(image, aspectRatio, null);
  }

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image       the image to crop
   * @param aspectRatio the aspect ratio of the crop, width:height
   * @param boosts      a list of boosts to apply to the crop
   * @return the recommended crop
   */
  default DefaultCropResult crop(BufferedImage image, float aspectRatio, List<CropBoost> boosts) {
    Validation.requirePositive(aspectRatio);
    int cropAspectWidth = 100;
    int cropAspectHeight = (int) Math.max(100.0f / aspectRatio, 1.0f);
    return crop(image, cropAspectWidth, cropAspectHeight, boosts);
  }

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image            the image to crop
   * @param cropAspectWidth  the width of the crop aspect ratio
   * @param cropAspectHeight the height of the crop aspect ratio
   * @return the recommended crop
   */
  default DefaultCropResult crop(BufferedImage image, int cropAspectWidth, int cropAspectHeight) {
    return crop(image, cropAspectWidth, cropAspectHeight, null);
  }

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image            the image to crop
   * @param cropAspectWidth  the width of the crop aspect ratio
   * @param cropAspectHeight the height of the crop aspect ratio
   * @param boosts           a list of boosts to apply to the crop
   * @return the recommended crop
   */
  public DefaultCropResult crop(BufferedImage image, int cropAspectWidth, int cropAspectHeight,
      List<CropBoost> boosts);
}
