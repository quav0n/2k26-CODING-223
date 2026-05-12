package m6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NetjeansController extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("netjeans.fxml"));
        
        primaryStage.setTitle("Netjeans");
        primaryStage.setScene(new Scene(root, 950, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
