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
        modified = new HashMap<>();
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
    private final Map<Institutions, Map<String, WaitList>> institutionWaitlists;

    // Map to track modified state for each institution
    private final Map<Institutions, Boolean> modified;

    // Save all waitlists to disk
    public synchronized void saveAllWaitlists() {
        for (Map.Entry<Institutions, Map<String, WaitList>> entry : institutionWaitlists.entrySet()) {
            Institutions institutionID = entry.getKey();
            if (Boolean.TRUE.equals(modified.getOrDefault(institutionID, false))) {
                saveWaitlistsByInstitution(institutionID, entry.getValue());
                modified.put(institutionID, false); // Reset the modified flag
            }
        }
    }

    // Save waitlists for a specific institution
    public synchronized void saveWaitlistsByInstitution(Institutions institutionID, Map<String, WaitList> waitlists) {
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;

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
        for (Institutions institutionID : Institutions.values()) {
            institutionWaitlists.put(institutionID, loadWaitlistsByInstitution(institutionID));
            modified.put(institutionID, false); // Reset modified flag after loading
        }
    }

    // Load waitlists for a specific institution from disk
    @SuppressWarnings("unchecked")
    public synchronized Map<String, WaitList> loadWaitlistsByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
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
        institutionWaitlists.putIfAbsent(institutionID, loadWaitlistsByInstitution(institutionID));
        institutionWaitlists.get(institutionID).put(sectionID, waitlist);
        modified.put(institutionID, true); // Mark as modified
        return true;
    }

    // Get a specific waitlist (Defensive Copy)
    public synchronized WaitList getWaitlist(Institutions institutionID, String sectionID) {
        if (!institutionWaitlists.containsKey(institutionID)) {
            institutionWaitlists.put(institutionID, loadWaitlistsByInstitution(institutionID));
        }
        WaitList waitlist = institutionWaitlists.get(institutionID).get(sectionID);
        return waitlist != null ? new WaitList(waitlist) : null;
    }

    // Get all waitlists for an institution (Unmodifiable Map)
    public synchronized Map<String, WaitList> getAllWaitlists(Institutions institutionID) {
        if (!institutionWaitlists.containsKey(institutionID)) {
            institutionWaitlists.put(institutionID, loadWaitlistsByInstitution(institutionID));
        }
        return Collections.unmodifiableMap(new HashMap<>(institutionWaitlists.get(institutionID)));
    }

    // Get a set of all waitlist IDs for an institution
    public synchronized Set<String> getWaitlistIDsByInstitution(Institutions institutionID) {
        if (!institutionWaitlists.containsKey(institutionID)) {
            institutionWaitlists.put(institutionID, loadWaitlistsByInstitution(institutionID));
        }
        return Collections.unmodifiableSet(new HashSet<>(institutionWaitlists.get(institutionID).keySet()));
    }

    // Get a waitlist by waitlist ID across all institutions
    public synchronized WaitList getWaitlistByWaitlistID(String waitlistID) {
        for (Map.Entry<Institutions, Map<String, WaitList>> institutionEntry : institutionWaitlists.entrySet()) {
            Map<String, WaitList> waitlists = institutionEntry.getValue();
            if (waitlists.containsKey(waitlistID)) {
                return new WaitList(waitlists.get(waitlistID));
            }
        }
        System.err.println("Waitlist not found for ID: " + waitlistID);
        return null;
    }

    // Remove a specific waitlist
    public synchronized boolean removeWaitlist(Institutions institutionID, String sectionID) {
        if (!institutionWaitlists.containsKey(institutionID)) {
            return false; // Institution not found
        }

        Map<String, WaitList> waitlists = institutionWaitlists.get(institutionID);
        if (waitlists.remove(sectionID) != null) {
            modified.put(institutionID, true); // Mark as modified
            return true;
        }
        return false;
    }

    // Remove all waitlists for an institution
    public synchronized boolean removeAllWaitlists(Institutions institutionID) {
        if (institutionWaitlists.remove(institutionID) != null) {
            modified.put(institutionID, true); // Mark as modified
            return true;
        }
        return false;
    }
}

