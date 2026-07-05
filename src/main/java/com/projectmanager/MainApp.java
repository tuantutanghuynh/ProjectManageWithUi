package com.projectmanager;

import java.io.IOException;

import com.projectmanager.ui.SceneSwitcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Entry point tam thoi de xac nhan Maven + JavaFX da chay duoc trong VS Code.
// Thay noi dung start() bang FXMLLoader khi Guide_05/06 (Controllers, FXML) duoc code.
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException{
        SceneSwitcher.setStage(primaryStage);
        primaryStage.setTitle("Project Manager");
        primaryStage.setResizable(false);

        // mo man hinh login khi khoi dong
        FXMLLoader loader = new FXMLLoader(
            MainApp.class.getResource("/com/projectmanager/ui/views/login.fxml")
        );
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
