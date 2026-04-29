module d {
    requires javafx.controls;
    requires javafx.fxml;

    opens d to javafx.fxml;
    exports d;
}
