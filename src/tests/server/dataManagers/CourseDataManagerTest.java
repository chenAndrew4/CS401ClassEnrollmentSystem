package tests.server.dataManagers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.dataManagers.CoursesDataManager;
import server.dataManagers.ScheduleDataManager;
import shared.enums.*;
import shared.models.*;

import java.io.IOException;
import java.util.*;

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

