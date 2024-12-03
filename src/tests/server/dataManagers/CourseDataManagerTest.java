package tests.server.dataManagers;

import org.junit.jupiter.api.Test;
import server.dataManagers.CoursesDataManager;
import server.dataManagers.ScheduleDataManager;
import shared.enums.*;
import shared.models.*;

import java.io.IOException;
import java.util.*;

// check end for list of courseID, sectionID and scheduleID, update it if regenerated
public class CourseDataManagerTest {
    static CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();

    public static void beforeTest() {

        // Generate 15 courses across different institutions and departments
        createCoursesForInstitution(Institutions.CSUEB);
        createCoursesForInstitution(Institutions.SJSU);
        createCoursesForInstitution(Institutions.CSUF);
    }

    private static void createCoursesForInstitution(Institutions institution) {
        String[] departments = {"CS", "MATH", "PHYS", "BIO", "CHEM"};
        for (int i = 0; i < 5; i++) { // 5 departments per institution
            String department = departments[i % departments.length];

            // Create 1 course per department
            Course course = new Course(institution, department);
            course.setName("Course " + department + " - " + institution);
            course.setDescription("Description for " + department + " course at " + institution);
            course.setLevel(LevelType.Lower);
            course.setAcademicProgram(AcademicProgramType.UGM);
            course.setUnits(1.0f + i);
            course.setPrerequisites(Arrays.asList("PREREQ" + i));

            // Create 2 sections per course
            List<CourseSection> sections = new ArrayList<>();
            for (int j = 1; j <= 2; j++) {
            	// 2024 fall schedules 
                CourseSection section = new CourseSection(institution, course.getCourseID());
                section.setEnrollmentLimit(30 + j * 5);
                section.setGrading(GradingType.Letter);
                section.setInstructionMode(InstructionModeType.InPerson);
                section.setNotes("No prerequisites for this course.");
                section.setClassRoster(new ClassRoster());
                WaitList w = new WaitList(institution);
                section.setWaitlistID(w.getWaitlistID());

                // Create a schedule for the section
                Schedule schedule = new Schedule(institution);
                schedule.setCourseID(course.getCourseID());
                schedule.setSectionID(section.getSectionID());
                schedule.setTerm(Term.FALL_SEMESTER);
                schedule.setDays(new Days[]{Days.TUESDAY, Days.THURSDAY});
                Calendar calendar = Calendar.getInstance();
                // Set the start date
                calendar.set(2024, Calendar.SEPTEMBER, 15); // Sep 15, 2024
                schedule.setStartDate(calendar.getTime());

                // Set the end date
                calendar.set(2024, Calendar.DECEMBER, 22); // Dec 22, 2024
                schedule.setEndDate(calendar.getTime());
                schedule.setStartTime(Time.TIME_1100);              // 11:00 AM
                schedule.setEndTime(Time.TIME_1230); 
                Building location = Building.BUSINESS_HALL;
                schedule.setLocation(location);
                Campus campus = Campus.HAYWARD;
                schedule.setCampus(campus);
                Room room = Room.ROOM1;
                schedule.setRoom(room);

                section.setScheduleID(schedule.getScheduleID());
                sections.add(section);
                
                // 2024 winter schedules 
                CourseSection section1 = new CourseSection(institution, course.getCourseID());
                section1.setEnrollmentLimit(30 + j * 5);
                section1.setGrading(GradingType.Letter);
                section1.setInstructionMode(InstructionModeType.InPerson);
                section1.setNotes("No prerequisites for this course.");
                section1.setClassRoster(new ClassRoster());
                WaitList w1 = new WaitList(institution);
                section1.setWaitlistID(w1.getWaitlistID());

                // Create a schedule for the section
                Schedule schedule1 = new Schedule(institution);
                schedule1.setCourseID(course.getCourseID());
                schedule1.setSectionID(section.getSectionID());
                schedule1.setTerm(Term.WINTER_SEMESTER);
                schedule1.setDays(new Days[]{Days.TUESDAY, Days.THURSDAY});
                Calendar calendar1 = Calendar.getInstance();
                // Set the start date
                calendar1.set(2024, Calendar.DECEMBER, 15); // 12 10, 2024
                schedule1.setStartDate(calendar.getTime());

                // Set the end date
                calendar1.set(2024, Calendar.JANUARY, 30); //1 10, 2024
                schedule1.setEndDate(calendar.getTime());
                schedule1.setStartTime(Time.TIME_1000);            
                schedule1.setEndTime(Time.TIME_1100);   
                Building location1 = Building.ENGINEERING_HALL;
                schedule1.setLocation(location1);
                Campus campus1 = Campus.HAYWARD;
                schedule1.setCampus(campus1);
                Room room1 = Room.ROOM1;
                schedule1.setRoom(room1);

                section1.setScheduleID(schedule1.getScheduleID());
                sections.add(section1);
                
                // 2024 spring schedules 
                CourseSection section2 = new CourseSection(institution, course.getCourseID());
                section2.setEnrollmentLimit(30 + j * 5);
                section2.setGrading(GradingType.Letter);
                section2.setInstructionMode(InstructionModeType.InPerson);
                section2.setNotes("No prerequisites for this course.");
                section2.setClassRoster(new ClassRoster());
                WaitList w2 = new WaitList(institution);
                section2.setWaitlistID(w2.getWaitlistID());

                // Create a schedule for the section
                Schedule schedule2 = new Schedule(institution);
                schedule2.setCourseID(course.getCourseID());
                schedule2.setSectionID(section.getSectionID());
                schedule2.setTerm(Term.SPRING_SEMESTER);
                schedule2.setDays(new Days[]{Days.TUESDAY, Days.THURSDAY});
                Calendar calendar2 = Calendar.getInstance();
                // Set the start date
                calendar2.set(2024, Calendar.JANUARY, 30); // Jan 30, 2024
                schedule2.setStartDate(calendar.getTime());

                // Set the end date
                calendar2.set(2024, Calendar.MAY, 30); // May 30, 2024
                schedule2.setEndDate(calendar.getTime());
                schedule2.setStartTime(Time.TIME_1400);             
                schedule2.setEndTime(Time.TIME_1500);   
                Building location2 = Building.BEHAVIORAL_SCIENCES;
                schedule2.setLocation(location2);
                Campus campus2 = Campus.HAYWARD;
                schedule2.setCampus(campus2);
                Room room2 = Room.ROOM1;
                schedule2.setRoom(room2);

                section2.setScheduleID(schedule2.getScheduleID());
                sections.add(section2);
                
                ScheduleDataManager.getInstance().addOrUpdateSchedule(institution, schedule1.getScheduleID(), schedule1);
                ScheduleDataManager.getInstance().addOrUpdateSchedule(institution, schedule2.getScheduleID(), schedule2);
                ScheduleDataManager.getInstance().addOrUpdateSchedule(institution, schedule.getScheduleID(), schedule);
            }
            course.setSections(sections);
            // Add course to CoursesDataManager
            coursesDataManager.addOrUpdateCourse(institution, course);
           
        }
        ScheduleDataManager.getInstance().saveAllSchedules();
    }

////    dont run is except you want to update the db
//    @Test
//    public void testSaveCourses() {
//    	beforeTest();
//        coursesDataManager.saveAllCourses();
//    }

