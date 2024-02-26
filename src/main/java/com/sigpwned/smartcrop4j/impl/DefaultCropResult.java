package com.sigpwned.smartcrop4j.impl;

import com.sigpwned.smartcrop4j.Crop;
import com.sigpwned.smartcrop4j.CropResult;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class DefaultCropResult extends CropResult {

  private final BufferedImage debugImage;


  public DefaultCropResult(Crop topCrop) {
    this(topCrop, null);
  }

  public DefaultCropResult(Crop topCrop, BufferedImage debugImage) {
    super(topCrop);
    this.debugImage = debugImage;
  }

  public BufferedImage getDebugImage() {
    return debugImage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DefaultCropResult)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    DefaultCropResult that = (DefaultCropResult) o;
    return Objects.equals(getDebugImage(), that.getDebugImage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getDebugImage());
  }

  @Override
  public String toString() {
    return "DefaultCropResult{" +
        "debugImage=" + debugImage +
        "} " + super.toString();
  }
}
