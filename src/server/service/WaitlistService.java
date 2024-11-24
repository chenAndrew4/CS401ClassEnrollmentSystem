package server.service;

import server.dataManagers.WaitlistDataManager;
import shared.enums.Institutions;
import shared.models.Student;
import shared.models.WaitList;

import java.util.Collections;
import java.util.Map;

public class WaitlistService {
    private static WaitlistService instance;
    private final WaitlistDataManager waitlistDataManager;

    private WaitlistService() {
        this.waitlistDataManager = WaitlistDataManager.getInstance();
    }

    public static synchronized WaitlistService getInstance() {
        if (instance == null) {
            instance = new WaitlistService();
        }
        return instance;
    }

//    // Add or Update a waitlist
//    public boolean addOrUpdateWaitlist(Institutions institutionID, String courseID, WaitList waitlist) {
//        if (institutionID == null || courseID == null || waitlist == null) {
//            System.err.println("Invalid input for adding or updating waitlist.");
//            return false;
//        }
//        return waitlistDataManager.addOrUpdateWaitlist(institutionID, courseID, waitlist);
//    }

    // Add or Update a waitlist
    public boolean addOrUpdateWaitlist(Institutions institutionID, String sectionID, WaitList waitlist) {
        if (institutionID == null || sectionID == null || waitlist == null) {
            System.err.println("Invalid input for adding or updating waitlist.");
            return false;
        }
        return waitlistDataManager.addOrUpdateWaitlist(institutionID, sectionID, waitlist);
    }

    // Get a specific waitlist
    public WaitList getWaitlist(Institutions institutionID, String sectionID) {
        if (institutionID == null || sectionID == null) {
            System.err.println("Invalid input for retrieving waitlist.");
            return null;
        }
        return waitlistDataManager.getWaitlist(institutionID, sectionID);
    }

    // Notify the next student on the waitlist
    public void notifyNextStudent(Institutions institutionID, String courseID) {
        WaitList waitlist = getWaitlist(institutionID, courseID);
        if (waitlist != null && !waitlist.isEmpty()) {
            Student nextStudent = waitlist.pollNextStudent();
            if (nextStudent != null) {
                System.out.println("Notifying " + nextStudent.getFirstName() + " about seat availability in " + courseID);
                // Logic to send a notification (e.g., email or socket message)
            } else {
                System.out.println("No students on the waitlist for course: " + courseID);
            }
        }
    }

    // Get all waitlists for an institution
    public Map<String, WaitList> getAllWaitlists(Institutions institutionID) {
        if (institutionID == null) {
            System.err.println("Invalid institution ID.");
            return Collections.emptyMap();
        }
        return waitlistDataManager.getAllWaitlists(institutionID);
    }

    // Remove a waitlist
    public boolean removeWaitlist(Institutions institutionID, String courseID) {
        if (institutionID == null || courseID == null) {
            System.err.println("Invalid input for removing waitlist.");
            return false;
        }
        return waitlistDataManager.removeWaitlist(institutionID, courseID);
    }

    public boolean addToWaitlist(Institutions institutionID, Student student, String sectionID) {
       WaitList cur = waitlistDataManager.getWaitlist(institutionID, sectionID);
       cur.addToWaitlist(student);
       return waitlistDataManager.addOrUpdateWaitlist(institutionID, sectionID, cur);
    }
}
