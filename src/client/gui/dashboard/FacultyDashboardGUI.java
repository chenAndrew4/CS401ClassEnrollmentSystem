package client.gui.dashboard;

import client.ClientConfig;
import shared.models.Faculty;
import shared.models.User;

import javax.swing.*;

public class FacultyDashboardGUI extends BaseDashboardGUI {

    public FacultyDashboardGUI(Faculty user) {
        super("Faculty Dashboard", user);

        // Set university image as the top panel background
//        switch(user.getInstitutionID()) {
//            case SJSU:
//                setUniversityImage(new ImageIcon(ClientConfig.SJSU_DASH_BACKGROUND_FILE_PATH));
//                break;
//            case CSUEB:
//                setUniversityImage(new ImageIcon(ClientConfig.CSUEB_DASH_BACKGROUND_FILE_PATH));
//                break;
//            case CSUF:
//                setUniversityImage(new ImageIcon(ClientConfig.CSUF_DASH_BACKGROUND_FILE_PATH));
//                break;
//            default:
//                setUniversityImage(new ImageIcon(ClientConfig.DEFAULT_DASH_BACKGROUND_PATH));
//                break;
//        }
        // Set personal image and info
        setPersonalImage(new ImageIcon(ClientConfig.DEFAULT_FACULTY_ICON));

        // Add options with images and labels
//        addOption("Manage Classes", new ImageIcon(ClientConfig.MANAGE_CLASSES_ICON), this::handleManageClasses);
        addOption("View Assigned Courses", new ImageIcon(ClientConfig.COURSE_ICON), this::handleViewAssignedCourses);
        addOption("View Waitlist", new ImageIcon(ClientConfig.WAITLIST_ICON), this::handleViewWaitlist);
        addOption("Grade Submissions", new ImageIcon(ClientConfig.GRADE_SUBMISSIONS_ICON), this::handleGradeSubmissions);
        addOption("View Schedules", new ImageIcon(ClientConfig.SCHEDULE_ICON), this::handleViewSchedules);
        addOption("Communication", new ImageIcon(ClientConfig.COMMUNICATION_ICON), this::handleCommunication);
        addOption("Logout", new ImageIcon(ClientConfig.LOGOUT_ICON), this::handleLogout);

        setVisible(true);
        revalidate();
        repaint();
    }

    private void handleViewWaitlist() {
        JOptionPane.showMessageDialog(this, "view waitlist clicked!");
        // Open manage classes window or perform related action
    }

    private void handleViewAssignedCourses() {
        JOptionPane.showMessageDialog(this, "view assigned courses clicked!");
        // Open manage classes window or perform related action
    }

    private void handleManageClasses() {
        JOptionPane.showMessageDialog(this, "Manage Classes clicked!");
        // Open manage classes window or perform related action
    }

    private void handleGradeSubmissions() {
        JOptionPane.showMessageDialog(this, "Grade Submissions clicked!");
        // Open grade submissions window or perform related action
    }

    private void handleViewSchedules() {
        JOptionPane.showMessageDialog(this, "View Schedules clicked!");
        // Open view schedules window or perform related action
    }

    private void handleCommunication() {
        JOptionPane.showMessageDialog(this, "Communication clicked!");
        // Open communication tools window or perform related action
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(this, "Logout clicked!");
        System.exit(0);
    }
}