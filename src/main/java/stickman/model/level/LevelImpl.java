package stickman.model.level;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import stickman.config.ConfigurationProvider;
import stickman.config.LevelSettings;
import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.entity.spawner.EntitySpawner;
import stickman.model.entity.spawner.EntitySpawnerImpl;
import stickman.model.level.collision.CollisionHandler;
import stickman.model.level.saving.LevelMemento;
import stickman.model.level.saving.Memento;
import stickman.model.level.scoring.ScoreStrategy;

/** The implementation class of the Level interface. */
public class LevelImpl implements Level {

  private EntitySpawner entitySpawner;
  private CollisionHandler collisionHandler;

  private List<Entity> entities;
  private Controllable hero;

  private double width;
  private double floorHeight;
  private Instant startTime;
  private boolean finished = false;
  private boolean victory = false;
  private int targetTime;

  private ScoreStrategy scoreStrategy;

  public LevelImpl(ConfigurationProvider provider) {

    this.entitySpawner = new EntitySpawnerImpl();
    this.collisionHandler = new CollisionHandler();
    this.entities = new ArrayList<>();

    LevelSettings levelData = provider.getLevelData();
    this.width = levelData.getWidth();
    this.floorHeight = levelData.getFloorHeight();
    this.targetTime = levelData.getTargetTime();
    setScoreStrategy(levelData.getScoreStrategy());
  }

  @Override
  public void start(ConfigurationProvider provider) {

    spawnHero(provider);
    spawnEntities(provider);
    populateScene(provider);

    startTime = Instant.now();
  }

  @Override
  public void finish(String outcome) {
    finished = true;
    victory = !"DEATH".equals(outcome.toUpperCase());
  }

  @Override
  public List<Entity> getEntities() {
    return this.entities;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getFloorHeight() {
    return this.floorHeight;
  }

  @Override
  public double getHeroX() {
    return hero.getXPos();
  }

  @Override
  public double getHeroY() {
    return hero.getYPos();
  }

  @Override
  public int getTargetTime() {
    return targetTime;
  }

  @Override
  public int getScore() {
    return scoreStrategy.getScore();
  }

  @Override
  public void setScoreStrategy(ScoreStrategy strategy) {
    this.scoreStrategy = strategy;
    strategy.setLevel(this);
  }

  @Override
  public Instant getStartTime() {
    return startTime;
  }

  @Override
  public void setStartTime(Instant startTime) {
    this.startTime = startTime;
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  @Override
  public boolean isVictory() {
    if(finished){
      return victory;
    }
    return false;
  }

  @Override
  public boolean jump() {
    return hero.setJumping(true);
  }

  @Override
  public boolean moveLeft() {
    return hero.setMovingLeft(true);
  }

  @Override
  public boolean moveRight() {
    return hero.setMovingRight(true);
  }

  @Override
  public boolean stopMoving() {
    hero.setMovingRight(false);
    hero.setMovingLeft(false);
    return true;
  }

  @Override
  public Memento save() {
    return new LevelMemento(entities, hero, width, floorHeight, startTime, scoreStrategy,
            targetTime, finished, victory);
  }

  @Override
  public boolean restore(Memento save) {
    LevelMemento saveFile;
    if(save instanceof LevelMemento){
      saveFile = (LevelMemento) save;
      if(saveFile.getEntities() == null || saveFile.getHero() == null){
        return false;
      }
      this.entities = saveFile.getEntities();
      this.hero = saveFile.getHero();
      this.width = saveFile.getWidth();
      this.floorHeight = saveFile.getFloorHeight();
      this.startTime = saveFile.getStartTime();
      this.targetTime = saveFile.getTargetTime();
      this.finished = saveFile.getFinished();
      this.victory = saveFile.getVictory();
      setScoreStrategy((ScoreStrategy) saveFile.getScoreStrategy().copy());
      return true;
    }
    return false;
  }

  @Override
  public void tick() {
    entities.forEach(e -> e.move(this));
    collisionHandler.detectCollisions(this, hero);
    scoreStrategy.updateScore();
  }

  private void spawnHero(ConfigurationProvider provider) {
    this.hero = entitySpawner.createHero(provider, this);
    entities.add(this.hero);
  }

  private void spawnEntities(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createEntities(provider, this));
  }

  private void populateScene(ConfigurationProvider provider) {
    entities.addAll(entitySpawner.createBackgroundEntities(provider, this));
  }

  private String prettyTimeFormat(Duration duration) {
    return duration.toString().substring(2).replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
  }
}
