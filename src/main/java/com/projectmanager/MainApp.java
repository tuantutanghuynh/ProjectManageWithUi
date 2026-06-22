package com.projectmanager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Entry point tam thoi de xac nhan Maven + JavaFX da chay duoc trong VS Code.
// Thay noi dung start() bang FXMLLoader khi Guide_05/06 (Controllers, FXML) duoc code.
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("JavaFX chay OK qua Maven (mvn javafx:run)");
        Scene scene = new Scene(new StackPane(label), 480, 240);
        stage.setTitle("ProjectManagerApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
