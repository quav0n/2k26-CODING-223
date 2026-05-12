module m6 {
    requires javafx.controls;
    requires javafx.fxml;

    opens m6 to javafx.fxml;
    exports m6;
}
