package shared.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class WaitList implements Serializable {
    private String sectionID;  // The course section associated with this waitlist
    private int maxCapacity;        // Maximum number of students on the waitlist
    private Queue<Student> waitingStudents;  // The queue of students on the waitlist

    public WaitList() {
        sectionID = null;
        maxCapacity = 0;
        waitingStudents = null;
    }

    public WaitList(String courseSectionID, int maxCapacity) {
        this.sectionID = courseSectionID;
        this.maxCapacity = maxCapacity;
        this.waitingStudents = new LinkedList<>();
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setWaitingStudents(Queue<Student> waitingStudents) {
        this.waitingStudents = waitingStudents;
    }

    // Add a student to the waitlist
    public boolean addToWaitlist(Student student) {
        if (waitingStudents.size() < maxCapacity) {
            return waitingStudents.offer(student); // Add student to the queue
        }
        return false; // Waitlist is full
    }

    // Remove a student from the waitlist
    public boolean removeFromWaitlist(Student student) {
        return waitingStudents.remove(student);
    }

    // Get the next student in the waitlist
    public Student getNextStudent() {
        return waitingStudents.peek(); // Peek at the front of the queue
    }

    // Get the current size of the waitlist
    public int getCurrentSize() {
        return waitingStudents.size();
    }

    // Check if the waitlist is full
    public boolean isFull() {
        return waitingStudents.size() >= maxCapacity;
    }

    // Get all students on the waitlist
    public Queue<Student> getWaitingStudents() {
        return new LinkedList<>(waitingStudents); // Return a copy to preserve encapsulation
    }

    public int getPosition(Student student) {
        int position = 1; // Positions start at 1
        for (Student s : waitingStudents) {
            if (s.equals(student)) {
                return position;
            }
            position++;
        }
        return -1;
    }

}