package client.handlers;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Course;
import shared.models.Student;
import shared.models.requests.DropCourseRequest;
import shared.models.responses.DropCourseResponse;

public class DropCourseHandler {
	public void handleDropCourse(Student student, Course course, String sectionId, JPanel parentGUI, final BaseDashboardGUI parentDashboard, String sessionToken)
	{	
		DropCourseRequest dropCourseRequest = new DropCourseRequest(MessageType.DISENROLL, null, sessionToken, course.getCourseID(), sectionId, student);
		
		Client.getInstance().sendRequest(dropCourseRequest, new ResponseCallback<DropCourseResponse, Void>() {
            @Override
            public Void onSuccess(DropCourseResponse dropCourseResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Student has successfully been dropped from " + course.getCourseID() + " Section: " + sectionId, "Disenroll", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Could not be dropped: " + reason, "Disenroll Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
