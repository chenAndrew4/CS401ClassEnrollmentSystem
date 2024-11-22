package tests.server.dataManagers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.dataManagers.UserDataManager;
import shared.enums.*;
import shared.models.*;

import java.io.IOException;
import java.util.*;

public class UserDataManagerTest {
        static Map<String, User> userMap;
        static List<User> testUsers;
        static Map<String, User> testUserMap;
        static UserDataManager userDataManager;

        @BeforeAll
        public static void beforeTest() {
            userDataManager = UserDataManager.getInstance();
            testUsers = new ArrayList<>();
            Administrator u1 = new Administrator();
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
//            testUsers.add(u1);
//            testUsers.add(u2);
//            testUserMap = new HashMap<>();
//            testUserMap.put(u1.getUsername(), u1);
//            testUserMap.put(u2.getUsername(), u2);

            userDataManager.addUserByInstitution(Institutions.SJSU, u1);
            userDataManager.addUserByInstitution(Institutions.SJSU, u2);
            System.out.println(u1.getUsername());
            System.out.println(u2.getUsername());

            Administrator u3 = new Administrator();
            u3.setUserId("e4eaaaf2-d142-11e1-b3e4-080027620cda");
            u3.setUsername("admin");
            u3.setPassword("password");
            u3.setFirstName("Connor");
            u3.setLastName("McMillan");
            u3.setInstitutionID(Institutions.CSUEB);
            u3.setAccountType(AccountType.Administrator);
            Student u4 = new Student();
            u4.setUserId("e4eaaaf2-d142-11e1-b3e4-080027620cdc");
            u4.setUsername("student");
            u4.setPassword("password");
            u4.setFirstName("Connor");
            u4.setLastName("McMillan");
            u4.setInstitutionID(Institutions.CSUEB);
            u4.setAccountType(AccountType.Student);
//            testUsers.add(u3);
//            testUsers.add(u4);
//            testUserMap = new HashMap<>();
//            testUserMap.put(u3.getUsername(), u3);
//            testUserMap.put(u4.getUsername(), u4);


            userDataManager.addUserByInstitution(Institutions.CSUEB, u3);
            userDataManager.addUserByInstitution(Institutions.CSUEB, u4);
            System.out.println(u3.getUsername());
            System.out.println(u4.getUsername());

        }

        //
        // save test always goes before load test
        //
        @Test
        public void testSaveUser() {
            userDataManager.commitDBByInstitution(Institutions.SJSU);
            userDataManager.commitDBByInstitution(Institutions.CSUEB);
        }

        @Test
        public void testLoadUserSJSU() throws IOException {
            userMap = userDataManager.getUsersByInstitution(Institutions.SJSU);
            for (User u : userMap.values()) {
                System.out.println(u.getUsername());
            }
        }
        @Test
        public void testLoadUserCSUEB() throws IOException {
            userMap = userDataManager.getUsersByInstitution(Institutions.CSUEB);
            for (User u : userMap.values()) {
                System.out.println(u.getUsername());
            }
        }
}
