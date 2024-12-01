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
import shared.models.requests.AdminDisenrollRequest;
import shared.models.responses.AdminDisenrollResponse;

public class AdminDisenrollHandler {
	public void handleAdminDisenroll(Administrator admin, Student student, Course course, String sectionId, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		AdminDisenrollRequest adminDisenrollRequest = new AdminDisenrollRequest(MessageType.DISENROLL, null, admin, student, course, sectionId);
		
		Client.getInstance().sendRequest(adminDisenrollRequest, new ResponseCallback<AdminDisenrollResponse, Void>() {
            @Override
            public Void onSuccess(AdminDisenrollResponse adminDisenrollResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Student has successfully been disenrolled from " + course.getCourseID() + " Section: " + sectionId, "Disenroll", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Student could not be disenrolled: " + reason, "Disenroll Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}