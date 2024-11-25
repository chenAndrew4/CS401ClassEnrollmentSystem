package client.gui.dashboard;

import client.ClientConfig;
//import client.gui.StudentScheduleViewGUI;
import client.gui.GUIConfig;
import client.gui.ManageCourseGUI;
import client.utils.ImageUtils;
import shared.models.Student;
import shared.models.User;

import javax.swing.*;
import java.awt.*;


public class StudentDashboardGUI extends BaseDashboardGUI {
	private JPanel mainOptionsPanel; // Stores the main options panel
    private ManageCourseGUI manageCourseGUI;
    JPanel mainContentPanel;

    public StudentDashboardGUI(Student user) {
        super("Student Dashboard", user);

        // Set personal image and info
        setPersonalImage( new ImageIcon(ClientConfig.DEFAULT_STUDENT_ICON));

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

        addOptionToPanel(mainOptionsPanel, "Manage Courses", new ImageIcon(ClientConfig.COURSE_ICON), this::handleManageCourses);
        addOptionToPanel(mainOptionsPanel, "Manage Waitlist", new ImageIcon(ClientConfig.WAITLIST_ICON), this::handleWaitlist);
        addOptionToPanel(mainOptionsPanel, "Manage Schedule", new ImageIcon(ClientConfig.SCHEDULE_ICON), this::handleViewSchedule);
        addOptionToPanel(mainOptionsPanel, "Notifications", new ImageIcon(ClientConfig.COMMUNICATION_ICON), this::handleCommunication);
        addOptionToPanel(mainOptionsPanel, "View Grades", new ImageIcon(ClientConfig.GRADES_ICON), this::handleViewGrades);
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

    private void replaceOptionPanel(JPanel newPanel) {

        getOptionsPanel().removeAll();
        getOptionsPanel().add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void handleManageCourses() {
        if (manageCourseGUI == null) {
            manageCourseGUI = new ManageCourseGUI(this);
        }
        System.out.println("handled");
        replaceOptionPanel(manageCourseGUI.getPanel());
    }

    private void handleSearchCourses() {
        JOptionPane.showMessageDialog(this, "Search Courses clicked!");
    }

    private void handleWaitlist() {
        JOptionPane.showMessageDialog(this, "Waitlist clicked!");
    }

    private void handleEnroll() {
        JOptionPane.showMessageDialog(this, "Enroll Courses clicked!");
    }

    private void handleDrop() {
        JOptionPane.showMessageDialog(this, "Drop Courses clicked!");
    }

    private void handleViewGrades() {
        JOptionPane.showMessageDialog(this, "View Grades clicked!");
    }

    private void handleViewSchedule() {
        JOptionPane.showMessageDialog(this, "View Schedule clicked!");
    }

    private void handleCommunication() {
        JOptionPane.showMessageDialog(this, "Notifications clicked!");
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(this, "Logout clicked!");
        System.exit(0);
    }
}
