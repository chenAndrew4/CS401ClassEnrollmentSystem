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
import shared.models.Student;
import shared.models.requests.AdminEnrollRequest;
import shared.models.responses.AdminEnrollResponse;

public class AdminEnrollHandler {
	public void handleAdminEnroll(Administrator admin, Student student, Course course, String sectionId, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		AdminEnrollRequest enrollCourseRequest = new AdminEnrollRequest(MessageType.ENROLL, null, admin, student, course, sectionId);
		
		Client.getInstance().sendRequest(enrollCourseRequest, new ResponseCallback<AdminEnrollResponse, Void>() {
            @Override
            public Void onSuccess(AdminEnrollResponse adminEnrollResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Student has successfully been enrolled into " + course.getCourseID() + " Section: " + sectionId, "Enroll Course", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Student could not be enrolled: " + reason, "Enroll Course Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}