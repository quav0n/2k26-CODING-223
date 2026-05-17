package m7;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty course;

    public Student(String id, String name, String course) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.course = new SimpleStringProperty(course);
    }

    public StringProperty idProperty() { 
        return id; 
    }

    public StringProperty nameProperty() { 
        return name; 
    }

    public StringProperty courseProperty() { 
        return course; 
    }

    public String getId() { 
        return id.get(); 
    }

    public String getName() { 
        return name.get(); 
    }

    public String getCourse() { 
        return course.get(); 
    }

    public void setId(String value) { 
        id.set(value); 
    }

    public void setName(String value) { 
        name.set(value); 
    }

    public void setCourse(String value) { 
        course.set(value); 
    }
}
