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
