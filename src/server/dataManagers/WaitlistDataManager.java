package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.WaitList;

import java.io.*;
import java.util.*;

public class WaitlistDataManager {
    private static WaitlistDataManager instance;

    private WaitlistDataManager() {}

    public static synchronized WaitlistDataManager getInstance() {
        if (instance == null) {
            instance = new WaitlistDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.WAITLISTS_DB_FILE_PATH_SUFFIX;

    // Save all waitlists grouped by institution
    public void saveAllWaitlists(Map<Institutions, Map<String, WaitList>> institutionWaitlists) {
        for (Map.Entry<Institutions, Map<String, WaitList>> entry : institutionWaitlists.entrySet()) {
            saveWaitlistsByInstitution(entry.getKey(), entry.getValue());
        }
    }

    // Save waitlists for a specific institution
    public void saveWaitlistsByInstitution(Institutions institutionID, Map<String, WaitList> waitlists) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(waitlists);
            System.out.println("Waitlists saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving waitlists for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load all waitlists grouped by institution
    public Map<Institutions, Map<String, WaitList>> loadAllWaitlists(List<Institutions> institutionIDs) {
        Map<Institutions, Map<String, WaitList>> institutionWaitlists = new HashMap<>();
        for (Institutions institutionID : institutionIDs) {
            institutionWaitlists.put(institutionID, loadWaitlistsByInstitution(institutionID));
        }
        return institutionWaitlists;
    }

    // Load waitlists for a specific institution
    @SuppressWarnings("unchecked")
    public Map<String, WaitList> loadWaitlistsByInstitution(Institutions institutionID) {
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
}

