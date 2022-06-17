package com.example.mickey_mouse;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ChooseMode extends Application {
    static int difflvl;

    @Override
    public void start(Stage stage) throws Exception {
        Button easy = new Button("Easy");
        Button mid = new Button("Middle");
        Button hard = new Button("Hard");
        Button back = new Button("Back");

        easy.setOnAction(e->{
            stage.close();
            startGame(1);
        });
        mid.setOnAction(e->{
            stage.close();
            startGame(2);
        });
        hard.setOnAction(e->{
            stage.close();
            startGame(3);
        });
        back.setOnAction(e->{
            try {
                stage.close();
                new HelloApplication().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        FlowPane column = new FlowPane();
        BorderPane main = new BorderPane();

        column.setMaxSize(60,170);
        column.setVgap(20);
        column.setAlignment(Pos.BASELINE_CENTER);

        main.setCenter(column);

        column.getChildren().add(easy);
        column.getChildren().add(mid);
        column.getChildren().add(hard);
        column.getChildren().add(back);


        Scene scene = new Scene(main, 200, 200);
        stage.setTitle("Chose game mode");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    void startGame(int difflvl){
        ChooseMode.difflvl = difflvl;
        try {
            new NewGame().start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
