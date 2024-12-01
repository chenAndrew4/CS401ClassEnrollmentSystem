package client.handlers;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Course;
import shared.models.requests.UpdateCourseRequest;
import shared.models.responses.UpdateCourseResponse;

public class UpdateCourseHandler {
	public void handleUpdateCourse(Administrator currentUser, Course courseToUpdate, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions to control what we can update and what cannot be updated.
		
		UpdateCourseRequest updateCourseRequest = new UpdateCourseRequest(MessageType.UPDATE_COURSE, null, currentUser, courseToUpdate);
        
		Client.getInstance().sendRequest(updateCourseRequest, new ResponseCallback<UpdateCourseResponse, Void>() {
            @Override
            public Void onSuccess(UpdateCourseResponse updateCourseResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Course updated successfully!", "Update Course", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Course could not be updated: " + reason, "Update Course Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
