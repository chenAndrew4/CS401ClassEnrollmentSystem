package shared.models;

import java.io.Serializable;

public class ClassRoster implements Serializable {
    public int getEnrollmentCount() {
        return 0;
    }

    public boolean addStudent(Student student) {

        return false;
    }

    public boolean removeStudent(Student student) {
        return true;
    }
}
