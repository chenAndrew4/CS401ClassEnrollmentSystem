package server.handlers;

import server.service.SessionService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.CourseSection;
import shared.models.Faculty;
import shared.models.requests.BaseRequest;
import shared.models.requests.GetAssignedCoursesRequest;
import shared.models.responses.GetAssignedCoursesResponse;

import java.io.IOException;
import java.util.List;

public class GetAssignedCoursesHandler {
    public static GetAssignedCoursesResponse handleGetAssignedCourses(BaseRequest request, Log log) throws IOException {
        GetAssignedCoursesRequest getAssignedCoursesRequest = (GetAssignedCoursesRequest) request;

        if (getAssignedCoursesRequest.isAuthenticated() && SessionService.getInstance().validateSession(getAssignedCoursesRequest.getUserID(), getAssignedCoursesRequest.getSessionToken())) {
             // Fetch list of assigned courses
            List<CourseSection> assignedCourses = getAssignedCoursesRequest.getUser().getAssignedCourses();

            if (assignedCourses != null) {
                return new GetAssignedCoursesResponse(MessageType.GET_ASSIGNED_COURSES, MessageStatus.SUCCESS, assignedCourses, "Success");

            } else {
                return new GetAssignedCoursesResponse(MessageType.GET_ASSIGNED_COURSES, MessageStatus.FAILURE, null, "Failed to retrieve courses");
            }
        }
        return new GetAssignedCoursesResponse(MessageType.GET_ASSIGNED_COURSES, MessageStatus.FAILURE, null, "Invalid request or authentication failed");
    }
}
