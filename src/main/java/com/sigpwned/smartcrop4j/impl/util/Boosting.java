package com.sigpwned.smartcrop4j.impl.util;

import com.sigpwned.smartcrop4j.impl.ImageData;
import com.sigpwned.smartcrop4j.util.MoreMath;

/**
 * Manual boosting by users, because sometimes the algorithm needs a little help.
 */
public final class Boosting {

  private Boosting() {
  }

  private static final int PIXEL_STRIDE = MoreImageData.PIXEL_STRIDE;

  private static final int AO = MoreImageData.AO;

  /**
   * Boosts the alpha channel of the given image in the given rectangle by the given weight. The
   * boosted alpha channel is clamped to the range [0, 255].
   *
   * @param o      The image to boost
   * @param x      The x-coordinate of the top-left corner of the rectangle to boost
   * @param y      The y-coordinate of the top-left corner of the rectangle to boost
   * @param width  The width of the rectangle to boost
   * @param height The height of the rectangle to boost
   * @param weight The weight to boost the alpha channel by
   */
  public static void boost(ImageData o, int x, int y, int width, int height, float weight) {
    int wi = o.width;
    int x0 = x;
    int x1 = x0 + width;
    int y0 = y;
    int y1 = y0 + height;
    float w = weight * 255.0f;
    for (int yi = y0; yi < y1; yi++) {
      for (int xi = x0; xi < x1; xi++) {
        var i = (yi * wi + xi) * PIXEL_STRIDE;
        o.data[i + AO] = MoreMath.clamp(o.data[i + AO] + w, 0.0f, 255.0f);
      }
    }
  }
}
