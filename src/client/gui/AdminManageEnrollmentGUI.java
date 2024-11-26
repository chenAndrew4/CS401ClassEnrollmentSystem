package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class AdminManageEnrollmentGUI {
    private JPanel manageEnrollmentPanel;

    public AdminManageEnrollmentGUI(AdminDashboardGUI adminDashboardGUI) {
        manageEnrollmentPanel = new JPanel();
        manageEnrollmentPanel.add(new JLabel("Admin Manage Enrollment Panel")); // Example content
    }

    public JPanel getPanel() {
        return manageEnrollmentPanel;
    }
}