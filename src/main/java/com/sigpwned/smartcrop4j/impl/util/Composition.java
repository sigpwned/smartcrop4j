package com.sigpwned.smartcrop4j.impl.util;

import static com.sigpwned.smartcrop4j.util.Validation.requireNonNegative;
import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;
import static java.util.Objects.requireNonNull;

import com.sigpwned.smartcrop4j.Crop;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Heuristics for photo composition, because there are good and bad ways to compose a photo.
 */
public final class Composition {

  private Composition() {
  }

  /**
   * Controls how tall and wide the bell curve is for the rule of thirds heuristic, and how quickly
   * it falls off.
   */
  private static final float RULE_OF_THIRDS_SENSITIVITY = 16.0f;

  /**
   * Calculates a heuristic value for alignment with the rule of thirds. This function evaluates how
   * closely a given point (x) aligns with the rule of thirds, returning a higher value for points
   * closer to the thirds lines (1/3 and 2/3) and lower values for points further away. The function
   * uses a bell-shaped curve that peaks at the thirds lines and decreases quadratically with
   * distance from these points.
   *
   * @param x The normalized position of a point within a frame, where 0 <= x <= 1.
   * @return A heuristic value indicating the alignment with the rule of thirds, where 1 is
   * perfectly aligned, and 0 indicates no alignment.
   */
  public static float evaluateRuleOfThirds(float x) {
    // Step 1: Shift 'x' so that the 1/3 mark aligns with 0
    float shiftedX = x - 1.0f / 3.0f;

    // Step 2: Add 1 to ensure the value is positive (necessary for the modulo operation)
    float positiveShiftedX = shiftedX + 1.0f;

    // Step 3: Apply modulo 2 to create a repeating pattern every 2 units
    float wrappedX = positiveShiftedX % 2.0f;

    // Step 4: Scale and shift the wrapped value to center the peak of the bell curve at 0
    // The '* 0.5f' scales the pattern down, and the '- 0.5f' shifts the peak to 0
    float shiftedAndWrappedX = (wrappedX * 0.5f) - 0.5f;

    // Step 5: Scale the pattern to increase sensitivity around the thirds
    float scaledX = shiftedAndWrappedX * RULE_OF_THIRDS_SENSITIVITY;

    // Step 6: Apply a quadratic function to create a bell-shaped curve that peaks at the thirds
    // and ensure the result is non-negative
    float heuristicValue = Math.max(1.0f - scaledX * scaledX, 0.0f);

    return heuristicValue;
  }

  /**
   * Calculates the importance of a specific point within a given crop area, taking into account
   * factors such as distance from the edges, and adherence to the rule of thirds. Points outside
   * the crop area are assigned a specified importance value.
   *
   * @param crop              The crop area within which the importance of the point is evaluated.
   * @param x                 The x-coordinate of the point.
   * @param y                 The y-coordinate of the point.
   * @param outsideImportance The importance value assigned to points outside the crop area.
   * @param edgeRadius        The radius within which the edge effect starts influencing the
   *                          importance.
   * @param edgeWeight        The weight given to the distance from the edge in the importance
   *                          calculation.
   * @param ruleOfThirds      A boolean indicating whether the rule of thirds should be considered
   *                          in the calculation.
   * @return The calculated importance of the point, with considerations for edge proximity and the
   * rule of thirds.
   */
  public static float calculatePointImportance(Crop crop, int x, int y, float outsideImportance,
      float edgeRadius, float edgeWeight, boolean ruleOfThirds) {
    // Validate our inputs
    crop = requireNonNull(crop);
    x = requireNonNegative(x);
    y = requireNonNegative(y);
    edgeRadius = requireNonNegative(edgeRadius);

    // Check if the point is outside the crop area
    if (!crop.contains(x, y)) {
      return outsideImportance;
    }

    // Normalize the coordinates relative to the crop area
    float normalizedX = (x - crop.getX()) / (float) crop.getWidth();
    float normalizedY = (y - crop.getY()) / (float) crop.getHeight();

    // Calculate proximity to the center, scaled to [0, 1]
    float proximityToCenterX = Math.abs(0.5f - normalizedX) * 2.0f;
    float proximityToCenterY = Math.abs(0.5f - normalizedY) * 2.0f;

    // Calculate the weighted distance from the edge, considering the edge radius
    float distanceFromEdgeX = Math.max(proximityToCenterX - 1.0f + edgeRadius, 0.0f);
    float distanceFromEdgeY = Math.max(proximityToCenterY - 1.0f + edgeRadius, 0.0f);
    float edgeDistanceImportance =
        (distanceFromEdgeX * distanceFromEdgeX + distanceFromEdgeY * distanceFromEdgeY)
            * edgeWeight;

    // Base importance score, inversely related to distance from the center
    float baseImportance = 1.41f - (float) Math.sqrt(
        proximityToCenterX * proximityToCenterX + proximityToCenterY * proximityToCenterY);

    // Adjust the importance based on the rule of thirds, if applicable
    if (ruleOfThirds) {
      baseImportance += Math.max(0.0f, baseImportance + edgeDistanceImportance + 0.5f) * 1.2f * (
          evaluateRuleOfThirds(proximityToCenterX) + evaluateRuleOfThirds(proximityToCenterY));
    }

    // Total importance combines base importance and edge distance importance
    return baseImportance + edgeDistanceImportance;
  }

