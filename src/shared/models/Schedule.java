package shared.models;

import shared.enums.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Schedule implements Serializable {
    private final String scheduleID;  // Unique identifier for the schedule
    private Days[] days;        // Days the course meets
    private Date endDate;       // End date of the schedule
    private Date startDate;     // Start date of the schedule
    private Time startTime;     // Start time of the course
    private Time endTime;       // End time of the course

    public Schedule() {
        scheduleID = null;
    }

    // Constructor
    public Schedule(String scheduleID, Days[] days, Date startDate, Date endDate, Time startTime, Time endTime) {
        this.scheduleID = scheduleID;
        this.days = days;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
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

    // Setters
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