package stickman.config;

import org.json.simple.JSONObject;
import stickman.model.level.scoring.ScoreStrategy;

/** The configuration containing all user-defined settings. */
public class LevelSettings {

  private JSONObject entityData;
  private double width;
  private double floorHeight;
  private int targetTime;
  private ScoreStrategy scoreStrategy;

  public LevelSettings(JSONObject entityData, double width, double floorHeight, int targetTime, ScoreStrategy scoreStrategy) {
    this.entityData = entityData;
    this.width = width;
    this.floorHeight = floorHeight;
    this.targetTime = targetTime;
    this.scoreStrategy = scoreStrategy;
  }

  /**
   * Get the stored Entity data.
   *
   * @return a JSONObject containing Entity data.
   */
  public JSONObject getEntityData() {
    return entityData;
  }

  /**
   * Get the level's floor height.
   *
   * @return the floor height.
   */
  public double getFloorHeight() {
    return floorHeight;
  }

  /**
   * Get the level's width.
   *
   * @return the width.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Returns the level's target time
   *
   * @return the target time in seconds
   */
  public int getTargetTime() {
    return targetTime;
  }

  /**
   * Retruns the level's score strategy
   *
   * @return the level's score strategy
   */
  public ScoreStrategy getScoreStrategy(){
    return scoreStrategy;
  }
}
