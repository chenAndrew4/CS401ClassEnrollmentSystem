package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class UpdateAssignedCourseResponse extends BaseResponse{
    private int enrollmentLimit;

    public UpdateAssignedCourseResponse(String message, MessageStatus messageStatus, MessageType messageType, int enrollmentLimit) {
        super(message, messageStatus, messageType);
        this.enrollmentLimit = enrollmentLimit;
    }
    public int getEnrollmentLimit() {
        return enrollmentLimit;
    }
    public void setEnrollmentLimit(int enrollmentLimit) {
        this.enrollmentLimit = enrollmentLimit;
    }

}
