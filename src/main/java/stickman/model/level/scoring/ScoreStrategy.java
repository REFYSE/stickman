package stickman.model.level.scoring;

import stickman.model.entity.Prototype;
import stickman.model.level.Level;

/** ScoreStrategy interface*/
public interface ScoreStrategy extends Prototype {

    /**
     * Sets the level to calculate scores for
     *
     * @param level the level
     */
    void setLevel(Level level);

    /**
     * Returns the current score
     *
     * @return the score
     */
    int getScore();

    /**
     * Calculate the score
     */
    void updateScore();
}
