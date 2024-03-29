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

public final class Colorspaces {

  private Colorspaces() {
  }

  /**
   * Compute luma, or brightness, of the given RGB color. The formula used is the Rec. 709 luma
   * formula: Y = 0.2126 R + 0.7152 G + 0.0722 B. Note that the magnitude of the output value is the
   * same as the magnitude of the channel input values. That is, if the input values are in the
   * range [0-255], then the output will be in the range [0-255], and if the input values are in the
   * range [0-1], then the output will be in the range [0-1].
   *
   * @see <a
   * href="https://en.wikipedia.org/wiki/Rec._709#Luma_coefficients">https://en.wikipedia.org/wiki/Rec._709#Luma_coefficients</a>
   */
  public static float brightness(float r, float g, float b) {
    return 0.2126f * r + 0.7152f * g + 0.0722f * b;
  }

  /**
   * Calculates the similarity between two RGB colors based on their hue, assuming the first RGB
   * color vector is of unit length. The method normalizes the second RGB color vector to unit
   * length, then computes the Euclidean distance between the first RGB color vector and the
   * normalized second RGB color vector. The similarity score is inversely related to this distance,
   * with a score closer to 1 indicating higher similarity.
   *
   * @param refr  The red component of the first RGB color vector, which must be part of a unit
   *              length vector.
   * @param refg  The green component of the first RGB color vector, which must be part of a unit
   *              length vector.
   * @param refb  The blue component of the first RGB color vector, which must be part of a unit
   *              length vector.
   * @param sampr The red component of the second RGB color vector.
   * @param sampg The green component of the second RGB color vector.
   * @param sampb The blue component of the second RGB color vector.
   * @return A float value representing the similarity between the two RGB colors, where 1.0
   * indicates identical hues and a value closer to 0 indicates more dissimilarity.
   */
  public static float similarity(float refr, float refg, float refb, float sampr, float sampg,
      float sampb) {
    float sampmag = (float) Math.sqrt(sampr * sampr + sampg * sampg + sampb * sampb);
    float rdist = sampr / sampmag - refr;
    float gdist = sampg / sampmag - refg;
    float bdist = sampb / sampmag - refb;
    float dist = (float) Math.sqrt(rdist * rdist + gdist * gdist + bdist * bdist);
    return 1.0f - dist;
  }

  /**
   * Calculates the saturation of the given RGB color. The method first normalizes the RGB color to
   * the range [0, 1], then computes the maximum and minimum values of the normalized RGB color
   * components. The method then computes the lightness of the color, and uses the lightness to
   * determine the saturation. The saturation is computed as the difference between the maximum and
   * minimum values of the normalized RGB color components, divided by the sum of the maximum and
   * minimum values. The saturation is then returned as a float value in the range [0, 1].
   *
   * @param r The red component of the RGB color. Assumed to be in the range [0, 255].
   * @param g The green component of the RGB color. Assumed to be in the range [0, 255].
   * @param b The blue component of the RGB color. Assumed to be in the range [0, 255].
   * @return A float value representing the saturation of the RGB color, where 0 indicates no
   * saturation and 1 indicates full saturation.
   */
  public static float saturation(float r, float g, float b) {
    float r255 = r / 255.0f;
    float g255 = g / 255.0f;
    float b255 = b / 255.0f;

    float maximum = Math.max(Math.max(r255, g255), b255);
    float minimum = Math.min(Math.min(r255, g255), b255);

    if (maximum == minimum) {
      return 0.0f;
    }

    float l = (maximum + minimum) / 2.0f;
    float d = maximum - minimum;

    return l > 0.5f ? d / (2.0f - maximum - minimum) : d / (maximum + minimum);
  }
}
