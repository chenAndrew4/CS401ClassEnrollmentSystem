package shared.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IDGenerator {
    private static IDGenerator instance;
    private final Set<String> courseIDs = new HashSet<>();
    private final Set<String> sectionIDs = new HashSet<>();
    private final Set<String> scheduleIDs = new HashSet<>();

    // Private constructor to prevent instantiation
    private IDGenerator() {}

    // Synchronized method to get the single instance
    public static synchronized IDGenerator getInstance() {
        if (instance == null) {
            instance = new IDGenerator();
        }
        return instance;
    }

    public String generateUniqueCourseID(String prefix) {
        String id;
        do {
            id = prefix + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (!courseIDs.add(id));
        return id;
    }

    public String generateUniqueSectionID(String courseID) {
        String id;
        do {
            id = courseID + "-SEC-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        } while (!sectionIDs.add(id));
        return id;
    }

    public String generateUniqueScheduleID(String sectionID) {
        String id;
        do {
            id = sectionID + "-SCH-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        } while (!scheduleIDs.add(id));
        return id;
    }

    public void reset() {
        courseIDs.clear();
        sectionIDs.clear();
        scheduleIDs.clear();
    }

    // Load IDs from existing data
    public synchronized void loadIDsFromData(Set<String> courseIds, Set<String> sectionIds, Set<String> scheduleIds) {
        courseIDs.addAll(courseIds);
        sectionIDs.addAll(sectionIds);
        scheduleIDs.addAll(scheduleIds);
    }
}
