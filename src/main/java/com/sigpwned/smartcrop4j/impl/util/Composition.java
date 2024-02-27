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
  private static final float RULE_OF_THIRDS_SENSITIVITY = 64.0f;

  /**
   * Given a value between 0 and 1 that represents the normalized position of a point within a
   * logical 2-by-2 frame centered at (0, 0), this method returns a heuristic value indicating the
   * value's alignment with the rule of thirds. A value of 1 indicates perfect alignment, and 0
   * indicates no alignment.
   *
   * @param x The normalized position of a point within a frame, where 0 <= x <= 1.
   * @return A heuristic value indicating the alignment with the rule of thirds, where 1 is
   * perfectly aligned, and 0 indicates no alignment.
   */
  public static float evaluateRuleOfThirds(float x) {
    // GOAL: We want a bell curve centered around 1/3.

    // Step 1: Shift 'x' so that the 1/3 mark aligns with 0
    float shiftedX = (x - 1.0f / 3.0f);

    // Step 2: Square the shifted value to create a bell curve
    float squaredX = shiftedX * shiftedX;

    // Step 3: Multiply by a weight value to create the desired shape. As an example, a weight of 64
    // will create a bell curve that equals -1 at roughly 0.2 and 0.45.
    float weightedX = squaredX * RULE_OF_THIRDS_SENSITIVITY;

    // Step 4: Add 1 to create a positive area under the curve around the 1/3 mark
    float heuristicValue = 1.0f - weightedX;

    // Step 5: Clamp the result to the range [0, 1]
    return Math.max(0.0f, Math.min(1.0f, heuristicValue));
  }

  private static final float SQRT2 = 1.4142135623730951f;

  /**
   * Calculates the importance of a specific point within a given crop area, taking into account
   * factors such as distance from the edges, and adherence to the rule of thirds. Points outside
   * the crop area are assigned a specified importance value.
   *
   * @param crop               The crop area within which the importance of the point is evaluated.
   * @param x                  The x-coordinate of the point.
   * @param y                  The y-coordinate of the point.
   * @param outsideImportance  The importance value assigned to points outside the crop area.
   * @param edgeRadius         The radius within which the edge effect starts influencing the
   *                           importance.
   * @param edgeWeight         The weight given to the distance from the edge in the importance
   *                           calculation. Set to 0 to disable edge proximity considerations.
   * @param ruleOfThirdsWeight The weight given to the rule of thirds in the importance calculation.
   *                           Set to 0 to disable rule of thirds considerations.
   * @return The calculated importance of the point, with considerations for edge proximity and the
   * rule of thirds.
   */
  public static float calculatePointImportance(Crop crop, int x, int y, float outsideImportance,
      float edgeRadius, float edgeWeight, float ruleOfThirdsWeight) {
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

    // Base importance score, inversely related to distance from the center. We use SQRT(2) here
    // because the maximum of the sum:
    //
    // proximityToCenterX * proximityToCenterX + proximityToCenterY * proximityToCenterY
    //
    // is 2, and we want to scale the result to [0, 1].
    float baseImportance = (SQRT2 - (float) Math.sqrt(
        proximityToCenterX * proximityToCenterX + proximityToCenterY * proximityToCenterY)) / SQRT2;

    // Calculate the edge distance importance, if desired
    float edgeDistanceImportance;
    if (edgeWeight > 0.0f) {
      float distanceFromEdgeX = Math.max(proximityToCenterX - 1.0f + edgeRadius, 0.0f);
      float distanceFromEdgeY = Math.max(proximityToCenterY - 1.0f + edgeRadius, 0.0f);
      float edgeRadius2 = edgeRadius * edgeRadius;
      edgeDistanceImportance = (edgeRadius2 - (float) Math.sqrt(
          distanceFromEdgeX * distanceFromEdgeX + distanceFromEdgeY * distanceFromEdgeY))
          / edgeRadius2;
    } else {
      edgeDistanceImportance = 0.0f;
    }

    // Calculate the rule of thirds importance, if desired
    float ruleOfThirdsImportance;
    if (ruleOfThirdsWeight > 0.0f) {
      ruleOfThirdsImportance =
          (evaluateRuleOfThirds(proximityToCenterX) + evaluateRuleOfThirds(proximityToCenterY))
              / 2.0f;
    } else {
      ruleOfThirdsImportance = 0.0f;
    }

    // Total importance combines base importance and edge distance importance
    return baseImportance + edgeDistanceImportance * edgeWeight
        + ruleOfThirdsImportance * ruleOfThirdsWeight;
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
