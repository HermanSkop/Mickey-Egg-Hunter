package com.example.mickey_mouse;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class usernameWindow extends Application {
    TextArea area;
    int score;
    int time;
    int difflv;

    usernameWindow(int score, int time, int difflv){
        this.score = score;
        this.time = time;
        this.difflv = difflv;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane main = new AnchorPane();
        area = new TextArea("Type here your name");
        area.setWrapText(true);

        Button confirm = new Button("Confirm");
        area.setMaxSize(150,10);

        AnchorPane.setTopAnchor(confirm, 75.0);
        AnchorPane.setTopAnchor(area, 20.0);
        AnchorPane.setLeftAnchor(area, 25.0);
        AnchorPane.setLeftAnchor(confirm, 75.0);
        main.getChildren().addAll(confirm,area);

        Scene scene = new Scene(main, 200, 120);
        stage.setTitle("Over");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        confirm.setOnAction(e->{
            // actual saving process by ExitGame class
            new SaveToFile(new ExitScore(getUserName(), score, time, difflv));
            stage.close();
            try {
                new HelloApplication().start(new Stage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private String getUserName() {
        return area.getText();
    }
}