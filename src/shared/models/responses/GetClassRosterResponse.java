package shared.models.responses;

import shared.models.User;

import java.util.List;

public class GetClassRosterResponse extends BaseResponse {
    private List<User> classRoster;
    private String courseId;
    private String sectionId;

    public GetClassRosterResponse(String message, List<User> classRoster, String courseId, String sectionId) {
        super(message);
        this.classRoster = classRoster;
        this.courseId = courseId;
        this.sectionId = sectionId;
    }

    public List<User> getClassRoster() {
        return classRoster;
    }

    public void setClassRoster(List<User> classRoster) {
        this.classRoster = classRoster;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
}

