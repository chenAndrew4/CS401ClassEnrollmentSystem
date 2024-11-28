package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Course;

public class AddCourseRequest extends BaseRequest {
    private Course course;
    private String userID;
    public AddCourseRequest(MessageType messageType, MessageStatus messageStatus, String userID,Institutions institutionID, String sessionToken, Course course, boolean isAuthenicated) {
        super(messageType, messageStatus,institutionID, sessionToken, isAuthenicated);
        this.course = course;
        this.userID = userID;
    }
    public Course getCourse() {
        return course;
    }

    public String getUserID() {
        return userID;
    }
}
