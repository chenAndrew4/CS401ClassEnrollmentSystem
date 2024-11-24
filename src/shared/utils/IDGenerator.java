package shared.utils;

import server.dataManagers.CoursesDataManager;
import server.dataManagers.ScheduleDataManager;
import server.dataManagers.UserDataManager;
import shared.enums.Institutions;

import java.util.*;

public class IDGenerator {
    private static IDGenerator instance;

    // Singleton Pattern
    private IDGenerator() {}

    public static synchronized IDGenerator getInstance() {
        if (instance == null) {
            instance = new IDGenerator();
        }
        return instance;
    }

    // Methods to generate unique IDs
    public String generateUniqueCourseID(Institutions institution, String prefix) {
        String id;
        Set<String> courseIDs = CoursesDataManager.getInstance().getCourseIDsByInstitution(institution);

        do {
            id = prefix + generateNumericUUID(3); // Example: Generate a 3-digit numeric ID
        } while (courseIDs.contains(id));

        return id;
    }

    public String generateUniqueSectionID(Institutions institution, String courseID) {
        String id;
        Set<String> sectionIDs = CoursesDataManager.getInstance().getSectionIDsByInstitution(institution);

        do {
            id = courseID + "-SEC" + generateNumericUUID(3); // Example: Generate a 3-digit numeric ID
        } while (sectionIDs.contains(id));

        return id;
    }

    public String generateUniqueScheduleID(Institutions institution) {
        String id;
        Set<String> scheduleIDs = ScheduleDataManager.getInstance().getScheduleIDsByInstitution(institution);
        do {
            id = "SCH" + generateNumericUUID(4); // Example: Generate a 4-digit numeric ID
        } while (scheduleIDs.contains(id));

        return id;
    }

    public String generateUniqueUserID(Institutions institution) {
        String id;
        Set<String> userIDs = UserDataManager.getInstance().getUserIDsByInstitution(institution);

        do {
            id = institution + "-USER-" + generateNumericUUID(5); // Example: Generate an 5-digit numeric ID
        } while (userIDs.contains(id));

        return id;
    }

    // Helper method to generate numeric UUIDs
    private String generateNumericUUID(int length) {
        String numericUUID = UUID.randomUUID().toString().replaceAll("\\D", ""); // Remove all non-numeric characters
        return numericUUID.substring(0, Math.min(length, numericUUID.length())); // Ensure the length matches
    }
}

