package com.groupsix.facescan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    @FXML private VBox sidebarContainer;
    @FXML private VBox collapsibleContentArea;
    @FXML private Button btnMaximizeWorkspace;
    @FXML private Button btnMinimizeWorkspace;
    
    @FXML private TextField nameInputField;
    @FXML private TableView<AttendanceRecord> attendanceTable;
    @FXML private TableColumn<AttendanceRecord, Integer> colId;
    @FXML private TableColumn<AttendanceRecord, String> colName;
    @FXML private TableColumn<AttendanceRecord, String> colTimestamp;
    @FXML private TableColumn<AttendanceRecord, String> colStatus;

    // Multi-View Windows Dynamic Injections
    @FXML private VBox logsView;
    @FXML private VBox reportsView;
    @FXML private Button btnNavLogs;
    @FXML private Button btnNavReports;
    @FXML private Label totalRecordsLabel;
    @FXML private Label biometricScansLabel;
    @FXML private Label manualOverridesLabel;

    // Use a static list instance to preserve records safely across sign-out sessions
    private static final ObservableList<AttendanceRecord> databaseMockRegistry = FXCollections.observableArrayList();
    private static int universalPrimaryKeyIdSequence = 1001;
    
    private boolean isMaximizedState = false;
    private boolean isMinimizedState = false;

    @FXML
    public void initialize() {
        // Pre-fill placeholder items if database is totally empty on initialization
        if (databaseMockRegistry.isEmpty()) {
            databaseMockRegistry.add(new AttendanceRecord(universalPrimaryKeyIdSequence++, "Jane Doe", getFormattedSystemTime(), "Clock-In (Face ID)"));
            databaseMockRegistry.add(new AttendanceRecord(universalPrimaryKeyIdSequence++, "John Smith", getFormattedSystemTime(), "Clock-In (Face ID)"));
        }
        attendanceTable.setItems(databaseMockRegistry);
    }

    /**
     * Public method exposed to accept incoming biometric transfer identification values
     */
    public void registerScannedEmployeeCheckIn(String explicitEmployeeName) {
        databaseMockRegistry.add(new AttendanceRecord(
                universalPrimaryKeyIdSequence++, explicitEmployeeName, getFormattedSystemTime(), "Clock-In (Face ID)"
        ));
    }

    /**
     * Handles navigating views to display raw logs grid matrices.
     */
    @FXML
    private void showLogsView(ActionEvent event) {
        logsView.setVisible(true);
        reportsView.setVisible(false);

        // Render iOS navigation style button highlight triggers
        btnNavLogs.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #007AFF; -fx-alignment: center-left; -fx-font-family: 'SF Pro Text', sans-serif; -fx-font-weight: bold; -fx-background-radius: 10; -fx-pref-height: 38; -fx-max-width: Infinity;");
        btnNavReports.setStyle("-fx-background-color: transparent; -fx-text-fill: #3A3A3C; -fx-alignment: center-left; -fx-font-family: 'SF Pro Text', sans-serif; -fx-background-radius: 10; -fx-pref-height: 38; -fx-max-width: Infinity;");
    }

    /**
     * Handles navigating views to display compiled statistics summaries.
     */
    @FXML
    private void showReportsView(ActionEvent event) {
        logsView.setVisible(false);
        reportsView.setVisible(true);

        // Render iOS navigation style button highlight triggers
        btnNavLogs.setStyle("-fx-background-color: transparent; -fx-text-fill: #3A3A3C; -fx-alignment: center-left; -fx-font-family: 'SF Pro Text', sans-serif; -fx-background-radius: 10; -fx-pref-height: 38; -fx-max-width: Infinity;");
        btnNavReports.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #007AFF; -fx-alignment: center-left; -fx-font-family: 'SF Pro Text', sans-serif; -fx-font-weight: bold; -fx-background-radius: 10; -fx-pref-height: 38; -fx-max-width: Infinity;");

        // Compute running statistics dynamically from our active registry database lists
        int total = databaseMockRegistry.size();
        long biometricCount = databaseMockRegistry.stream()
                .filter(record -> record.getStatus().contains("Face ID"))
                .count();
        long manualCount = total - biometricCount;

        // Render computations instantly to text layouts
        totalRecordsLabel.setText("Total Records Logged: " + total + " entries");
        biometricScansLabel.setText("Biometric Face ID Matches: " + biometricCount + " records recognized");
        manualOverridesLabel.setText("Manual Administrative Overrides: " + manualCount + " modifications");
    }

    /**
     * Toggles layout maximization state by completely collapsing the sidebar navigation matrix.
     */
    @FXML
    private void toggleMaximizeWorkspace(ActionEvent event) {
        isMaximizedState = !isMaximizedState;
        
        if (isMaximizedState) {
            sidebarContainer.setVisible(false);
            sidebarContainer.setManaged(false); // Free up design layout space completely
            btnMaximizeWorkspace.setText("Show Sidebar");
        } else {
            sidebarContainer.setVisible(true);
            sidebarContainer.setManaged(true);
            btnMaximizeWorkspace.setText("Maximize View");
        }
    }

    /**
     * Toggles minimization state of internal content tracking grid metrics.
     */
    @FXML
    private void toggleMinimizeWorkspace(ActionEvent event) {
        isMinimizedState = !isMinimizedState;
        
        if (isMinimizedState) {
            collapsibleContentArea.setVisible(false);
            collapsibleContentArea.setManaged(false);
            btnMinimizeWorkspace.setText("Expand Layout");
        } else {
            collapsibleContentArea.setVisible(true);
            collapsibleContentArea.setManaged(true);
            btnMinimizeWorkspace.setText("Collapse Layout");
        }
    }

    @FXML
    private void handleAddRecord(ActionEvent event) {
        String inputIdentityNameString = nameInputField.getText().trim();
        if (inputIdentityNameString.isEmpty()) {
            displayMaterialAlertDialog("Input Required", "Name Field Empty", "Please type an employee name before selecting add.");
            return;
        }

        databaseMockRegistry.add(new AttendanceRecord(
                universalPrimaryKeyIdSequence++, inputIdentityNameString, getFormattedSystemTime(), "Manual Override"
        ));
        nameInputField.clear();
    }

    @FXML
    private void handleDeleteRecord(ActionEvent event) {
        AttendanceRecord targetSelectionNodeIndex = attendanceTable.getSelectionModel().getSelectedItem();
        if (targetSelectionNodeIndex == null) {
            displayMaterialAlertDialog("Selection Required", "No Row Selected", "Please select a log record from the table list to delete.");
            return;
        }
        databaseMockRegistry.remove(targetSelectionNodeIndex);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Parent securityLoginScreenNodeRoot = FXMLLoader.load(getClass().getResource("/com/groupsix/facescan/Login.fxml"));
            Stage targetedActiveWindowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene securityVerificationScreenScene = new Scene(securityLoginScreenNodeRoot);
            targetedActiveWindowStage.setScene(securityVerificationScreenScene);
            targetedActiveWindowStage.centerOnScreen();
            targetedActiveWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFormattedSystemTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void displayMaterialAlertDialog(String title, String summary, String body) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(summary);
        alert.setContentText(body);
        alert.showAndWait();
    }
}