  /**
   * Generates a list of potential crop areas within an image, based on specified dimensions and
   * scale factors. Crops are generated at varying scales and positions, ensuring a comprehensive
   * search space for optimal cropping.
   *
   * @param imageWidth     The width of the original image.
   * @param imageHeight    The height of the original image.
   * @param cropWidth      The base width for crop areas before scaling.
   * @param cropHeight     The base height for crop areas before scaling.
   * @param minCropScale   The minimum scale factor to apply to crop dimensions.
   * @param maxCropScale   The maximum scale factor to apply to crop dimensions.
   * @param cropSearchStep The step size to use when moving the crop area across the image, for both
   *                       x and y directions.
   * @return A list of {@link Crop} objects representing potential crop areas within the image.
   */
  public static List<Crop> generateCandidateCrops(int imageWidth, int imageHeight, int cropWidth,
      int cropHeight, float minCropScale, float maxCropScale, float cropScaleStep,
      int cropSearchStep) {
    // Validate our inputs
    imageWidth = requirePositive(imageWidth);
    imageHeight = requirePositive(imageHeight);
    cropWidth = requirePositive(cropWidth);
    cropHeight = requirePositive(cropHeight);
    if (cropWidth > imageWidth || cropHeight > imageHeight) {
      throw new IllegalArgumentException("Crop dimensions must be smaller than image dimensions");
    }
    minCropScale = requirePositive(minCropScale);
    maxCropScale = requirePositive(maxCropScale);
    if (maxCropScale < minCropScale) {
      throw new IllegalArgumentException(
          "Max crop scale must be greater than or equal to min crop scale");
    }
    cropSearchStep = requirePositive(cropSearchStep);

    List<Crop> result = new ArrayList<>();

    // Iterate over possible scales, starting from the maximum, and decrementing by the crop search step size.
    for (float scale = maxCropScale; scale >= minCropScale; scale -= cropScaleStep) {
      int scaledWidth = (int) (cropWidth * scale);
      int scaledHeight = (int) (cropHeight * scale);

      // Skip scales that result in a zero dimension for either width or height.
      if (scaledWidth == 0 || scaledHeight == 0) {
        continue;
      }

      // Generate crops at this scale, covering the image area by moving the top-left corner of the crop.
      for (int y = 0; y + scaledHeight <= imageHeight; y += cropSearchStep) {
        for (int x = 0; x + scaledWidth <= imageWidth; x += cropSearchStep) {
          result.add(new Crop(x, y, scaledWidth, scaledHeight));
        }
      }
    }

    // Return the list of crop candidates, ensuring it cannot be modified.
    return Collections.unmodifiableList(result);
  }
}
