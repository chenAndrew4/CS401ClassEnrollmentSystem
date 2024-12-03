package tests.server.dataManagers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;

//import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shared.models.WaitList;
import server.dataManagers.WaitlistDataManager;
import shared.enums.Institutions;

class WaitlistDataManagerTest {
	private static WaitlistDataManager waitlistDataManager;
	
	@BeforeEach
	void setUp() {
		waitlistDataManager = WaitlistDataManager.getInstance();
	}
	
	@AfterEach
	void cleanUp() {
		waitlistDataManager.removeAllWaitlists(Institutions.CSUEB); 
	}
	
	@Test
	void testAddOrUpdate() {
		Institutions institution = Institutions.CSUEB;
		String sectionID = "7837";
		WaitList waitlist = new WaitList(institution,sectionID, 30);
		
		boolean added = waitlistDataManager.addOrUpdateWaitlist(institution, sectionID, waitlist);
		assertTrue(added);
		
		WaitList get = waitlistDataManager.getWaitlist(institution, sectionID);
		
		assertEquals(sectionID, get.getSectionID());
        
		WaitList waitlist1 = new WaitList(institution,sectionID, 31);
        
		boolean updated = waitlistDataManager.addOrUpdateWaitlist(institution, sectionID, waitlist1);
		assertTrue(updated);
		
		WaitList getUpdate = waitlistDataManager.getWaitlist(institution, sectionID);

		assertEquals(31, getUpdate.getMaxCapacity());

	}
	
//	@Ignore
//	void testSaveAndLoadAllWaitLists() {
//		Institutions institution = Institutions.CSUEB;
//		String sectionID = "7837";
//		WaitList waitlist = new WaitList(institution, sectionID, 30);
//
//		String waitlistId = waitlist.getWaitlistID();
//
//		waitlistDataManager.addOrUpdateWaitlist(institution, sectionID, waitlist);
//		waitlistDataManager.saveAllWaitlists();
//		waitlistDataManager.removeAllWaitlists(Institutions.CSUEB);
//		waitlistDataManager.loadAllWaitlists();
//
//		WaitList get = waitlistDataManager.getWaitlistByWaitlistID(waitlistId);
//
//		assertEquals(sectionID, get.getSectionID());
//	}
	
	@Test
	void testGetAllWaitLists() {
		Institutions institution = Institutions.CSUEB;
		WaitList waitlist1 = new WaitList(institution, "7837", 30);
		WaitList waitlist2 = new WaitList(institution, "7838", 31);
        
		waitlistDataManager.addOrUpdateWaitlist(institution, "7837", waitlist1);
		waitlistDataManager.addOrUpdateWaitlist(institution, "7838", waitlist2);
        
		Map<String, WaitList> waitlists = waitlistDataManager.getAllWaitlists(institution);

		assertEquals(2, waitlists.size());
		assertTrue(waitlists.containsKey("7837"));
		assertTrue(waitlists.containsKey("7838"));		
	}

	@Test
	void testGetWaitlistIDsByInstitution() {
		WaitList waitlist1 = new WaitList(Institutions.CSUEB, "7837", 30);
		WaitList waitlist2 = new WaitList(Institutions.SJSU, "7838", 31);
        
		waitlistDataManager.addOrUpdateWaitlist(Institutions.CSUEB, "7837", waitlist1);
		waitlistDataManager.addOrUpdateWaitlist(Institutions.SJSU, "7838", waitlist2);
        
		Set<String> waitListIDs = waitlistDataManager.getWaitlistIDsByInstitution(Institutions.CSUEB);

		assertEquals(1, waitListIDs.size());
		assertTrue(waitListIDs.contains("7837"));
	}
	
	@Test
	void testRemoveWaitlist() {
		Institutions institution = Institutions.CSUEB;
		WaitList waitlist1 = new WaitList(institution, "7837", 30);
		WaitList waitlist2 = new WaitList(institution, "7838", 31);
        
		waitlistDataManager.addOrUpdateWaitlist(institution, "7837", waitlist1);
		waitlistDataManager.addOrUpdateWaitlist(institution, "7838", waitlist2);
        
		waitlistDataManager.removeWaitlist(institution, "7837");
        
		Map<String, WaitList> waitlists = waitlistDataManager.getAllWaitlists(institution);

		assertEquals(1, waitlists.size());
		assertFalse(waitlists.containsKey("7837"));
	}
}
