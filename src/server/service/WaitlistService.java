package server.service;

import server.dataManagers.CoursesDataManager;
import server.dataManagers.WaitlistDataManager;
import server.gui.ServerGUI;
import server.utils.Log;
import shared.enums.Institutions;
import shared.models.CourseSection;
import shared.models.Student;
import shared.models.WaitList;

import java.util.Collections;
import java.util.Map;

public class WaitlistService {
	private Log log;
    private static WaitlistService instance;
    private final WaitlistDataManager waitlistDataManager;

    private WaitlistService() {
        this.waitlistDataManager = WaitlistDataManager.getInstance();
        log = Log.getInstance(ServerGUI.logTextArea);
    }

    public static synchronized WaitlistService getInstance() {
        if (instance == null) {
            instance = new WaitlistService();
        }
        return instance;
    }

    // Add or Update a waitlist
    public boolean addOrUpdateWaitlist(Institutions institutionID, String sectionID, WaitList waitlist) {
        if (institutionID == null || sectionID == null || waitlist == null) {
            log.error("WaitlistService: Invalid input for adding or updating waitlist.");
            return false;
        }
        return waitlistDataManager.addOrUpdateWaitlist(institutionID, sectionID, waitlist);
    }

    // Get a specific waitlist
    public WaitList getWaitlist(Institutions institutionID, String sectionID) {
        if (institutionID == null || sectionID == null) {
            log.error("WaitlistService: Invalid input for retrieving waitlist.");
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
                log.println("WaitlistService: Notifying " + nextStudent.getFirstName() + " about seat availability in " + courseID);
                // Logic to send a notification (e.g., email or socket message)
            } else {
                log.println("WaitlistService: No students on the waitlist for course: " + courseID);
            }
        }
    }

    // Get all waitlists for an institution
    public Map<String, WaitList> getAllWaitlists(Institutions institutionID) {
        if (institutionID == null) {
            log.error("WaitlistService: Invalid institution ID.");
            return Collections.emptyMap();
        }
        return waitlistDataManager.getAllWaitlists(institutionID);
    }

    // Remove a waitlist
    public boolean removeWaitlist(Institutions institutionID, String courseID) {
        if (institutionID == null || courseID == null) {
            log.error("WaitlistService: Invalid input for removing waitlist.");
            return false;
        }
        return waitlistDataManager.removeWaitlist(institutionID, courseID);
    }

    // student
    public boolean addToWaitlist(Institutions institutionID, Student student, String sectionID) {
       WaitList cur = waitlistDataManager.getWaitlist(institutionID, sectionID);
       cur.addToWaitlist(student);
       return waitlistDataManager.addOrUpdateWaitlist(institutionID, sectionID, cur);
    }

    public int getWaitlistPositions(Institutions institutionID, String sectionID, Student student) {
        CourseSection section = CoursesDataManager.getInstance().getSectionById(institutionID, sectionID);
        if (section != null) {
            return WaitlistService.getInstance().getWaitlist(institutionID, section.getSectionID()).getPosition(student);
        }
        return -1;
    }

}
