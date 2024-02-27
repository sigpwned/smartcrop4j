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
package com.sigpwned.smartcrop4j.util;

import com.sigpwned.smartcrop4j.Crop;
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
   * Scales the given image to the given dimensions using the given type. The returned image is of
   * the same type as the input. Equivalent to calling
   * {@code scaled(image, newWidth, newHeight, image.getType())}.
   *
   * @see #scaled(BufferedImage, int, int, int)
   */
  public static BufferedImage scaled(BufferedImage image, int newWidth, int newHeight) {
    return scaled(image, newWidth, newHeight, image.getType());
  }


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

  /**
   * Crops the given image to the given region. The returned image is of the same type as the input.
   * Equivalent to calling {@code cropped(image, region, image.getType())}.
   *
   * @see #cropped(BufferedImage, Crop, int, int)
   */
  public static BufferedImage cropped(BufferedImage image, Crop region) {
    return cropped(image, region, region.getWidth(), region.getHeight());
  }

  /**
   * Crops the given image to the given region and scales it to the given dimensions using the
   * default interpolation style. The returned image is of the same type as the input. Equivalent to
   * calling {@code cropped(image, region, newWidth, newHeight, image.getType())}.
   *
   * @see #cropped(BufferedImage, Crop, int, int, int)
   * @see #DEFAULT_INTERPOLATION_STYLE
   */
  public static BufferedImage cropped(BufferedImage image, Crop region, int newWidth,
      int newHeight) {
    return cropped(image, region, newWidth, newHeight, image.getType());
  }

  /**
   * Crops the given image to the given region and scales it to the given dimensions using the
   * default interpolation style. The returned image is of the given type. Equivalent to calling
   * {@code cropped(image, region, newWidth, newHeight, newType, null)}.
   *
   * @see #cropped(BufferedImage, Crop, int, int, int)
   * @see #DEFAULT_INTERPOLATION_STYLE
   */
  public static BufferedImage cropped(BufferedImage image, Crop region, int newWidth, int newHeight,
      int newType) {
    return cropped(image, region, newWidth, newHeight, newType, null);
  }

  /**
   * Crops the given image to the given region and scales it to the given dimensions using the given
   * interpolation style. The returned image is of the given type.
   *
   * @param image              the image to crop
   * @param region             the region to crop
   * @param newWidth           the new width
   * @param newHeight          the new height
   * @param newType            the new type
   * @param interpolationStyle the interpolation style
   * @return the cropped and scaled image
   */
  public static BufferedImage cropped(BufferedImage image, Crop region, int newWidth, int newHeight,
      int newType, Object interpolationStyle) {
    final BufferedImage result = new BufferedImage(newWidth, newHeight, newType);

    final Graphics2D g = result.createGraphics();
    try {
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          Optional.ofNullable(interpolationStyle).orElse(DEFAULT_INTERPOLATION_STYLE));
      g.drawImage(image, region.getX(), region.getY(), region.getWidth(), region.getHeight(), null);
    } finally {
      g.dispose();
    }

    return result;
  }
}
