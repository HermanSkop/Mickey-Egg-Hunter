package com.example.mickey_mouse;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Egg extends ImageView {
    double x;
    double y;

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
        int temp = (int) (Math.random() * 10) + 1;
        while (temp > 4) {
            temp = (int) (Math.random() * 10) + 1;
            System.out.println(temp);
        }
        switch (temp) {
            case 1 -> setPosition(230,230);
            case 2 -> setPosition(230,340);
            case 3 -> setPosition(428,230);
            case 4 -> setPosition(428,340);
        }
    }
    void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
