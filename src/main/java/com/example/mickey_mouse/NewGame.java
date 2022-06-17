package com.example.mickey_mouse;

import javafx.animation.*;
import javafx.application.Application;
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
    Stage stage;
    AnchorPane main;
    NewGame game;

    Timeline waveController;
    ImageView character = new ImageView();
    Label score;
    Label timer;
    Label tname;
    Label sname;
    int currPos;
    int hp;
    int currEgg;
    boolean alive;

    LinkedList<Egg> hpEggs;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        main = new AnchorPane();
        game = this;
        alive = true;

        createHUD();

        main.getChildren().add(character);
        main.getChildren().add(score);
        main.getChildren().add(timer);
        main.getChildren().add(tname);
        main.getChildren().add(sname);

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

        timeController();
        runEggs(ChooseMode.difflvl);
    }

    private void createHpBar() {
        hpEggs = new LinkedList<>();
        currEgg = 0;
        hp = 4;

        hpEggs.add(new Egg());
        hpEggs.add(new Egg());
        hpEggs.add(new Egg());
        hpEggs.add(new Egg());

        AnchorPane.setLeftAnchor(hpEggs.get(0), 50.0);
        AnchorPane.setLeftAnchor(hpEggs.get(1),100.0);
        AnchorPane.setLeftAnchor(hpEggs.get(2),150.0);
        AnchorPane.setLeftAnchor(hpEggs.get(3),200.0);
        AnchorPane.setTopAnchor(hpEggs.get(0), 43.0);
        AnchorPane.setTopAnchor(hpEggs.get(1), 43.0);
        AnchorPane.setTopAnchor(hpEggs.get(2), 43.0);
        AnchorPane.setTopAnchor(hpEggs.get(3), 43.0);

        main.getChildren().addAll(hpEggs);

    }
    private void createTimer() {
        timer = new Label("0");
        timer.setFont(new Font(20));
        tname = new Label("Timer:");
        tname.setFont(new Font(20));

        AnchorPane.setTopAnchor(timer, 50.0);
        AnchorPane.setRightAnchor(timer,270.0);
        AnchorPane.setTopAnchor(tname, 50.0);
        AnchorPane.setRightAnchor(tname,300.0);
    }
    private void createScoreCounter() {
        score = new Label("0");
        score.setFont(new Font(20));
        sname = new Label("Score:");
        sname.setFont(new Font(20));

        AnchorPane.setTopAnchor(score, 50.0);
        AnchorPane.setRightAnchor(score,100.0);
        AnchorPane.setTopAnchor(sname, 50.0);
        AnchorPane.setRightAnchor(sname,130.0);
    }
    private void createCharacter() throws FileNotFoundException {
        currPos = 1;
        character.setImage(new Image(new FileInputStream("ltm.png")));
        AnchorPane.setTopAnchor(character, 310.0);
        AnchorPane.setLeftAnchor(character, 280.0);
    }

    private void createHUD() throws FileNotFoundException {
        createHpBar();
        createTimer();
        createScoreCounter();
        createCharacter();
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

    void runEggs(int difficulty)/*
        Here is the main method that controls eggs' speed and quantity
     */ {
        if (alive) {
            waveController = new Timeline(
                    new KeyFrame(Duration.millis((float)2000/difficulty),
                            event -> {
                                Egg egg = new Egg();
                                egg.setRandomPosition();
                                AnchorPane.setTopAnchor(egg, egg.shift * -1);
                                setFallAnimations(egg, difficulty);
                                main.getChildren().add(egg);
                            }));
            waveController.setCycleCount(10);
            waveController.setOnFinished(e -> {
                runEggs(difficulty+1);
            });
            waveController.play();
        }
    }

    void timeController() {
        Timeline timeRaise = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timer.setText(String.valueOf(Integer.parseInt(timer.getText()) + 1));
                        }));
        timeRaise.setCycleCount(Animation.INDEFINITE);
        timeRaise.play();
    }

    void setFallAnimations(Egg egg, int difficulty){
        Path path = new Path();
        path.getElements().add (new MoveTo(egg.x, egg.y));
        path.getElements().add (new LineTo(egg.tox, egg.toy));

        setEggRotation(egg, difficulty);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis((float)2000/difficulty));
        pathTransition.setNode(egg);
        pathTransition.setPath(path);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(e -> {
            if(currPos == egg.pos)successFall(egg);
            else {
                try {
                    failFall(egg, difficulty);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    void setEggRotation(Egg egg, int difficulty){
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis((float)2000/difficulty));
        rotateTransition.setNode(egg);
        rotateTransition.setByAngle(egg.rotateSide);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();
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

    private void failFall(Egg egg, int difficulty) throws FileNotFoundException {
        setBreakAnimation(egg);
        setEggRotation(egg, difficulty);
        hp--;
        hpEggs.get(currEgg).setImage(new Image(new FileInputStream("begg.png")));
        if(hp==0)death();
        else currEgg++;
    }

    private void successFall(Egg egg) {
        egg.setImage(null);
        score.setText(String.valueOf(Integer.parseInt(score.getText())+1)); // making score +1
    }

    private void death() {
        try {
            alive = false;
            waveController.stop();
            stage.close();
            new usernameWindow(Integer.parseInt(score.getText()), Integer.parseInt(timer.getText()), ChooseMode.difflvl).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
