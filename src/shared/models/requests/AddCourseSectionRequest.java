package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.CourseSection;

public class AddCourseSectionRequest extends BaseRequest{
    private CourseSection Section;
    private String userID;
    private String courseID;
    public AddCourseSectionRequest(MessageType messageType, MessageStatus messageStatus, String userID,Institutions institutionID, String sessionToken, boolean isAuthenicated, CourseSection section, String courseID) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
        this.Section = section;
        this.userID = userID;
        this.courseID = courseID;
    }

    public CourseSection getSection() {
        return Section;
    }

    public String getUserID() {
        return userID;
    }

    public String getCourseID() {
        return courseID;
    }
}
