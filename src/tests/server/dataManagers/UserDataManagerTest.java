package tests.server.dataManagers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.dataManagers.UserDataManager;
import shared.enums.*;
import shared.models.*;

import java.io.IOException;
import java.util.*;

public class UserDataManagerTest {
    static UserDataManager userDataManager;

    @BeforeAll
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
        Administrator admin = new Administrator();
        admin.setUserId(generateUUID());
        admin.setUsername(adminUsername);
        admin.setPassword(commonPassword);
        admin.setFirstName("Admin");
        admin.setLastName(institution + " User");
        admin.setInstitutionID(institution);
        admin.setAccountType(AccountType.Administrator);
        admin.setDate(new Date()); // Current date
        admin.setGenderIdentity(GenderIdentity.MALE);
        userDataManager.addUserByInstitution(institution, admin);

        // Faculty
        Faculty faculty = new Faculty();
        faculty.setUserId(generateUUID());
        faculty.setUsername(facultyUsername);
        faculty.setPassword(commonPassword);
        faculty.setFirstName("Faculty");
        faculty.setLastName(institution + " User");
        faculty.setInstitutionID(institution);
        faculty.setAccountType(AccountType.Faculty);
        faculty.setDepartment(Department.CS); // Example department
        faculty.setDate(new Date());
        faculty.setGenderIdentity(GenderIdentity.NONE);
        userDataManager.addUserByInstitution(institution, faculty);

        // Student
        Student student = new Student();
        student.setUserId(generateUUID());
        student.setUsername(studentUsername);
        student.setPassword(commonPassword);
        student.setFirstName("Student");
        student.setLastName(institution + " User");
        student.setInstitutionID(institution);
        student.setAccountType(AccountType.Student);
        student.setDate(new Date());
        student.setGenderIdentity(GenderIdentity.FEMALE);
        userDataManager.addUserByInstitution(institution, student);
    }

    private static String generateUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    @Test
    public void testSaveUser() {
        userDataManager.commitDBByInstitution(Institutions.SJSU);
        userDataManager.commitDBByInstitution(Institutions.CSUEB);
        userDataManager.commitDBByInstitution(Institutions.CSUF);
    }

    @Test
    public void testLoadUserSJSU() {
        Map<String, User> userMap = userDataManager.getUsersByInstitution(Institutions.SJSU);
        System.out.println("SJSU Users:");
        for (User u : userMap.values()) {
            System.out.println(getUserDetails(u));
        }
    }

    @Test
    public void testLoadUserCSUEB() {
        Map<String, User> userMap = userDataManager.getUsersByInstitution(Institutions.CSUEB);
        System.out.println("CSUEB Users:");
        for (User u : userMap.values()) {
            System.out.println(getUserDetails(u));
        }
    }

    @Test
    public void testLoadUserCSUF() {
        Map<String, User> userMap = userDataManager.getUsersByInstitution(Institutions.CSUF);
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
