package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class AdminManageWaitlistGUI {
    private JPanel manageWaitlistPanel;

    public AdminManageWaitlistGUI(AdminDashboardGUI adminDashboardGUI) {
        manageWaitlistPanel = new JPanel();
        manageWaitlistPanel.add(new JLabel("Admin Manage Waitlist Panel")); // Example content
    }

    public JPanel getPanel() {
        return manageWaitlistPanel;
    }
}