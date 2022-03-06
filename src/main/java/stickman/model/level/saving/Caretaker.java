package stickman.model.level.saving;

public interface Caretaker {

    /**
     * Creates a memento
     */
    void save();

    /**
     * Restores the previous state from a memento
     *
     * @return true if successful, else false
     */
    boolean restore();
}
