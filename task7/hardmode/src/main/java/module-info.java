module m7 {
    requires javafx.controls;
    requires javafx.fxml;

    opens m7 to javafx.fxml;
    exports m7;
}
