package com.example.mickey_mouse;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.WritableDoubleValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Provider;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewGame extends Application{
    Timeline waveController;
    NewGame game;
    ImageView character = new ImageView();
    Label score;
    int currPos;
    boolean alive = true;
    AnchorPane main;
    int hp;
    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        main = new AnchorPane();
        game = this;
        score = new Label();
        hp = 4;

        Image lbm = new Image(new FileInputStream("ltm.png"));
        currPos = 1;
        character.setImage(lbm);
        AnchorPane.setTopAnchor(character, 310.0);
        AnchorPane.setLeftAnchor(character, 280.0);
        AnchorPane.setTopAnchor(score, 50.0);
        AnchorPane.setRightAnchor(score,100.0);

        score.setText("0");
        score.setFont(new Font(20));

        main.getChildren().add(character);
        main.getChildren().add(score);

        Scene scene = new Scene(main, 700, 500);
        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                changePosition(keyEvent);
            }
        });
        main.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream("bg.png")), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(700, 500, true, true, true, true))));

        stage.setTitle("Fight!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Platform.setImplicitExit(false);

        runEggs();
        System.out.println("finish");
    }
    void changePosition(KeyEvent e) {
        try {
            if (Objects.equals(e.getCharacter(), "q")) {
                character.setImage(new Image(new FileInputStream("ltm.png")));
                currPos = 1;
            }
            else if (Objects.equals(e.getCharacter(), "w")) {
                character.setImage(new Image(new FileInputStream("lbm.png")));
                currPos = 2;
            }
            else if (Objects.equals(e.getCharacter(), "e")) {
                character.setImage(new Image(new FileInputStream("rbm.png")));
                currPos = 3;
            }
            else if (Objects.equals(e.getCharacter(), "r")) {
                character.setImage(new Image(new FileInputStream("rtm.png")));
                currPos = 4;
            }
        }
        catch (FileNotFoundException exception){
            System.out.println("One of the images is missed, please reinstall game!");
        }
    }
    void runEggs()/*
        Here is the main method that controls eggs' speed and quantity
     */ {
        waveController = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            Egg egg = new Egg();
                            egg.setRandomPosition();
                            AnchorPane.setTopAnchor(egg, egg.shift*-1);

                            setFallAnimations(egg);
                            main.getChildren().add(egg);
                        }));
        waveController.setCycleCount(Timeline.INDEFINITE);
        waveController.play();
    }
    void setFallAnimations(Egg egg){
        Path path = new Path();
        path.getElements().add (new MoveTo(egg.x, egg.y));
        path.getElements().add (new LineTo(egg.tox, egg.toy));

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setNode(egg);
        rotateTransition.setByAngle(egg.rotateSide);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(egg);
        pathTransition.setPath(path);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(e -> {
            if(currPos == egg.pos)successFall();
            else failFall();
            egg.setImage(null);
        });

    }

    private void failFall() {
         hp--;
         if(hp==0)death();
    }

    private void successFall() {
         score.setText(String.valueOf(Integer.parseInt(score.getText())+1)); // making score +1
    }

    private void death() {
        try {
            waveController.stop();
            stage.close();
            new usernameWindow().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
