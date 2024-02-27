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
package com.sigpwned.smartcrop4j.impl;

import static java.util.Collections.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.sigpwned.smartcrop4j.CropBoost;
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
    DefaultSmartCropper unit = new DefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{40, 0, 229, 229}));
  }

  @Test
  public void givenTestImage1AndLandscapeAspectRatioAndDefaultConfig_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 100, 50);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{0, 32, 344, 171}));
  }

  @Test
  public void givenTestImage1AndPortraitAspectRatioAndDefaultConfig_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 50, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{56, 0, 114, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndDefaultConfigAndBoosts_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper();

    DefaultCropResult crop = unit.crop(testImage1, 100, 100,
        singletonList(new CropBoost(testImage1.getWidth() - 64, 0, 64, 64, 1.0f)));

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{152, 0, 229, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargePositiveSkinWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper(
        DefaultSmartCropperOptions.builder().setSkinWeight(1000000.0f).build());

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{40, 0, 229, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargeNegativeSkinWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper(
        DefaultSmartCropperOptions.builder().setSkinWeight(-1000000.0f).build());

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{152, 0, 229, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargePositiveSaturationWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper(
        DefaultSmartCropperOptions.builder().setSaturationWeight(1000000.0f).build());

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{48, 24, 229, 229}));
  }

  @Test
  public void givenTestImage1AndSquareAspectRatioAndLargeNegativeSaturationWeight_whenSmartCropImage_thenReceiveExpectedCrop()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper(
        DefaultSmartCropperOptions.builder().setSaturationWeight(-1000000.0f).build());

    DefaultCropResult crop = unit.crop(testImage1, 100, 100);

    assertThat(
        new int[]{crop.getTopCrop().getX(), crop.getTopCrop().getY(), crop.getTopCrop().getWidth(),
            crop.getTopCrop().getHeight()}, is(new int[]{152, 0, 229, 229}));
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenAnyImageAndZeroAspectRatioAndAnyConfig_whenSmartCropImage_thenThrowIllegalArgumentException()
      throws IOException {
    DefaultSmartCropper unit = new DefaultSmartCropper();

    unit.crop(testImage1, 0, 0);
  }
}
