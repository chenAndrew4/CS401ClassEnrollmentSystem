package server.service;

import server.dataManagers.CoursesDataManager;
import server.dataManagers.ScheduleDataManager;
import server.gui.ServerGUI;
import server.utils.Log;
import shared.enums.Institutions;
import shared.enums.Time;
import shared.models.CourseSection;
import shared.models.Faculty;
import shared.models.Schedule;

import java.util.*;

public class ScheduleService {
	private Log log;
    private static ScheduleService instance;
    private final ScheduleDataManager scheduleDataManager;

    // Private constructor for Singleton
    private ScheduleService() {
        this.scheduleDataManager = ScheduleDataManager.getInstance();
        log = Log.getInstance(ServerGUI.logTextArea);
    }

    public static synchronized ScheduleService getInstance() {
        if (instance == null) {
            instance = new ScheduleService();
        }
        return instance;
    }

    // Add or Update a Schedule and update course schedule
    public boolean addOrUpdateSchedule(Institutions institutionID, String scheduleID, Schedule updatedSchedule, String sectionID) {

        CourseSection course = CoursesDataManager.getInstance().getSectionById(institutionID, sectionID);
        if (institutionID == null || scheduleID == null || updatedSchedule == null || course == null) {
            log.error("ScheduleService: Invalid input for adding or updating schedule.");
            return false;
        }
        course.setScheduleID(updatedSchedule.getScheduleID());
        scheduleDataManager.addOrUpdateSchedule(institutionID, sectionID, updatedSchedule);
        return true;
    }

    // Get a specific Schedule by ID
    public Schedule getSchedule(Institutions institutionID, String scheduleID) {
        if (institutionID == null || scheduleID == null) {
            log.error("ScheduleService: Invalid input for retrieving schedule.");
            return null;
        }
        return scheduleDataManager.getSchedule(institutionID, scheduleID);
    }

    // Get all schedules for an institution
    public Map<String, Schedule> getAllSchedules(Institutions institutionID) {
        if (institutionID == null) {
            log.error("ScheduleService: Invalid institution ID.");
            return Collections.emptyMap();
        }
        return scheduleDataManager.getAllSchedules(institutionID);
    }

    // Delete a specific Schedule and update a course
    public boolean deleteSchedule(Institutions institutionID, String scheduleID, String sectionID) {
        if (institutionID == null || scheduleID == null) {
            log.error("ScheduleService: Invalid input for deleting schedule.");
            return false;
        }
        boolean result = scheduleDataManager.deleteSchedule(institutionID, scheduleID);

        CourseSection course = CoursesDataManager.getInstance().getSectionById(institutionID, sectionID);
        if (course != null) {
            if (course.getScheduleID().equals(scheduleID)) {
                course.setScheduleID(null);
            }
            return true;
        }

        if (result) {
            log.println("ScheduleService: Schedule deleted: " + scheduleID);
        } else {
            log.error("ScheduleService: Failed to delete schedule: " + scheduleID);
        }
        return result;
    }

    // Delete all schedules for an institution
    public boolean deleteAllSchedules(Institutions institutionID) {
        if (institutionID == null) {
            log.error("ScheduleService: Invalid institution ID.");
            return false;
        }
        boolean result = scheduleDataManager.deleteAllSchedules(institutionID);
        if (result) {
        	log.println("ScheduleService: All schedules deleted for institution: " + institutionID);
        } else {
            log.error("ScheduleService: Failed to delete schedules for institution: " + institutionID);
        }
        return result;
    }

    // Check if a Schedule Exists
    public boolean scheduleExists(Institutions institutionID, String scheduleID) {
        if (institutionID == null || scheduleID == null) {
            log.error("ScheduleService: Invalid input for checking schedule existence.");
            return false;
        }
        Schedule schedule = scheduleDataManager.getSchedule(institutionID, scheduleID);
        return schedule != null;
    }

    // Validate Schedule (example business rule)
    public boolean validateSchedule(Schedule schedule) {
        if (schedule == null) {
            log.error("ScheduleService: Invalid schedule: null");
            return false;
        }
        if (schedule.getStartTime().after(schedule.getEndTime())) {
            log.error("ScheduleService: Invalid schedule: start time is after end time.");
            return false;
        }
        log.println("ScheduleService: Schedule is valid.");
        return true;
    }

    // Search schedules by time range
    public List<Schedule> searchSchedulesByTime(Institutions institutionID, Time startTime, Time endTime) {
        if (institutionID == null || startTime == null || endTime == null) {
            log.error("ScheduleService: Invalid input for searching schedules by time.");
            return Collections.emptyList();
        }
        Map<String, Schedule> schedules = scheduleDataManager.getAllSchedules(institutionID);
        List<Schedule> matchingSchedules = new ArrayList<>();
        for (Schedule schedule : schedules.values()) {
            if (!schedule.getStartTime().before(startTime) && !schedule.getEndTime().after(endTime)) {
                matchingSchedules.add(schedule);
            }
        }
        return matchingSchedules;
    }

    // get list of schedule by list of sections
    public List<Schedule> getSchedulesByCourses(List<CourseSection> enrolledCourses, Institutions institutionID) {
        List<Schedule> scheduleList = new ArrayList<>();
        for (CourseSection section : enrolledCourses) {
            Schedule schedule = getSchedule(institutionID, section.getScheduleID());
            if (schedule != null) {
                scheduleList.add(schedule);
            }
        }
        return scheduleList;
    }

    // admin
    // Add schedule for the faculty and set faculty's schedule
    public boolean addTeachingSchedule(Faculty faculty,  Schedule schedule) {
        List<Schedule> teachingSchedule = faculty.getSchedule();
        if (teachingSchedule.contains(schedule)) {
            schedule.setFacultyID(faculty.getUserId());
            teachingSchedule.add(schedule);
            return true;
        }
        return false;
    }

    // Remove schedule for the faculty and set faculty's schedule to null
    public boolean removeTeachingSchedule(Faculty faculty, Schedule schedule) {
        List<Schedule> teachingSchedule = faculty.getSchedule();
        if (teachingSchedule.contains(schedule)) {
            schedule.setFacultyID(null);
            teachingSchedule.remove(schedule);
            return true;
        }
        return false;
    }
}