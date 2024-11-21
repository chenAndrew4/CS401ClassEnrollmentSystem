package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutes;
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

    // Save all waitlists grouped by institute
    public void saveAllWaitlists(Map<Institutes, Map<String, WaitList>> instituteWaitlists) {
        for (Map.Entry<Institutes, Map<String, WaitList>> entry : instituteWaitlists.entrySet()) {
            saveWaitlistsByInstitute(entry.getKey(), entry.getValue());
        }
    }

    // Save waitlists for a specific institute
    public void saveWaitlistsByInstitute(Institutes instituteID, Map<String, WaitList> waitlists) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(waitlists);
            System.out.println("Waitlists saved successfully for institute: " + instituteID);
        } catch (Exception e) {
            System.err.println("Error saving waitlists for institute: " + instituteID);
            e.printStackTrace();
        }
    }

    // Load all waitlists grouped by institute
    public Map<Institutes, Map<String, WaitList>> loadAllWaitlists(List<Institutes> instituteIDs) {
        Map<Institutes, Map<String, WaitList>> instituteWaitlists = new HashMap<>();
        for (Institutes instituteID : instituteIDs) {
            instituteWaitlists.put(instituteID, loadWaitlistsByInstitute(instituteID));
        }
        return instituteWaitlists;
    }

    // Load waitlists for a specific institute
    @SuppressWarnings("unchecked")
    public Map<String, WaitList> loadWaitlistsByInstitute(Institutes instituteID) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;
        Map<String, WaitList> waitlists = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            waitlists = (Map<String, WaitList>) ois.readObject();
            System.out.println("Waitlists loaded successfully for institute: " + instituteID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institute: " + instituteID + ". Returning empty map.");
            waitlists = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading waitlists for institute: " + instituteID);
            e.printStackTrace();
        }

        return waitlists;
    }
}

