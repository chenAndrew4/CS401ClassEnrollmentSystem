package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.Schedule;

import java.io.*;
import java.util.*;

public class ScheduleDataManager {
    private static ScheduleDataManager instance;

    // Internal map to store schedules grouped by institution
    private final Map<Institutions, Map<String, Schedule>> institutionSchedules;

    private ScheduleDataManager() {
        institutionSchedules = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllSchedules);
    }

    public static synchronized ScheduleDataManager getInstance() {
        if (instance == null) {
            instance = new ScheduleDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.SCHEDULES_DB_FILE_PATH_SUFFIX;

    // Save all schedules to disk
    public synchronized void saveAllSchedules() {
        for (Map.Entry<Institutions, Map<String, Schedule>> entry : institutionSchedules.entrySet()) {
            saveSchedulesByInstitution(entry.getKey(), entry.getValue());
        }
    }

    // Save schedules for a specific institution
    public synchronized void saveSchedulesByInstitution(Institutions institutionID, Map<String, Schedule> schedules) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(schedules);
            System.out.println("Schedules saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving schedules for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load all schedules from disk
    public synchronized void loadAllSchedules(List<Institutions> institutionIDs) {
        for (Institutions institutionID : institutionIDs) {
            Map<String, Schedule> schedules = loadSchedulesByInstitution(institutionID);
            institutionSchedules.put(institutionID, schedules);
        }
    }

    // Load schedules for a specific institution
    @SuppressWarnings("unchecked")
    public synchronized Map<String, Schedule> loadSchedulesByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
        Map<String, Schedule> schedules = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            schedules = (Map<String, Schedule>) ois.readObject();
            System.out.println("Schedules loaded successfully for institution: " + institutionID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
            schedules = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading schedules for institution: " + institutionID);
            e.printStackTrace();
        }

        return schedules;
    }

    // CRUD Operations

    // Create or Update a Schedule
    public synchronized boolean saveSchedule(Institutions institutionID, String scheduleID, Schedule schedule) {
        institutionSchedules.putIfAbsent(institutionID, new HashMap<>());
        Map<String, Schedule> schedules = institutionSchedules.get(institutionID);

        schedules.put(scheduleID, schedule);
        saveSchedulesByInstitution(institutionID, schedules);
        return true;
    }

    // Read a Schedule (Defensive Copy)
    public synchronized Schedule getSchedule(Institutions institutionID, String scheduleID) {
        if (!institutionSchedules.containsKey(institutionID)) {
            return null; // Institution not found
        }
        Schedule schedule = institutionSchedules.get(institutionID).get(scheduleID);
        return schedule != null ? new Schedule(schedule) : null; // Copy constructor required in Schedule
    }

    // Read all schedules for an institution (Unmodifiable Map)
    public synchronized Map<String, Schedule> getAllSchedules(Institutions institutionID) {
        if (!institutionSchedules.containsKey(institutionID)) {
            return Collections.emptyMap(); // Return empty map if no schedules
        }
        Map<String, Schedule> original = institutionSchedules.get(institutionID);
        Map<String, Schedule> copiedMap = new HashMap<>();
        for (Map.Entry<String, Schedule> entry : original.entrySet()) {
            copiedMap.put(entry.getKey(), new Schedule(entry.getValue())); // Copy each Schedule
        }
        return Collections.unmodifiableMap(copiedMap);
    }

    // Delete a Schedule
    public synchronized boolean deleteSchedule(Institutions institutionID, String scheduleID) {
        if (!institutionSchedules.containsKey(institutionID)) {
            return false; // Institution not found
        }

        Map<String, Schedule> schedules = institutionSchedules.get(institutionID);
        if (schedules.remove(scheduleID) != null) {
            saveSchedulesByInstitution(institutionID, schedules); // Save changes to disk
            return true;
        }
        return false; // Schedule not found
    }

    // Delete all schedules for an institution
    public synchronized boolean deleteAllSchedules(Institutions institutionID) {
        if (institutionSchedules.remove(institutionID) != null) {
            // Also delete the file on disk
            String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
            File file = new File(filePath);
            if (file.exists() && file.delete()) {
                System.out.println("All schedules deleted for institution: " + institutionID);
            }
            return true;
        }
        return false; // Institution not found
    }
}

