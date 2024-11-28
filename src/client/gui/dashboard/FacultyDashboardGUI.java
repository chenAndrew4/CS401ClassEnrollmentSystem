package client.gui.dashboard;

import client.ClientConfig;
import client.gui.GUIConfig;
import client.gui.ManageAssignedCoursesGUI;
import client.gui.ViewSchedulesGUI;
import client.gui.ViewWaitlistGUI;
import shared.models.Faculty;
import javax.swing.*;
import java.awt.*;

public class FacultyDashboardGUI extends BaseDashboardGUI {
    private JPanel mainOptionsPanel; // Stores the main options panel
    private ManageAssignedCoursesGUI manageAssignedCoursesGUI;
    private ViewWaitlistGUI viewWaitlistGUI;
    private ViewSchedulesGUI viewSchedulesGUI;

    public FacultyDashboardGUI(Faculty user) {
        super("Faculty Dashboard", user);

        // Set personal image and info
        setPersonalImage(new ImageIcon(ClientConfig.DEFAULT_FACULTY_ICON));

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

        addOptionToPanel(mainOptionsPanel, "Manage Assigned Courses", new ImageIcon(ClientConfig.COURSE_ICON), this::handleManageAssignedCourses);
        addOptionToPanel(mainOptionsPanel, "View Waitlist", new ImageIcon(ClientConfig.WAITLIST_ICON), this::handleViewWaitlist);
        addOptionToPanel(mainOptionsPanel, "Grade Students", new ImageIcon(ClientConfig.GRADE_SUBMISSIONS_ICON), this::handleGradeSubmissions);
        addOptionToPanel(mainOptionsPanel, "View Schedules", new ImageIcon(ClientConfig.SCHEDULE_ICON), this::handleViewSchedules);
        addOptionToPanel(mainOptionsPanel, "Notifications", new ImageIcon(ClientConfig.COMMUNICATION_ICON), this::handleCommunication);
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

    private void handleManageAssignedCourses() {
        if (manageAssignedCoursesGUI == null) {
            manageAssignedCoursesGUI = new ManageAssignedCoursesGUI(this);
        }
        replaceOptionPanel(manageAssignedCoursesGUI.getPanel());
    }

    private void handleViewWaitlist() {
        if (viewWaitlistGUI == null) {
            viewWaitlistGUI = new ViewWaitlistGUI(this);
        }
        replaceOptionPanel(viewWaitlistGUI.getPanel());
    }

    private void handleGradeSubmissions() {
        JOptionPane.showMessageDialog(this, "Grade Submissions clicked!");
        // Implement the grading functionality
    }

    private void handleViewSchedules() {
        if (viewWaitlistGUI == null) {
            viewSchedulesGUI = new ViewSchedulesGUI(this, ((Faculty)getUser()).getSchedule());
        }
        replaceOptionPanel(viewSchedulesGUI.getPanel());
    }

    private void handleCommunication() {
        JOptionPane.showMessageDialog(this, "Communication clicked!");
        // Implement the communication functionality
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(this, "Logout clicked!");
        System.exit(0);
    }
}
