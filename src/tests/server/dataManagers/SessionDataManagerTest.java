package tests.server.dataManagers;

import server.dataManagers.SessionDataManager;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionDataManagerTest {
	private static SessionDataManager sessionDataManager;
	@BeforeEach
	void setUp() {
		sessionDataManager = SessionDataManager.getInstance();
	}
	
	@Test
	void testSaveLoadSessionData() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		sessionDataManager.addOrUpdateSession("user1", "session1");
		
		sessionDataManager.saveSessionData();
		
		sessionDataManager.removeSession("user1");
		
		assertNull(sessionDataManager.getSessionData("user1"));
				 
		Method loadSessionDataMethod = SessionDataManager.class.getDeclaredMethod("loadSessionData");
		loadSessionDataMethod.setAccessible(true); 
		loadSessionDataMethod.invoke(sessionDataManager); 
                
		assertEquals("session1", sessionDataManager.getSessionData("user1"));
        
		sessionDataManager.removeSession("user1");
    }
	
	@Test
	void testGetAllSessionData() throws Exception {
		sessionDataManager.addOrUpdateSession("user1", "session1");
		sessionDataManager.addOrUpdateSession("user2", "session2");

		Map<String, String> allSessionData = sessionDataManager.getAllSessionData();

		assertEquals("session1", allSessionData.get("user1"));
		assertEquals("session2", allSessionData.get("user2"));
	    
		sessionDataManager.removeSession("user1");
		sessionDataManager.removeSession("user2");
	}
}
