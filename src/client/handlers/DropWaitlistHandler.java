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
import shared.models.requests.DropWaitlistRequest;
import shared.models.responses.DropWaitlistResponse;

public class DropWaitlistHandler {
	public void handleStudentDropWaitlist(Student student, Course course, String sectionId, JPanel parentGUI, final BaseDashboardGUI parentDashboard, String sessionToken) 
	{
		DropWaitlistRequest dropWaitlistRequest = new DropWaitlistRequest(MessageType.DROP_WAITLIST, null, sessionToken, course.getCourseID(), sectionId, student);
		Client.getInstance().sendRequest(dropWaitlistRequest, new ResponseCallback<DropWaitlistResponse, Void>() {
            @Override
            public Void onSuccess(DropWaitlistResponse dropWaitlistResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Student has successfully been dropped from " + course.getCourseID() + " Section: " + sectionId, "Drop Waitlist", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Could not be dropped: " + reason, "Drop Waitlist Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
