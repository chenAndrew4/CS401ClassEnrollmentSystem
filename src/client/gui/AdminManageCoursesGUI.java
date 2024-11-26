package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class AdminManageCoursesGUI {
    private JPanel manageCoursesPanel;

    public AdminManageCoursesGUI(AdminDashboardGUI adminDashboardGUI) {
        manageCoursesPanel = new JPanel();
        manageCoursesPanel.add(new JLabel("Admin Manage Courses Panel")); // Example content
    }

    public JPanel getPanel() {
        return manageCoursesPanel;
    }
}
