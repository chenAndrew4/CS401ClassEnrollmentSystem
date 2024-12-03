package tests.server.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.service.ScheduleService;
import shared.enums.Building;
import shared.enums.Campus;
import shared.enums.Days;
import shared.enums.Institutions;
import shared.enums.Room;
import shared.enums.Term;
import shared.enums.Time;
import shared.models.Faculty;
import shared.models.Schedule;

class ScheduleServiceTest {
	private static ScheduleService scheduleService;
	@BeforeEach
	void setUp() {
		scheduleService = ScheduleService.getInstance();

	}
	
	@Test
	public void testAddGetRemoveExistValidate() {
        Schedule schedule = new Schedule(Institutions.CSUEB, "Math102", "7838", Term.FALL_SEMESTER,
                new Days[]{Days.MONDAY, Days.WEDNESDAY}, null, null, Time.TIME_700, Time.TIME_815, 
                Building.BUSINESS_HALL, Campus.HAYWARD, Room.ROOM1, "John Smith");
        
        String ScheduleID = schedule.getScheduleID();
        
        boolean validate = scheduleService.validateSchedule(schedule);
        assertTrue(validate);
        
        boolean success = scheduleService.addOrUpdateSchedule(Institutions.CSUEB, ScheduleID, schedule, schedule.getSectionID());
        assertFalse(success);
        
        Schedule get = scheduleService.getSchedule(Institutions.CSUEB, ScheduleID);
        assertEquals(ScheduleID, get.getScheduleID());
        
        boolean exist = scheduleService.scheduleExists(Institutions.CSUEB, ScheduleID);
        assertTrue(exist);
        		
        boolean result = scheduleService.deleteSchedule(Institutions.CSUEB, ScheduleID, schedule.getSectionID());
        assertTrue(result);
	}
	
	@Test 
	public void testAddRemoveTeachingSchedule(){
		Faculty faculty = new Faculty(Institutions.CSUEB);
        Schedule schedule = new Schedule(Institutions.CSUEB, "Math102", "7838", Term.FALL_SEMESTER,
                new Days[]{Days.MONDAY, Days.WEDNESDAY}, null, null, Time.TIME_700, Time.TIME_815, 
                Building.BUSINESS_HALL, Campus.HAYWARD, Room.ROOM1, "John Smith");
        
		boolean success = scheduleService.addTeachingSchedule(faculty, schedule);
		assertFalse(success);
		
		boolean delete = scheduleService.removeTeachingSchedule(faculty, schedule);
		assertFalse(delete);
	}

}
