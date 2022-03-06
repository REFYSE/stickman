package stickman.view.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import stickman.model.engine.GameEngine;
import stickman.model.level.Level;
import stickman.view.GameManager;

import java.time.Duration;
import java.time.Instant;

/**
 * A standard UI
 */
public class StandardUI implements UIDrawer{

    private GameManager gameManager;
    private GameEngine model;
    private Pane pane;
    private Level level;

    private boolean displayed = false;

    private VBox displayPanel = new VBox();
    private Label scoreLabel;
    private Label totalScoreLabel;

    @Override
    public void draw(GameManager gameManger, GameEngine model, Pane pane) {
        this.gameManager = gameManger;
        this.model = model;
        this.pane = pane;
        this.level = model.getCurrentLevel();
        pane.getChildren().add(displayPanel);

        displayPanel.setSpacing(10);
        scoreLabel = createLabel("LEVEL SCORE : " + level.getScore(), 30);
        totalScoreLabel = createLabel("TOTAL SCORE : " + level.getScore(), 30);
        displayPanel.getChildren().addAll(scoreLabel, totalScoreLabel);
    }

    @Override
    public void update(){
        checkNextLevel();
        checkEndGame();
        updateScores();
    }

    private void checkEndGame(){
        if(displayed){
            try{
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException ignored) {
            }
        }
        if(level.isFinished()){
            String endMsg = null;
            if(!level.isVictory()){
                endMsg = "======YOU LOST======";
            } else if(!model.hasNextLevel()){
                endMsg = "======WINNER======";
            }
            if(endMsg != null){
                Label endMsgLabel = createLabel(endMsg, 60);
                Label endScoreLabel = createLabel("FINAL SCORE: " + model.getTotalScore(), 40);
                endMsgLabel.layoutXProperty().bind(pane.widthProperty().subtract(endMsgLabel.widthProperty()).divide(2));
                endMsgLabel.layoutYProperty().bind(pane.heightProperty().divide(2).subtract(endMsgLabel.heightProperty()));
                endScoreLabel.layoutXProperty().bind(pane.widthProperty().subtract(endScoreLabel.widthProperty()).divide(2));
                endScoreLabel.layoutYProperty().bind(pane.heightProperty().divide(2));
                pane.getChildren().addAll(endMsgLabel, endScoreLabel);
                pane.getChildren().remove(displayPanel);
                displayed = true;
            }
        }
    }

    private void checkNextLevel(){
        if(model.getCurrentLevel() != level){
            createLevelNotification("Level Complete", "Continue");
            level = model.getCurrentLevel();
        }
    }

    private void createLevelNotification(String msg, String btnMsg){
        gameManager.pause();
        Instant startPause = Instant.now();
        Alert exitGame = new Alert(Alert.AlertType.INFORMATION, msg, new ButtonType(btnMsg));
        exitGame.setHeaderText("");
        exitGame.setGraphic(null);
        exitGame.setTitle("Level Message");
        exitGame.setOnHidden(e -> {
            Instant endPause = Instant.now();
            level.setStartTime(level.getStartTime().plus(Duration.between(startPause, endPause)));
            gameManager.play();
        });
        exitGame.show();
    }

    private Label createLabel(String text, int fontSize){
        Label label = new Label(text);
        label.setStyle(String.format("-fx-font-size:%dpx; -fx-font-family: Roboto", fontSize));
        return label;
    }

    private void updateScores(){
        scoreLabel.setText("LEVEL SCORE : " + level.getScore());
        totalScoreLabel.setText("TOTAL SCORE : " + model.getTotalScore());
    }

}
