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

//Courses for Institution: CSUEB
//Course: Course CHEM - CSUEB | CourseID: CHEM981
//Section ID: CHEM981-SEC946
//Schedule ID: SCH9249
//Section ID: CHEM981-SEC223
//Schedule ID: SCH2322
//Section ID: CHEM981-SEC565
//Schedule ID: SCH5646
//Section ID: CHEM981-SEC745
//Schedule ID: SCH1926
//Section ID: CHEM981-SEC236
//Schedule ID: SCH0502
//Section ID: CHEM981-SEC995
//Schedule ID: SCH6803
//Course: Course CS - CSUEB | CourseID: CS022
//Section ID: CS022-SEC827
//Schedule ID: SCH0804
//Section ID: CS022-SEC533
//Schedule ID: SCH2616
//Section ID: CS022-SEC046
//Schedule ID: SCH7569
//Section ID: CS022-SEC802
//Schedule ID: SCH1305
//Section ID: CS022-SEC538
//Schedule ID: SCH6082
//Section ID: CS022-SEC215
//Schedule ID: SCH5481
//Course: Course PHYS - CSUEB | CourseID: PHYS291
//Section ID: PHYS291-SEC889
//Schedule ID: SCH7089
//Section ID: PHYS291-SEC372
//Schedule ID: SCH1473
//Section ID: PHYS291-SEC160
//Schedule ID: SCH8533
//Section ID: PHYS291-SEC795
//Schedule ID: SCH5744
//Section ID: PHYS291-SEC945
//Schedule ID: SCH3118
//Section ID: PHYS291-SEC719
//Schedule ID: SCH7373
//Course: Course BIO - CSUEB | CourseID: BIO465
//Section ID: BIO465-SEC903
//Schedule ID: SCH5554
//Section ID: BIO465-SEC971
//Schedule ID: SCH5766
//Section ID: BIO465-SEC849
//Schedule ID: SCH3204
//Section ID: BIO465-SEC886
//Schedule ID: SCH9556
//Section ID: BIO465-SEC693
//Schedule ID: SCH0063
//Section ID: BIO465-SEC245
//Schedule ID: SCH8717
//Course: Course CS - CSUEB | CourseID: CS155
//Section ID: CS155-SEC682
//Schedule ID: SCH9417
//Section ID: CS155-SEC768
//Schedule ID: SCH3677
//Section ID: CS155-SEC722
//Schedule ID: SCH5539
//Section ID: CS155-SEC142
//Schedule ID: SCH3135
//Section ID: CS155-SEC162
//Schedule ID: SCH9433
//Section ID: CS155-SEC335
//Schedule ID: SCH0593
//Course: Course MATH - CSUEB | CourseID: MATH973
//Section ID: MATH973-SEC468
//Schedule ID: SCH7337
//Section ID: MATH973-SEC988
//Schedule ID: SCH3981
//Section ID: MATH973-SEC373
//Schedule ID: SCH7001
//Section ID: MATH973-SEC486
//Schedule ID: SCH4004
//Section ID: MATH973-SEC310
//Schedule ID: SCH6117
//Section ID: MATH973-SEC357
//Schedule ID: SCH8721
//Course: Course MATH - CSUEB | CourseID: MATH404
//Section ID: MATH404-SEC750
//Schedule ID: SCH4227
//Section ID: MATH404-SEC361
//Schedule ID: SCH9169
//Section ID: MATH404-SEC369
//Schedule ID: SCH4519
//Section ID: MATH404-SEC772
//Schedule ID: SCH4122
//Section ID: MATH404-SEC227
//Schedule ID: SCH1147
//Section ID: MATH404-SEC546
//Schedule ID: SCH2712
//Course: Course BIO - CSUEB | CourseID: BIO875
//Section ID: BIO875-SEC061
//Schedule ID: SCH0848
//Section ID: BIO875-SEC428
//Schedule ID: SCH1608
//Section ID: BIO875-SEC886
//Schedule ID: SCH6890
//Section ID: BIO875-SEC966
//Schedule ID: SCH7922
//Section ID: BIO875-SEC484
//Schedule ID: SCH8609
//Section ID: BIO875-SEC649
//Schedule ID: SCH0040
//Course: Course PHYS - CSUEB | CourseID: PHYS761
//Section ID: PHYS761-SEC259
//Schedule ID: SCH6435
//Section ID: PHYS761-SEC961
//Schedule ID: SCH2082
//Section ID: PHYS761-SEC403
//Schedule ID: SCH6771
//Section ID: PHYS761-SEC549
//Schedule ID: SCH7677
//Section ID: PHYS761-SEC534
//Schedule ID: SCH0423
//Section ID: PHYS761-SEC745
//Schedule ID: SCH2134
//Course: Course CHEM - CSUEB | CourseID: CHEM020
//Section ID: CHEM020-SEC299
//Schedule ID: SCH4757
//Section ID: CHEM020-SEC272
//Schedule ID: SCH4563
//Section ID: CHEM020-SEC578
//Schedule ID: SCH2248
//Section ID: CHEM020-SEC285
//Schedule ID: SCH7986
//Section ID: CHEM020-SEC896
//Schedule ID: SCH1800
//Section ID: CHEM020-SEC077
//Schedule ID: SCH2577
//Courses for Institution: SJSU
//Course: Course MATH - SJSU | CourseID: MATH910
//Section ID: MATH910-SEC925
//Schedule ID: SCH2493
//Section ID: MATH910-SEC124
//Schedule ID: SCH0052
//Section ID: MATH910-SEC380
//Schedule ID: SCH2304
//Section ID: MATH910-SEC766
//Schedule ID: SCH6116
//Section ID: MATH910-SEC095
//Schedule ID: SCH0481
//Section ID: MATH910-SEC108
//Schedule ID: SCH4216
//Course: Course PHYS - SJSU | CourseID: PHYS278
//Section ID: PHYS278-SEC573
//Schedule ID: SCH9794
//Section ID: PHYS278-SEC050
//Schedule ID: SCH2509
//Section ID: PHYS278-SEC626
//Schedule ID: SCH5517
//Section ID: PHYS278-SEC401
//Schedule ID: SCH4526
//Section ID: PHYS278-SEC646
//Schedule ID: SCH1609
//Section ID: PHYS278-SEC580
//Schedule ID: SCH3150
//Course: Course BIO - SJSU | CourseID: BIO118
//Section ID: BIO118-SEC665
//Schedule ID: SCH3193
//Section ID: BIO118-SEC658
//Schedule ID: SCH0065
//Section ID: BIO118-SEC733
//Schedule ID: SCH9642
//Section ID: BIO118-SEC571
//Schedule ID: SCH9780
//Section ID: BIO118-SEC266
//Schedule ID: SCH9524
//Section ID: BIO118-SEC685
//Schedule ID: SCH6558
//Course: Course MATH - SJSU | CourseID: MATH361
//Section ID: MATH361-SEC792
//Schedule ID: SCH6466
//Section ID: MATH361-SEC665
//Schedule ID: SCH2444
//Section ID: MATH361-SEC044
//Schedule ID: SCH5506
//Section ID: MATH361-SEC990
//Schedule ID: SCH4031
//Section ID: MATH361-SEC100
//Schedule ID: SCH1736
//Section ID: MATH361-SEC686
//Schedule ID: SCH0002
//Course: Course CHEM - SJSU | CourseID: CHEM498
//Section ID: CHEM498-SEC752
//Schedule ID: SCH6270
//Section ID: CHEM498-SEC188
//Schedule ID: SCH2213
//Section ID: CHEM498-SEC688
//Schedule ID: SCH2553
//Section ID: CHEM498-SEC861
//Schedule ID: SCH2975
//Section ID: CHEM498-SEC771
//Schedule ID: SCH5656
//Section ID: CHEM498-SEC694
//Schedule ID: SCH9335
//Course: Course CS - SJSU | CourseID: CS966
//Section ID: CS966-SEC475
//Schedule ID: SCH6523
//Section ID: CS966-SEC168
//Schedule ID: SCH2697
//Section ID: CS966-SEC382
//Schedule ID: SCH9381
//Section ID: CS966-SEC774
//Schedule ID: SCH7956
//Section ID: CS966-SEC178
//Schedule ID: SCH2832
//Section ID: CS966-SEC571
//Schedule ID: SCH5675
//Course: Course PHYS - SJSU | CourseID: PHYS922
//Section ID: PHYS922-SEC995
//Schedule ID: SCH2688
//Section ID: PHYS922-SEC560
//Schedule ID: SCH0091
//Section ID: PHYS922-SEC554
//Schedule ID: SCH4104
//Section ID: PHYS922-SEC106
//Schedule ID: SCH5130
//Section ID: PHYS922-SEC985
//Schedule ID: SCH8876
//Section ID: PHYS922-SEC592
//Schedule ID: SCH3298
//Course: Course CHEM - SJSU | CourseID: CHEM752
//Section ID: CHEM752-SEC721
//Schedule ID: SCH5601
//Section ID: CHEM752-SEC915
//Schedule ID: SCH0020
//Section ID: CHEM752-SEC715
//Schedule ID: SCH0162
//Section ID: CHEM752-SEC672
//Schedule ID: SCH6009
//Section ID: CHEM752-SEC555
//Schedule ID: SCH2806
//Section ID: CHEM752-SEC506
//Schedule ID: SCH1655
//Course: Course BIO - SJSU | CourseID: BIO159
//Section ID: BIO159-SEC151
//Schedule ID: SCH1181
//Section ID: BIO159-SEC425
//Schedule ID: SCH2086
//Section ID: BIO159-SEC931
//Schedule ID: SCH7891
//Section ID: BIO159-SEC714
//Schedule ID: SCH8872
//Section ID: BIO159-SEC746
//Schedule ID: SCH6815
//Section ID: BIO159-SEC517
//Schedule ID: SCH0997
//Course: Course CS - SJSU | CourseID: CS049
//Section ID: CS049-SEC701
//Schedule ID: SCH4320
//Section ID: CS049-SEC237
//Schedule ID: SCH0542
//Section ID: CS049-SEC783
//Schedule ID: SCH7963
//Section ID: CS049-SEC928
//Schedule ID: SCH2709
//Section ID: CS049-SEC246
//Schedule ID: SCH8541
//Section ID: CS049-SEC115
//Schedule ID: SCH4522
//Courses for Institution: CSUF
//Course: Course PHYS - CSUF | CourseID: PHYS299
//Section ID: PHYS299-SEC468
//Schedule ID: SCH6141
//Section ID: PHYS299-SEC339
//Schedule ID: SCH0765
//Section ID: PHYS299-SEC276
//Schedule ID: SCH7609
//Section ID: PHYS299-SEC589
//Schedule ID: SCH0044
//Section ID: PHYS299-SEC791
//Schedule ID: SCH8377
//Section ID: PHYS299-SEC374
//Schedule ID: SCH8890
//Course: Course CHEM - CSUF | CourseID: CHEM334
//Section ID: CHEM334-SEC705
//Schedule ID: SCH7095
//Section ID: CHEM334-SEC096
//Schedule ID: SCH3570
//Section ID: CHEM334-SEC802
//Schedule ID: SCH6929
//Section ID: CHEM334-SEC954
//Schedule ID: SCH3444
//Section ID: CHEM334-SEC030
//Schedule ID: SCH5267
//Section ID: CHEM334-SEC111
//Schedule ID: SCH7936
//Course: Course CS - CSUF | CourseID: CS422
//Section ID: CS422-SEC373
//Schedule ID: SCH2548
//Section ID: CS422-SEC775
//Schedule ID: SCH9067
//Section ID: CS422-SEC806
//Schedule ID: SCH6194
//Section ID: CS422-SEC344
//Schedule ID: SCH2480
//Section ID: CS422-SEC921
//Schedule ID: SCH7472
//Section ID: CS422-SEC477
//Schedule ID: SCH4810
//Course: Course BIO - CSUF | CourseID: BIO649
//Section ID: BIO649-SEC223
//Schedule ID: SCH8565
//Section ID: BIO649-SEC077
//Schedule ID: SCH9870
//Section ID: BIO649-SEC537
//Schedule ID: SCH2863
//Section ID: BIO649-SEC220
//Schedule ID: SCH0391
//Section ID: BIO649-SEC917
//Schedule ID: SCH7599
//Section ID: BIO649-SEC176
//Schedule ID: SCH9315
//Course: Course BIO - CSUF | CourseID: BIO431
//Section ID: BIO431-SEC778
//Schedule ID: SCH6281
//Section ID: BIO431-SEC256
//Schedule ID: SCH5039
//Section ID: BIO431-SEC595
//Schedule ID: SCH6045
//Section ID: BIO431-SEC582
//Schedule ID: SCH2357
//Section ID: BIO431-SEC938
//Schedule ID: SCH1885
//Section ID: BIO431-SEC338
//Schedule ID: SCH6315
//Course: Course CHEM - CSUF | CourseID: CHEM085
//Section ID: CHEM085-SEC548
//Schedule ID: SCH0813
//Section ID: CHEM085-SEC522
//Schedule ID: SCH4473
//Section ID: CHEM085-SEC266
//Schedule ID: SCH9804
//Section ID: CHEM085-SEC379
//Schedule ID: SCH8693
//Section ID: CHEM085-SEC486
//Schedule ID: SCH3583
//Section ID: CHEM085-SEC999
//Schedule ID: SCH8150
//Course: Course MATH - CSUF | CourseID: MATH376
//Section ID: MATH376-SEC289
//Schedule ID: SCH5868
//Section ID: MATH376-SEC619
//Schedule ID: SCH5266
//Section ID: MATH376-SEC548
//Schedule ID: SCH2771
//Section ID: MATH376-SEC697
//Schedule ID: SCH6952
//Section ID: MATH376-SEC947
//Schedule ID: SCH6209
//Section ID: MATH376-SEC728
//Schedule ID: SCH4407
//Course: Course MATH - CSUF | CourseID: MATH816
//Section ID: MATH816-SEC405
//Schedule ID: SCH2159
//Section ID: MATH816-SEC289
//Schedule ID: SCH5054
//Section ID: MATH816-SEC345
//Schedule ID: SCH7500
//Section ID: MATH816-SEC892
//Schedule ID: SCH9547
//Section ID: MATH816-SEC718
//Schedule ID: SCH9329
//Section ID: MATH816-SEC342
//Schedule ID: SCH7757
//Course: Course CS - CSUF | CourseID: CS745
//Section ID: CS745-SEC035
//Schedule ID: SCH4435
//Section ID: CS745-SEC212
//Schedule ID: SCH2014
//Section ID: CS745-SEC892
//Schedule ID: SCH0809
//Section ID: CS745-SEC846
//Schedule ID: SCH5468
//Section ID: CS745-SEC358
//Schedule ID: SCH4970
//Section ID: CS745-SEC646
//Schedule ID: SCH0178
//Course: Course PHYS - CSUF | CourseID: PHYS993
//Section ID: PHYS993-SEC607
//Schedule ID: SCH7225
//Section ID: PHYS993-SEC774
//Schedule ID: SCH4070
//Section ID: PHYS993-SEC291
//Schedule ID: SCH1579
//Section ID: PHYS993-SEC606
//Schedule ID: SCH8783
//Section ID: PHYS993-SEC572
//Schedule ID: SCH5014
//Section ID: PHYS993-SEC019
//Schedule ID: SCH3873
