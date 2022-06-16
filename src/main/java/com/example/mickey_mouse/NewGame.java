package com.example.mickey_mouse;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

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
    int currEgg;

    LinkedList<Egg> hpEggs;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        main = new AnchorPane();
        game = this;
        score = new Label();
        hp = 4;
        currEgg = 0;
        hpEggs = new LinkedList<>();

        createHpBar();

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

    private void createHpBar() {
        hpEggs.add(new Egg());
        hpEggs.add(new Egg());
        hpEggs.add(new Egg());
        hpEggs.add(new Egg());

        AnchorPane.setLeftAnchor(hpEggs.get(0), 50.0);
        AnchorPane.setLeftAnchor(hpEggs.get(1),100.0);
        AnchorPane.setLeftAnchor(hpEggs.get(2),150.0);
        AnchorPane.setLeftAnchor(hpEggs.get(3),200.0);
        AnchorPane.setTopAnchor(hpEggs.get(0), 40.0);
        AnchorPane.setTopAnchor(hpEggs.get(1), 40.0);
        AnchorPane.setTopAnchor(hpEggs.get(2), 40.0);
        AnchorPane.setTopAnchor(hpEggs.get(3), 40.0);

        main.getChildren().addAll(hpEggs);

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

        setRotation(egg);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(egg);
        pathTransition.setPath(path);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(e -> {
            if(currPos == egg.pos)successFall();
            else {
                try {
                    failFall(egg);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            //egg.setImage(null);
        });

    }

    void setRotation(Egg egg){
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setNode(egg);
        rotateTransition.setByAngle(egg.rotateSide);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
    }

    private void failFall(Egg egg) throws FileNotFoundException {
         setBreakAnimation(egg);
         setRotation(egg);
         hp--;
         hpEggs.get(currEgg).setImage(new Image(new FileInputStream("begg.png")));
         if(hp==0)death();
         else currEgg++;
    }

    private void setBreakAnimation(Egg egg) {
        Path path = new Path();
        path.getElements().add (new MoveTo(egg.tox, egg.toy));
        path.getElements().add (new LineTo(egg.falltox, egg.falltoy));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(egg);
        pathTransition.setPath(path);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(e -> {
            try {
                egg.setImage(new Image(new FileInputStream("begg.png")));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void successFall() {
         score.setText(String.valueOf(Integer.parseInt(score.getText())+1)); // making score +1
    }

    private void death() {
        try {
            waveController.stop();
            stage.close();
            new usernameWindow(Integer.parseInt(score.getText()), 1, 1).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
