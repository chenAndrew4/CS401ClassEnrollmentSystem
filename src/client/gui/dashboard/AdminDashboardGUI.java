package client.gui.dashboard;

import client.ClientConfig;
import shared.models.Administrator;
import shared.models.User;

import javax.swing.*;

public class AdminDashboardGUI extends BaseDashboardGUI {

    public AdminDashboardGUI(Administrator user) {
        super("Administrator Dashboard", user);

        // Set personal image and info
        setPersonalImage(new ImageIcon(ClientConfig.DEFAULT_ADMIN_ICON));

        // Add options with images and labels
        addOption("Manage Users", new ImageIcon(ClientConfig.MANAGE_USERS_ICON), this::handleManageUsers);
        addOption("Manage Courses", new ImageIcon(ClientConfig.MANAGE_COURSES_ICON), this::handleManageCourses);
        addOption("Reports", new ImageIcon(ClientConfig.REPORTS_ICON), this::handleViewReports);
        addOption("System Configuration", new ImageIcon(ClientConfig.SETTING_ICON), this::handleSystemSettings);
        addOption("Logout", new ImageIcon(ClientConfig.LOGOUT_ICON), this::handleLogout);
        addOption("Manage Enrollment", new ImageIcon(ClientConfig.ENROLL_ICON), this::handleEnroll);
        addOption("Manage Waitlist", new ImageIcon(ClientConfig.MANAGE_WAITLIST_ICON), this::handleManageWaitlists);
        addOption("Manage Schedule", new ImageIcon(ClientConfig.MANAGE_SCHEDULE_ICON), this::handleManageSchedules);
        addOption("Manage notification", new ImageIcon(ClientConfig.MANAGE_NOTICE_ICON), this::handleManageNotifications);

        setVisible(true);
        revalidate();
        repaint();
    }

    private void handleManageNotifications() {
        JOptionPane.showMessageDialog(this, "Manage notifications clicked!");
    }

    private void handleEnroll() {
        JOptionPane.showMessageDialog(this, "Manage Enroll clicked!");
    }

    private void handleManageSchedules() {
        JOptionPane.showMessageDialog(this, "Manage Schedule clicked!");
        // Open manage courses window or perform related action
    }

    private void handleManageWaitlists() {
        JOptionPane.showMessageDialog(this, "Manage Waitlist clicked!");
        // Open manage courses window or perform related action
    }

    private void handleManageUsers() {
        JOptionPane.showMessageDialog(this, "Manage Users clicked!");
        // Open manage users window or perform related action
    }

    private void handleManageCourses() {
        JOptionPane.showMessageDialog(this, "Manage Courses clicked!");
        // Open manage courses window or perform related action
    }

    private void handleViewReports() {
        JOptionPane.showMessageDialog(this, "View Reports clicked!");
        // Open reports window or perform related action
    }

    private void handleSystemSettings() {
        JOptionPane.showMessageDialog(this, "System Settings clicked!");
        // Open system settings window or perform related action
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(this, "Logout clicked!");
        System.exit(0);
    }
}
