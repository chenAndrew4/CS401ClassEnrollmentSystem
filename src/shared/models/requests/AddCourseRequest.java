package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Course;

public class AddCourseRequest extends BaseRequest {
    private Course course;

    public AddCourseRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, Course course, boolean isAuthenicated) {
        super(messageType, messageStatus,institutionID, sessionToken, isAuthenicated);
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
