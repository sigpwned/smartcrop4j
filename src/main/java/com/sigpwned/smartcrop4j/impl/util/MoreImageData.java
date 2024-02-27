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

public final class MoreImageData {

  private MoreImageData() {
  }

  public static final int PIXEL_STRIDE = ImageData.PIXEL_STRIDE;

  public static final int RO = ImageData.RO;

  public static final int GO = ImageData.GO;

  public static final int BO = ImageData.BO;

  public static final int AO = ImageData.AO;

  /**
   * Scales down the image by the indicated constant integer factor.
   *
   * @param input  the input image
   * @param factor the factor to scale down by
   * @return the scaled down image
   */
  public static ImageData scaledDown(ImageData input, int factor) {
    final float[] idata = input.data;
    final int iwidth = input.width;
    final int iheight = input.height;
    final int owidth = Math.max(iwidth / factor, 1);
    final int oheight = Math.max(iheight / factor, 1);
    final float[] odata = new float[owidth * oheight * ImageData.PIXEL_STRIDE];
    final float ifactor2 = 1.0f / (factor * factor);

    for (int y = 0; y < oheight; y++) {
      for (int x = 0; x < owidth; x++) {
        int pos = (y * owidth + x) * PIXEL_STRIDE;

        float r = 0.0f;
        float g = 0.0f;
        float b = 0.0f;
        float a = 0.0f;

        float mr = 0.0f;
        float mg = 0.0f;
        float mb = 0.0f;

        for (int v = 0; v < factor; v++) {
          for (int u = 0; u < factor; u++) {
            int j = ((y * factor + v) * iwidth + (x * factor + u)) * PIXEL_STRIDE;

            float rj = idata[j + RO];
            float gj = idata[j + GO];
            float bj = idata[j + BO];
            float aj = idata[j + AO];

            r += rj;
            g += gj;
            b += bj;
            a += aj;

            mr = Math.max(mr, rj);
            mg = Math.max(mg, gj);
            mb = Math.max(mb, bj);
          }
        }

        // this is some funky magic to preserve detail a bit more for
        // skin (r) and detail (g). Saturation (b) does not get this boost.
        odata[pos + RO] = r * ifactor2 * 0.5f + mr * 0.5f;
        odata[pos + GO] = g * ifactor2 * 0.7f + mg * 0.3f;
        odata[pos + BO] = b * ifactor2;
        odata[pos + AO] = a * ifactor2;
      }
    }
    return new ImageData(owidth, oheight, odata);
  }

  /**
   * Computes the luma, or brightness, of the indicated pixel. Output domain matches image color
   * domain.
   *
   * @see Colorspaces#brightness(float, float, float)
   */
  public static float brightness(ImageData image, int pos) {
    return Colorspaces.brightness(image.data[pos + RO], image.data[pos + GO],
        image.data[pos + BO]);
  }
}
