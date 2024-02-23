package com.sigpwned.smartcrop4j;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class CropResult {

  private final Crop topCrop;

  public CropResult(Crop topCrop) {
    this.topCrop = requireNonNull(topCrop);
  }

  public Crop getTopCrop() {
    return topCrop;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CropResult)) {
      return false;
    }
    CropResult that = (CropResult) o;
    return Objects.equals(getTopCrop(), that.getTopCrop());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTopCrop());
  }

  @Override
  public String toString() {
    return "CropResult{" +
        "topCrop=" + topCrop +
        '}';
  }
}
