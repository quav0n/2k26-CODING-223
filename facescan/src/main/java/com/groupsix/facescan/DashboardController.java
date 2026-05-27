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

    private final ObservableList<AttendanceRecord> databaseMockRegistry = FXCollections.observableArrayList();
    private int universalPrimaryKeyIdSequence = 1001;
    
    private boolean isMaximizedState = false;
    private boolean isMinimizedState = false;

    @FXML
    public void initialize() {
        databaseMockRegistry.add(new AttendanceRecord(universalPrimaryKeyIdSequence++, "Jane Doe", getFormattedSystemTime(), "Verified Access"));
        databaseMockRegistry.add(new AttendanceRecord(universalPrimaryKeyIdSequence++, "John Smith", getFormattedSystemTime(), "Verified Access"));
        attendanceTable.setItems(databaseMockRegistry);
    }

    @FXML
    private void toggleMaximizeWorkspace(ActionEvent event) {
        isMaximizedState = !isMaximizedState;
        
        if (isMaximizedState) {
            sidebarContainer.setVisible(false);
            sidebarContainer.setManaged(false); 
            btnMaximizeWorkspace.setText("Show Sidebar");
        } else {
            sidebarContainer.setVisible(true);
            sidebarContainer.setManaged(true);
            btnMaximizeWorkspace.setText("Maximize View");
        }
    }

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
                universalPrimaryKeyIdSequence++, inputIdentityNameString, getFormattedSystemTime(), "Verified Access"
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