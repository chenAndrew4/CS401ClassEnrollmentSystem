package client.handlers;

import client.Client;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.MessageType;
import shared.models.CourseSection;
import shared.models.Faculty;
import shared.models.requests.UpdateAssignedCourseRequest;
import shared.models.responses.UpdateAssignedCourseResponse;

import javax.swing.*;

public class UpdateAssignedCourseHandler {
    public  void updateAssignedCourse(Faculty currentUser, CourseSection course, JPanel parentGUI, final BaseDashboardGUI parentDashboardGUI) {
        UpdateAssignedCourseRequest updateAssignedCourseRequest = new UpdateAssignedCourseRequest(MessageType.UPDATE_COURSE, null, currentUser, course);

       Client.getInstance().sendRequest(updateAssignedCourseRequest, new ResponseCallback<UpdateAssignedCourseResponse, Void>() {
           @Override
           public Void onSuccess(UpdateAssignedCourseResponse updateAssignedCourseResponse) {
               JOptionPane.showMessageDialog(parentGUI, "Course updated successfully!");
               return null;
           }
           @Override
           public void onFailure(String reason) {
               SwingUtilities.invokeLater(() -> {
                   JOptionPane.showMessageDialog(parentGUI, "Course could not be updated: " + reason);
               });
           }
       });
    }
}