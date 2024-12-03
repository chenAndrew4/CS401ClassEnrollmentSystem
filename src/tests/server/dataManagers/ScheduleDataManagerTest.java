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
class ScheduleDataManagerTest {
// go to end for a list of scheduleID, update it if regenerated
	
    private static ScheduleDataManager scheduleDataManager;
    
    static void setUp() {   
//        scheduleDataManager = ScheduleDataManager.getInstance();
//        Institutions institution = Institutions.CSUEB;
//        Days[] days = {Days.MONDAY, Days.WEDNESDAY};
//        Term term = Term.FALL_SEMESTER;
//        Time startTime = Time.TIME_700;
//        Time endTime = Time.TIME_815;
//        Building location = Building.BUSINESS_HALL;
//        Campus campus = Campus.HAYWARD;
//        Room room = Room.ROOM1;
//        
//        Schedule schedule = new Schedule(institution, "Math101", "7837", term, days, null, null, 
//                startTime, endTime, location, campus, room, "John Smith");
//        
//        String scheduleID = schedule.getScheduleID();
//
//        scheduleDataManager.addOrUpdateSchedule(institution, scheduleID, schedule);
    	scheduleDataManager = ScheduleDataManager.getInstance();
    }
    
//    @AfterAll
//    static void cleanUp() {
//    	scheduleDataManager.deleteAllSchedules(Institutions.CSUEB);    	
//    }
    
    @Test
    void testEnsureSchedulesLoaded() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
    	
    	setUp();
    	
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
    	setUp();
        ScheduleDataManager manager = ScheduleDataManager.getInstance();
        
    	manager.loadAllSchedules();
    	
    	System.out.println("Separator for LoadAllSchedules \n");
    }
    
    @Test
    void testLoadSchedulesByInstitution() {
    	setUp();
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
        
        boolean result = scheduleDataManager.addOrUpdateSchedule(institution, scheduleID, schedule);
        System.out.println("SaveSchedule result: " + result + "\n");
    }
    
    @Test
    void testGetSchedule() {
        Institutions institution = Institutions.CSUEB;

//        Schedule schedule = new Schedule(institution, "Math100", "7000", Term.FALL_SEMESTER,
//                new Days[]{Days.MONDAY, Days.WEDNESDAY}, null, null, Time.TIME_700, Time.TIME_815, 
//                Building.BUSINESS_HALL, Campus.HAYWARD, Room.ROOM1, "John Smith");
//        
//        String scheduleID = schedule.getScheduleID();

//        scheduleDataManager.addOrUpdateSchedule(institution, scheduleID, schedule);

        // find list of scheduleID from end of the file
        Schedule get = scheduleDataManager.getInstance().getSchedule(institution, "SCH9249");

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
        
        scheduleDataManager.addOrUpdateSchedule(institution, scheduleID, schedule1);
        
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
        
        scheduleDataManager.addOrUpdateSchedule(institution, scheduleID, schedule);
        
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
    	setUp();
    	Institutions institution = Institutions.CSUEB;

    	Set<String> scheduleIDs = scheduleDataManager.getScheduleIDsByInstitution(institution);

    	System.out.println("GetScheduleIDsByinstitution for " + institution + ": " + scheduleIDs + "\n");
    }    
}

