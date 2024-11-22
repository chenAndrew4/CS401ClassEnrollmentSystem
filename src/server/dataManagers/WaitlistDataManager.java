package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.WaitList;

import java.io.*;
import java.util.*;

public class WaitlistDataManager {
    private static WaitlistDataManager instance;

    private WaitlistDataManager() {
        institutionWaitlists = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllWaitlists);
    }

    public static synchronized WaitlistDataManager getInstance() {
        if (instance == null) {
            instance = new WaitlistDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.WAITLISTS_DB_FILE_PATH_SUFFIX;

    // In-memory storage for waitlists grouped by institution
    private Map<Institutions, Map<String, WaitList>> institutionWaitlists;

    // Save all waitlists to disk
    public synchronized void saveAllWaitlists() {
        for (Map.Entry<Institutions, Map<String, WaitList>> entry : institutionWaitlists.entrySet()) {
            saveWaitlistsByInstitution(entry.getKey(), entry.getValue());
        }
    }

    // Save waitlists for a specific institution
    public synchronized void saveWaitlistsByInstitution(Institutions institutionID, Map<String, WaitList> waitlists) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(waitlists);
            System.out.println("Waitlists saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving waitlists for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load all waitlists from disk into memory
    public synchronized void loadAllWaitlists() {
        for (Institutions institutionID :  Institutions.values()) {
            institutionWaitlists.put(institutionID, loadWaitlistsByInstitution(institutionID));
        }
    }

    // Load waitlists for a specific institution from disk
    @SuppressWarnings("unchecked")
    public synchronized Map<String, WaitList> loadWaitlistsByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
        Map<String, WaitList> waitlists = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            waitlists = (Map<String, WaitList>) ois.readObject();
            System.out.println("Waitlists loaded successfully for institution: " + institutionID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
            waitlists = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading waitlists for institution: " + institutionID);
            e.printStackTrace();
        }

        return waitlists;
    }

    // Add or Update a waitlist
    public synchronized boolean addOrUpdateWaitlist(Institutions institutionID, String sectionID, WaitList waitlist) {
        if (!institutionWaitlists.containsKey(institutionID)) {
            institutionWaitlists.put(institutionID,loadWaitlistsByInstitution(institutionID));
        }
        institutionWaitlists.get(institutionID).put(sectionID, waitlist);
        return true;
    }

    // Get a specific waitlist
    public synchronized WaitList getWaitlist(Institutions institutionID, String sectionID) {
        if (!institutionWaitlists.containsKey(institutionID)) {
            institutionWaitlists.put(institutionID,loadWaitlistsByInstitution(institutionID));
        }
        return new WaitList(institutionWaitlists.get(institutionID).get(sectionID));
    }

    // Get all waitlists for an institution
    public synchronized Map<String, WaitList> getAllWaitlists(Institutions institutionID) {
        Map<String, WaitList> waitlists = institutionWaitlists.get(institutionID) == null
                ? loadWaitlistsByInstitution(institutionID)
                : institutionWaitlists.get(institutionID);

        return Collections.unmodifiableMap(waitlists);
    }

    // Remove a specific waitlist
    public synchronized boolean removeWaitlist(Institutions institutionID, String sectionID) {
        if (institutionWaitlists.containsKey(institutionID)) {
            Map<String, WaitList> waitlists = institutionWaitlists.get(institutionID);
            if (waitlists.remove(sectionID) != null) {
                saveWaitlistsByInstitution(institutionID, waitlists);
                return true;
            }
        }
        return false;
    }
}

