package shared.models;

import shared.enums.*;
import shared.utils.IDGenerator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
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
    private Building building;
    private Campus campus;
    private Room room;
    private String facultyID;

    public Schedule(Institutions institutionID) {
        scheduleID = IDGenerator.getInstance().generateUniqueScheduleID(institutionID);
    }

    public Schedule(Institutions institutionID, String courseID, String sectionID, Term term, Days[] days, Date endDate, Date startDate, Time startTime, Time endTime, Building building, Campus campus, Room room, String facultyID) {
        this.scheduleID = IDGenerator.getInstance().generateUniqueScheduleID(institutionID);
        this.courseID = courseID;
        this.sectionID = sectionID;
        this.term = term;
        this.days = days;
        this.endDate = endDate;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.building = building;
        this.campus = campus;
        this.room = room;
        this.facultyID = facultyID;
    }
    // Copy constructor
    public Schedule(Schedule other) {
        this.scheduleID = other.scheduleID; // Copy immutable field
        this.courseID = other.courseID;
        this.sectionID = other.sectionID;
        this.term = other.term;
        this.days = other.days != null ? other.days.clone() : null; // Deep copy for array
        this.endDate = other.endDate != null ? new Date(other.endDate.getTime()) : null; // Deep copy for Date
        this.startDate = other.startDate != null ? new Date(other.startDate.getTime()) : null;
        this.startTime = other.startTime; // Deep copy for Time
        this.endTime = other.endTime;
        this.building = other.building;
        this.campus = other.campus;
        this.room = other.room;
        this.facultyID = other.facultyID;
    }

    public boolean isDateWithinSchedule(Date selectedDate) {
        Calendar scheduleStart = Calendar.getInstance();
        scheduleStart.setTime(this.getStartDate());

        Calendar scheduleEnd = Calendar.getInstance();
        scheduleEnd.setTime(this.getEndDate());

        Calendar selected = Calendar.getInstance();
        selected.setTime(selectedDate);

        // Check if the selected date falls within the schedule's start and end dates
        return !selected.before(scheduleStart) && !selected.after(scheduleEnd);
    }

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

    public Building getLocation() {
        return building;
    }

    public void setLocation(Building building) {
        this.building = building;
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