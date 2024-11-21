package shared.models.responses;

public class DropCourseResponse extends BaseResponse {
    private String courseId;
    private String sectionId;

    public DropCourseResponse(String message, String courseId, String sectionId) {
        super(message);
        this.courseId = courseId;
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}

