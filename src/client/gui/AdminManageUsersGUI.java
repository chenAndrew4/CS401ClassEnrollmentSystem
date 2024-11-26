package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class AdminManageUsersGUI {
    private JPanel manageUsersPanel;

    public AdminManageUsersGUI(AdminDashboardGUI adminDashboardGUI) {
        manageUsersPanel = new JPanel();
        manageUsersPanel.add(new JLabel("Admin Manage Users Panel")); // Example content
    }

    public JPanel getPanel() {
        return manageUsersPanel;
    }
}
