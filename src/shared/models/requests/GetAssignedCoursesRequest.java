package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.CourseSection;

import java.util.List;

public class GetAssignedCoursesRequest extends BaseRequest {
    private String userID;
    private List<CourseSection> assignedCourses;

    public GetAssignedCoursesRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String userID, List<CourseSection> assignedCourses, String sessionToken, boolean isAuthenticated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenticated);
        this.userID = userID;
        this.assignedCourses = assignedCourses;
    }

    public String getUserID() {
        return userID;
    }

    public List<CourseSection> getAssignedCourses() {
        return assignedCourses;
    }
}
