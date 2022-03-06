package stickman.model.level.saving;

import stickman.model.entity.Controllable;
import stickman.model.entity.Entity;
import stickman.model.level.scoring.ScoreStrategy;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LevelMemento implements Memento {

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> entitiesCopy = new ArrayList<>();
    private Controllable hero;
    private Controllable heroCopy;

    private final double width;
    private final double floorHeight;
    private final Duration elapsedTime;
    private final int targetTime;
    private final boolean finished;
    private final boolean victory;
    private final ScoreStrategy strategy;

    public LevelMemento(List<Entity> entities, Controllable hero,
                     double width, double floorHeight, Instant startTime, ScoreStrategy strategy,
                     int targetTime, boolean finished, boolean victory) {
        for(Entity e: entities){
            if(e == hero){
                this.hero = (Controllable) hero.copy();
                this.entities.add(this.hero);
            } else{
                this.entities.add((Entity)e.copy());
            }
        }
        this.width = width;
        this.floorHeight = floorHeight;
        this.elapsedTime = Duration.between(startTime, Instant.now());
        this.targetTime = targetTime;
        this.finished = finished;
        this.victory = victory;
        this.strategy = (ScoreStrategy)strategy.copy();
    }

    public List<Entity> getEntities() {
        entitiesCopy = new ArrayList<>();
        for(Entity e: entities){
            if(e == hero){
                heroCopy = (Controllable) hero.copy();
                entitiesCopy.add(heroCopy);
            } else{
                entitiesCopy.add((Entity)e.copy());
            }
        }
        return entitiesCopy;
    }

    public Controllable getHero() {
        return heroCopy;
    }

    public double getWidth() {
        return width;
    }

    public double getFloorHeight() {
        return floorHeight;
    }

    public Instant getStartTime() {
        return Instant.now().minus(elapsedTime);
    }

    public int getTargetTime() {
        return targetTime;
    }

    public boolean getFinished() {
        return finished;
    }

    public boolean getVictory() {
        return victory;
    }

    public ScoreStrategy getScoreStrategy(){
        return strategy;
    }

}
