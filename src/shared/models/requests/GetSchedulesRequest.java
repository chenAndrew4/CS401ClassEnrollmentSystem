package shared.models.requests;

import client.Client;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.CourseSection;
import shared.models.User;

import javax.swing.*;
import java.util.List;

public class GetSchedulesRequest extends BaseRequest {
    private String  userID;
    private List<CourseSection> enrolledCourses;
    public GetSchedulesRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String userID, List<CourseSection> enrolledCourses, String sessionToken, boolean isAuthenticated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenticated);
        this.userID = userID;
        this.enrolledCourses = enrolledCourses;
    }
    public String getUserID() {
        return userID;
    }

    public List<CourseSection> getEnrolledCourses() {
        return enrolledCourses;
    }
}