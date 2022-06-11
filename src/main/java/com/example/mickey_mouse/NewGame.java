package com.example.mickey_mouse;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Provider;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewGame extends Application{
    NewGame game;
    ImageView character = new ImageView();
    int currPos;
    boolean alive = true;
    AnchorPane main;

    @Override
    public void start(Stage stage) throws Exception {
        main = new AnchorPane();
        game = this;

        Image lbm = new Image(new FileInputStream("ltm.png"));
        currPos = 1;
        character.setImage(lbm);
        AnchorPane.setTopAnchor(character, 310.0);
        AnchorPane.setLeftAnchor(character, 280.0);
        main.getChildren().add(character);

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
     */
 {
        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            Egg egg = new Egg();
                            egg.setRandomPosition();
                            AnchorPane.setTopAnchor(egg, egg.y);
                            AnchorPane.setLeftAnchor(egg, egg.x);
                            main.getChildren().add(egg);
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }
}
