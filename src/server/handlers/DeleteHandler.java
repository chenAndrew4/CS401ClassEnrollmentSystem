package server.handlers;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;

public class DeleteHandler {
    // delete class
    public static Message handleDelete(Message request) {
//        String classId = (String) request.getContent();
//        boolean isDeleted = CourseManager.deleteCourse(classId);

//        if (isDeleted) {
            return new Message(MessageType.DELETE, "Deletion successful").setStatus(MessageStatus.SUCCESS);
//        } else {
//            return new Message(MessageType.DELETE, "Deletion failed").setStatus(MessageStatus.ERROR);
//        }
    }
}
