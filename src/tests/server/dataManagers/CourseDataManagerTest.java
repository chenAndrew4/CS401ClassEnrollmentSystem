package tests.server.dataManagers;

import org.junit.jupiter.api.BeforeAll;
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

    @Test
    public void testSaveCourses() {
    	beforeTest();
        coursesDataManager.saveAllCourses();
    }

    @Test
    public void testLoadCourses() throws IOException {
        loadAndPrintCourses(Institutions.CSUEB);
        loadAndPrintCourses(Institutions.SJSU);
        loadAndPrintCourses(Institutions.CSUF);
    }

    private void loadAndPrintCourses(Institutions institution) {
        Map<String, Course> loadedCourses = coursesDataManager.getAllCourses(institution);
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
//Courses for Institution: CSUEB
//Course: Course CS - CSUEB | CourseID: CS694
//Section ID: CS694-SEC040
//Schedule ID: SCH9827
//Section ID: CS694-SEC826
//Schedule ID: SCH0897
//Section ID: CS694-SEC674
//Schedule ID: SCH0990
//Section ID: CS694-SEC562
//Schedule ID: SCH2466
//Section ID: CS694-SEC075
//Schedule ID: SCH5415
//Section ID: CS694-SEC421
//Schedule ID: SCH6556
//Course: Course PHYS - CSUEB | CourseID: PHYS455
//Section ID: PHYS455-SEC793
//Schedule ID: SCH4374
//Section ID: PHYS455-SEC107
//Schedule ID: SCH7769
//Section ID: PHYS455-SEC802
//Schedule ID: SCH5633
//Section ID: PHYS455-SEC367
//Schedule ID: SCH5724
//Section ID: PHYS455-SEC057
//Schedule ID: SCH4874
//Section ID: PHYS455-SEC638
//Schedule ID: SCH7246
//Course: Course BIO - CSUEB | CourseID: BIO719
//Section ID: BIO719-SEC196
//Schedule ID: SCH8750
//Section ID: BIO719-SEC470
//Schedule ID: SCH4887
//Section ID: BIO719-SEC951
//Schedule ID: SCH1492
//Section ID: BIO719-SEC035
//Schedule ID: SCH9046
//Section ID: BIO719-SEC085
//Schedule ID: SCH7087
//Section ID: BIO719-SEC642
//Schedule ID: SCH2989
//Course: Course MATH - CSUEB | CourseID: MATH039
//Section ID: MATH039-SEC902
//Schedule ID: SCH0626
//Section ID: MATH039-SEC185
//Schedule ID: SCH1410
//Section ID: MATH039-SEC926
//Schedule ID: SCH3270
//Section ID: MATH039-SEC714
//Schedule ID: SCH8861
//Section ID: MATH039-SEC319
//Schedule ID: SCH3341
//Section ID: MATH039-SEC097
//Schedule ID: SCH4795
//Course: Course CHEM - CSUEB | CourseID: CHEM390
//Section ID: CHEM390-SEC601
//Schedule ID: SCH8584
//Section ID: CHEM390-SEC139
//Schedule ID: SCH7599
//Section ID: CHEM390-SEC662
//Schedule ID: SCH7858
//Section ID: CHEM390-SEC565
//Schedule ID: SCH9073
//Section ID: CHEM390-SEC901
//Schedule ID: SCH1524
//Section ID: CHEM390-SEC748
//Schedule ID: SCH9198
//Courses for Institution: SJSU
//Course: Course PHYS - SJSU | CourseID: PHYS809
//Section ID: PHYS809-SEC188
//Schedule ID: SCH0073
//Section ID: PHYS809-SEC310
//Schedule ID: SCH5229
//Section ID: PHYS809-SEC342
//Schedule ID: SCH4701
//Section ID: PHYS809-SEC577
//Schedule ID: SCH9358
//Section ID: PHYS809-SEC049
//Schedule ID: SCH9294
//Section ID: PHYS809-SEC953
//Schedule ID: SCH0454
//Course: Course CS - SJSU | CourseID: CS860
//Section ID: CS860-SEC432
//Schedule ID: SCH0964
//Section ID: CS860-SEC625
//Schedule ID: SCH2211
//Section ID: CS860-SEC555
//Schedule ID: SCH4992
//Section ID: CS860-SEC303
//Schedule ID: SCH2666
//Section ID: CS860-SEC724
//Schedule ID: SCH0834
//Section ID: CS860-SEC866
//Schedule ID: SCH6526
//Course: Course CHEM - SJSU | CourseID: CHEM543
//Section ID: CHEM543-SEC840
//Schedule ID: SCH0107
//Section ID: CHEM543-SEC066
//Schedule ID: SCH0318
//Section ID: CHEM543-SEC891
//Schedule ID: SCH4747
//Section ID: CHEM543-SEC736
//Schedule ID: SCH6627
//Section ID: CHEM543-SEC706
//Schedule ID: SCH1153
//Section ID: CHEM543-SEC687
//Schedule ID: SCH8002
//Course: Course BIO - SJSU | CourseID: BIO879
//Section ID: BIO879-SEC591
//Schedule ID: SCH6708
//Section ID: BIO879-SEC656
//Schedule ID: SCH2708
//Section ID: BIO879-SEC214
//Schedule ID: SCH1559
//Section ID: BIO879-SEC062
//Schedule ID: SCH3041
//Section ID: BIO879-SEC825
//Schedule ID: SCH3008
//Section ID: BIO879-SEC783
//Schedule ID: SCH0266
//Course: Course MATH - SJSU | CourseID: MATH558
//Section ID: MATH558-SEC025
//Schedule ID: SCH4086
//Section ID: MATH558-SEC084
//Schedule ID: SCH3347
//Section ID: MATH558-SEC012
//Schedule ID: SCH7970
//Section ID: MATH558-SEC580
//Schedule ID: SCH9342
//Section ID: MATH558-SEC339
//Schedule ID: SCH1496
//Section ID: MATH558-SEC337
//Schedule ID: SCH1987
//Courses for Institution: CSUF
//Course: Course PHYS - CSUF | CourseID: PHYS466
//Section ID: PHYS466-SEC452
//Schedule ID: SCH1614
//Section ID: PHYS466-SEC748
//Schedule ID: SCH8562
//Section ID: PHYS466-SEC668
//Schedule ID: SCH2410
//Section ID: PHYS466-SEC654
//Schedule ID: SCH1413
//Section ID: PHYS466-SEC151
//Schedule ID: SCH1349
//Section ID: PHYS466-SEC864
//Schedule ID: SCH8251
//Course: Course BIO - CSUF | CourseID: BIO028
//Section ID: BIO028-SEC318
//Schedule ID: SCH1579
//Section ID: BIO028-SEC688
//Schedule ID: SCH8885
//Section ID: BIO028-SEC506
//Schedule ID: SCH3877
//Section ID: BIO028-SEC935
//Schedule ID: SCH3498
//Section ID: BIO028-SEC260
//Schedule ID: SCH0201
//Section ID: BIO028-SEC891
//Schedule ID: SCH6186
//Course: Course CS - CSUF | CourseID: CS022
//Section ID: CS022-SEC091
//Schedule ID: SCH2880
//Section ID: CS022-SEC705
//Schedule ID: SCH7697
//Section ID: CS022-SEC570
//Schedule ID: SCH7117
//Section ID: CS022-SEC753
//Schedule ID: SCH4677
//Section ID: CS022-SEC982
//Schedule ID: SCH4118
//Section ID: CS022-SEC913
//Schedule ID: SCH5729
//Course: Course MATH - CSUF | CourseID: MATH317
//Section ID: MATH317-SEC494
//Schedule ID: SCH8678
//Section ID: MATH317-SEC172
//Schedule ID: SCH9805
//Section ID: MATH317-SEC600
//Schedule ID: SCH3243
//Section ID: MATH317-SEC957
//Schedule ID: SCH5282
//Section ID: MATH317-SEC461
//Schedule ID: SCH2818
//Section ID: MATH317-SEC471
//Schedule ID: SCH8483
//Course: Course CHEM - CSUF | CourseID: CHEM425
//Section ID: CHEM425-SEC129
//Schedule ID: SCH9408
//Section ID: CHEM425-SEC242
//Schedule ID: SCH3425
//Section ID: CHEM425-SEC695
//Schedule ID: SCH5502
//Section ID: CHEM425-SEC632
//Schedule ID: SCH5649
//Section ID: CHEM425-SEC898
//Schedule ID: SCH7359
//Section ID: CHEM425-SEC946
//Schedule ID: SCH4932

