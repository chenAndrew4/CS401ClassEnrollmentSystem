package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutes;
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

    // Save all schedules grouped by institute
    public void saveAllSchedules(Map<Institutes, Map<String, Schedule>> instituteSchedules) {
        for (Map.Entry<Institutes, Map<String, Schedule>> entry : instituteSchedules.entrySet()) {
            saveSchedulesByInstitute(entry.getKey(), entry.getValue());
        }
    }

    // Save schedules for a specific institute
    public void saveSchedulesByInstitute(Institutes instituteID, Map<String, Schedule> schedules) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(schedules);
            System.out.println("Schedules saved successfully for institute: " + instituteID);
        } catch (Exception e) {
            System.err.println("Error saving schedules for institute: " + instituteID);
            e.printStackTrace();
        }
    }

    // Load all schedules grouped by institute
    public Map<Institutes, Map<String, Schedule>> loadAllSchedules(List<Institutes> instituteIDs) {
        Map<Institutes, Map<String, Schedule>> instituteSchedules = new HashMap<>();
        for (Institutes instituteID : instituteIDs) {
            instituteSchedules.put(instituteID, loadSchedulesByInstitute(instituteID));
        }
        return instituteSchedules;
    }

    // Load schedules for a specific institute
    @SuppressWarnings("unchecked")
    public Map<String, Schedule> loadSchedulesByInstitute(Institutes instituteID) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;
        Map<String, Schedule> schedules = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            schedules = (Map<String, Schedule>) ois.readObject();
            System.out.println("Schedules loaded successfully for institute: " + instituteID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institute: " + instituteID + ". Returning empty map.");
            schedules = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading schedules for institute: " + instituteID);
            e.printStackTrace();
        }

        return schedules;
    }
}

