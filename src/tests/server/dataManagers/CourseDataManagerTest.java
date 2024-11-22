package tests.server.dataManagers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.dataManagers.CoursesDataManager;
import shared.enums.*;
import shared.models.*;

import java.io.IOException;
import java.util.*;

public class CourseDataManagerTest {
    static Map<String, Course> coursesMap;
    static Map<String, Course> testCoursesMap;

    @BeforeAll
    public static void beforeTest() {
        // Create a Schedule instance
        Schedule schedule = new Schedule("SCH001");
        schedule.setDays(new Days[]{Days.MONDAY, Days.WEDNESDAY, Days.FRIDAY});
        Date sD = new Date(2024, Calendar.JANUARY, 15);
        schedule.setStartDate(sD); // Example: Jan 15, 2024
        Date eD = new Date(2024, Calendar.MAY, 10);
        schedule.setEndDate(eD);       // Example: May 10, 2024
        schedule.setStartTime(Time.TIME_900);                        // 9:00 AM
        schedule.setEndTime(Time.TIME_1030);
        schedule.setTerm(Term.SPRING_SEMESTER);// 10:30 AM

        // Create a CourseSection instance
        CourseSection section = new CourseSection();
        section.setSectionID("CS101-01");
        section.setEnrollmentLimit(30);
        section.setGrading(GradingType.Letter);
        section.setInstructionMode(InstructionModeType.InPerson);
//        section.setInstructor("Dr. Jane Doe");
//        section.setLocation(Location.LIBRARY);
//        section.setCampus(Campus.HAYWARD);
//        section.setRoom(Room.ROOM1);
//        section.setSchedule(schedule);
        section.setWaitlist(new WaitList());
        section.setClassRoster(new ClassRoster());

        // Create a CourseSection instance
        CourseSection section2 = new CourseSection();
        section2.setSectionID("CS101-02");
        section2.setEnrollmentLimit(30);
        section2.setGrading(GradingType.Letter);
        section2.setInstructionMode(InstructionModeType.InPerson);
//        section2.setInstructor("Dr. Jane Doe");
//        section2.setLocation(Location.LIBRARY);
//        section2.setCampus(Campus.HAYWARD);
//        section2.setRoom(Room.ROOM1);
//        section2.setSchedule(schedule);
        section2.setWaitlist(new WaitList());
        section2.setClassRoster(new ClassRoster());
        List<CourseSection> sections = new ArrayList<>();
        sections.add(section);
        sections.add(section2);

        // Create a Course instance
        Course course = new Course();
        course.setCourseID("CS101");
        course.setName("Introduction to Computer Science");
        course.setDescription("Basic principles of computer science.");
        course.setInstitutionID(Institutions.SJSU);
        course.setNotes("No prior experience required.");
        course.setLevel(LevelType.Lower);
        course.setAcademicProgram(AcademicProgramType.UGM);
        course.setUnits(4.0f);
        course.setDepartment(Department.CS);
        course.setPrerequisites(Arrays.asList("MATH101"));
        course.setSections(sections);

        // Output course details for verification
        System.out.println("Course: " + course.getName());
        for (CourseSection s : sections) {
            System.out.println("Schedule ID: " + s.getSectionID());
        }

        testCoursesMap = new HashMap<>();
        testCoursesMap.put(course.getCourseID(), course);
        coursesMap = new HashMap<>();
    }
    @Test
    public void testSaveCourse() {
        CoursesDataManager.getInstance().saveCoursesByInstitution(Institutions.SJSU, testCoursesMap);
    }

    @Test
    public void testLoadUser() throws IOException {
        coursesMap = CoursesDataManager.getInstance().loadCoursesByInstitution(Institutions.SJSU);
//        coursesMap = CourseDataManager.loadUsersByInstitution(Institutions.SJSU);
        for (Course c : coursesMap.values()) {
            System.out.println(c.getName());
            for (CourseSection s : c.getSections()) {
                System.out.println("Schedule ID: " + s.getSectionID());
            }
        }
    }
}
