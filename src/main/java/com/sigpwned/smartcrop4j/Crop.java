package com.sigpwned.smartcrop4j;

import static com.sigpwned.smartcrop4j.util.Validation.requireNonNegative;
import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;

import java.util.Objects;

public class Crop {

  private final int x;
  private final int y;
  private final int width;
  private final int height;

  public Crop(int x, int y, int width, int height) {
    this.x = requireNonNegative(x);
    this.y = requireNonNegative(y);
    this.width = requirePositive(width);
    this.height = requirePositive(height);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Crop)) {
      return false;
    }
    Crop crop = (Crop) o;
    return getX() == crop.getX() && getY() == crop.getY() && getWidth() == crop.getWidth()
        && getHeight() == crop.getHeight();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public String toString() {
    return "Crop{" +
        "x=" + x +
        ", y=" + y +
        ", width=" + width +
        ", height=" + height +
        '}';
  }

  public boolean contains(int x, int y) {
    return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
  }
}
