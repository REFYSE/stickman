package stickman.config.parser;

import org.json.simple.JSONObject;
import stickman.config.ConfigurationUtils;
import stickman.config.LevelSettings;
import stickman.model.level.scoring.ScoreStrategy;
import stickman.model.level.scoring.TimedStrategy;

/** Used for parsing Level configuration data */
public class LevelSettingsParser {

  /**
   * Parses a LevelSettings object from JSON configuration.
   *
   * @param configObject the JSONObject to parse.
   * @return the created LevelSettings object.
   * @throws IllegalArgumentException if the user-defined configuration is invalid.
   */
  public LevelSettings parse(JSONObject configObject) {

    // set defaults
    JSONObject entityData = null;
    double width = 1000.0;
    double floorHeight = 350.0;
    int targetTime = 60;
    String scoreStrategy = "Timed";

    // check that settings data exists
    JSONObject settingsData = ConfigurationUtils.toJSONObject(configObject.get("settings"));
    if (settingsData == null) {
      System.out.println("Error: Configuration file does not contain level settings information.");
      System.out.println("Please refer to the documentation regarding configuration.");
      throw new IllegalArgumentException("Invalid configuration file");
    }

    // check that entity data exists
    JSONObject newEntityData = ConfigurationUtils.toJSONObject(configObject.get("entities"));
    if (newEntityData == null) {
      System.out.println("Error: Configuration file does not contain level entity information.");
      System.out.println("Please refer to the documentation regarding configuration.");
      throw new IllegalArgumentException("Invalid configuration file");
    } else {
      entityData = newEntityData;
    }

    // parse from settings data

    Double newWidth = ConfigurationUtils.parseDoubleFromObject(settingsData, "width");
    if (newWidth != null) {
      width = newWidth;
    }

    Double newFloorHeight = ConfigurationUtils.parseDoubleFromObject(settingsData, "floorHeight");
    if (newFloorHeight != null) {
      floorHeight = newFloorHeight;
    }

    Integer newTargetTime = ConfigurationUtils.parseIntegerFromObject(settingsData, "targetTime");
    if(newTargetTime != null){
      targetTime = newTargetTime;
    }

    String newScoreStrategy = ConfigurationUtils.parseStringFromObject(settingsData, "scoreStrategy");
    if(newScoreStrategy != null){
      scoreStrategy = newScoreStrategy;
    }
    ScoreStrategy strategy;
    switch (scoreStrategy.toUpperCase()) {
      case "TIMED":
        strategy = new TimedStrategy();
        break;
      default:
        throw new IllegalArgumentException("Score strategy \"" + scoreStrategy + "\" does not exist.");
    }

    return new LevelSettings(entityData, width, floorHeight, targetTime, strategy);
  }
}
