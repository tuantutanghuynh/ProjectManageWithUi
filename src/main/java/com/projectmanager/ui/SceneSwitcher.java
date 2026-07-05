package com.projectmanager.ui;

import java.io.IOException;

import com.projectmanager.MainApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {
    private static Stage stage;

    public static void setStage(Stage s){
        stage = s;
    }

    public static void switchScene(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            MainApp.class.getResource("/com/projectmanager/ui/views/" + fxml)
        );
        stage.setScene(new Scene(loader.load()));
    }

    //Overload tra ve controller sau khi load - dung khi truyen data san man moi
    public static <C> C switchSceneAndGetController(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            MainApp.class.getResource("/com/projectmanager/ui/views/" + fxml)
        );
        stage.setScene(new Scene(loader.load()));
        return loader.getController();
    }
    
}
