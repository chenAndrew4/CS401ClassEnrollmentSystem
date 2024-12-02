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
import shared.models.requests.EnrollWaitlistRequest;
import shared.models.responses.EnrollWaitlistResponse;

public class EnrollWaitlistHandler {
	public void handleStudentEnrollWaitlist(Student student, Course course, String sectionId, JPanel parentGUI, final BaseDashboardGUI parentDashboard, String sessionToken) 
	{
		EnrollWaitlistRequest enrollWaitlistRequest = new EnrollWaitlistRequest(MessageType.ENROLL_WAITLIST, null, sessionToken, course.getCourseID(), sectionId, student);
		Client.getInstance().sendRequest(enrollWaitlistRequest, new ResponseCallback<EnrollWaitlistResponse, Void>() {
            @Override
            public Void onSuccess(EnrollWaitlistResponse enrollWaitlistResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Student has successfully been enrolled into waitlist for" + course.getCourseID() + " Section: " + sectionId, "Enroll Course", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Could not be enrolled: " + reason, "Enroll Waitlist Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
