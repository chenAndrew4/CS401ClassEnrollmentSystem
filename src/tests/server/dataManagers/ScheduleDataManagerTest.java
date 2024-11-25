package tests.server.dataManagers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import server.dataManagers.ScheduleDataManager;
import shared.enums.Building;
import shared.enums.Campus;
import shared.enums.Days;
import shared.enums.Institutions;
import shared.enums.Room;
import shared.enums.Term;
import shared.enums.Time;
import shared.models.Schedule;
class ScheduleDataManagerTest { //coming back to this later to change db

    private static ScheduleDataManager scheduleDataManager;
    
    @BeforeAll
    static void setUp() {   
        scheduleDataManager = ScheduleDataManager.getInstance();
        Institutions institution = Institutions.CSUEB;
        Days[] days = {Days.MONDAY, Days.WEDNESDAY};
        Term term = Term.FALL_SEMESTER;
        Time startTime = Time.TIME_700;
        Time endTime = Time.TIME_815;
        Building location = Building.BUSINESS_HALL;
        Campus campus = Campus.HAYWARD;
        Room room = Room.ROOM1;
        
        Schedule schedule = new Schedule(institution, "Math101", "7837", term, days, null, null, 
                startTime, endTime, location, campus, room, "John Smith");
        
        String scheduleID = schedule.getScheduleID();

        scheduleDataManager.saveSchedule(institution, scheduleID, schedule);
    }
    
//    @AfterAll
//    static void cleanUp() {
//    	scheduleDataManager.deleteAllSchedules(Institutions.CSUEB);    	
//    }
    
    @Test
    void testEnsureSchedulesLoaded() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
    	Institutions institution = Institutions.CSUEB;
       
    	//Make the method accessible
    	Method ensureSchedulesLoadedMethod = ScheduleDataManager.class.getDeclaredMethod("ensureSchedulesLoaded", Institutions.class);
        ensureSchedulesLoadedMethod.setAccessible(true);
        ensureSchedulesLoadedMethod.invoke(scheduleDataManager, institution);

