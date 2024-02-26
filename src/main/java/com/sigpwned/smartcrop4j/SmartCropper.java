package com.sigpwned.smartcrop4j;

import com.sigpwned.smartcrop4j.impl.DefaultCropResult;
import com.sigpwned.smartcrop4j.util.Validation;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Uses the content of an image to recommend a good crop of same.
 */
public interface SmartCropper {

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image       the image to crop
   * @param aspectRatio the aspect ratio of the crop, width:height
   * @return the recommended crop
   */
  default DefaultCropResult crop(BufferedImage image, float aspectRatio) {
    return crop(image, aspectRatio, null);
  }

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image       the image to crop
   * @param aspectRatio the aspect ratio of the crop, width:height
   * @param boosts      a list of boosts to apply to the crop
   * @return the recommended crop
   */
  default DefaultCropResult crop(BufferedImage image, float aspectRatio, List<CropBoost> boosts) {
    Validation.requirePositive(aspectRatio);
    int cropAspectWidth = 100;
    int cropAspectHeight = (int) Math.max(100.0f / aspectRatio, 1.0f);
    return crop(image, cropAspectWidth, cropAspectHeight, boosts);
  }

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image            the image to crop
   * @param cropAspectWidth  the width of the crop aspect ratio
   * @param cropAspectHeight the height of the crop aspect ratio
   * @return the recommended crop
   */
  default DefaultCropResult crop(BufferedImage image, int cropAspectWidth, int cropAspectHeight) {
    return crop(image, cropAspectWidth, cropAspectHeight, null);
  }

  /**
   * Recommend a crop of the given image of the given aspect ratio.
   *
   * @param image            the image to crop
   * @param cropAspectWidth  the width of the crop aspect ratio
   * @param cropAspectHeight the height of the crop aspect ratio
   * @param boosts           a list of boosts to apply to the crop
   * @return the recommended crop
   */
  public DefaultCropResult crop(BufferedImage image, int cropAspectWidth, int cropAspectHeight,
      List<CropBoost> boosts);
}
