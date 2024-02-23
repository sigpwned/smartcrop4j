package com.sigpwned.smartcrop4j;

import static com.sigpwned.smartcrop4j.util.Validation.requirePositive;
import static java.util.Collections.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CropOptions {

  public static class Builder {

    private float minScale = 1.0f;
    private float maxScale = 1.0f;
    private int width = 100;
    private int height = 100;
    private List<CropBoost> boosts = emptyList();
    private boolean ruleOfThirds = false;
    private boolean debug = false;

    public Builder() {
    }

    private Builder(CropOptions that) {
      this.minScale = that.minScale;
      this.maxScale = that.maxScale;
      this.width = that.width;
      this.height = that.height;
      this.boosts = that.boosts;
      this.ruleOfThirds = that.ruleOfThirds;
      this.debug = that.debug;
    }

    public Builder minScale(float minScale) {
      this.minScale = minScale;
      return this;
    }

    public Builder maxScale(float maxScale) {
      this.maxScale = maxScale;
      return this;
    }

    public Builder width(int width) {
      this.width = width;
      return this;
    }

    public Builder height(int height) {
      this.height = height;
      return this;
    }

    public Builder boosts(List<CropBoost> boosts) {
      this.boosts = Optional.ofNullable(boosts).map(Collections::unmodifiableList)
          .orElseGet(Collections::emptyList);
      return this;
    }

    public Builder ruleOfThirds(boolean ruleOfThirds) {
      this.ruleOfThirds = ruleOfThirds;
      return this;
    }

    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    public CropOptions build() {
      return new CropOptions(this);
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  public static CropOptions create() {
    return builder().build();
  }

  public static CropOptions of(float minScale, float maxScale, int width, int height,
      List<CropBoost> boosts, boolean ruleOfThirds, boolean debug) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  public static final CropOptions DEFAULT = create();

  private final float minScale;
  private final float maxScale;
  private final int width;
  private final int height;
  private final List<CropBoost> boosts;
  private final boolean ruleOfThirds;
  private final boolean debug;

  public CropOptions() {
    this(new Builder());
  }

  public CropOptions(float minScale, float maxScale, int width, int height, List<CropBoost> boosts,
      boolean ruleOfThirds, boolean debug) {
    this.minScale = requirePositive(minScale);
    this.maxScale = requirePositive(maxScale);
    this.width = requirePositive(width);
    this.height = requirePositive(height);
    this.boosts = Optional.ofNullable(boosts).map(Collections::unmodifiableList)
        .orElseGet(Collections::emptyList);
    this.ruleOfThirds = ruleOfThirds;
    this.debug = debug;
    if (minScale > maxScale) {
      throw new IllegalArgumentException("minScale must be less than or equal to maxScale");
    }
  }

  private CropOptions(Builder builder) {
    this(builder.minScale, builder.maxScale, builder.width, builder.height, builder.boosts,
        builder.ruleOfThirds, builder.debug);
  }

  public Builder toBuilder() {
    return new Builder(this);
  }

  public float getMinScale() {
    return minScale;
  }

  public CropOptions withMinScale(float minScale) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  public float getMaxScale() {
    return maxScale;
  }

  public CropOptions withMaxScale(float maxScale) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  public int getWidth() {
    return width;
  }

  public CropOptions withWidth(int width) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  public int getHeight() {
    return height;
  }

  public CropOptions withHeight(int height) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  public List<CropBoost> getBoosts() {
    return boosts;
  }

  public CropOptions withBoosts(List<CropBoost> boosts) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  public boolean getRuleOfThirds() {
    return ruleOfThirds;
  }

  public CropOptions withRuleOfThirds(boolean ruleOfThirds) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  public boolean getDebug() {
    return debug;
  }

  public CropOptions withDebug(boolean debug) {
    return new CropOptions(minScale, maxScale, width, height, boosts, ruleOfThirds, debug);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CropOptions)) {
      return false;
    }
    CropOptions that = (CropOptions) o;
    return Float.compare(getMinScale(), that.getMinScale()) == 0
        && Float.compare(getMaxScale(), that.getMaxScale()) == 0
        && getWidth() == that.getWidth() && getHeight() == that.getHeight()
        && getRuleOfThirds() == that.getRuleOfThirds() && getDebug() == that.getDebug()
        && Objects.equals(getBoosts(), that.getBoosts());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMinScale(), getMaxScale(), getWidth(), getHeight(), getBoosts(),
        getRuleOfThirds(), getDebug());
  }

  @Override
  public String toString() {
    return "CropOptions{" +
        "minScale=" + minScale +
        ", maxScale=" + maxScale +
        ", width=" + width +
        ", height=" + height +
        ", boosts=" + boosts +
        ", ruleOfThirds=" + ruleOfThirds +
        ", debug=" + debug +
        '}';
  }
}
