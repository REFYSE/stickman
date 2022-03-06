package stickman.model.engine;

import stickman.model.level.Level;

/** The GameEngine interface */
public interface GameEngine {

  /**
   * Returns the currently active level.
   *
   * @return The current level.
   */
  Level getCurrentLevel();

  /** Starts the current level */
  void startLevel();

  /** Loads the next level */
  void loadNextLevel();

  /**
   * Checks if the game has a next level
   *
   * @return true if a next level exists, else false
   */
  boolean hasNextLevel();

  /**
   * Calls the current level's jump method.
   *
   * @return True if the hero will jump, else false.
   */
  boolean jump();

  /**
   * Calls the current level's moveLeft method.
   *
   * @return True if the hero will move left, else false.
   */
  boolean moveLeft();

  /**
   * Calls the current level's moveRight method.
   *
   * @return True if the hero will move right, else false.
   */
  boolean moveRight();

  /**
   * Calls the current level's stopMoving method.
   *
   * @return True if the hero will cease all movement, else false.
   */
  boolean stopMoving();

  /** Calls the current level's tick method. */
  void tick();

  /** Saves the state of the current level */
  void save();

  /**
   * Restores the state of the current level
   *
   * @return true if successful, else false
   */
  boolean restore();

  /**
   * Gets the total score of all levels so far
   *
   * @return total score
   */
  int getTotalScore();

}
