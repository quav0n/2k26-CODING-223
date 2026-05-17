package m7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HardController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colCourse;
    @FXML private TableColumn<Student, Void> colAction;

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private ComboBox<String> cbCourse;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private Student selectedStudent = null;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));

        setupActionColumn();

        studentList.add(new Student("567896", "Mark Jian Lanzar", "Computer Engineering"));
        studentList.add(new Student("689543", "Theo Damian", "Criminology"));
        studentTable.setItems(studentList);

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                populateFields(newVal);
            }
        });
    }

private void setupActionColumn() {
    colAction.setCellFactory(param -> new TableCell<Student, Void>() {
        private final MenuButton menuButton = new MenuButton("•••");

        {
            MenuItem updateItem = new MenuItem("Edit Record");
            MenuItem deleteItem = new MenuItem("Delete");
            
            deleteItem.setStyle("-fx-text-fill: #ff3b30;"); 

            updateItem.setOnAction(e -> {
                Student target = getTableView().getItems().get(getIndex());
                populateFields(target);
            });

            deleteItem.setOnAction(e -> {
                Student target = getTableView().getItems().get(getIndex());
                studentList.remove(target);
                clearFields();
            });

            menuButton.getItems().addAll(updateItem, deleteItem);
            
            menuButton.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #8e8e93; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12; " +
                "-fx-cursor: hand;"
            );
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(menuButton);
            }
            }
        });
    }

    private void populateFields(Student student) {
        selectedStudent = student;
        txtId.setText(student.getId());
        txtName.setText(student.getName());
        cbCourse.setValue(student.getCourse());
    }

    @FXML
    private void handleSave() {
        if (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty()) {
            return;
        }

        if (selectedStudent != null) {
            selectedStudent.setId(txtId.getText());
            selectedStudent.setName(txtName.getText());
            selectedStudent.setCourse(cbCourse.getValue());
            studentTable.refresh(); 
            clearFields();
        } else {
            studentList.add(new Student(txtId.getText(), txtName.getText(), cbCourse.getValue()));
            clearFields();
        }
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        cbCourse.setValue(null);
        selectedStudent = null;
        studentTable.getSelectionModel().clearSelection();
    }
}