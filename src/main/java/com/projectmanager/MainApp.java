package com.projectmanager;

import java.io.IOException;

import com.projectmanager.ui.SceneSwitcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
