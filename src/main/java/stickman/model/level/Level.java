package stickman.model.level;

import java.time.Instant;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.model.entity.Entity;
import stickman.model.level.saving.LevelMemento;
import stickman.model.level.saving.Memento;
import stickman.model.level.scoring.ScoreStrategy;

/** The Level interface */
public interface Level {

  /**
   * Starts the level running
   *
   * @param provider the ConfigurationProvider
   */
  void start(ConfigurationProvider provider);

  /**
   * Stops and completes the level
   *
   * @param outcome how the player finished the level (e.g. DEATH, FLAG)
   */
  void finish(String outcome);

  /**
   * Get a list of all Entities assigned to the level
   *
   * @return the level's entities
   */
  List<Entity> getEntities();

  /**
   * Get the level's size along the x-axis.
   *
   * @return the level's width
   */
  double getWidth();

  /**
   * Get the level's floor height.
   *
   * @return the level's floor height
   */
  double getFloorHeight();

  /**
   * Get the hero's current x-axis position.
   *
   * @return the hero's x value
   */
  double getHeroX();

  /**
   * Get the hero's current y-axis position.
   *
   * @return the hero's y value
   */
  double getHeroY();

  /**
   * Get the target time in seconds
   *
   * @return target time
   */
  int getTargetTime();

  /**
   * Get the current score for the level
   *
   * @return current score for the level
   */
  int getScore();

  /**
   * Sets the score strategy for the level
   *
   * @param strategy the score strategy
   */
  void setScoreStrategy(ScoreStrategy strategy);

  /**
   * Get the Instant in which start() was called
   *
   * @return the game's start Instant
   */
  Instant getStartTime();

  /**
   * Sets the start time of the level
   *
   * @param startTime the instant to set the start time to
   */
  void setStartTime(Instant startTime);

  /**
   * Checks if the level is finished
   *
   * @return true if finished, else false
   */
  boolean isFinished();

  /**
   * Checks if the level finished in a victory
   *
   * @return true if finished and victory, else false
   */
  boolean isVictory();

  /**
   * Attempt to make the hero jump.
   *
   * @return True if the hero will jump, else false
   */
  boolean jump();

  /**
   * Attempt to move the hero left.
   *
   * @return True if the hero will move left, else false
   */
  boolean moveLeft();

  /**
   * Attempt to move the hero right.
   *
   * @return True if the hero will move right, else false
   */
  boolean moveRight();

  /**
   * Attempt to stop the hero's movement.
   *
   * @return True if the hero will cease all movement, else false
   */
  boolean stopMoving();

  /**
   * Creates a memento storing the current state
   *
   * @return the memento
   */
  Memento save();

  /**
   * Restores a previous state using a given memento
   *
   * @param save the memento storing the previous state
   * @return true if successfully restores to previous state, else false
   */
  boolean restore(Memento save);

  /** Updates the position of all entities in the level. */
  void tick();
}
