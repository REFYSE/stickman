package stickman.model.engine;

import stickman.config.ConfigurationProvider;
import stickman.config.parser.LevelsParser;
import stickman.model.level.Level;
import stickman.model.level.LevelImpl;
import stickman.model.level.saving.Caretaker;
import stickman.model.level.saving.GameCaretaker;

import java.util.List;

/** The implementation class of the GameEngine interface. */
public class GameEngineImpl implements GameEngine {

  private ConfigurationProvider provider;
  private Level currentLevel;
  private Caretaker gameCaretaker;
  private List<String> levels;
  private int totalScore = 0;
  private int levelCounter = -1;

  private int savedScore = 0;
  private int savedLevelCounter = 0;

  public GameEngineImpl(String configPath) {
    levels = (new LevelsParser(configPath)).getLevels();
    gameCaretaker = new GameCaretaker(this);
    loadNextLevel();
  }

  @Override
  public Level getCurrentLevel() {
    return this.currentLevel;
  }

  @Override
  public void startLevel() {
    currentLevel.start(provider);
  }

  @Override
  public void loadNextLevel() {
    if(hasNextLevel()) {
      levelCounter++;
      provider = new ConfigurationProvider(levels.get(levelCounter));
      currentLevel = new LevelImpl(provider);
      startLevel();
    }
  }

  @Override
  public boolean hasNextLevel() {
    return levelCounter + 1 < levels.size();
  }


  @Override
  public boolean jump() {
    return currentLevel.jump();
  }

  @Override
  public boolean moveLeft() {
    return currentLevel.moveLeft();
  }

  @Override
  public boolean moveRight() {
    return currentLevel.moveRight();
  }

  @Override
  public boolean stopMoving() {
    return currentLevel.stopMoving();
  }

  @Override
  public void tick() {
    currentLevel.tick();
    if(currentLevel.isFinished()){
          totalScore += currentLevel.getScore();
        if(currentLevel.isVictory() && hasNextLevel()){
            loadNextLevel();
        }
    }
  }

  @Override
  public void save() {
    savedLevelCounter = levelCounter;
    savedScore = totalScore;
    gameCaretaker.save();
  }

  @Override
  public boolean restore() {
    boolean result = gameCaretaker.restore();
    if(result){
      levelCounter = savedLevelCounter;
      totalScore = savedScore;
    }
    return result;
  }

  @Override
  public int getTotalScore() {
    return totalScore;
  }
}
