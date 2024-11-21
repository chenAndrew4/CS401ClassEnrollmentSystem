package shared.models.requests;

import shared.enums.Institutes;
import shared.models.Course;

public class AddCourseRequest extends BaseRequest {
    private Course course;

    public AddCourseRequest(Institutes instituteID, String sessionToken, Course course) {
        super(instituteID, sessionToken);
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
