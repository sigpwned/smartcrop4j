package com.sigpwned.smartcrop4j.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultSmartCropperTest {

  public static BufferedImage testImage1;

  @BeforeClass
  public static void setupDefaultSmartCropperTestClass() throws IOException {
    testImage1 = ImageIO.read(DefaultSmartCropper.class.getResource("test.jpeg"));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndDefaultConfig_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.create();

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{32, 8, 229, 229}));
  }

  @Test
  public void givenTestImage1AndLandscapeAspectRatioAndDefaultConfig_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.create();

    DefaultCropResult crop = unit.crop(testImage1, 100, 50);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{0, 8, 344, 171}));
  }

  @Test
  public void givenTestImage1AndPortraitAspectRatioAndDefaultConfig_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.create();

    DefaultCropResult crop = unit.crop(testImage1, 50, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{48, 0, 114, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargePositiveSkinWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.builder().setSkinWeight(1000000.0f)
        .buildDefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{32, 8, 229, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargeNegativeSkinWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.builder().setSkinWeight(-1000000.0f)
        .buildDefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{120, 0, 229, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargePositiveSaturationWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.builder().setSaturationWeight(1000000.0f)
        .buildDefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{40, 0, 255, 255}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargeNegativeSaturationWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.builder().setSaturationWeight(-1000000.0f)
        .buildDefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{120, 16, 229, 229}));
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenAnyImageAndZeroAspectRatioAndAnyConfig_whenSmartCropImage_thenThrowIllegalArgumentException()
      throws IOException {
    DefaultSmartCropper unit = DefaultSmartCropper.create();

    unit.crop(testImage1, 0, 0);
  }
}
