package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import stickman.model.engine.GameEngine;
import stickman.view.ui.StandardUI;
import stickman.view.ui.UIDrawer;

/** A "manager" type class for operating critical game functions */
public class GameManager {

  public static final int FRAMEFRATE_MS = 17;
  private final int width;
  private final int height;

  private Scene scene;
  private Timeline timeline;
  private GameDrawer entityDrawer;

  public GameManager(GameEngine model, int width, int height) {

    Pane pane = new Pane();

    this.width = width;
    this.height = height;
    this.scene = new Scene(pane, width, height);

    // register input handler
    KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);
    scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
    scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

    // start drawers
    BackgroundDrawer backgroundDrawer = new BlockedBackground();
    UIDrawer uiDrawer = new StandardUI();
    this.entityDrawer = new GameDrawer(model, pane, backgroundDrawer, uiDrawer, width, height);

    backgroundDrawer.draw(model, pane);
    uiDrawer.draw(this, model, pane);
  }

  /**
   * Get the Window's current Scene object
   *
   * @return the scene object
   */
  public Scene getScene() {
    return this.scene;
  }

  /** Initiates the game's display update sequence. */
  public void run() {

    timeline =
        new Timeline(new KeyFrame(Duration.millis(FRAMEFRATE_MS), t -> entityDrawer.draw()));

    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  public void play(){
    timeline.play();
  }

  public void pause(){
    timeline.pause();
  }

  // exposed for testing
  public GameDrawer getEntityDrawer() {
    return entityDrawer;
  }

}
