package shared.models.requests;

import shared.enums.Institutions;

public class DropCourseRequest extends BaseRequest {
    private String courseId;
    private String sectionId;

    public DropCourseRequest(Institutions institutionID, String sessionToken, String courseId, String sectionId) {
        super(institutionID, sessionToken);
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
