package com.example.mickey_mouse;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Egg extends ImageView {
    double x;
    double y;
    double tox;
    double toy;
    double rotateSide;
    double shift = 100;
    int pos; //1-4, depending on egg spawn side


    Egg() {
        x = 0;
        y = 0;
        try {
            this.setImage(new Image(new FileInputStream("egg.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void setRandomPosition() {
        pos = (int) (Math.random() * 10) + 1;
        while (pos > 4) {
            pos = (int) (Math.random() * 10) + 1;
        }
        switch (pos) {
            case 1 -> setPosition(250,250+shift);
            case 2 -> setPosition(250,350+shift);
            case 3 -> setPosition(450,350+shift);
            case 4 -> setPosition(450,250+shift);
        }
        switch (pos) {
            case 1 -> setDestination(300,300+shift);
            case 2 -> setDestination(300,400+shift);
            case 3 -> setDestination(400,400+shift);
            case 4 -> setDestination(400,300+shift);
        }
        // set rotation side to egg, depending on position
        if(x==250)rotateSide = 360;
        else rotateSide = -360;
    }
    void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    void setDestination(double x, double y) {
        this.tox = x;
        this.toy = y;
    }

}
