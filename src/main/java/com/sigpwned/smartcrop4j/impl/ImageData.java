package com.sigpwned.smartcrop4j.impl;

import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageData {

  /**
   * Creates an ImageData from the given BufferedImage. A pixel format of
   * BufferedImage.TYPE_INT_ARGB is preferred but not required. The float values are in the range
   * [0, 255].
   *
   * @param image The BufferedImage to convert
   * @return The ImageData
   */
  public static ImageData fromBufferedImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    float[] data = new float[width * height * PIXEL_STRIDE];
    int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
    for (int i = 0; i < pixels.length; i++) {
      int pixel = pixels[i];
      data[i * PIXEL_STRIDE + RO] = (float) ((pixel >>> 16) & 0xff);
      data[i * PIXEL_STRIDE + GO] = (float) ((pixel >>> 8) & 0xff);
      data[i * PIXEL_STRIDE + BO] = (float) ((pixel >>> 0) & 0xff);
      data[i * PIXEL_STRIDE + AO] = (float) ((pixel >>> 24) & 0xff);
    }
    return new ImageData(width, height, data);
  }

  public static final int PIXEL_STRIDE = 4;
  public static final int RO = 0;

  public static final int GO = 1;

  public static final int BO = 2;

  public static final int AO = 3;

  public final int width;
  public final int height;

  /**
   * format: [r1, g1, b1, a1, r2, g2, b2, a2, ...]
   */
  public final float[] data;

  public ImageData(int width, int height) {
    this(width, height, null);
  }

  public ImageData(int width, int height, float[] data) {
    this.width = requirePositive(width);
    this.height = requirePositive(height);
    if (data != null) {
      if (data.length != width * height * PIXEL_STRIDE) {
        throw new IllegalArgumentException("data.length must be width * height * 4");
      }
      this.data = Arrays.copyOf(data, data.length);
    } else {
      this.data = new float[width * height * PIXEL_STRIDE];
    }
  }

  /**
   * Converts the ImageData to a BufferedImage. Assumes the float values are in the range [0, 255].
   */
  public BufferedImage toBufferedImage() {
    BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    int[] pixels = new int[width * height];
    for (int i = 0; i < pixels.length; i++) {
      int pos = i * PIXEL_STRIDE;
      int r = Math.min(255, Math.max(0, Math.round(data[pos + RO])));
      int g = Math.min(255, Math.max(0, Math.round(data[pos + GO])));
      int b = Math.min(255, Math.max(0, Math.round(data[pos + BO])));
      int a = 255; //Math.min(255, Math.max(0, Math.round(data[pos + AO])));
      pixels[i] = (a << 24) | (r << 16) | (g << 8) | (b << 0);
    }
    result.setRGB(0, 0, width, height, pixels, 0, width);
    return result;
  }
}
