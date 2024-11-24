package server.dataManagers;

import java.util.ArrayList;
import java.util.List;

// for centralize data saving before the server shut down
public class DataSaveManager {
    private static DataSaveManager instance;
    private final List<Runnable> saveTasks;

    private DataSaveManager() {
        saveTasks = new ArrayList<>();
    }

    public static synchronized DataSaveManager getInstance() {
        if (instance == null) {
            instance = new DataSaveManager();
        }
        return instance;
    }

    // Register a save task
    public synchronized void registerSaveTask(Runnable saveTask) {
        saveTasks.add(saveTask);
    }

    // Trigger all save tasks
    public synchronized void saveAll() {
        for (Runnable saveTask : saveTasks) {
            saveTask.run();
        }
    }
}
