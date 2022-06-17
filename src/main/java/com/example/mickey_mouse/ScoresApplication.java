package com.example.mickey_mouse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.LinkedList;

public class ScoresApplication extends Application {
    com.example.mickey_mouse.Container container;
    ScoresApplication(com.example.mickey_mouse.Container container){
        this.container = container;
    }
    @Override
    public void start(Stage stage) throws Exception {

        ListView<ExitScore> main = new ListView<>();
        if(container!=null) main.getItems().setAll(container.scoreList);
        main.setEditable(false);

        Scene scene = new Scene(main, 400, 150);
        stage.setTitle("Scores table");
        stage.setScene(scene);
        stage.show();
    }
}
