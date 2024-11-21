package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.Schedule;

import java.io.*;
import java.util.*;

public class ScheduleDataManager {
    private static ScheduleDataManager instance;

    private ScheduleDataManager() {}

    public static synchronized ScheduleDataManager getInstance() {
        if (instance == null) {
            instance = new ScheduleDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.SCHEDULES_DB_FILE_PATH_SUFFIX;

    // Save all schedules grouped by institution
    public void saveAllSchedules(Map<Institutions, Map<String, Schedule>> institutionSchedules) {
        for (Map.Entry<Institutions, Map<String, Schedule>> entry : institutionSchedules.entrySet()) {
            saveSchedulesByInstitution(entry.getKey(), entry.getValue());
        }
    }

    // Save schedules for a specific institution
    public void saveSchedulesByInstitution(Institutions institutionID, Map<String, Schedule> schedules) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(schedules);
            System.out.println("Schedules saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving schedules for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load all schedules grouped by institution
    public Map<Institutions, Map<String, Schedule>> loadAllSchedules(List<Institutions> institutionIDs) {
        Map<Institutions, Map<String, Schedule>> institutionSchedules = new HashMap<>();
        for (Institutions institutionID : institutionIDs) {
            institutionSchedules.put(institutionID, loadSchedulesByInstitution(institutionID));
        }
        return institutionSchedules;
    }

    // Load schedules for a specific institution
    @SuppressWarnings("unchecked")
    public Map<String, Schedule> loadSchedulesByInstitution(Institutions institutionID) {
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
}

