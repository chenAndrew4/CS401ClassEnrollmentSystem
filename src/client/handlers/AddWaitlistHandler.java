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
import shared.models.WaitList;
import shared.models.requests.AddWaitlistRequest;
import shared.models.responses.AddWaitlistResponse;


public class AddWaitlistHandler {
	public void handleAddWaitlist(Administrator admin, WaitList waitlist, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		AddWaitlistRequest addWaitlistRequest = new AddWaitlistRequest(MessageType.ADD_WAITLIST, null, admin, waitlist);
		
		Client.getInstance().sendRequest(addWaitlistRequest, new ResponseCallback<AddWaitlistResponse, Void>() {
            @Override
            public Void onSuccess(AddWaitlistResponse addWaitlistResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Waitlist has been successfully added!", "Add Waitlist", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Waitlist could not be created: " + reason, "Add Waitlist Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}