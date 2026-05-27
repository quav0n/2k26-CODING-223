module com.groupsix.facescan {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.groupsix.facescan to javafx.fxml;
    exports com.groupsix.facescan;
}
