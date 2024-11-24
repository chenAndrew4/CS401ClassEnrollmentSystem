package client.gui.dashboard;

import client.ClientConfig;
//import client.gui.StudentScheduleViewGUI;
import shared.models.Student;
import shared.models.User;

import javax.swing.*;


public class StudentDashboardGUI extends BaseDashboardGUI {

    public StudentDashboardGUI(Student user) {
        super("Student Dashboard", user);

        // Set personal image and info
        setPersonalImage(new ImageIcon(ClientConfig.DEFAULT_STUDENT_ICON));
//        setInfo("Name: " + user.getLastName() + " " + user.getFirstName() + "\nMajor: " + user.getDepartment() + "\nAdmin Date: " + user.getDate());

        // Add options with images and labels
        addOption("Enroll", new ImageIcon(ClientConfig.ENROLL_ICON), this::handleEnroll);
        addOption("Drop", new ImageIcon(ClientConfig.DROP_ICON), this::handleDrop);
        addOption("Waitlist", new ImageIcon(ClientConfig.WAITLIST_ICON), this::handleWaitlist);
        addOption("Grades", new ImageIcon(ClientConfig.GRADES_ICON), this::handleViewGrades);
        addOption("Schedule", new ImageIcon(ClientConfig.SCHEDULE_ICON), this::handleViewSchedule);
        addOption("Logout", new ImageIcon(ClientConfig.LOGOUT_ICON), this::handleLogout);

        setVisible(true);

    }

    private void handleWaitlist() {
        JOptionPane.showMessageDialog(this, "waitlist clicked!");
    }

    private void handleEnroll() {
        JOptionPane.showMessageDialog(this, "Enroll in Course clicked!");
    }

    private void handleDrop() {
        JOptionPane.showMessageDialog(this, "Drop Course clicked!");
    }

    private void handleViewGrades() {
        JOptionPane.showMessageDialog(this, "View Grades clicked!");
    }

    private void handleViewSchedule() {
//        new StudentScheduleViewGUI((Student) getUser());
//        JOptionPane.showMessageDialog(this, "View Schedule clicked!");
    }

    private void handleLogout() {
        JOptionPane.showMessageDialog(this, "Logout clicked!");
        System.exit(0);
    }
}
//public class StudentDashboardGUI extends JFrame {
//
//    private Student student; // Logged-in student
//    private List<CourseSection> enrolledCourses; // Student's enrolled courses
//
//    public StudentDashboardGUI(Student student) {
//        this.student = student;
//        this.enrolledCourses = student.getEnrolledCourses();
//
//        setTitle("Student Dashboard");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 600);
//        setLocationRelativeTo(null); // Center the frame
//
//        // Set up the layout
//        setLayout(new BorderLayout());
//
//        // Top panel with college icon and student details
//        JPanel topPanel = createTopPanel();
//        add(topPanel, BorderLayout.NORTH);
//
//        // Center panel with course list
//        JPanel courseListPanel = createCourseListPanel();
//        add(courseListPanel, BorderLayout.CENTER);
//
//        // Bottom panel with action buttons
//        JPanel actionPanel = createActionPanel();
//        add(actionPanel, BorderLayout.SOUTH);
//
//        setVisible(true);
//    }
//
//    // Create the top panel with college icon and user details
//    private JPanel createTopPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        // College Icon
//        JLabel iconLabel = new JLabel();
//        ImageIcon collegeIcon = new ImageIcon("college_logo.png"); // Replace with actual logo path
//        Image scaledIcon = collegeIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//        iconLabel.setIcon(new ImageIcon(scaledIcon));
//        panel.add(iconLabel, BorderLayout.WEST);
//
//        // Student Details
//        JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
//        detailsPanel.add(new JLabel("Name: " + student.getFirstName() + " " + student.getLastName()));
//        detailsPanel.add(new JLabel("Institution: " + student.getInstitutionID()));
//        detailsPanel.add(new JLabel("Account Type: Student"));
//        panel.add(detailsPanel, BorderLayout.CENTER);
//
//        return panel;
//    }
//
//    // Create the center panel with the course list
//    private JPanel createCourseListPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        JLabel header = new JLabel("Your Enrolled Courses:");
//        header.setFont(new Font("Arial", Font.BOLD, 16));
//        panel.add(header, BorderLayout.NORTH);
//
//        // Course List
//        DefaultListModel<String> listModel = new DefaultListModel<>();
//        for (CourseSection courseSection : enrolledCourses) {
//            listModel.addElement(courseSection.getSectionID() + " - " + courseSection.getSectionID());
//        }
//        JList<String> courseList = new JList<>(listModel);
//        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        JScrollPane scrollPane = new JScrollPane(courseList);
//        panel.add(scrollPane, BorderLayout.CENTER);
//
//        return panel;
//    }
//
//    // Create the bottom panel with action buttons
//    private JPanel createActionPanel() {
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//
//        JButton viewGradesButton = new JButton("View Grades");
//        viewGradesButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "View Grades feature coming soon!"));
//
//        JButton addDropCoursesButton = new JButton("Add/Drop Courses");
//        addDropCoursesButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add/Drop Courses feature coming soon!"));
//
//        JButton logoutButton = new JButton("Logout");
//        logoutButton.addActionListener(e -> {
//            JOptionPane.showMessageDialog(this, "You have been logged out.");
//            dispose(); // Close the dashboard
//            new LoginGUI(); // Redirect to login (assuming LoginGUI is implemented)
//        });
//
//        panel.add(viewGradesButton);
//        panel.add(addDropCoursesButton);
//        panel.add(logoutButton);
//
//        return panel;
//    }
//
//    // Main method for testing
//    public static void main(String[] args) {
////        // Mock data for testing
////        Student student = new Student("john_doe", "John", "Doe", Institutions.CSUEB);
////        List<Course> courses = List.of(
////                new Course("CS101", "Introduction to Computer Science"),
////                new Course("MATH201", "Calculus I"),
////                new Course("PHYS101", "Physics I")
////        );
////
////        SwingUtilities.invokeLater(() -> new StudentDashboardGUI(student, courses));
//    }
//}

