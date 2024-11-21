package shared.models.requests;

import shared.enums.Institutes;


public class EnrollCourseRequest extends BaseRequest {
    private String courseId;
    private String sectionId;

    public EnrollCourseRequest(Institutes instituteID, String sessionToken, String courseId, String sectionId) {
        super(instituteID, sessionToken);
        this.courseId = courseId;
        this.sectionId = sectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSectionId() {
        return sectionId;
    }
}
