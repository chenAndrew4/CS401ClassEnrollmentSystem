package client.gui.dashboard;

import client.ClientConfig;
import shared.models.Administrator;
import shared.models.User;

import javax.swing.*;

public class AdminDashboardGUI extends BaseDashboardGUI {

    public AdminDashboardGUI(Administrator user) {
        super("Administrator Dashboard", user);

        // Set university image as the top panel background
        switch(user.getInstitutionID()) {
            case SJSU:
                setUniversityImage(new ImageIcon(ClientConfig.SJSU_DASH_BACKGROUND_FILE_PATH));
                break;
            case CSUEB:
                setUniversityImage(new ImageIcon(ClientConfig.CSUEB_DASH_BACKGROUND_FILE_PATH));
                break;
            case CSUF:
                setUniversityImage(new ImageIcon(ClientConfig.CSUF_DASH_BACKGROUND_FILE_PATH));
                break;
            default:
                setUniversityImage(new ImageIcon(ClientConfig.DEFAULT_DASH_BACKGROUND_PATH));
                break;
        }

        // Add options with images and labels
        addOption("Manage Users", new ImageIcon(ClientConfig.MANAGE_USERS_ICON), this::handleManageUsers);
        addOption("Manage Courses", new ImageIcon(ClientConfig.MANAGE_COURSES_ICON), this::handleManageCourses);
        addOption("Reports", new ImageIcon(ClientConfig.REPORTS_ICON), this::handleViewReports);
        addOption("System Settings", new ImageIcon(ClientConfig.SETTING_ICON), this::handleSystemSettings);
        addOption("Logout", new ImageIcon(ClientConfig.LOGOUT_ICON), this::handleLogout);
        addOption("Manage Waitlist", new ImageIcon(ClientConfig.MANAGE_WAITLIST_ICON), this::handleManageWaitlists);
        addOption("Manage Schedule", new ImageIcon(ClientConfig.MANAGE_SCHEDULE_ICON), this::handleManageSchedules);
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
