package com.groupsix.facescan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application entry point for the Face Recognition Attendance System.
 * Adheres to sleek iOS-inspired layout patterns and responsive configurations.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryApplicationStageWindow) throws Exception {
        Parent primaryAppInterfaceLayoutRootNode = FXMLLoader.load(getClass().getResource("/com/groupsix/facescan/Login.fxml"));
        
        // Cleaned up window title
        primaryApplicationStageWindow.setTitle("Attendance System Console");
        
        Scene baselineInterfaceDisplayPlatformScene = new Scene(primaryAppInterfaceLayoutRootNode, 1050, 650);
        primaryApplicationStageWindow.setScene(baselineInterfaceDisplayPlatformScene);
        
        primaryApplicationStageWindow.setResizable(true); 
        primaryApplicationStageWindow.centerOnScreen();
        primaryApplicationStageWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}