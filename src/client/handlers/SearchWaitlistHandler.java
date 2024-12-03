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
import shared.models.requests.SearchWaitlistRequest;
import shared.models.responses.SearchWaitlistResponse;

public class SearchWaitlistHandler 
{
	public void handleSearchWaitlist(Student student, JPanel parentGUI, final BaseDashboardGUI parentDashboard, String sessionToken) 
	{
		SearchWaitlistRequest searchWaitlistRequest = new SearchWaitlistRequest(MessageType.SEARCH_WAITLIST, null, sessionToken, student);
		Client.getInstance().sendRequest(searchWaitlistRequest, new ResponseCallback<SearchWaitlistResponse, Void>() {
            @Override
            public Void onSuccess(SearchWaitlistResponse searchWaitlistResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Waitlists Found", "Search Waitlist", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Could not find any waitlists: " + reason, "Search Waitlist Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
