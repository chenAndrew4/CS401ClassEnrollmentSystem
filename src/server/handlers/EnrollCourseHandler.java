package server.handlers;

import server.CourseManager;
import server.SessionManager;
import shared.models.Message;
import shared.models.User;

public class EnrollCourseHandler {
    public static Message handleEnrollCourse(Message request) {
        SessionManager sessionManager = SessionManager.getInstance();
        User user = (User) request.getContent();
        CourseManager courseManager = CourseManager.getInstance();
        return new Message();
    }
}
