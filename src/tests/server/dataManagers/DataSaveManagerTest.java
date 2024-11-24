package tests.server.dataManagers;

import server.dataManagers.DataSaveManager;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

class DataSaveManagerTest {

    @Test
    void testRegisterSaveTask() throws NoSuchFieldException, IllegalAccessException {

    	DataSaveManager manager = DataSaveManager.getInstance();
        Runnable task = () -> System.out.println("test");

        manager.registerSaveTask(task);

        // Use reflection to access the private saveTasks field
        Field saveTasksField = DataSaveManager.class.getDeclaredField("saveTasks");
        saveTasksField.setAccessible(true);

        @SuppressWarnings("unchecked")
		List<Runnable> saveTasks = (List<Runnable>) saveTasksField.get(manager);
        
        assertTrue(saveTasks.contains(task));
    }

    @Test
    void testSaveAll() {
        DataSaveManager manager = DataSaveManager.getInstance();
        boolean[] tasksExecuted = {false, false};

        // Create tasks and set to true
        Runnable task1 = () -> tasksExecuted[0] = true;
        Runnable task2 = () -> tasksExecuted[1] = true;

        manager.registerSaveTask(task1);
        manager.registerSaveTask(task2);

        // Execute tasks
        manager.saveAll();

        // Check if all have been set to true
        assertTrue(tasksExecuted[0]);
        assertTrue(tasksExecuted[1]);
    }
}
