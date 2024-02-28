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

import static com.sigpwned.smartcrop4j.util.Validation.requireFinite;
import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;

import com.sigpwned.smartcrop4j.impl.DefaultSmartCropper;
import com.sigpwned.smartcrop4j.impl.DefaultSmartCropperOptions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Smartcrop {

  private Smartcrop() {
  }

  /**
   * Crops the given image file to the given width and height using the default smart cropper. The
   * returned image is of the same type and file format as the original image. The returned file is
   * a temporary file that the caller should delete when it is no longer needed. Equivalent to
   * calling
   * {@code crop(DefaultSmartCropperConfiguration.create(), originalImageFile, width, height)}.
   *
   * @param originalImageFile the original image file
   * @param aspectRatio       the crop aspect ratio
   * @return the cropped image file
   * @throws IOException if the original image file cannot be read or the cropped image file cannot
   *                     be written
   */
  public static File crop(File originalImageFile, float aspectRatio) throws IOException {
    return crop(DefaultSmartCropperOptions.create(), originalImageFile, aspectRatio);
  }

  /**
   * Crops the given image file to the given width and height using the default smart cropper. The
   * returned image is of the same type and file format as the original image. The returned file is
   * a temporary file that the caller should delete when it is no longer needed. Equivalent to
   * calling
   * {@code crop(DefaultSmartCropperConfiguration.create(), originalImageFile, width, height)}.
   *
   * @param originalImageFile the original image file
   * @param width             the crop width
   * @param height            the crop height
   * @return the cropped image file
   * @throws IOException if the original image file cannot be read or the cropped image file cannot
   *                     be written
   */
  public static File crop(File originalImageFile, int width, int height) throws IOException {
    return crop(DefaultSmartCropperOptions.create(), originalImageFile, width, height);
  }

  /**
   * Crops the given image file to the given width and height using the given smart cropper. The
   * returned image is of the same type and file format as the original image. The returned file is
   * a temporary file that the caller should delete when it is no longer needed.
   *
   * @param configuration     the smart cropper configuration
   * @param originalImageFile the original image file
   * @param aspectRatio       the crop aspect ratio
   * @return the cropped image file
   * @throws IOException if the original image file cannot be read or the cropped image file cannot
   *                     be written
   */
  public static File crop(DefaultSmartCropperOptions configuration, File originalImageFile,
      float aspectRatio) throws IOException {
    String fileBasename = MoreFiles.getFileBasename(originalImageFile);
    String fileExtension = MoreFiles.getFileExtension(originalImageFile)
        .orElseThrow(() -> new IllegalArgumentException("originalImageFile must have extension"));

    BufferedImage originalImage = ImageIO.read(originalImageFile);
    if (originalImage == null) {
      throw new IllegalArgumentException("originalImageFile must contain a valid image");
    }

    BufferedImage croppedImage = crop(configuration, originalImage, aspectRatio);

    File result = null;
    File croppedImageFile = File.createTempFile(fileBasename + ".", ".cropped." + fileExtension);
    try {
      ImageIO.write(croppedImage, fileExtension, croppedImageFile);
      result = croppedImageFile;
    } finally {
      if (result == null) {
        croppedImageFile.delete();
      }
    }

    return result;
  }


  /**
   * Crops the given image file to the given width and height using the given smart cropper. The
   * returned image is of the same type and file format as the original image. The returned file is
   * a temporary file that the caller should delete when it is no longer needed.
   *
   * @param configuration     the smart cropper configuration
   * @param originalImageFile the original image file
   * @param width             the width
   * @param height            the height
   * @return the cropped image file
   * @throws IOException if the original image file cannot be read or the cropped image file cannot
   *                     be written
   */
  public static File crop(DefaultSmartCropperOptions configuration, File originalImageFile,
      int width, int height) throws IOException {
    String fileBasename = MoreFiles.getFileBasename(originalImageFile);
    String fileExtension = MoreFiles.getFileExtension(originalImageFile)
        .orElseThrow(() -> new IllegalArgumentException("originalImageFile must have extension"));

    BufferedImage originalImage = ImageIO.read(originalImageFile);
    if (originalImage == null) {
      throw new IllegalArgumentException("originalImageFile must contain a valid image");
    }

    BufferedImage croppedImage = crop(configuration, originalImage, width, height);

    File result = null;
    File croppedImageFile = File.createTempFile(fileBasename + ".", ".cropped." + fileExtension);
    try {
      ImageIO.write(croppedImage, fileExtension, croppedImageFile);
      result = croppedImageFile;
    } finally {
      if (result == null) {
        croppedImageFile.delete();
      }
    }

    return result;
  }

  /**
   * Crops the given image to the given aspect ratio using the default smart cropper. The returned
   * image is of the same type as the original image. Equivalent to calling
   * {@code crop(DefaultSmartCropperConfiguration.create(), image, aspectRatio)}.
   *
   * @param image       the image to crop
   * @param aspectRatio the aspect ratio of the crop
   * @return the cropped image
   */
  public static BufferedImage crop(BufferedImage image, float aspectRatio) {
    return crop(DefaultSmartCropperOptions.create(), image, aspectRatio);
  }

  /**
   * Crops the given image to the given aspect ratio using the given smart crop configuration. The
   * returned image is of the same type as the original image.
   *
   * @param configuration the smart cropper configuration
   * @param image         the image to crop
   * @param aspectRatio   the aspect ratio of the crop
   * @return the cropped image
   */
  public static BufferedImage crop(DefaultSmartCropperOptions configuration,
      BufferedImage image, float aspectRatio) {
    aspectRatio = requireFinite(requirePositive(aspectRatio));

    final int imageWidth = image.getWidth();
    final int imageHeight = image.getHeight();

    final int cropWidth, cropHeight;
    if (aspectRatio < 1) {
      // "tall" crop, i.e. portrait
      cropWidth = (int) Math.max(Math.min(imageHeight * aspectRatio, imageWidth), 1.0f);
      cropHeight = (int) Math.max(cropWidth / aspectRatio, 1.0f);
    } else {
      // "wide" crop, i.e. landscape, or square
      cropHeight = (int) Math.max(Math.min(imageWidth / aspectRatio, imageHeight), 1.0f);
      cropWidth = (int) Math.max(cropHeight * aspectRatio, 1.0f);
    }

    return BufferedImages.cropped(image,
        new DefaultSmartCropper(configuration).crop(image, aspectRatio).getTopCrop(), cropWidth,
        cropHeight);
  }

  /**
   * Crops the given image to the given width and height using the default smart cropper. The
   * returned image is of the same type as the original image. Equivalent to calling
   * {@code crop(DefaultSmartCropperConfiguration.create(), image, width, height)}.
   *
   * @param image  the image to crop
   * @param width  the width of the crop
   * @param height the height of the crop
   * @return the cropped image
   */
  public static BufferedImage crop(BufferedImage image, int width, int height) {
    return crop(DefaultSmartCropperOptions.create(), image, width, height);
  }


  /**
   * Crops the given image to the given width and height using the given smart cropper
   * configuration. The returned image is of the same type as the original image.
   *
   * @param configuration the smart cropper configuration
   * @param image         the image to crop
   * @param width         the width of the crop
   * @param height        the height of the crop
   * @return the cropped image
   */
  public static BufferedImage crop(DefaultSmartCropperOptions configuration,
      BufferedImage image, int width, int height) {
    return BufferedImages.cropped(image,
        new DefaultSmartCropper(configuration).crop(image, width, height).getTopCrop(), width,
        height);
  }
}
