package shared.models.requests;

import shared.enums.Institutions;
import shared.models.Course;

public class AddCourseRequest extends BaseRequest {
    private Course course;

    public AddCourseRequest(Institutions institutionID, String sessionToken, Course course) {
        super(institutionID, sessionToken);
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
