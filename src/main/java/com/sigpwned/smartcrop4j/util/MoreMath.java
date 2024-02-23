package com.sigpwned.smartcrop4j.util;

public final class MoreMath {

  private MoreMath() {
  }

  public static float clamp(float x, float min, float max) {
    return Math.max(min, Math.min(max, x));
  }
}
