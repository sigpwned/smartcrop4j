package com.sigpwned.smartcrop4j.impl.util;

import com.sigpwned.smartcrop4j.Crop;

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
  public static float calculateImportance(Crop crop, int x, int y, float outsideImportance,
      float edgeRadius, float edgeWeight, boolean ruleOfThirds) {
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
}
