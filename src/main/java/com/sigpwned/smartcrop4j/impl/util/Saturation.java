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
import com.sigpwned.smartcrop4j.util.Colorspaces;

/**
 * Heuristics involving the saturation of an image, because humans find bright colors interesting.
 */
public final class Saturation {

  private Saturation() {
  }

  private static final int PIXEL_STRIDE = MoreImageData.PIXEL_STRIDE;

  private static final int RO = MoreImageData.RO;

  private static final int GO = MoreImageData.GO;

  private static final int BO = MoreImageData.BO;

  /**
   * Computes the saturation of the input image and writes the result to the blue channel of the
   * output image. Output domain is [0, 255].
   *
   * @param i
   * @param o
   * @param saturationThreshold
   * @param saturationBrightnessMin
   * @param saturationBrightnessMax
   */
  public static void saturationDetect(ImageData i, ImageData o, float saturationThreshold,
      float saturationBrightnessMin, float saturationBrightnessMax) {
    if (o.width != i.width || o.height != i.height) {
      throw new IllegalArgumentException(
          "output image must have the same dimensions as the input image");
    }
    var w = i.width;
    var h = i.height;

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        var pos = (y * w + x) * PIXEL_STRIDE;

        float posr = i.data[pos + RO];
        float posg = i.data[pos + GO];
        float posb = i.data[pos + BO];

        float brightness = Colorspaces.brightness(posr, posg, posb);
        float saturation = Colorspaces.saturation(posr, posg, posb);

        boolean acceptableSaturation = saturation > saturationThreshold;
        boolean acceptableLightness =
            brightness >= saturationBrightnessMin && brightness <= saturationBrightnessMax;

        float saturatedness;
        if (acceptableLightness && acceptableSaturation) {
          // Normalize the saturation to the interval [0, 255]
          saturatedness =
              (saturation - saturationThreshold) * (255.0f / (1.0f - saturationThreshold));
        } else {
          saturatedness = 0.0f;
        }

        o.data[pos + BO] = saturatedness;
      }
    }
  }
}
