package com.sigpwned.smartcrop4j.impl.util;

import com.sigpwned.smartcrop4j.CropBoost;
import com.sigpwned.smartcrop4j.impl.ImageData;
import com.sigpwned.smartcrop4j.util.MoreMath;
import java.util.List;

/**
 * Manual boosting by users, because sometimes the algorithm needs a little help.
 */
public final class Boosting {

  private Boosting() {
  }

  private static final int PIXEL_STRIDE = MoreImageData.PIXEL_STRIDE;

  private static final int AO = MoreImageData.AO;

  /**
   * Applies the given boosts to the given image. Output domain is clamped to [0, 255].
   *
   * @param o
   * @param boosts
   */
  public static void applyBoosts(ImageData o, List<CropBoost> boosts) {
    // TODO What on earth is this doing? Bug?
    for (CropBoost boost : boosts) {
      applyBoost(o, boost);
    }
  }

  /**
   * Boosts the alpha channel of the given image in the given rectangle by the given weight. The
   * boosted alpha channel is clamped to the range [0, 255].
   *
   * @param o     The image to boost
   * @param boost The boost to apply
   */
  public static void applyBoost(ImageData o, CropBoost boost) {
    int wi = o.width;
    int x0 = boost.getX();
    int x1 = x0 + boost.getWidth();
    int y0 = boost.getY();
    int y1 = y0 + boost.getHeight();
    float w = boost.getWeight() * 255.0f;
    for (int yi = y0; yi < y1; yi++) {
      for (int xi = x0; xi < x1; xi++) {
        var i = (yi * wi + xi) * PIXEL_STRIDE;
        o.data[i + AO] = MoreMath.clamp(o.data[i + AO] + w, 0.0f, 255.0f);
      }
    }
  }
}