        System.out.println("ensureSchedulesLoaded successfully for: " + institution + "\n");
    }
    
    @SuppressWarnings("unchecked")
	@Test
    void testSaveAllSchedules() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        //Access modified map before saving 
        Field modifiedField = ScheduleDataManager.class.getDeclaredField("modified");
        modifiedField.setAccessible(true);

        Map<Institutions, Boolean> modifiedStatus = (Map<Institutions, Boolean>) modifiedField.get(scheduleDataManager);

        System.out.println("Before saving schedules:");
        System.out.println("Modified status: " + modifiedStatus);

        scheduleDataManager.saveAllSchedules();

        //Access modified map after saving 
        modifiedStatus = (Map<Institutions, Boolean>) modifiedField.get(scheduleDataManager);

        System.out.println("After saving schedules:");
        System.out.println("Modified status: " + modifiedStatus + "\n");
    }
    
    @Test
    void testSaveSchedulesByInstitution() throws NoSuchFieldException, IllegalAccessException {
        Institutions institution = Institutions.CSUEB;

        //Retrieve the current schedules from private field
        Field institutionSchedulesField = ScheduleDataManager.class.getDeclaredField("institutionSchedules");
        institutionSchedulesField.setAccessible(true);
        
        @SuppressWarnings("unchecked")
        Map<Institutions, Map<String, Schedule>> institutionSchedules =
                (Map<Institutions, Map<String, Schedule>>) institutionSchedulesField.get(scheduleDataManager);

        Map<String, Schedule> schedules = institutionSchedules.get(institution);

        scheduleDataManager.saveSchedulesByInstitution(institution, schedules);

        System.out.println("Schedules saved successfully ByInstitution: " + institution + "\n");
    }
    
    @Test
    void testLoadAllSchedules() {
        ScheduleDataManager manager = ScheduleDataManager.getInstance();
        
    	manager.loadAllSchedules();
    	
    	System.out.println("Separator for LoadAllSchedules \n");
    }
    
    @Test
    void testLoadSchedulesByInstitution() {
        Institutions institution = Institutions.CSUEB;

        Map<String, Schedule> schedules = scheduleDataManager.loadSchedulesByInstitution(institution);

        System.out.println("Schedules loaded using LoadSchedulesByInstitution for: " + institution);
        for (Map.Entry<String, Schedule> entry : schedules.entrySet()) {
            System.out.println("CourseID: " + entry.getValue().getCourseID() + " SectionID: " + entry.getValue().getSectionID());
        }
        
        System.out.println("\n");
    }

    
    @Test
    void testSaveSchedule() {
        Institutions institution = Institutions.CSUEB;
        
        Schedule schedule = new Schedule(institution, "Math102", "7838", Term.FALL_SEMESTER,
                new Days[]{Days.MONDAY, Days.WEDNESDAY}, null, null, Time.TIME_700, Time.TIME_815, 
                Building.BUSINESS_HALL, Campus.HAYWARD, Room.ROOM1, "John Smith");
        
        String scheduleID = schedule.getScheduleID();
        
        boolean result = scheduleDataManager.saveSchedule(institution, scheduleID, schedule);
        System.out.println("SaveSchedule result: " + result + "\n");
    }
    
    @Test
    void testGetSchedule() {
        Institutions institution = Institutions.CSUEB;

        Schedule schedule = new Schedule(institution, "Math100", "7000", Term.FALL_SEMESTER,
                new Days[]{Days.MONDAY, Days.WEDNESDAY}, null, null, Time.TIME_700, Time.TIME_815, 
                Building.BUSINESS_HALL, Campus.HAYWARD, Room.ROOM1, "John Smith");
        
        String scheduleID = schedule.getScheduleID();

        scheduleDataManager.saveSchedule(institution, scheduleID, schedule);

        Schedule get = scheduleDataManager.getSchedule(institution, scheduleID);

        if (get != null) {
            System.out.println("GetSchedule:" + "\n" + "CourseID: " + get.getCourseID() + " " + "SectionID: " + get.getSectionID() + "\n");
        } 
        
        else {
            System.out.println("No schedule found." + "\n");
        }
    }


    @Test 
    void testGetAllSchedules() {
        Institutions institution = Institutions.CSUEB;
        
        Schedule schedule1 = new Schedule(institution, "Math103", "7839", Term.FALL_SEMESTER,
                new Days[]{Days.MONDAY, Days.WEDNESDAY}, null, null, Time.TIME_700, Time.TIME_815, 
                Building.BUSINESS_HALL, Campus.HAYWARD, Room.ROOM1, "John Smith");
        
        String scheduleID = schedule1.getScheduleID();
        
        scheduleDataManager.saveSchedule(institution, scheduleID, schedule1);
        
        Map<String, Schedule> schedules = scheduleDataManager.getAllSchedules(institution);

        System.out.println("All schedules using getAllSchedules for institution: " + institution);
        for (Map.Entry<String, Schedule> entry : schedules.entrySet()) {
            System.out.println("CourseID: " + entry.getValue().getCourseID());
        }
        
        System.out.println("\n");
    }

    
    @Test
    void testDeleteSchedule() {
        Institutions institution = Institutions.CSUEB;

        Schedule schedule = new Schedule(institution, "Math104", "7840", Term.FALL_SEMESTER,
                new Days[]{Days.MONDAY, Days.WEDNESDAY}, null, null, Time.TIME_700, Time.TIME_815, 
                Building.BUSINESS_HALL, Campus.HAYWARD, Room.ROOM1, "John Smith");
        
        String scheduleID = schedule.getScheduleID();
        
        scheduleDataManager.saveSchedule(institution, scheduleID, schedule);
        
        boolean result = scheduleDataManager.deleteSchedule(institution, scheduleID);

        System.out.println("DeleteSchedule result: " + result + "\n");
    }
    
//  @Test   //Don't use this test when testing other methods
//  void testDeleteAllSchedules() {
//     Institutions institution = Institutions.CSUEB; 

//     boolean result = scheduleDataManager.deleteAllSchedules(institution);

//     System.out.println("DeleteAllSchedules result: " + result + "\n");
// }
    
    @Test
    void testGetScheduleIDsByInstitution() {
    Institutions institution = Institutions.CSUEB;

    Set<String> scheduleIDs = scheduleDataManager.getScheduleIDsByInstitution(institution);

    System.out.println("GetScheduleIDsByinstitution for " + institution + ": " + scheduleIDs + "\n");
    }    
}
