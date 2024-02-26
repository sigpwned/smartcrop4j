package com.sigpwned.smartcrop4j.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class BufferedImages {

  private BufferedImages() {
  }

  private static final Object DEFAULT_INTERPOLATION_STYLE = RenderingHints.VALUE_INTERPOLATION_BILINEAR;

  /**
   * Scales the given image to the given dimensions using the given type.
   *
   * @param image     the image to scale
   * @param newWidth  the new width
   * @param newHeight the new height
   * @param type      the type
   * @return the scaled image
   */
  public static BufferedImage scaled(BufferedImage image, int newWidth, int newHeight, int type) {
    final BufferedImage result = new BufferedImage(newWidth, newHeight, type);

    final Graphics2D g = result.createGraphics();
    try {
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, DEFAULT_INTERPOLATION_STYLE);
      g.drawImage(image, 0, 0, newWidth, newHeight, null);
    } finally {
      g.dispose();
    }

    return result;
  }
}
