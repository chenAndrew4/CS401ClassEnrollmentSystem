package tests.server.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import server.service.WaitlistService;
import shared.enums.Institutions;
import shared.models.Student;
import shared.models.WaitList;

class WaitListServiceTest {

	@Test 
	public void testAddRemoveFromWaitlist() {
		String sectionID = "7837";
		WaitList waitlist = new WaitList(Institutions.CSUEB, sectionID, 30);
		Student student = new Student(Institutions.CSUEB);

		WaitlistService waitlistService = WaitlistService.getInstance();

		boolean addToWaitListResult = waitlistService.addToWaitlist(Institutions.CSUEB, student, sectionID);
		assertTrue(addToWaitListResult);
		
		WaitList getResult = waitlistService.getWaitlist(Institutions.CSUEB, sectionID);
		assertEquals(sectionID, getResult.getSectionID());

		boolean removeFromWaitListResult = waitlistService.removeFromWaitlist(Institutions.CSUEB, student, sectionID);
		assertTrue(removeFromWaitListResult);
	}

	@Test
	public void testAddToAndRemove() {
		WaitList waitlist = new WaitList(Institutions.CSUEB, "7837", 30);
		Student student = new Student(Institutions.CSUEB);

		WaitlistService waitlistService = WaitlistService.getInstance();

		boolean success = waitlistService.addToWaitlist(Institutions.CSUEB, student, "7837");
		assertTrue(success);
		
		int position = waitlistService.getWaitlistPositions(Institutions.CSUEB, "7837", student);
		System.out.println(position);
		
		boolean remove = waitlistService.removeFromWaitlist(Institutions.CSUEB, student, "7837");
		assertTrue(remove);
	}
}
