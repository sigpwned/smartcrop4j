package com.sigpwned.smartcrop4j.impl;

import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;

import java.util.Arrays;

public class ImageData {

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
}
