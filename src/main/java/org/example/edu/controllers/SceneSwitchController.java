package org.example.edu.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SceneSwitchController {

    private static SceneSwitchController instance;

    private SceneSwitchController(){
    }

    public static SceneSwitchController getInstance(){
        if (instance==null){
            return instance = new SceneSwitchController();
        }
        return instance;
    }

    public void switchScene(AnchorPane landing, String fxml) throws IOException {
        AnchorPane pane  = FXMLLoader.load(getClass().getResource("/view/"+fxml));
        landing.getChildren().setAll(pane);
    }
}

