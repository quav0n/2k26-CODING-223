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

/**
 * Controller class for the Login view of the Face Recognition Attendance System.
 * Manages the biometric face-scan simulation animation, expression morphing logic,
 * failure/success probability matrix matching benchmarks, and scene transition routing.
 */
public class LoginController {

    @FXML private Label statusLabel;
    @FXML private Line scanLine;
    @FXML private Button scanButton;
    @FXML private Path faceMouthPath;

    private int attemptCounter = 0;
    private TranslateTransition laserSweep;
    private final Random randomGenerator = new Random();

    // Simulated Registered Face Profiles Database for automated cool factor recognition
    private final String[] recognizedEmployees = {"Alex Rivers", "Jordan Blake", "Taylor Morgan", "Morgan Vance"};

    /**
     * Initializes the controller automatically after its FXML file has been loaded.
     * Configures the timeline parameters for the scanning laser overlay element and
     * sets the target biometric mouth shape to its baseline expression state.
     */
    @FXML
    public void initialize() {
        // Initialize the tracking linear laser sweep parameters
        laserSweep = new TranslateTransition(Duration.seconds(1.2), scanLine);
        laserSweep.setFromY(-75);
        laserSweep.setToY(75);
        laserSweep.setCycleCount(Animation.INDEFINITE);
        laserSweep.setAutoReverse(true);
        
        resetMouthToNeutral();
    }

    /**
     * Handles the biometric scanner activation event triggered by the user interface.
     * Increments the authentication tracking state counters, updates interactive controls,
     * and runs the simulated scanning delay inside an independent worker thread.
     * @param event The ActionEvent passed by the JavaFX runtime from the trigger button node.
     */
    @FXML
    private void handleFaceScan(ActionEvent event) {
        attemptCounter++;
        setUiScanningState(true);

        // Run scanning process concurrently to protect the execution thread from hanging
        new Thread(() -> {
            try {
                Thread.sleep(2200); // 2.2 Second scan simulation latency window
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Platform.runLater(() -> {
                setUiScanningState(false);
                performAuthenticationCheck(event);
            });
        }).start();
    }

    /**
     * Processes the current scan interaction against the system validation requirements.
     * Enforces a strict 33.3% failure rate check on attempts 1 and 2, while providing
     * a hard override logic configuration to guarantee a pass on attempt 3.
     * @param event The active UI context event required to route scene changes upon success.
     */
    private void performAuthenticationCheck(ActionEvent event) {
        // Rule: 3rd Attempt is an absolute system override bypass success
        if (attemptCounter >= 3) {
            handleSuccess(event);
            return;
        }

        // Rule: Exact 1 in 3 chance (33.3% rate threshold window) of throwing failure curves
        if (randomGenerator.nextInt(3) == 0) { 
            handleFailure();
        } else {
            handleSuccess(event);
        }
    }

    /**
     * Updates layout tokens to reflect a successful match verification sequence.
     * Morphs the mouth spline into a warm curve smile expression and flags an
     * asynchronous post-delay scene routing sequence into the main metrics dashboard.
     * @param event Context token used to extract the application stage scene window.
     */
    private void handleSuccess(ActionEvent event) {
        updateMouthExpression(true); // Pulls path vectors down into a smile
        
        // Randomly identify one of our registered employees from the biometric database
        String identifiedUser = recognizedEmployees[randomGenerator.nextInt(recognizedEmployees.length)];
        
        statusLabel.setText("Verified: " + identifiedUser);
        statusLabel.setStyle("-fx-text-fill: #34C759; -fx-font-weight: bold;");
        scanButton.setDisable(true);

        // Retain smile expression vector configuration briefly before loading dashboard scene platform
        new Thread(() -> {
            try { 
                Thread.sleep(1500); 
            } catch (Exception ignored) {}
            Platform.runLater(() -> navigateToDashboard(event, identifiedUser));
        }).start();
    }

    /**
     * Updates layout tokens to reflect a failed biometric match sequence.
     * Morphs the mouth spline into an upset curve frown expression and updates the
     * HUD status label with context metrics regarding the rejected match tracking index.
     */
    private void handleFailure() {
        updateMouthExpression(false); // Warps vectors up into a frown
        statusLabel.setText("Face Not Recognized (Attempt " + attemptCounter + ")");
        statusLabel.setStyle("-fx-text-fill: #FF3B30; -fx-font-weight: bold;");
    }

    /**
     * Toggles interactive layout properties based on whether a scanner evaluation sequence is running.
     * Controls the layout presence of the tracking laser sweep animation bar.
     * @param active Sets structural active scanning conditions when true, disables them when false.
     */
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

    /**
     * Linearly adjusts the control interpolation values inside the primary mouth vector spline.
     * Uses index 1 specifically to completely step past the structural starting coordinates 
     * definition element and target the functional curve element without throwing errors.
     * @param smile Toggles control anchor coordinates down if true (smile), or up if false (frown).
     */
    private void updateMouthExpression(boolean smile) {
        // Explicitly pulls index element 1 to bypass MoveTo and grab QuadCurveTo
        QuadCurveTo curve = (QuadCurveTo) faceMouthPath.getElements().get(1);
        curve.setControlY(smile ? 38.0 : -2.0); 
    }

    /**
     * Resets the structural mouth arc back to its flat neutral geometric configuration.
     */
    private void resetMouthToNeutral() {
        QuadCurveTo curve = (QuadCurveTo) faceMouthPath.getElements().get(1);
        curve.setControlY(18.0); // Flattens spline coordinates to baseline horizontal alignment
    }

    /**
     * Generates a new dashboard layout hierarchy and attaches it to the current primary display stage window.
     * @param event Root UI element context event used to locate and anchor the parent container stage layer.
     * @param employeeName Passed biometric string value mapped into the dashboard controller.
     */
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
            statusLabel.setText("Fatal Route Navigation Failure Exception.");
            e.printStackTrace();
        }
    }
}