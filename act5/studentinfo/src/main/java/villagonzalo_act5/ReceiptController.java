package villagonzalo_act5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReceiptController {
@FXML private Label nameDisplay;
    @FXML private Label idDisplay;
    @FXML private Label courseDisplay;
    @FXML private Label yearDisplay;
    @FXML private Button doneButton;

    @FXML
    public void initialize() {
        doneButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
                Parent root = loader.load();
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                
                Stage currentStage = (Stage) doneButton.getScene().getWindow();
                currentStage.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setStudentData(String name, String id, String course, String year) {
        nameDisplay.setText(name);
        idDisplay.setText(id);
        courseDisplay.setText(course);
        yearDisplay.setText(year);
    }
} 