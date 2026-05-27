package com.groupsix.facescan;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AttendanceRecord {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty employeeName;
    private final SimpleStringProperty timestamp;
    private final SimpleStringProperty status;

    public AttendanceRecord(int id, String employeeName, String timestamp, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.timestamp = new SimpleStringProperty(timestamp);
        this.status = new SimpleStringProperty(status);
    }

    public int getId() { return id.get(); }
    public SimpleIntegerProperty idProperty() { return id; }

    public String getEmployeeName() { return employeeName.get(); }
    public SimpleStringProperty employeeNameProperty() { return employeeName; }

    public String getTimestamp() { return timestamp.get(); }
    public SimpleStringProperty timestampProperty() { return timestamp; }

    public String getStatus() { return status.get(); }
    public SimpleStringProperty statusProperty() { return status; }
}