//        Courses for Institution: CSUEB
//        Course: Course PHYS - CSUEB | CourseID: PHYS961
//        Section ID: PHYS961-SEC184
//        Schedule ID: SCH9309
//        Section ID: PHYS961-SEC988
//        Schedule ID: SCH6555
//        Section ID: PHYS961-SEC106
//        Schedule ID: SCH1814
//        Section ID: PHYS961-SEC287
//        Schedule ID: SCH9824
//        Section ID: PHYS961-SEC758
//        Schedule ID: SCH6704
//        Section ID: PHYS961-SEC480
//        Schedule ID: SCH0868
//        Course: Course CS - CSUEB | CourseID: CS638
//        Section ID: CS638-SEC881
//        Schedule ID: SCH9616
//        Section ID: CS638-SEC626
//        Schedule ID: SCH9260
//        Section ID: CS638-SEC984
//        Schedule ID: SCH8032
//        Section ID: CS638-SEC981
//        Schedule ID: SCH3640
//        Section ID: CS638-SEC895
//        Schedule ID: SCH9089
//        Section ID: CS638-SEC208
//        Schedule ID: SCH5088
//        Course: Course MATH - CSUEB | CourseID: MATH874
//        Section ID: MATH874-SEC251
//        Schedule ID: SCH0190
//        Section ID: MATH874-SEC061
//        Schedule ID: SCH8387
//        Section ID: MATH874-SEC982
//        Schedule ID: SCH2626
//        Section ID: MATH874-SEC530
//        Schedule ID: SCH4410
//        Section ID: MATH874-SEC244
//        Schedule ID: SCH5244
//        Section ID: MATH874-SEC474
//        Schedule ID: SCH0221
//        Course: Course BIO - CSUEB | CourseID: BIO804
//        Section ID: BIO804-SEC549
//        Schedule ID: SCH5109
//        Section ID: BIO804-SEC491
//        Schedule ID: SCH2540
//        Section ID: BIO804-SEC555
//        Schedule ID: SCH8342
//        Section ID: BIO804-SEC828
//        Schedule ID: SCH9613
//        Section ID: BIO804-SEC403
//        Schedule ID: SCH1133
//        Section ID: BIO804-SEC701
//        Schedule ID: SCH7389
//        Course: Course CHEM - CSUEB | CourseID: CHEM198
//        Section ID: CHEM198-SEC077
//        Schedule ID: SCH3955
//        Section ID: CHEM198-SEC458
//        Schedule ID: SCH3770
//        Section ID: CHEM198-SEC343
//        Schedule ID: SCH0403
//        Section ID: CHEM198-SEC185
//        Schedule ID: SCH6643
//        Section ID: CHEM198-SEC715
//        Schedule ID: SCH2549
//        Section ID: CHEM198-SEC656
//        Schedule ID: SCH2241
//        Courses for Institution: SJSU
//        Course: Course MATH - SJSU | CourseID: MATH169
//        Section ID: MATH169-SEC215
//        Schedule ID: SCH1338
//        Section ID: MATH169-SEC601
//        Schedule ID: SCH2162
//        Section ID: MATH169-SEC068
//        Schedule ID: SCH2173
//        Section ID: MATH169-SEC792
//        Schedule ID: SCH8801
//        Section ID: MATH169-SEC119
//        Schedule ID: SCH1525
//        Section ID: MATH169-SEC646
//        Schedule ID: SCH7807
//        Course: Course CS - SJSU | CourseID: CS287
//        Section ID: CS287-SEC553
//        Schedule ID: SCH5773
//        Section ID: CS287-SEC013
//        Schedule ID: SCH3358
//        Section ID: CS287-SEC391
//        Schedule ID: SCH0117
//        Section ID: CS287-SEC817
//        Schedule ID: SCH1408
//        Section ID: CS287-SEC868
//        Schedule ID: SCH2224
//        Section ID: CS287-SEC533
//        Schedule ID: SCH9539
//        Course: Course BIO - SJSU | CourseID: BIO359
//        Section ID: BIO359-SEC407
//        Schedule ID: SCH6656
//        Section ID: BIO359-SEC468
//        Schedule ID: SCH0547
//        Section ID: BIO359-SEC971
//        Schedule ID: SCH7932
//        Section ID: BIO359-SEC807
//        Schedule ID: SCH0331
//        Section ID: BIO359-SEC460
//        Schedule ID: SCH3444
//        Section ID: BIO359-SEC321
//        Schedule ID: SCH9204
//        Course: Course PHYS - SJSU | CourseID: PHYS837
//        Section ID: PHYS837-SEC249
//        Schedule ID: SCH6118
//        Section ID: PHYS837-SEC490
//        Schedule ID: SCH3407
//        Section ID: PHYS837-SEC632
//        Schedule ID: SCH0270
//        Section ID: PHYS837-SEC403
//        Schedule ID: SCH6345
//        Section ID: PHYS837-SEC978
//        Schedule ID: SCH3487
//        Section ID: PHYS837-SEC352
//        Schedule ID: SCH8743
//        Course: Course CHEM - SJSU | CourseID: CHEM124
//        Section ID: CHEM124-SEC123
//        Schedule ID: SCH3924
//        Section ID: CHEM124-SEC822
//        Schedule ID: SCH7240
//        Section ID: CHEM124-SEC798
//        Schedule ID: SCH4257
//        Section ID: CHEM124-SEC345
//        Schedule ID: SCH8719
//        Section ID: CHEM124-SEC075
//        Schedule ID: SCH1410
//        Section ID: CHEM124-SEC157
//        Schedule ID: SCH7655
//        Courses for Institution: CSUF
//        Course: Course MATH - CSUF | CourseID: MATH815
//        Section ID: MATH815-SEC847
//        Schedule ID: SCH4756
//        Section ID: MATH815-SEC761
//        Schedule ID: SCH6450
//        Section ID: MATH815-SEC596
//        Schedule ID: SCH1572
//        Section ID: MATH815-SEC601
//        Schedule ID: SCH1224
//        Section ID: MATH815-SEC122
//        Schedule ID: SCH7439
//        Section ID: MATH815-SEC168
//        Schedule ID: SCH6294
//        Course: Course CHEM - CSUF | CourseID: CHEM011
//        Section ID: CHEM011-SEC755
//        Schedule ID: SCH3926
//        Section ID: CHEM011-SEC454
//        Schedule ID: SCH7417
//        Section ID: CHEM011-SEC837
//        Schedule ID: SCH5259
//        Section ID: CHEM011-SEC444
//        Schedule ID: SCH7633
//        Section ID: CHEM011-SEC938
//        Schedule ID: SCH1790
//        Section ID: CHEM011-SEC820
//        Schedule ID: SCH4705
//        Course: Course CS - CSUF | CourseID: CS462
//        Section ID: CS462-SEC436
//        Schedule ID: SCH4302
//        Section ID: CS462-SEC549
//        Schedule ID: SCH6763
//        Section ID: CS462-SEC098
//        Schedule ID: SCH0699
//        Section ID: CS462-SEC420
//        Schedule ID: SCH4814
//        Section ID: CS462-SEC320
//        Schedule ID: SCH7614
//        Section ID: CS462-SEC812
//        Schedule ID: SCH6316
//        Course: Course PHYS - CSUF | CourseID: PHYS477
//        Section ID: PHYS477-SEC497
//        Schedule ID: SCH4818
//        Section ID: PHYS477-SEC394
//        Schedule ID: SCH4114
//        Section ID: PHYS477-SEC051
//        Schedule ID: SCH0213
//        Section ID: PHYS477-SEC243
//        Schedule ID: SCH7373
//        Section ID: PHYS477-SEC298
//        Schedule ID: SCH4899
//        Section ID: PHYS477-SEC798
//        Schedule ID: SCH4455
//        Course: Course BIO - CSUF | CourseID: BIO416
//        Section ID: BIO416-SEC237
//        Schedule ID: SCH1328
//        Section ID: BIO416-SEC760
//        Schedule ID: SCH9214
//        Section ID: BIO416-SEC485
//        Schedule ID: SCH5506
//        Section ID: BIO416-SEC587
//        Schedule ID: SCH4441
//        Section ID: BIO416-SEC781
//        Schedule ID: SCH2889
//        Section ID: BIO416-SEC657
//        Schedule ID: SCH0741
