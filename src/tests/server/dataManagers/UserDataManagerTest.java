package tests.server.dataManagers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.dataManagers.UserDataManager;
import shared.enums.*;
import shared.models.*;

import java.io.IOException;
import java.util.*;

public class UserDataManagerTest {
        static List<User> users;
        static Map<String, User> userMap;
        static List<User> testUsers;
        static Map<String, User> testUserMap;
        static List<Course> courses;
        static List<Course> testCourses;
        static UserDataManager userDataManager;

        @BeforeAll
        public static void beforeTest() {
            userDataManager = UserDataManager.getInstance();
            testUsers = new ArrayList<>();
            User u1 = new User();
            u1.setUserId("e4eaaaf2-d142-11e1-b3e4-080027620cda");
            u1.setUsername("admin");
            u1.setPassword("password");
            u1.setFirstName("Connor");
            u1.setLastName("McMillan");
            u1.setInstitutionID(Institutions.SJSU);
            u1.setAccountType(AccountType.Administrator);
            Student u2 = new Student();
            u2.setUserId("e4eaaaf2-d142-11e1-b3e4-080027620cdc");
            u2.setUsername("student");
            u2.setPassword("password");
            u2.setFirstName("Connor");
            u2.setLastName("McMillan");
            u2.setInstitutionID(Institutions.SJSU);
            u2.setAccountType(AccountType.Student);
            testUsers.add(u1);
            testUsers.add(u2);
            testUserMap = new HashMap<>();
            testUserMap.put(u1.getUsername(), u1);
            testUserMap.put(u2.getUsername(), u2);
            System.out.println(u1.getUsername());
            System.out.println(u2.getUsername());

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
//            section.setInstructor("Dr. Jane Doe");
//            section.setLocation(Location.LIBRARY);
//            section.setCampus(Campus.HAYWARD);
//            section.setRoom(Room.ROOM1);
//            section.setSchedule(schedule);
            section.setWaitlist(new WaitList());
            section.setClassRoster(new ClassRoster());

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
            course.setSections(Collections.singletonList(section));

            // Output course details for verification
            System.out.println("Course: " + course.getName());
            System.out.println("Section: " + section.getSectionID());
            System.out.println("Schedule ID: " + schedule.getScheduleID());

            testCourses = new ArrayList<>();
            testCourses.add(course);
        }

        //
        // save test always goes before load test
        //
        @Test
        public void testSaveUser() {
            userDataManager.commitDBByInstitution(Institutions.SJSU, testUserMap);
        }

        @Test
        public void testLoadUser() throws IOException {
            userMap = userDataManager.getUsersByInstitution(Institutions.SJSU);
            for (User u : userMap.values()) {
                System.out.println(u.getUsername());
            }
        }
}