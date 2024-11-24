package tests.server.dataManagers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.dataManagers.CoursesDataManager;
import shared.enums.*;
import shared.models.*;

import java.io.IOException;
import java.util.*;

public class CourseDataManagerTest {
    static CoursesDataManager coursesDataManager;

    @BeforeAll
    public static void beforeTest() {
        coursesDataManager = CoursesDataManager.getInstance();

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
            course.setNotes("No prerequisites for this course.");
            course.setLevel(LevelType.Lower);
            course.setAcademicProgram(AcademicProgramType.UGM);
            course.setUnits(3.0f + i);
            course.setPrerequisites(Arrays.asList("PREREQ" + i));

            // Create 2 sections per course
            List<CourseSection> sections = new ArrayList<>();
            for (int j = 1; j <= 2; j++) {
                CourseSection section = new CourseSection(institution, course.getCourseID());
                section.setEnrollmentLimit(30 + j * 5);
                section.setGrading(GradingType.Letter);
                section.setInstructionMode(InstructionModeType.InPerson);

                // Create a schedule for the section
                Schedule schedule = new Schedule(institution);
                schedule.setCourseID(course.getCourseID());
                schedule.setSectionID(section.getSectionID());
                schedule.setTerm(Term.SPRING_SEMESTER);
                schedule.setDays(new Days[]{Days.TUESDAY, Days.THURSDAY});
                Calendar calendar = Calendar.getInstance();
                // Set the start date
                calendar.set(2024, Calendar.JANUARY, 15); // Jan 15, 2024
                schedule.setStartDate(calendar.getTime());

// Set the end date
                calendar.set(2024, Calendar.MAY, 22); // May 22, 2024
                schedule.setEndDate(calendar.getTime());
                schedule.setStartTime(Time.TIME_1100);              // 11:00 AM
                schedule.setEndTime(Time.TIME_1230);               // 12:30 PM

                section.setScheduleID(schedule.getScheduleID());
                sections.add(section);
            }
            course.setSections(sections);

            // Add course to CoursesDataManager
            coursesDataManager.addOrUpdateCourse(institution, course);
        }
    }

    @Test
    public void testSaveCourses() {
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

