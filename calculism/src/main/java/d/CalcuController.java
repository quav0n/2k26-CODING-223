package d;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CalcuController {
    @FXML private Text display;
    private double number1 = 0;
    private String operator = "";
    private boolean start = true;
    private calc model = new calc();

    @FXML
    private void processNumbers(ActionEvent event) {
        if (start) {
            display.setText("");
            start = false;
        }
        String value = ((Button)event.getSource()).getText();
        if (value.equals(".") && display.getText().contains(".")) return;
        display.setText(display.getText() + value);
    }
    @FXML
    private void processOperators(ActionEvent event) {
        String value = ((Button)event.getSource()).getText();
        if (!"=".equals(value)) {
            if (!operator.isEmpty()) return;
            operator = value;
            number1 = Double.parseDouble(display.getText());
            display.setText("");
        } else {
            if (operator.isEmpty()) return;
            double number2 = Double.parseDouble(display.getText());
            display.setText(String.valueOf(model.calculate(number1, number2, operator)));
            operator = "";
            start = true;
        }
    }

    @FXML
    private void handleSpecial(ActionEvent event) {
        String type = ((Button)event.getSource()).getText();
        if (type.equals("C")) {
            display.setText("0");
            start = true;
            operator = "";
        } else {
            double val = Double.parseDouble(display.getText());
            display.setText(String.valueOf(model.specialshii(type, val)));
        }
    }
}