    @Test
    public void testLoadCourses() throws IOException {
        loadAndPrintCourses(Institutions.CSUEB);
        loadAndPrintCourses(Institutions.SJSU);
        loadAndPrintCourses(Institutions.CSUF);
    }
    
    @Test
    public void testGetCourseByCourseID() {
    	Course c = coursesDataManager.getCourseByCourseID(Institutions.CSUEB, "PHYS961");
    	System.out.println(c.getCourseID() + "," + c.getName() + "," + c.getDepartment());
    }
    
    @Test
    public void testGetCourseBySectionID() {
    	coursesDataManager.getCourseBySectionID(Institutions.CSUEB, "PHYS961-SEC184");
    }

    private void loadAndPrintCourses(Institutions institution) {
        Map<String, Course> loadedCourses = coursesDataManager.getAllCourses(institution);
        if (loadedCourses.isEmpty()) {
        	System.out.println("empty map");
        }
        System.out.println("Courses for Institution: " + institution);
        for (Course course : loadedCourses.values()) {
            System.out.println("Course: " + course.getName() + " | CourseID: " + course.getCourseID());
            for (CourseSection section : course.getSections()) {
                System.out.println("  Section ID: " + section.getSectionID());
                System.out.println("  Schedule ID: " + section.getScheduleID());
            }
        }
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

