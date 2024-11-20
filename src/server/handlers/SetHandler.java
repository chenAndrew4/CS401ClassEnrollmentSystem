package server.handlers;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Course;
import shared.models.Message;

public class SetHandler {
    // setCourse
    public static Message handleSet(Message request) {
//        Course course = (Course) request.getContent();
//        boolean isUpdated = CourseManager.updateCourse(course);
//
//        if (isUpdated) {
            return new Message(MessageType.SET_SYLLABUS, "Update successful").setStatus(MessageStatus.SUCCESS);
//        } else {
//            return new Message(MessageType.SET, "Update failed").setStatus(MessageStatus.FAILURE);
//        }

    }
}
