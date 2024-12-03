package client.gui;

import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import client.utils.ImageUtils;
import client.handlers.*;
import shared.enums.AcademicProgramType;
import shared.enums.Days;
import shared.enums.GradingType;
import shared.enums.InstructionModeType;
import shared.enums.LevelType;
import shared.enums.Term;
import shared.enums.Time;
import shared.models.*;
import server.service.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ManageCourseGUI {
    private JPanel manageCoursesPanel; // Main panel for this GUI
    private JPanel optionsPanel; // Options for Search, Enroll, and Drop
    private JLabel termLabel; // Displays the current term
    private JButton backArrow; // Back to main options
    private JButton nextTermButton; // Navigate to the next term
    private int termOffset; // Tracks term navigation
    private String currentTerm; // Current term
    private int currentYear; // Current year
    private JPanel topRowPanel; // Top row of the options panel
    private final StudentDashboardGUI parentDashboard;
    private Student student;
    private Course[] courses;
    private String[] courseNames;
    

    public ManageCourseGUI(StudentDashboardGUI parentDashboard) {
        this.parentDashboard = parentDashboard;
        this.student = (Student) parentDashboard.getUser();
        createTestClasses();
        initializeManageCoursesPanel();
    }

    public JPanel getPanel() {
        return manageCoursesPanel;
    }

    private void initializeManageCoursesPanel() {
        manageCoursesPanel = new JPanel(new BorderLayout());
        manageCoursesPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN * 2, GUIConfig.MARGIN * 2)); // Increase spacing
        optionsPanel.setOpaque(false);

        parentDashboard.addOptionToPanel(optionsPanel, "Search Courses", new ImageIcon(ClientConfig.SEARCH_ICON), this::handleSearchCourses);
        parentDashboard.addOptionToPanel(optionsPanel, "Enroll Courses", new ImageIcon(ClientConfig.ENROLL_ICON), this::handleEnroll);
        parentDashboard.addOptionToPanel(optionsPanel, "Drop Courses", new ImageIcon(ClientConfig.DROP_ICON), this::handleDrop);

        initializeTopRow(true); // Back and next buttons

        assert topRowPanel != null;
        manageCoursesPanel.add(topRowPanel, BorderLayout.NORTH);
        manageCoursesPanel.add(optionsPanel, BorderLayout.CENTER);
    }

    private void initializeTopRow(boolean showNextTerm) {
        topRowPanel = new JPanel(new BorderLayout());
        topRowPanel.setOpaque(false);
        topRowPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add vertical space

        determineCurrentTerm();

        // Back arrow on the left
        backArrow = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.BACK_ARROW_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));
        backArrow.setContentAreaFilled(false);
        backArrow.setBorderPainted(false);
        backArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backArrow.addActionListener(e -> handleBack());
        topRowPanel.add(backArrow, BorderLayout.WEST);

        // Term label in the center
        termLabel = new JLabel(getNextTermLabel(), SwingConstants.CENTER);
        termLabel.setFont(new Font("Arial", Font.BOLD, 16));
        termLabel.setForeground(Color.BLACK);
        topRowPanel.add(termLabel, BorderLayout.CENTER);

        // Next term button on the right
        nextTermButton = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.NEXT_TERM_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));
        nextTermButton.setContentAreaFilled(false);
        nextTermButton.setBorderPainted(false);
        nextTermButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextTermButton.addActionListener(e -> goToNextTerm());

        if (showNextTerm) {
            topRowPanel.add(nextTermButton, BorderLayout.EAST);
        }
        manageCoursesPanel.add(topRowPanel, BorderLayout.NORTH);
    }

    private void determineCurrentTerm() {
        LocalDate today = LocalDate.now();
        currentYear = today.getYear();

        if (today.getMonthValue() >= 9) {
            currentTerm = "Fall";
        } else if (today.getMonthValue() >= 5) {
            currentTerm = "Summer";
        } else if (today.getMonthValue() >= 1) {
            currentTerm = "Spring";
        } else {
            currentTerm = "Winter";
        }

        termOffset = 1; // Start at next term
    }

    private String getNextTermLabel() {
        String term = currentTerm;
        int year = currentYear;

        for (int i = 0; i < termOffset; i++) {
            switch (term) {
                case "Fall" -> {
                    term = "Winter";
                    year++;
                }
                case "Winter" -> term = "Spring";
                case "Spring" -> term = "Summer";
                case "Summer" -> term = "Fall";
            }
        }

        return term + " " + year;
    }

    private void goToNextTerm() {
        termOffset++;
        termLabel.setText(getNextTermLabel());

        // Hide next term button after reaching 2 terms ahead
        if (termOffset >= 2) {
            nextTermButton.setVisible(false);
        }
    }

    private void handleBack() {
        if (termOffset > 1) {
            termOffset--;
            termLabel.setText(getNextTermLabel());
            nextTermButton.setVisible(true); // Restore next term button if backing up
        } else {
            parentDashboard.goBackToMainOptions();
        }
    }

    private void handleSearchCourses() 
    {
        //JOptionPane.showMessageDialog(manageCoursesPanel, "Search Courses clicked!");
        //SearchCourseHandler searchCourseHandler = new SearchCourseHandler();
        //searchCourseHandler.handleSearchCourse(student, manageCoursesPanel, parentDashboard, SessionService.getInstance().createSession(student.getUserId()));
    	JList courseList = new JList(courseNames);
    	courseList.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent event)
			{
				JOptionPane.showMessageDialog(manageCoursesPanel, courses[courseList.getSelectedIndex()].toString());
			}
		});
    	JOptionPane.showMessageDialog(manageCoursesPanel, courseList);
    }

    private void handleEnroll() 
    {
        JOptionPane.showMessageDialog(manageCoursesPanel, "Enroll Courses clicked!");
        //EnrollCourseHandler enrollCourseHandler = new EnrollCourseHandler();
        //public void handleStudentEnroll(Student student, Course course, String sectionId, JPanel parentGUI, final BaseDashboardGUI parentDashboard, String sessionToken
        //Dont know where to get Course yet.
        /*JList courseList = new JList(courseNames);
      
        courseList.addListSelectionListener(new ListSelectionListener()
		{
        	String[] courseSections = new String[courses[courseList.getSelectedIndex()].getSections().size()];
        	JList sections = new JList(courseSections);
			public void valueChanged(ListSelectionEvent event)
			{
				JOptionPane.showMessageDialog(manageCoursesPanel, sections);
				sections.addListSelectionListener(new ListSelectionListener()
				{
					public void valueChanged(ListSelectionEvent event)
					{
						//String sectionId = courses[courseList.getSelectedIndex()].getSections()[sections.getSelectedIndex()];
						enrollCourseHandler.handleStudentEnroll(student, courses[courseList.getSelectedIndex()], "123", manageCoursesPanel, parentDashboard, SessionService.getInstance().createSession(student.getUserId()));
					}
				});
			}
		});
    	JOptionPane.showMessageDialog(manageCoursesPanel, courseList); */
        
    }

    private void handleDrop() 
    {
        //JOptionPane.showMessageDialog(manageCoursesPanel, "Drop Courses clicked!");
    	DropCourseHandler dropCourseHandler = new DropCourseHandler();
    	dropCourseHandler.handleDropCourse(student, null, null, manageCoursesPanel, parentDashboard, SessionService.getInstance().createSession(student.getUserId()));
    }
    
    public void createTestClasses()
    {
    	String[] departments = {"CS", "MATH", "PHYS", "BIO", "CHEM"};
    	courses = new Course[15];
    	courseNames = new String[15];
    	for (int i = 0; i < 15; i++) 
        { // 5 departments per institution
            String department = departments[i % 5];

            // Create 1 course per department
            Course course = new Course(student.getInstitutionID(), department);
            course.setName("Course " + department + " - " + student.getInstitutionID());
            course.setDescription("Description for " + department + " course at " + student.getInstitutionID());
            course.setLevel(LevelType.Lower);
            course.setAcademicProgram(AcademicProgramType.UGM);
            course.setUnits(3.0f + i);
            course.setPrerequisites(Arrays.asList("PREREQ" + i));
            courses[i] = course;
            courseNames[i] = course.getCourseID();
            // Create 2 sections per course
            List<CourseSection> sections = new ArrayList<>();
            for (int j = 1; j <= 2; j++) {
                CourseSection section = new CourseSection(student.getInstitutionID(), course.getCourseID());
                section.setEnrollmentLimit(30 + j * 5);
                section.setGrading(GradingType.Letter);
                section.setInstructionMode(InstructionModeType.InPerson);
                section.setNotes("No prerequisites for this course.");

                // Create a schedule for the section
                Schedule schedule = new Schedule(student.getInstitutionID());
                schedule.setCourseID(course.getCourseID());
                schedule.setSectionID(section.getSectionID());
                schedule.setTerm(Term.SPRING_SEMESTER);
                schedule.setDays(new Days[]{Days.TUESDAY, Days.THURSDAY});
                Calendar calendar = Calendar.getInstance();
                // Set the start date
                calendar.set(2024, Calendar.JANUARY, 15); // Jan 15, 2024
                schedule.setStartDate(calendar.getTime());

                calendar.set(2024, Calendar.MAY, 22); // May 22, 2024
                schedule.setEndDate(calendar.getTime());
                schedule.setStartTime(Time.TIME_1100);              // 11:00 AM
                schedule.setEndTime(Time.TIME_1230);               // 12:30 PM

                section.setScheduleID(schedule.getScheduleID());
                sections.add(section);
            }
            course.setSections(sections);
        } 
    }
}




