package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.CourseSection;
import shared.models.Faculty;

import java.util.List;

public class GetAssignedCoursesRequest extends BaseRequest {
    Faculty currentUser;
    String userID;
    private List<CourseSection> assignedCourses;

    public GetAssignedCoursesRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, Faculty currentUser, List<CourseSection> assignedCourses, String sessionToken, boolean isAuthenticated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenticated);
        this.currentUser = currentUser;
        this.userID = currentUser.getUserId();
        this.assignedCourses = assignedCourses;
    }

    public String getUserID() {
        return userID;
    }

    public Faculty getUser() {
        return currentUser;
    }

    public List<CourseSection> getAssignedCourses() {
        return assignedCourses;
    }
}
