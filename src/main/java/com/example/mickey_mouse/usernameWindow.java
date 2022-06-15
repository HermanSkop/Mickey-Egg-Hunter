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

public class usernameWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane main = new AnchorPane();
        TextArea area = new TextArea("Type here your name");
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
        stage.show();        // confirm button should create object ExitScore to be saved
       /* confirmName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // actual saving process by ExitGame class
                new SaveToFile().saveDmgToFile(Integer.parseInt(weaponField.getText()));
                new SaveToFile().savePointsToFile(Integer.parseInt(PointsField.getText()));
                new SaveToFile(new ExitScore(getUserName(), Integer.parseInt(PointsField.getText()), Integer.parseInt(timeField.getText()), startDifflvl));
                userName.dispose();
            }
        });*/
    }
}