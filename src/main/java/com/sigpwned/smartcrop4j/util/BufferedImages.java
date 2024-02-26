package com.sigpwned.smartcrop4j.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Optional;

public final class BufferedImages {

  private BufferedImages() {
  }

  /**
   * By default, use bilinear interpolation, which is a good compromise between speed and quality.
   */
  public static final Object DEFAULT_INTERPOLATION_STYLE = RenderingHints.VALUE_INTERPOLATION_BILINEAR;

  /**
   * Scales the given image to the given dimensions using the given type. Equivalent to calling
   * {@code scaled(image, newWidth, newHeight, type, null)}.
   *
   * @see #scaled(BufferedImage, int, int, int, Object)
   */
  public static BufferedImage scaled(BufferedImage image, int newWidth, int newHeight, int type) {
    return scaled(image, newWidth, newHeight, type, null);
  }

  /**
   * Scales the given image to the given dimensions using the given type and interpolation style.
   *
   * @param image              the image to scale
   * @param newWidth           the new width
   * @param newHeight          the new height
   * @param type               the type
   * @param interpolationStyle the interpolation style
   * @return the scaled image
   */
  public static BufferedImage scaled(BufferedImage image, int newWidth, int newHeight, int type,
      Object interpolationStyle) {
    final BufferedImage result = new BufferedImage(newWidth, newHeight, type);

    final Graphics2D g = result.createGraphics();
    try {
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          Optional.ofNullable(interpolationStyle).orElse(DEFAULT_INTERPOLATION_STYLE));
      g.drawImage(image, 0, 0, newWidth, newHeight, null);
    } finally {
      g.dispose();
    }

    return result;
  }
}
