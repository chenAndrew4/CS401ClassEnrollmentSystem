package shared.models.responses;


import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.CourseSection;
import java.util.List;

public class GetAssignedCoursesResponse extends BaseResponse{
    List<CourseSection> assignedCourses;

    public GetAssignedCoursesResponse(MessageType messageType, MessageStatus messageStatus, List<CourseSection> assignedCourses, String message){
        super(message, messageStatus, messageType);
        this.assignedCourses = assignedCourses;
    }
    public List<CourseSection> getAssignedCourses(){
        return assignedCourses;
    }
}
