package stickman.view.ui;

import javafx.scene.layout.Pane;
import stickman.model.engine.GameEngine;
import stickman.view.GameManager;

public interface UIDrawer {

    /**
     * Draws the game UI onto the game window.
     *
     * @param model the GameEngine being used for level execution.
     * @param pane the Pane being used for display.
     */
    void draw(GameManager gameManger, GameEngine model, Pane pane);

    /**
     * Updates the UI
     */
    void update();

}
