package tests.server.dataManagers;

import server.dataManagers.ServerLogDataManager;
import shared.enums.Institutions;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServerLogDatamanagerTest {//Date methods have not been done, maybe will do when I figure it out, need to find way to change db
	private static ServerLogDataManager serverLogDataManager;
	
	@BeforeEach
	void setUp() {
		serverLogDataManager = ServerLogDataManager.getInstance();
//		serverLogDataManager.clearLogs(Institutions.CSUEB); 
	}
	
	@Test
	void testAddLog() {
		Institutions institution = Institutions.CSUEB;
		String entry = "test"; 
		
		serverLogDataManager.addLog(institution, String.class, entry);
		
		List<String> log = serverLogDataManager.getLogsByContent(institution, entry);
	
		assertTrue(log.get(0).contains(entry));
	}
	
	@Test
	void testGetAllLogs() {
	    Institutions institution = Institutions.CSUEB;

	    serverLogDataManager.addLog(institution, String.class, "test1");
	    serverLogDataManager.addLog(institution, String.class, "test2");
	    serverLogDataManager.addLog(institution, String.class, "test3");

	    List<String> log = serverLogDataManager.getAllLogs(institution);
	    assertTrue(log.get(0).contains("test1"));
	    assertTrue(log.get(1).contains("test2"));
	    assertTrue(log.get(2).contains("test3"));
	}
	
	@Test
	void testGetLogsByType() {
		Institutions institution = Institutions.CSUEB;
		String entry = "test"; 
		
		serverLogDataManager.addLog(institution, String.class, entry);
		
		List<String> log = serverLogDataManager.getLogsByType(institution, String.class);
		
		assertTrue(log.get(0).contains(entry));
	}
	
	@Ignore
	void testGetLogsByDate() { //Don't know how to work with date
	}
	
	@Ignore
	void testGetLogsByInstitutionIDAndDate() {
	}
	
	@Ignore
	void testGetLogsByTimeRange() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testSaveAndLoadAllLogs() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Institutions institution = Institutions.CSUEB;

	    serverLogDataManager.addLog(institution, String.class, "test1");
	    serverLogDataManager.addLog(institution, String.class, "test2");
	    
	    Field modifiedField = ServerLogDataManager.class.getDeclaredField("modified");
	    modifiedField.setAccessible(true);
	    Map<Institutions, Map<Class<?>, Boolean>> modifiedStatus = (Map<Institutions, Map<Class<?>, Boolean>>) modifiedField.get(serverLogDataManager);
	    
	    System.out.println("Before saving logs:");
	    System.out.println("Modified status: " + modifiedStatus);

	    serverLogDataManager.saveAllLogs();

	    modifiedStatus = (Map<Institutions, Map<Class<?>, Boolean>>) modifiedField.get(serverLogDataManager);
	    System.out.println("After saving logs:");
	    System.out.println("Modified status: " + modifiedStatus);
        
//	    serverLogDataManager.clearLogs(institution);  
	    serverLogDataManager.loadAllLogs();  

	    List<String> log = serverLogDataManager.getAllLogs(institution);
	    
	    assertTrue(log.get(0).contains("test1"));
	    assertTrue(log.get(1).contains("test2"));
	}
}
