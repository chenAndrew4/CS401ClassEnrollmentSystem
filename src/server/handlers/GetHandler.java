package server.handlers;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Course;
import shared.models.Message;
import shared.models.requests.BaseRequest;
import shared.models.responses.BaseResponse;

public class GetHandler {
    // handleGetCourse
    public static BaseResponse handleGet(BaseRequest request) {
//        String classId = (String) request.getContent();
//        Course course = CourseManager.getCourseById(classId);
//
//        if (course != null) {
        //**** to do need to add course
//            return new Message(MessageType.GET_SYLLABUS, new Course()).setStatus(MessageStatus.SUCCESS);
//        } else {
//            return new Message(MessageType.GET, "Class not found").setStatus(MessageStatus.ERROR);
//        }
        return null;
    }
}