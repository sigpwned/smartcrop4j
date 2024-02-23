package com.sigpwned.smartcrop4j.impl.util;

/**
 * Heuristics for photo composition, because there are good and bad ways to compose a photo.
 */
public final class Composition {

  private Composition() {
  }

  public static final float RULE_OF_THIRDS_WEIGHT = 16.0f;

  /**
   * <p>
   * The rule of thirds is a principle in photography and visual arts that suggests that an image
   * should be divided into nine equal parts by two equally spaced horizontal lines and two equally
   * spaced vertical lines. Important compositional elements should be placed along these lines or
   * at their intersections to create more tension, energy, and interest in the composition compared
   * to simply centering the subject.
   *
   * <p>
   * This method provides a heuristic designed to quantify how well a particular point (given by its
   * x coordinate) aligns with the rule of thirds. The function manipulates the x coordinate in a
   * way that likely aims to give higher values (closer to 1) when x is near the thirds (either 1/3
   * or 2/3 of the way across the frame), and lower values (closer to 0) as x moves away from these
   * points. Step-by-step:
   *
   * <ol>
   * <li>
   * x - 1.0f / 3.0f + 1.0f: This shifts the x value so that one of the thirds (specifically, the
   * 1/3 mark) is moved to the origin (0 point), and then adds 1 to ensure the value is positive.
   * </li>
   * <li>
   * ((... % 2.0f) * 0.5f - 0.5f): The modulo operation with 2 ensures that the result wraps around
   * every 2 units, creating a repeating pattern. This is scaled down by 0.5 and then shifted by
   * -0.5 to center the peak of the pattern at 0. The pattern repeats every 2 units, so there will
   * be peaks at positions corresponding to the rule of thirds (1/3 and 2/3) due to the initial
   * shift and wrap-around effect.
   * </li>
   * <li>
   * * 16.0f: This scales the pattern up, increasing the sensitivity of the function around the
   * thirds. The weight is somewhat arbitrary and can be adjusted based on how sharply the
   * function should penalize deviations from the rule of thirds. Experimentally, 16.0f seems to
   * work well.
   * </li>
   * <li>
   * Math.max(1.0f - x * x, 0.0f): Finally, the function squares the scaled value (increasing the
   * penalty for being away from the thirds), subtracts it from 1 (inverting the curve so that
   * values near the thirds are higher), and ensures that the result is not negative (using Math.max
   * with 0).
   * </li>
   * </ol>
   *
   * <p>
   * In essence, this function is designed to produce a bell-shaped curve that peaks at points
   * corresponding to the rule of thirds, and the curve's width and height are manipulated through
   * the scaling and shifting operations. The closer an element is to a third, the higher the
   * heuristic value returned by the function, with the value decreasing quadratically as the
   * element moves away from the third.
   *
   * <p>
   * This heuristic could be used in image cropping algorithms to score potential crops based on how
   * well they align with the rule of thirds, favoring compositions where important elements are
   * near these thirds lines.
   *
   * @param x The coordinate of the point to evaluate.
   * @param w The weight to apply. Experimentally, 16.0f is a good value.
   * @return A float value representing the heuristic score for the given coordinate, where 1.0 is a
   * perfect score (on a third) and 0.0 is the worst score (far from a third).
   */
  public static float thirds(float x, float w) {
    x = (((x - 1.0f / 3.0f + 1.0f) % 2.0f) * 0.5f - 0.5f) * w;
    return Math.max(1.0f - x * x, 0.0f);
  }
}
