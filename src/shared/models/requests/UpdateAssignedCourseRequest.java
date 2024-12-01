package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Course;
import shared.models.CourseSection;
import shared.models.Faculty;

public class UpdateAssignedCourseRequest extends BaseRequest{
    private Faculty faculty;
    private CourseSection course;

    public UpdateAssignedCourseRequest(MessageType messageType, MessageStatus messageStatus, Faculty faculty, CourseSection course) {
        super(messageType, messageStatus, faculty.getInstitutionID(), faculty.getSessionToken(), faculty.isAuthenticated());
        this.faculty = faculty;
        this.course = course;
    }
    public String getUserId() {
    	return this.faculty.getUserId();
    }

    public CourseSection getCourse() {
        return this.course;
    }
}
