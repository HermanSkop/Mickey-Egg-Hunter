package com.example.mickey_mouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Button ng = new Button("New Game");
        Button hs = new Button("High Scores");
        Button exit = new Button("Exit");

        ng.setOnAction(e->{
            try {
                stage.close();
                new NewGame().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        exit.setOnAction(e->{
            stage.close();
        });

        FlowPane column = new FlowPane();
        BorderPane main = new BorderPane();

        column.setMaxSize(90,100);
        column.setVgap(20);
        column.setAlignment(Pos.BASELINE_CENTER);

        main.setCenter(column);

        column.getChildren().add(ng);
        column.getChildren().add(hs);
        column.getChildren().add(exit);

        Scene scene = new Scene(main, 200, 150);
        stage.setTitle("ZXC!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}