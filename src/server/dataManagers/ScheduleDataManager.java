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

	public void addOrUpdateSchedule(Institutions institutionID, String sectionID, Schedule updatedSchedule) {
		// TODO Auto-generated method stub
		
	}

	public Schedule getSchedule(Institutions institutionID, String scheduleID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Schedule> getAllSchedules(Institutions institutionID) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteAllSchedules(Institutions institutionID) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteSchedule(Institutions institutionID, String scheduleID) {
		// TODO Auto-generated method stub
		return false;
	}
}


