package stickman.model.level.scoring;

import stickman.model.entity.Entity;
import stickman.model.entity.impl.EnemyEntity;
import stickman.model.level.Level;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TimedStrategy implements ScoreStrategy {

    private Level level = null;
    private List<Entity> entities = new ArrayList<>();
    private int score = 0;
    private int enemyKillScore = 0;
    private int startingScore = 0;

    @Override
    public void setLevel(Level level){
        this.level = level;
        for(Entity e: level.getEntities()){
            if(e instanceof EnemyEntity){
                entities.add(e);
            }
        }
    }

    @Override
    public int getScore() {
        if(score < 0){
            return 0;
        }
        return score;
    }

    @Override
    public void updateScore() {
        score = startingScore;
        for(Entity e: level.getEntities()){
            if(e instanceof EnemyEntity && !entities.contains(e)){
                entities.add(e);
            }
        }
        for(Entity e: entities){
            if(!level.getEntities().contains(e)){
                score += 100;
            }
        }
        enemyKillScore = score;
        score = score + level.getTargetTime() - ((int)(Duration.between(level.getStartTime(),
                Instant.now()).getSeconds()));
    }

    @Override
    public Object copy() {
        TimedStrategy copy = new TimedStrategy();
        copy.startingScore = enemyKillScore;
        copy.enemyKillScore = enemyKillScore;
        return copy;
    }
}
