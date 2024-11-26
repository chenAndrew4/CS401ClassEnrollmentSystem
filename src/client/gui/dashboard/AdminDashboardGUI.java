package client.gui.dashboard;

import client.ClientConfig;
import shared.models.Administrator;
import shared.models.User;
import client.gui.AdminManageCoursesGUI;
import client.gui.AdminManageEnrollmentGUI;
import client.gui.AdminManageNotificationsGUI;
import client.gui.AdminManageSchedulesGUI;
import client.gui.AdminManageUsersGUI;
import client.gui.AdminManageWaitlistGUI;
import client.gui.GUIConfig;
import client.gui.ReportsGUI;
import client.gui.SystemSettingsGUI;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardGUI extends BaseDashboardGUI {
    private JPanel mainOptionsPanel; // Stores the main options panel
    private AdminManageCoursesGUI adminManageCoursesGUI;
    private AdminManageUsersGUI manageUsersGUI;
    private AdminManageWaitlistGUI manageWaitlistGUI;
    private AdminManageEnrollmentGUI manageEnrollmentGUI;
    private AdminManageNotificationsGUI manageNotificationsGUI;
    private AdminManageSchedulesGUI manageSchedulesGUI;
    private ReportsGUI reportsGUI;
    private SystemSettingsGUI systemSettingsGUI;

    public AdminDashboardGUI(Administrator user) {
        super("Administrator Dashboard", user);

        // Set personal image and info
        setPersonalImage(new ImageIcon(ClientConfig.DEFAULT_ADMIN_ICON));

        // Initialize main options
        initializeMainOptions();

        // Add main components to the options panel
        getOptionsPanel().setLayout(new BorderLayout());
        getOptionsPanel().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around options panel
        getOptionsPanel().add(mainOptionsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void initializeMainOptions() {
        mainOptionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN * 2, GUIConfig.MARGIN * 2)); // Increase cell spacing
        mainOptionsPanel.setOpaque(false);

        addOptionToPanel(mainOptionsPanel, "Manage Users", new ImageIcon(ClientConfig.MANAGE_USERS_ICON), this::handleManageUsers);
        addOptionToPanel(mainOptionsPanel, "Manage Courses", new ImageIcon(ClientConfig.MANAGE_COURSES_ICON), this::handleManageCourses);
        addOptionToPanel(mainOptionsPanel, "Reports", new ImageIcon(ClientConfig.REPORTS_ICON), this::handleViewReports);
        addOptionToPanel(mainOptionsPanel, "System Configuration", new ImageIcon(ClientConfig.SETTING_ICON), this::handleSystemSettings);
        addOptionToPanel(mainOptionsPanel, "Manage Enrollment", new ImageIcon(ClientConfig.ENROLL_ICON), this::handleEnroll);
        addOptionToPanel(mainOptionsPanel, "Manage Waitlist", new ImageIcon(ClientConfig.MANAGE_WAITLIST_ICON), this::handleWaitlists);
        addOptionToPanel(mainOptionsPanel, "Manage Schedule", new ImageIcon(ClientConfig.MANAGE_SCHEDULE_ICON), this::handleSchedules);
        addOptionToPanel(mainOptionsPanel, "Manage Notifications", new ImageIcon(ClientConfig.MANAGE_NOTICE_ICON), this::handleNotifications);
        addOptionToPanel(mainOptionsPanel, "Logout", new ImageIcon(ClientConfig.LOGOUT_ICON), this::handleLogout);
    }

    public void goBackToMainOptions() {
        getOptionsPanel().removeAll();
        initializeMainOptions();
        // Add main components to the options panel
        getOptionsPanel().setLayout(new BorderLayout());
        getOptionsPanel().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around options panel
        getOptionsPanel().add(mainOptionsPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void replaceOptionPanel(JPanel newPanel) {
        getOptionsPanel().removeAll();
        getOptionsPanel().add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void handleManageUsers() {
        if (manageUsersGUI == null) {
            manageUsersGUI = new AdminManageUsersGUI(this);
        }
        replaceOptionPanel(manageUsersGUI.getPanel());
    }

    private void handleManageCourses() {
        if (adminManageCoursesGUI == null) {
            adminManageCoursesGUI = new AdminManageCoursesGUI(this);
        }
        replaceOptionPanel(adminManageCoursesGUI.getPanel());
    }

    private void handleViewReports() {
        if (reportsGUI == null) {
            reportsGUI = new ReportsGUI(this);
        }
        replaceOptionPanel(reportsGUI.getPanel());
    }

    private void handleSystemSettings() {
        if (systemSettingsGUI == null) {
            systemSettingsGUI = new SystemSettingsGUI(this);
        }
        replaceOptionPanel(systemSettingsGUI.getPanel());
    }

    private void handleEnroll() {
        if (manageEnrollmentGUI == null) {
            manageEnrollmentGUI = new AdminManageEnrollmentGUI(this);
        }
        replaceOptionPanel(manageEnrollmentGUI.getPanel());
    }

    private void handleWaitlists() {
        if (manageWaitlistGUI == null) {
            manageWaitlistGUI = new AdminManageWaitlistGUI(this);
        }
        replaceOptionPanel(manageWaitlistGUI.getPanel());
    }

    private void handleSchedules() {
        if (manageSchedulesGUI == null) {
            manageSchedulesGUI = new AdminManageSchedulesGUI(this);
        }
        replaceOptionPanel(manageSchedulesGUI.getPanel());
    }

    private void handleNotifications() {
        if (manageNotificationsGUI == null) {
            manageNotificationsGUI = new AdminManageNotificationsGUI(this);
        }
        replaceOptionPanel(manageNotificationsGUI.getPanel());
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(this, "Logout clicked!");
        System.exit(0);
    }
}

