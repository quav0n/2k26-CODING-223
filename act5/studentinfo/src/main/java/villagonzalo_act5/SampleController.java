package villagonzalo_act5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SampleController {
@FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField courseField;
    @FXML private TextField yearField;

    @FXML
    public void handleSubmit(ActionEvent event) {
        try {
            String name = nameField.getText();
            String id = idField.getText();
            String course = courseField.getText();
            String year = yearField.getText();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("receipt.fxml"));
            Parent root = loader.load();

            ReceiptController receiptController = loader.getController();
            receiptController.setStudentData(name, id, course, year);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        nameField.clear();
        idField.clear();
        courseField.clear();
        yearField.clear();
    }
}