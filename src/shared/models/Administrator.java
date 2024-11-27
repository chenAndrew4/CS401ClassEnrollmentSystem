package shared.models;

import server.dataManagers.CoursesDataManager;
import server.service.ScheduleService;
import shared.enums.*;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    private List<User> managedUsers; // All users managed by the administrator

    public Administrator(Institutions institutionID) {
        super(institutionID);
        this.managedUsers = new ArrayList<>();
    }

    public Administrator(Administrator other) {
        super(other);
        this.managedUsers = other.managedUsers;
    }


    // Constructor
    public Administrator(String username,String firstName, String lastName, String password, Institutions institutionID, Department department, GenderIdentity genderIdentity, String phone, String address) {
        super(username, firstName, lastName, password, institutionID, department,AccountType.Administrator, genderIdentity, phone, address);
        this.managedUsers = new ArrayList<>();
    }
    
    

    public List<User> getManagedUsers() {
		return managedUsers;
	}

	public void setManagedUsers(List<User> managedUsers) {
		this.managedUsers = managedUsers;
	}

	// Add a student
    public boolean addStudent(Student student) {
        if (!managedUsers.contains(student)) {
            managedUsers.add(student);
            return true;
        }
        return false;
    }

    // Add a faculty
    public boolean addFaculty(Faculty faculty) {
        if (!managedUsers.contains(faculty)) {
            managedUsers.add(faculty);
            return true;
        }
        return false;
    }

    // Generate a report to do
}

