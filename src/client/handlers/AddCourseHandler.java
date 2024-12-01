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
import shared.models.requests.AddCourseRequest;
import shared.models.responses.AddCourseResponse;

public class AddCourseHandler {
	public void handleAddCourse(Administrator currentUser, Course courseToAdd, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		if (courseToAdd.getCourseID().isBlank() || courseToAdd.getDescription().isBlank() || courseToAdd.getName().isBlank()) {
			JOptionPane.showMessageDialog(parentGUI, "One or more of the following fields are empty:\n-Course ID\n-Description\n-Name.", "Add Course Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
			return;
		}
		AddCourseRequest addCourseRequest = new AddCourseRequest(MessageType.ADD_COURSE, null, currentUser, courseToAdd);
		
		Client.getInstance().sendRequest(addCourseRequest, new ResponseCallback<AddCourseResponse, Void>() {
            @Override
            public Void onSuccess(AddCourseResponse addCourseResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Course added successfully!", "Add Course", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Course could not be added: " + reason, "Add Course Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
