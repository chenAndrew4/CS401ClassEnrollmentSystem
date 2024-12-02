package client.handlers;

import javax.swing.JPanel;
import client.Client;
import client.gui.ViewAssignedCoursesGUI;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageType;
import shared.models.CourseSection;
import shared.models.Faculty;
import shared.models.requests.GetAssignedCoursesRequest;
import shared.models.responses.GetAssignedCoursesResponse;

import javax.swing.*;
import java.util.List;

public class GetAssignedCoursesHandler {
    public void handleGetAssignedCourses(Faculty currentUser, Institutions institution, JPanel parentGUI, final BaseDashboardGUI parentDashboard){
        GetAssignedCoursesRequest getAssignedCoursesRequest = new GetAssignedCoursesRequest(MessageType.GET_ASSIGNED_COURSES, null, institution, currentUser.getUserId(), currentUser.getAssignedCourses(), currentUser.getSessionToken(), currentUser.isAuthenticated());

        Client.getInstance().sendRequest(getAssignedCoursesRequest, new ResponseCallback<GetAssignedCoursesResponse, Void>(){
            @Override
            public Void onSuccess(GetAssignedCoursesResponse getAssignedCoursesResponse) {
                List<CourseSection> assignedCourses = getAssignedCoursesResponse.getAssignedCourses();

                SwingUtilities.invokeLater(() -> {
                    ViewAssignedCoursesGUI viewAssignedCoursesGUI = new ViewAssignedCoursesGUI(null , assignedCourses);
                });
                return null;
            }

        @Override
        public void onFailure(String reason) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(parentGUI, "Could not retrieve assigned courses: " + reason);
            });
        }});
    }
}
