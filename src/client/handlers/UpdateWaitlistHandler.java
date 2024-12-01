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
import shared.models.requests.UpdateWaitlistRequest;
import shared.models.responses.UpdateWaitlistResponse;

public class UpdateWaitlistHandler {
	public void handleUpdateWaitlist(Administrator admin, WaitList newWaitlist, WaitList oldWaitlist, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		UpdateWaitlistRequest updateWaitlistRequest = new UpdateWaitlistRequest(MessageType.UPDATE_WAITLIST, null, admin, newWaitlist, oldWaitlist);
		
		Client.getInstance().sendRequest(updateWaitlistRequest, new ResponseCallback<UpdateWaitlistResponse, Void>() {
            @Override
            public Void onSuccess(UpdateWaitlistResponse updateWaitlistResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Waitlist has been successfully updated!", "Update Waitlist", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Waitlist could not be updated: " + reason, "Update Waitlist Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
