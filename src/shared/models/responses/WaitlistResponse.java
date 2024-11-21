package shared.models.responses;

public class WaitlistResponse extends BaseResponse {
    private String courseId;
    private int waitlistPosition;

    public WaitlistResponse(String message, String courseId, int waitlistPosition) {
        super(message);
        this.courseId = courseId;
        this.waitlistPosition = waitlistPosition;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getWaitlistPosition() {
        return waitlistPosition;
    }

    public void setWaitlistPosition(int waitlistPosition) {
        this.waitlistPosition = waitlistPosition;
    }
}

