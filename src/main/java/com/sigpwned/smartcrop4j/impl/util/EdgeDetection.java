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
package com.sigpwned.smartcrop4j.impl.util;

import com.sigpwned.smartcrop4j.impl.ImageData;

/**
 * Hueristics for edge detection, because good crops do not split objects.
 */
public final class EdgeDetection {

  private EdgeDetection() {
  }

  private static final int PIXEL_STRIDE = MoreImageData.PIXEL_STRIDE;

  private static final int GO = MoreImageData.GO;

  /**
   * Detect edges in the input image and write the result to the green channel of the output image.
   *
   * @param i Input image
   * @param o Output image
   */
  public static void edgeDetect(ImageData i, ImageData o) {
    if (o.width != i.width || o.height != i.height) {
      throw new IllegalArgumentException(
          "output image must have the same dimensions as the input image");
    }
    var w = i.width;
    var h = i.height;

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int pos = (y * w + x) * PIXEL_STRIDE;

        float brightness;
        if (x == 0 || x >= w - 1 || y == 0 || y >= h - 1) {
          brightness = MoreImageData.brightness(i, pos);
        } else {
          brightness = MoreImageData.brightness(i, pos) * 4 -
              MoreImageData.brightness(i, pos - w * PIXEL_STRIDE) -
              MoreImageData.brightness(i, pos - PIXEL_STRIDE) -
              MoreImageData.brightness(i, pos + PIXEL_STRIDE) -
              MoreImageData.brightness(i, pos + w * PIXEL_STRIDE);
        }

        o.data[pos + GO] = brightness;
      }
    }
  }
}
