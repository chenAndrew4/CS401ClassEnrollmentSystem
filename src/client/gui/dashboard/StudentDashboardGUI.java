package client.gui.dashboard;

import client.ClientConfig;
import client.gui.GUIConfig;
import client.gui.ManageCourseGUI;
import client.gui.ManageWaitlistGUI;
import client.gui.ViewSchedulesGUI;
import client.handlers.GetSchedulesHandler;
import shared.models.Student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboardGUI extends BaseDashboardGUI {
    private JPanel mainOptionsPanel; // Stores the main options panel
    private ManageCourseGUI manageCourseGUI;
    private ManageWaitlistGUI manageWaitlistGUI;
    private ViewSchedulesGUI viewSchedulesGUI;

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
        addOptionToPanel(mainOptionsPanel, "Notifications", new ImageIcon(ClientConfig.COMMUNICATION_ICON), this::handleNotification);
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

    public void replaceOptionPanel(JPanel newPanel) {

        getOptionsPanel().removeAll();
        getOptionsPanel().add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void handleManageCourses() {
        if (manageCourseGUI == null) {
            manageCourseGUI = new ManageCourseGUI(this);
        }
        replaceOptionPanel(manageCourseGUI.getPanel());
    }

    private void handleWaitlist() {
        if (manageWaitlistGUI == null) {
            manageWaitlistGUI = new ManageWaitlistGUI(this);
        }
        replaceOptionPanel(manageWaitlistGUI .getPanel());
    }

    private void handleViewGrades() {
        JOptionPane.showMessageDialog(this, "View Grades clicked!");
    }

    private void handleViewSchedule() {
        GetSchedulesHandler getSchedulesHandler = new GetSchedulesHandler();
        getSchedulesHandler.handleGetSchedules((Student) getUser(), getUser().getInstitutionID(), getOptionsPanel(), this);
    }

    private void handleNotification() {
        JOptionPane.showMessageDialog(this, "Notifications clicked!");
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(this, "Logout clicked!");
        System.exit(0);
    }
}