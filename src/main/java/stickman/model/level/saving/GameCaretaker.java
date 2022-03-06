package stickman.model.level.saving;

import stickman.model.engine.GameEngine;

public class GameCaretaker implements Caretaker {

    private GameEngine model;
    private Memento save;

    public GameCaretaker(GameEngine model){
        this.model = model;
    }

    @Override
    public void save() {
        save = model.getCurrentLevel().save();
    }

    @Override
    public boolean restore() {
        return model.getCurrentLevel().restore(save);
    }

}
