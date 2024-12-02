package client.gui;

import client.ClientConfig;
import client.utils.ImageUtils;
import shared.models.CourseSection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class ViewAssignedCoursesGUI {
    private ManageAssignedCoursesGUI parentDashboard;
    private JPanel topRowPanel;
    private JButton backArrow;
    private JLabel titleLabel;
    private List<CourseSection> assignedCourses;
    private JTextField enrollmentLimit;
    private JPanel assignedCoursesPanel;
    private JPanel coursesPanel;

    public ViewAssignedCoursesGUI(ManageAssignedCoursesGUI parentDashboard, List<CourseSection> assignedCourses) {
        this.parentDashboard = parentDashboard;
        this.assignedCourses = assignedCourses;
        initializeManageAssignedCoursesPanel();
    }

    private void initializeManageAssignedCoursesPanel() {
        assignedCoursesPanel = new JPanel(new BorderLayout());
        assignedCoursesPanel.setOpaque(false);

        coursesPanel = new JPanel(new BorderLayout());
        coursesPanel.setOpaque(false);

        for (CourseSection courseSection : assignedCourses){
            coursesPanel.add(new JLabel(courseSection.getSectionID()));
        }

        initializeTopRow();

        assignedCoursesPanel.add(coursesPanel, BorderLayout.CENTER);
    }
    private void initializeTopRow() {
        topRowPanel = new JPanel(new BorderLayout());
        topRowPanel.setOpaque(false);
        topRowPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add vertical space

        // Back arrow on the left
        backArrow = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.BACK_ARROW_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));
        backArrow.setContentAreaFilled(false);
        backArrow.setBorderPainted(false);
        backArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backArrow.addActionListener(e -> handleBack());
        topRowPanel.add(backArrow, BorderLayout.WEST);

        // Title label in the center
        titleLabel = new JLabel("Manage Assigned Courses", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        assignedCoursesPanel.add(topRowPanel, BorderLayout.NORTH);
    }

    private void handleBack() {
        parentDashboard.goBackToManageAssignedCourses();
    }

    public JPanel getPanel() {
        return assignedCoursesPanel;
    }

    private void updateCourse(){
    }
}
