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
class ScheduleDataManagerTest { //made this when sleepy, probably gonna redo this, looks bad in my opinion
// go to end for a list of scheduleID
	
    private static ScheduleDataManager scheduleDataManager;
    
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

        scheduleDataManager.addOrUpdateSchedule(institution, scheduleID, schedule);
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
        Schedule get = scheduleDataManager.getSchedule(institution, "SCH9827");

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
    	Institutions institution = Institutions.CSUEB;

    	Set<String> scheduleIDs = scheduleDataManager.getScheduleIDsByInstitution(institution);

    	System.out.println("GetScheduleIDsByinstitution for " + institution + ": " + scheduleIDs + "\n");
    }    
}

//Courses for Institution: CSUEB
//Course: Course CS - CSUEB | CourseID: CS694
//  Section ID: CS694-SEC040
//  Schedule ID: SCH9827
//  Section ID: CS694-SEC826
//  Schedule ID: SCH0897
//  Section ID: CS694-SEC674
//  Schedule ID: SCH0990
//  Section ID: CS694-SEC562
//  Schedule ID: SCH2466
//  Section ID: CS694-SEC075
//  Schedule ID: SCH5415
//  Section ID: CS694-SEC421
//  Schedule ID: SCH6556
//Course: Course PHYS - CSUEB | CourseID: PHYS455
//  Section ID: PHYS455-SEC793
//  Schedule ID: SCH4374
//  Section ID: PHYS455-SEC107
//  Schedule ID: SCH7769
//  Section ID: PHYS455-SEC802
//  Schedule ID: SCH5633
//  Section ID: PHYS455-SEC367
//  Schedule ID: SCH5724
//  Section ID: PHYS455-SEC057
//  Schedule ID: SCH4874
//  Section ID: PHYS455-SEC638
//  Schedule ID: SCH7246
//Course: Course BIO - CSUEB | CourseID: BIO719
//  Section ID: BIO719-SEC196
//  Schedule ID: SCH8750
//  Section ID: BIO719-SEC470
//  Schedule ID: SCH4887
//  Section ID: BIO719-SEC951
//  Schedule ID: SCH1492
//  Section ID: BIO719-SEC035
//  Schedule ID: SCH9046
//  Section ID: BIO719-SEC085
//  Schedule ID: SCH7087
//  Section ID: BIO719-SEC642
//  Schedule ID: SCH2989
//Course: Course MATH - CSUEB | CourseID: MATH039
//  Section ID: MATH039-SEC902
//  Schedule ID: SCH0626
//  Section ID: MATH039-SEC185
//  Schedule ID: SCH1410
//  Section ID: MATH039-SEC926
//  Schedule ID: SCH3270
//  Section ID: MATH039-SEC714
//  Schedule ID: SCH8861
//  Section ID: MATH039-SEC319
//  Schedule ID: SCH3341
//  Section ID: MATH039-SEC097
//  Schedule ID: SCH4795
//Course: Course CHEM - CSUEB | CourseID: CHEM390
//  Section ID: CHEM390-SEC601
//  Schedule ID: SCH8584
//  Section ID: CHEM390-SEC139
//  Schedule ID: SCH7599
//  Section ID: CHEM390-SEC662
//  Schedule ID: SCH7858
//  Section ID: CHEM390-SEC565
//  Schedule ID: SCH9073
//  Section ID: CHEM390-SEC901
//  Schedule ID: SCH1524
//  Section ID: CHEM390-SEC748
//  Schedule ID: SCH9198
//Courses for Institution: SJSU
//Course: Course PHYS - SJSU | CourseID: PHYS809
//  Section ID: PHYS809-SEC188
//  Schedule ID: SCH0073
//  Section ID: PHYS809-SEC310
//  Schedule ID: SCH5229
//  Section ID: PHYS809-SEC342
//  Schedule ID: SCH4701
//  Section ID: PHYS809-SEC577
//  Schedule ID: SCH9358
//  Section ID: PHYS809-SEC049
//  Schedule ID: SCH9294
//  Section ID: PHYS809-SEC953
//  Schedule ID: SCH0454
//Course: Course CS - SJSU | CourseID: CS860
//  Section ID: CS860-SEC432
//  Schedule ID: SCH0964
//  Section ID: CS860-SEC625
//  Schedule ID: SCH2211
//  Section ID: CS860-SEC555
//  Schedule ID: SCH4992
//  Section ID: CS860-SEC303
//  Schedule ID: SCH2666
//  Section ID: CS860-SEC724
//  Schedule ID: SCH0834
//  Section ID: CS860-SEC866
//  Schedule ID: SCH6526
//Course: Course CHEM - SJSU | CourseID: CHEM543
//  Section ID: CHEM543-SEC840
//  Schedule ID: SCH0107
//  Section ID: CHEM543-SEC066
//  Schedule ID: SCH0318
//  Section ID: CHEM543-SEC891
//  Schedule ID: SCH4747
//  Section ID: CHEM543-SEC736
//  Schedule ID: SCH6627
//  Section ID: CHEM543-SEC706
//  Schedule ID: SCH1153
//  Section ID: CHEM543-SEC687
//  Schedule ID: SCH8002
//Course: Course BIO - SJSU | CourseID: BIO879
//  Section ID: BIO879-SEC591
//  Schedule ID: SCH6708
//  Section ID: BIO879-SEC656
//  Schedule ID: SCH2708
//  Section ID: BIO879-SEC214
//  Schedule ID: SCH1559
//  Section ID: BIO879-SEC062
//  Schedule ID: SCH3041
//  Section ID: BIO879-SEC825
//  Schedule ID: SCH3008
//  Section ID: BIO879-SEC783
//  Schedule ID: SCH0266
//Course: Course MATH - SJSU | CourseID: MATH558
//  Section ID: MATH558-SEC025
//  Schedule ID: SCH4086
//  Section ID: MATH558-SEC084
//  Schedule ID: SCH3347
//  Section ID: MATH558-SEC012
//  Schedule ID: SCH7970
//  Section ID: MATH558-SEC580
//  Schedule ID: SCH9342
//  Section ID: MATH558-SEC339
//  Schedule ID: SCH1496
//  Section ID: MATH558-SEC337
//  Schedule ID: SCH1987
//Courses for Institution: CSUF
//Course: Course PHYS - CSUF | CourseID: PHYS466
//  Section ID: PHYS466-SEC452
//  Schedule ID: SCH1614
//  Section ID: PHYS466-SEC748
//  Schedule ID: SCH8562
//  Section ID: PHYS466-SEC668
//  Schedule ID: SCH2410
//  Section ID: PHYS466-SEC654
//  Schedule ID: SCH1413
//  Section ID: PHYS466-SEC151
//  Schedule ID: SCH1349
//  Section ID: PHYS466-SEC864
//  Schedule ID: SCH8251
//Course: Course BIO - CSUF | CourseID: BIO028
//  Section ID: BIO028-SEC318
//  Schedule ID: SCH1579
//  Section ID: BIO028-SEC688
//  Schedule ID: SCH8885
//  Section ID: BIO028-SEC506
//  Schedule ID: SCH3877
//  Section ID: BIO028-SEC935
//  Schedule ID: SCH3498
//  Section ID: BIO028-SEC260
//  Schedule ID: SCH0201
//  Section ID: BIO028-SEC891
//  Schedule ID: SCH6186
//Course: Course CS - CSUF | CourseID: CS022
//  Section ID: CS022-SEC091
//  Schedule ID: SCH2880
//  Section ID: CS022-SEC705
//  Schedule ID: SCH7697
//  Section ID: CS022-SEC570
//  Schedule ID: SCH7117
//  Section ID: CS022-SEC753
//  Schedule ID: SCH4677
//  Section ID: CS022-SEC982
//  Schedule ID: SCH4118
//  Section ID: CS022-SEC913
//  Schedule ID: SCH5729
//Course: Course MATH - CSUF | CourseID: MATH317
//  Section ID: MATH317-SEC494
//  Schedule ID: SCH8678
//  Section ID: MATH317-SEC172
//  Schedule ID: SCH9805
//  Section ID: MATH317-SEC600
//  Schedule ID: SCH3243
//  Section ID: MATH317-SEC957
//  Schedule ID: SCH5282
//  Section ID: MATH317-SEC461
//  Schedule ID: SCH2818
//  Section ID: MATH317-SEC471
//  Schedule ID: SCH8483
//Course: Course CHEM - CSUF | CourseID: CHEM425
//  Section ID: CHEM425-SEC129
//  Schedule ID: SCH9408
//  Section ID: CHEM425-SEC242
//  Schedule ID: SCH3425
//  Section ID: CHEM425-SEC695
//  Schedule ID: SCH5502
//  Section ID: CHEM425-SEC632
//  Schedule ID: SCH5649
//  Section ID: CHEM425-SEC898
//  Schedule ID: SCH7359
//  Section ID: CHEM425-SEC946
//  Schedule ID: SCH4932