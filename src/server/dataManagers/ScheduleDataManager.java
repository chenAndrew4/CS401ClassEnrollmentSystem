package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.Schedule;

import java.io.*;
import java.util.*;

public class ScheduleDataManager {

    private static ScheduleDataManager instance;

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.SCHEDULES_DB_FILE_PATH_SUFFIX;

    private final Map<Institutions, Map<String, Schedule>> institutionSchedules;
    private final Map<Institutions, Boolean> modified; // Track modified institutions

    private ScheduleDataManager() {
        institutionSchedules = new HashMap<>();
        modified = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllSchedules);
    }

    public static synchronized ScheduleDataManager getInstance() {
        if (instance == null) {
            instance = new ScheduleDataManager();
        }
        return instance;
    }

    private synchronized void ensureSchedulesLoaded(Institutions institution) {
        if (!institutionSchedules.containsKey(institution)) {
            institutionSchedules.put(institution, loadSchedulesByInstitution(institution));
            modified.putIfAbsent(institution, false);
        }
    }

    // Save all schedules to disk
    public synchronized void saveAllSchedules() {
        if (institutionSchedules.isEmpty()) {
            return;
        }
        for (Map.Entry<Institutions, Map<String, Schedule>> entry : institutionSchedules.entrySet()) {
            if (Boolean.TRUE.equals(modified.get(entry.getKey()))) {
                saveSchedulesByInstitution(entry.getKey(), entry.getValue());
                modified.put(entry.getKey(), false); // Reset modification flag
            }
        }
    }

    // Save schedules for a specific institution
    public synchronized void saveSchedulesByInstitution(Institutions institutionID, Map<String, Schedule> schedules) {
        if (schedules == null || schedules.isEmpty()) {
            return;
        }
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(schedules);
            System.out.println("Schedules saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving schedules for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load all schedules from disk
    public synchronized void loadAllSchedules() {
        for (Institutions institution : Institutions.values()) {
            ensureSchedulesLoaded(institution);
        }
    }

    // Load schedules for a specific institution
    @SuppressWarnings("unchecked")
    public synchronized Map<String, Schedule> loadSchedulesByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
        Map<String, Schedule> schedules = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            schedules = (Map<String, Schedule>) ois.readObject();
            System.out.println("Schedules loaded successfully for institution: " + institutionID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
        } catch (Exception e) {
            System.err.println("Error loading schedules for institution: " + institutionID);
            e.printStackTrace();
        }
        return schedules;
    }

    // CRUD Operations

    public synchronized boolean saveSchedule(Institutions institutionID, String scheduleID, Schedule schedule) {
        if (institutionID == null || scheduleID == null || schedule == null) {
            return false;
        }
        ensureSchedulesLoaded(institutionID);

//        institutionSchedules.putIfAbsent(institutionID, new HashMap<>());
        institutionSchedules.get(institutionID).put(scheduleID, schedule);
        modified.put(institutionID, true); // Mark as modified
        return true;
    }

    public synchronized Schedule getSchedule(Institutions institutionID, String scheduleID) {
        if (institutionID == null || scheduleID == null) {
            return null;
        }
        ensureSchedulesLoaded(institutionID);

        Schedule schedule = institutionSchedules.getOrDefault(institutionID, Collections.emptyMap()).get(scheduleID);
        return schedule != null ? new Schedule(schedule) : null; // Copy constructor required in Schedule
    }

    public synchronized Map<String, Schedule> getAllSchedules(Institutions institutionID) {
        if (institutionID == null) {
            return Collections.emptyMap();
        }
        ensureSchedulesLoaded(institutionID);

        Map<String, Schedule> original = institutionSchedules.getOrDefault(institutionID, Collections.emptyMap());
        Map<String, Schedule> copiedMap = new HashMap<>();
        for (Map.Entry<String, Schedule> entry : original.entrySet()) {
            copiedMap.put(entry.getKey(), new Schedule(entry.getValue())); // Copy each Schedule
        }
        return Collections.unmodifiableMap(copiedMap);
    }

    public synchronized boolean deleteSchedule(Institutions institutionID, String scheduleID) {
        if (institutionID == null || scheduleID == null) {
            return false;
        }
        ensureSchedulesLoaded(institutionID);

        Map<String, Schedule> schedules = institutionSchedules.get(institutionID);
        if (schedules != null && schedules.remove(scheduleID) != null) {
            modified.put(institutionID, true); // Mark as modified
            return true;
        }
        return false;
    }

    public synchronized boolean deleteAllSchedules(Institutions institutionID) {
        if (institutionID == null) {
            return false;
        }

        if (institutionSchedules.remove(institutionID) != null) {
            // Delete the file on disk
            String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
            File file = new File(filePath);
            if (file.exists() && file.delete()) {
                System.out.println("All schedules deleted for institution: " + institutionID);
            }
            modified.put(institutionID, true); // Mark as modified
            return true;
        }
        return false;
    }

    public synchronized Set<String> getScheduleIDsByInstitution(Institutions institutionID) {
        if (institutionID == null) {
            return Collections.emptySet();
        }
        ensureSchedulesLoaded(institutionID);

        return Collections.unmodifiableSet(new HashSet<>(institutionSchedules.getOrDefault(institutionID, Collections.emptyMap()).keySet()));
    }
}


