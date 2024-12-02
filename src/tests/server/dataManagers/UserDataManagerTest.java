package tests.server.dataManagers;

import client.gui.dashboard.AdminDashboardGUI;
import client.gui.dashboard.FacultyDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.dataManagers.UserDataManager;
import shared.enums.*;
import shared.models.*;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.security.PublicKey;
import java.util.*;

public class UserDataManagerTest {
    static UserDataManager userDataManager;

    public static void beforeTest() {
        userDataManager = UserDataManager.getInstance();

        // Common usernames and passwords for all institutions
        String adminUsername = "admin";
        String facultyUsername = "faculty";
        String studentUsername = "student";
        String commonPassword = "password1";

        // Create users for SJSU
        addUsersForInstitution(Institutions.SJSU, adminUsername, facultyUsername, studentUsername, commonPassword);

        // Create users for CSUEB
        addUsersForInstitution(Institutions.CSUEB, adminUsername, facultyUsername, studentUsername, commonPassword);

        // Create users for CSUF
        addUsersForInstitution(Institutions.CSUF, adminUsername, facultyUsername, studentUsername, commonPassword);
    }

    private static void addUsersForInstitution(
            Institutions institution,
            String adminUsername,
            String facultyUsername,
            String studentUsername,
            String commonPassword) {

        // Administrator
        Administrator admin = new Administrator(institution);
        admin.setUsername(adminUsername);
        admin.setPassword(commonPassword);
        admin.setFirstName("Admin");
        admin.setLastName(institution + " User");
        admin.setAccountType(AccountType.Administrator);
        admin.setDate(new Date()); // Current date
        admin.setDepartment(Department.CS);
        admin.setGenderIdentity(GenderIdentity.MALE);
        userDataManager.addUserByInstitution(institution, admin);

        // Faculty
        Faculty faculty = new Faculty(institution);
        faculty.setUsername(facultyUsername);
        faculty.setPassword(commonPassword);
        faculty.setFirstName("Faculty");
        faculty.setLastName(institution + " User");
        faculty.setAccountType(AccountType.Faculty);
        faculty.setDepartment(Department.CS); // Example department
        faculty.setDate(new Date());
        faculty.setGenderIdentity(GenderIdentity.NONE);
        userDataManager.addUserByInstitution(institution, faculty);

        // Student
        Student student = new Student(institution);
        student.setUsername(studentUsername);
        student.setPassword(commonPassword);
        student.setFirstName("Student");
        student.setLastName(institution + " User");
        student.setInstitutionID(institution);
        student.setAccountType(AccountType.Student);
        student.setDepartment(Department.CS);
        student.setDate(new Date());
        student.setGenderIdentity(GenderIdentity.FEMALE);
        userDataManager.addUserByInstitution(institution, student);
    }

    public void testDeleteDB() throws IOException {
        UserDataManager.getInstance().deleteDB();
    }

    @Test
    public void testSaveUser() {
        beforeTest();
        userDataManager.commitDBByInstitution(Institutions.SJSU);
        userDataManager.commitDBByInstitution(Institutions.CSUEB);
        userDataManager.commitDBByInstitution(Institutions.CSUF);
    }

    @Test
    public void testLoadUserSJSU() {
        Map<String, User> userMap = UserDataManager.getInstance().getUsersByInstitution(Institutions.SJSU);
        System.out.println("SJSU Users:");
        for (User u : userMap.values()) {
            if (u instanceof Student) {
                System.out.println("S " + getUserDetails(u));
            } else if (u instanceof Faculty) {
                System.out.println("F " + getUserDetails(u));
            } else if (u instanceof Administrator) {
                System.out.println("A " + getUserDetails(u));
            }

        }
    }

    @Test
    public void testLoadUserCSUEB() {
        Map<String, User> userMap = UserDataManager.getInstance().getUsersByInstitution(Institutions.CSUEB);
        System.out.println("CSUEB Users:");
        for (User u : userMap.values()) {
            System.out.println(getUserDetails(u));
        }
    }

    @Test
    public void testLoadUserCSUF() {
        Map<String, User> userMap = UserDataManager.getInstance().getUsersByInstitution(Institutions.CSUF);
        System.out.println("CSUF Users:");
        for (User u : userMap.values()) {
            System.out.println(getUserDetails(u));
        }
    }

    private String getUserDetails(User user) {
        return String.format(
                "ID: %s, Username: %s, Name: %s %s, AccountType: %s, Institution: %s, Department: %s, AdmissionDate: %s",
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getAccountType(),
                user.getInstitutionID(),
                user.getDepartment() != null ? user.getDepartment() : "N/A",
                user.getDate()
        );
    }
}
