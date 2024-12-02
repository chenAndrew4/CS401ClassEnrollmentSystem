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
import shared.models.requests.SearchCourseRequest;
import shared.models.responses.SearchCourseResponse;

public class SearchCourseHandler 
{
	public void handleSearchCourse(Student student, JPanel parentGUI, final BaseDashboardGUI parentDashboard, String sessionToken) 
	{
		SearchCourseRequest searchCourseRequest = new SearchCourseRequest(MessageType.SEARCH_COURSE, null, sessionToken, student);
		Client.getInstance().sendRequest(searchCourseRequest, new ResponseCallback<SearchCourseResponse, Void>() {
            @Override
            public Void onSuccess(SearchCourseResponse searchCourseResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Courses Found", "Search Course", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Could not find any courses: " + reason, "Search Course Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
