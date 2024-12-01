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
import shared.models.Schedule;
import shared.models.requests.AddScheduleRequest;
import shared.models.responses.AddScheduleResponse;

public class AddScheduleHandler {
	public void handleAddSchedule(Administrator admin, Schedule schedule, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		AddScheduleRequest addScheduleRequest = new AddScheduleRequest(MessageType.ADD_SCHEDULE, null, admin, schedule);
		
		Client.getInstance().sendRequest(addScheduleRequest, new ResponseCallback<AddScheduleResponse, Void>() {
            @Override
            public Void onSuccess(AddScheduleResponse addScheduleResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Schedule has been successfully added!", "Add Schedule", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Schedule could not be created: " + reason, "Add Schedule Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}