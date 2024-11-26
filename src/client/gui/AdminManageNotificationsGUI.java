package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class AdminManageNotificationsGUI {
    private JPanel manageNotificationsPanel;

    public AdminManageNotificationsGUI(AdminDashboardGUI adminDashboardGUI) {
        manageNotificationsPanel = new JPanel();
        manageNotificationsPanel.add(new JLabel("Admin Manage Notifications Panel")); // Example content
    }

    public JPanel getPanel() {
        return manageNotificationsPanel;
    }
}
