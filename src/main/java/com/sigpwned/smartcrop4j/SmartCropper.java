package com.sigpwned.smartcrop4j;

import java.awt.image.BufferedImage;

public interface SmartCropper {

  public CropResult crop(BufferedImage image);
}
