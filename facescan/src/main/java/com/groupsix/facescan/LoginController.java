package com.groupsix.facescan;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class LoginController {

    @FXML private Label statusLabel;
    @FXML private Line scanLine;
    @FXML private Button scanButton;
    @FXML private Path faceMouthPath;

    private int attemptCounter = 0;
    private TranslateTransition laserSweep;
    private final Random randomGenerator = new Random();

    // Simulated Registered Face Profiles Database
    private final String[] recognizedEmployees = {"Alex Rivers", "Jordan Blake", "Taylor Morgan", "Morgan Vance"};

    @FXML
    public void initialize() {
        laserSweep = new TranslateTransition(Duration.seconds(1.2), scanLine);
        laserSweep.setFromY(-75);
        laserSweep.setToY(75);
        laserSweep.setCycleCount(Animation.INDEFINITE);
        laserSweep.setAutoReverse(true);
        
        resetMouthToNeutral();
    }

    @FXML
    private void handleFaceScan(ActionEvent event) {
        attemptCounter++;
        setUiScanningState(true);

        new Thread(() -> {
            try {
                Thread.sleep(2200); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Platform.runLater(() -> {
                setUiScanningState(false);
                performAuthenticationCheck(event);
            });
        }).start();
    }

    private void performAuthenticationCheck(ActionEvent event) {
        if (attemptCounter >= 3) {
            handleSuccess(event);
            return;
        }

        if (randomGenerator.nextInt(3) == 0) { 
            handleFailure();
        } else {
            handleSuccess(event);
        }
    }

    private void handleSuccess(ActionEvent event) {
        updateMouthExpression(true); 
        
        // Randomly identify one of our registered employees from the biometric database
        String identifiedUser = recognizedEmployees[randomGenerator.nextInt(recognizedEmployees.length)];
        
        statusLabel.setText("Verified: " + identifiedUser);
        statusLabel.setStyle("-fx-text-fill: #34C759; -fx-font-weight: bold;"); 
        scanButton.setDisable(true);

        new Thread(() -> {
            try { 
                Thread.sleep(1500); 
            } catch (Exception ignored) {}
            Platform.runLater(() -> navigateToDashboard(event, identifiedUser));
        }).start();
    }

    private void handleFailure() {
        updateMouthExpression(false); 
        statusLabel.setText("Face Not Recognized (Attempt " + attemptCounter + ")");
        statusLabel.setStyle("-fx-text-fill: #FF3B30; -fx-font-weight: bold;"); 
    }

    private void setUiScanningState(boolean active) {
        if (active) {
            scanButton.setDisable(true);
            statusLabel.setText("Analyzing Facial Matrix...");
            statusLabel.setStyle("-fx-text-fill: #007AFF;"); 
            resetMouthToNeutral();
            scanLine.setVisible(true);
            laserSweep.play();
        } else {
            laserSweep.stop();
            scanLine.setVisible(false);
            scanButton.setDisable(false);
        }
    }

    private void updateMouthExpression(boolean smile) {
        QuadCurveTo curve = (QuadCurveTo) faceMouthPath.getElements().get(1);
        curve.setControlY(smile ? 38.0 : -2.0); 
    }

    private void resetMouthToNeutral() {
        QuadCurveTo curve = (QuadCurveTo) faceMouthPath.getElements().get(1);
        curve.setControlY(18.0); 
    }

    // Pass the identified name forward directly to the dashboard
    private void navigateToDashboard(ActionEvent event, String employeeName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupsix/facescan/Dashboard.fxml"));
            Parent root = loader.load();
            
            // Send user context data straight to the dashboard controller instance
            DashboardController dashboard = loader.getController();
            dashboard.registerScannedEmployeeCheckIn(employeeName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException e) {
            statusLabel.setText("Navigation failed.");
            e.printStackTrace();
        }
    }
}