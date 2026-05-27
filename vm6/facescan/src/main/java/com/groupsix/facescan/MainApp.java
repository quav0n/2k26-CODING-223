package com.groupsix.facescan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application entry point for the Face Recognition Attendance System.
 * Adheres to Material Design 3 guidelines and supports fully responsive configurations.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryApplicationStageWindow) throws Exception {
        // Absolute package path to locate your login view file cleanly
        Parent primaryAppInterfaceLayoutRootNode = FXMLLoader.load(getClass().getResource("/com/groupsix/facescan/Login.fxml"));
        
        // Window frame configurations
        primaryApplicationStageWindow.setTitle("Face Recognition Attendance System Management Console v1.0");
        
        // Instantiating the scene profile mapped to your unified dimensions (1050px by 650px)
        Scene baselineInterfaceDisplayPlatformScene = new Scene(primaryAppInterfaceLayoutRootNode, 1050, 650);
        primaryApplicationStageWindow.setScene(baselineInterfaceDisplayPlatformScene);
        
        // Enables window maximizing, minimizing, dragging, and adaptive scaling
        primaryApplicationStageWindow.setResizable(true); 
        
        // Positions stage centered relative to primary system monitor resolution coordinates
        primaryApplicationStageWindow.centerOnScreen();
        
        // Launch display stage
        primaryApplicationStageWindow.show();
    }

    /**
     * Fallback application loader.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}