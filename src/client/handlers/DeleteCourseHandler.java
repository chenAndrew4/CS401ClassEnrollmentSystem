package client.handlers;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Course;
import shared.models.requests.DeleteCourseRequest;
import shared.models.responses.DeleteCourseResponse;

public class DeleteCourseHandler {
	public void handleDeleteCourse(Administrator currentUser, Course courseToDelete, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions to control what we can delete and what cannot be deleted. e.g. if a student is enrolled, we cannot delete the course
		// We would need to query more data to find out if students are enrolled in a course.
		// We should do that at the server side in dataManager?
		
		DeleteCourseRequest deleteCourseRequest = new DeleteCourseRequest(MessageType.DELETE_COURSE, null, currentUser, courseToDelete);
        
		Client.getInstance().sendRequest(deleteCourseRequest, new ResponseCallback<DeleteCourseResponse, Void>() {
            @Override
            public Void onSuccess(DeleteCourseResponse deleteCourseResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Course deleted successfully!", "Delete Course", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Course could not be deleted: " + reason, "Delete Course Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
