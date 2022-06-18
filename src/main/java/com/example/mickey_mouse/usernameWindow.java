package com.example.mickey_mouse;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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
    public void start(Stage stage) {
        AnchorPane main = new AnchorPane();
        area = new TextArea("Type here your name");
        area.setWrapText(true);
        area.setMaxSize(150,10);
        Button confirm = new Button("Confirm");

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
        stage.setOnCloseRequest(e->{
            try {
                new HelloApplication().start(new Stage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        confirm.setOnAction(e->{
            // Saving process
            try {
                new SaveToFile(new ExitScore(filter(getUserName()), score, time, difflv));
                stage.close();

                new HelloApplication().start(new Stage());
            } catch (IOException | ClassNotFoundException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Some error appeared");
                alert.setContentText("It is not possible to run the game anymore. \n Try to reinstall the game");
                alert.showAndWait();
            }
        });
    }

    private String getUserName() {
        return area.getText();
    }

    String filter(String str){
        return str.replaceAll("\n", " e");
    }
}