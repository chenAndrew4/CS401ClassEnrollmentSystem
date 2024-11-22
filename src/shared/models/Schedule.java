package shared.models;

import shared.enums.*;

import java.io.Serializable;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;

public class Schedule implements Serializable {
    private final String scheduleID;// Unique identifier for the schedule
    private String courseID;
    private String sectionID;
    private Term term;  // course semester or quarter
    private Days[] days;   // Days the course meets
    private Date endDate;     // End date of the schedule
    private Date startDate;    // Start date of the schedule
    private Time startTime;    // Start time of the course
    private Time endTime;     // End time of the course
    private Location location;
    private Campus campus;
    private Room room;
    private String facultyID;

    public Schedule() {
        scheduleID = null;
    }

    public Schedule(String scheduleID, String courseID, String sectionID, Term term, Days[] days, Date endDate, Date startDate, Time startTime, Time endTime, Location location, Campus campus, Room room, String facultyID) {
        this.scheduleID = scheduleID;
        this.courseID = courseID;
        this.sectionID = sectionID;
        this.term = term;
        this.days = days;
        this.endDate = endDate;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.campus = campus;
        this.room = room;
        this.facultyID = facultyID;
    }

    //    // Constructor
//    public Schedule(String scheduleID, String sectionID,Days[] days, Year year, Term term, Date startDate, Date endDate, Time startTime, Time endTime) {
//        this.scheduleID = scheduleID;
//        this.sectionID = sectionID;
//        this.term = term;
//        this.days = days;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }

    public Schedule(String sch) {
        scheduleID = sch;
    }

    // Getters
    public String getScheduleID() {
        return scheduleID;
    }

    public Days[] getDays() {
        return days;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Term getTerm() {
        return term;
    }

    public String getSectionID() {
        return sectionID;
    }

    // Setters
    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public boolean setDays(Days[] days) {
        this.days = days;
        return true;
    }

    public boolean setEndDate(Date endDate) {
        if (startDate != null || endDate.after(this.startDate)) {
            this.endDate = endDate;
            return true;
        }
        return false;
    }

    public boolean setStartDate(Date startDate) {
        if (this.endDate == null || startDate.before(this.endDate)) {
            this.startDate = startDate;
            return true;
        }
        return false;
    }

    public boolean setStartTime(Time startTime) {
        this.startTime = startTime;
        return true;
    }

    public boolean setEndTime(Time endTime) {
        this.endTime = endTime;
        return true;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    // Method to check if this schedule conflicts with another schedule
    public boolean doesThisScheduleConflict(Schedule otherSchedule) {
        // Check if the days overlap
        for (Days day : this.days) {
            if (Arrays.asList(otherSchedule.getDays()).contains(day)) {
                // Check for time conflict
                if (this.startTime.before(otherSchedule.getEndTime()) && otherSchedule.getStartTime().before(this.endTime)) {
                    return true;
                }
            }
        }
        return false;
    }
}