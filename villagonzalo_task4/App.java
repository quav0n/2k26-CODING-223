package villagonzalo_task4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        TextField num1 = new TextField();
        num1.setPromptText("Enter first number");
        num1.setStyle("-fx-font-size: 18px; -fx-background-color: #1c1c1c; -fx-text-fill: white;");

        TextField num2 = new TextField();
        num2.setPromptText("Enter second number");
        num2.setStyle("-fx-font-size: 18px; -fx-background-color: #1c1c1c; -fx-text-fill: white;");

        Label resultLabel = new Label("0");
        resultLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label resultText = new Label("Result:");
        resultText.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        VBox resultBox = new VBox(resultText, resultLabel);
        resultBox.setAlignment(Pos.CENTER_RIGHT);

        Button add = createButton("+", "#505050");
        Button sub = createButton("-", "#505050");
        Button mul = createButton("×", "#505050");
        Button div = createButton("÷", "#ff9500"); // orange like iPhone

        add.setOnAction(e -> calculate(num1, num2, resultLabel, "+"));
        sub.setOnAction(e -> calculate(num1, num2, resultLabel, "-"));
        mul.setOnAction(e -> calculate(num1, num2, resultLabel, "*"));
        div.setOnAction(e -> calculate(num1, num2, resultLabel, "/"));

        HBox buttons = new HBox(10, add, sub, mul, div);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, num1, num2, resultBox, buttons);
        root.setStyle("-fx-background-color: black; -fx-padding: 20;");
        
        Scene scene = new Scene(root, 350, 400);

        stage.setTitle("Blacked Calculator");
        stage.setScene(scene);
        stage.show();
    }

    // Button design
    private Button createButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPrefSize(70, 70);
        btn.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-background-radius: 50;" +
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;"
        );
        return btn;
    }
    private void calculate(TextField num1, TextField num2, Label resultLabel, String op) {
        try {
            double n1 = Double.parseDouble(num1.getText());
            double n2 = Double.parseDouble(num2.getText());
            double result = 0;

            switch (op) {
                case "+": result = n1 + n2; break;
                case "-": result = n1 - n2; break;
                case "*": result = n1 * n2; break;
                case "/":
                    if (n2 == 0) {
                        showError("Cannot divide by zero!");
                        return;
                    }
                    result = n1 / n2;
                    break;
            }
            resultLabel.setText(String.valueOf(result));
        } catch (Exception e) {
            showError("Devalid! don't put text");
        }
    }
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Blacked Error");
        alert.setHeaderText("ERRAWR");
        alert.setContentText(msg);
        alert.getDialogPane().setStyle("-fx-background-color: #ff9500; -fx-text-fill: white;");
        alert.showAndWait();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}