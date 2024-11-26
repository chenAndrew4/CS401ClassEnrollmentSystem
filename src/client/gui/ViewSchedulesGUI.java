package client.gui;

import client.ClientConfig;
import client.gui.dashboard.AdminDashboardGUI;
import client.gui.dashboard.BaseDashboardGUI;
import client.gui.dashboard.FacultyDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import client.utils.ImageUtils;
import shared.enums.Days;
import shared.enums.Time;
import shared.models.Schedule;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewSchedulesGUI {
    private JPanel viewSchedulePanel;
    private JPanel topRowPanel;
    private JPanel navigationRowPanel;
    private final BaseDashboardGUI parentDashboard;
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private String[][] courseData; // Stores course details for each cell
    private int cellSize = 150;// Default cell size
    private List<Schedule> schedules;

    public ViewSchedulesGUI(BaseDashboardGUI parentDashboard, List<Schedule> schedules) {
    	 if (parentDashboard instanceof StudentDashboardGUI) {
             this.parentDashboard = (StudentDashboardGUI) parentDashboard;
         } else if (parentDashboard instanceof FacultyDashboardGUI) {
             this.parentDashboard = (FacultyDashboardGUI) parentDashboard;
         } else {
             this.parentDashboard = (AdminDashboardGUI) parentDashboard;
         }
        this.schedules = schedules;
        initializeViewSchedulePanel();
    }

    public JPanel getPanel() {
        return viewSchedulePanel;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    private void initializeViewSchedulePanel() {
        viewSchedulePanel = new JPanel(new BorderLayout());
        viewSchedulePanel.setOpaque(false);

        // Top row navigation
        initializeTopRow();

        // Separate navigation row for month/year selection
        initializeNavigationRow();

        // Initialize schedule grid
        initializeScheduleTable();

        viewSchedulePanel.add(topRowPanel, BorderLayout.NORTH);
        viewSchedulePanel.add(navigationRowPanel, BorderLayout.CENTER);
        viewSchedulePanel.add(new JScrollPane(scheduleTable), BorderLayout.SOUTH);
    }

    private void initializeTopRow() {
        topRowPanel = new JPanel(new BorderLayout());
        topRowPanel.setOpaque(false);
        topRowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Back arrow on the left
        JButton backArrow = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.BACK_ARROW_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));
        backArrow.setContentAreaFilled(false);
        backArrow.setBorderPainted(false);
        backArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backArrow.addActionListener(e -> parentDashboard.goBackToMainOptions());
        topRowPanel.add(backArrow, BorderLayout.WEST);

        // Title in the center
        JLabel titleLabel = new JLabel("Weekly Schedule     ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topRowPanel.add(titleLabel, BorderLayout.CENTER);
    }

    private void initializeNavigationRow() {
        navigationRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Add space between components
        navigationRowPanel.setOpaque(false);

        // Day of the month field
        LocalDate currentDate = LocalDate.now();

        // Month/Year navigation buttons
        JButton leftArrow = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.LEFT_ARROW_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));
        JTextField dayField = new JTextField(String.valueOf(currentDate.getDayOfMonth()), 4);
        JTextField monthField = new JTextField(String.valueOf(currentDate.getMonthValue()), 4); // Larger size
        JTextField yearField = new JTextField(String.valueOf(currentDate.getYear()), 6); // Larger size
        JButton rightArrow = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.RIGHT_ARROW_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));

        // Adjust button appearance
        leftArrow.setContentAreaFilled(false);
        leftArrow.setBorderPainted(false);
        rightArrow.setContentAreaFilled(false);
        rightArrow.setBorderPainted(false);

        // Add actions to buttons
        leftArrow.addActionListener(e -> updateDayOfMonth(-1, dayField, monthField, yearField));
        rightArrow.addActionListener(e -> updateDayOfMonth(1, dayField, monthField, yearField));

        // Adjust text field font and alignment
        monthField.setFont(new Font("Arial", Font.BOLD, 16));
        yearField.setFont(new Font("Arial", Font.BOLD, 16));
        dayField.setFont(new Font("Arial", Font.BOLD, 16));
        dayField.setHorizontalAlignment(SwingConstants.CENTER);
        monthField.setHorizontalAlignment(SwingConstants.CENTER);
        yearField.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to navigation row
        navigationRowPanel.add(leftArrow);
        navigationRowPanel.add(dayField);
        navigationRowPanel.add(monthField);
        navigationRowPanel.add(yearField);
        navigationRowPanel.add(rightArrow);
    }

    private void updateDayOfMonth(int dayOffset, JTextField dayField, JTextField monthField, JTextField yearField) {
        // Get the current day, month, and year
        int day = Integer.parseInt(dayField.getText());
        int month = Integer.parseInt(monthField.getText());
        int year = Integer.parseInt(yearField.getText());

        // Adjust the date using Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day); // Calendar months are zero-based
        calendar.add(Calendar.DAY_OF_MONTH, dayOffset);

        // Update fields
        dayField.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        monthField.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        yearField.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        // Update table for the selected date
        updateScheduleTableForDate(calendar.getTime(), schedules);
    }

    private void addFakeCourseDataForDate(LocalDate date) {
        // Clear existing course data
        for (int i = 0; i < courseData.length; i++) {
            Arrays.fill(courseData[i], null);
        }

        // Add fake course details for the selected date
        int columnIndex = date.getDayOfWeek().getValue(); // Get the day of the week (1 = Monday, 7 = Sunday)
        if (columnIndex == 7) columnIndex = 1; // Map Sunday to the first column for consistency

        String courseDetails = "Course: CS401\nSection: 01\nInstructor: Dr. Smith";
        courseData[5][columnIndex] = courseDetails;
        courseData[6][columnIndex] = courseDetails;

        // Update the table with the new data
        tableModel.setValueAt("CS401 (Dr. Smith)", 5, columnIndex);
        tableModel.setValueAt("CS401 (Dr. Smith)", 6, columnIndex);
    }

    private void initializeScheduleTable() {
        String[] columnNames = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[][] rowData = new String[25][8]; // 24 rows for hours + 1 header row
        courseData = new String[25][8]; // Store course info for each cell

        // Fill the table
        rowData[0][0] = ""; // Top-left corner blank
        for (int i = 0; i < 24; i++) {
            rowData[i + 1][0] = (i % 12 == 0 ? 12 : i % 12) + ":00" + (i < 12 ? " AM" : " PM"); // Time labels
        }

        tableModel = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row > 0 && column > 0; // Only schedule cells are editable
            }
        };

        scheduleTable = new JTable(tableModel);
        scheduleTable.setRowHeight(cellSize);

        // Apply custom header font
        JTableHeader header = scheduleTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font for headers
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40)); // Increase header height

        scheduleTable.setDefaultRenderer(Object.class, new ScheduleCellRenderer());

        // Add mouse click listener for cell interaction
        scheduleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = scheduleTable.rowAtPoint(e.getPoint());
                int column = scheduleTable.columnAtPoint(e.getPoint());
                if (row > 0 && column > 0) {
                    String courseDetails = courseData[row][column];
                    JOptionPane.showMessageDialog(viewSchedulePanel, courseDetails != null ? courseDetails : "No schedule for this slot.");
                }
            }
        });

        // Adjust table size
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setPreferredSize(new Dimension(800, 600)); // Larger size for table display
    }

    private class ScheduleCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setVerticalAlignment(SwingConstants.TOP);

            // Apply semi-transparent blue background for cells with schedule
            if (courseData[row][column] != null) {
                cell.setBackground(new Color(173, 216, 230, 128)); // Light blue with transparency
                cell.setText(value != null ? value.toString() : "");
            } else {
                cell.setBackground(Color.WHITE);
            }

            return cell;
        }
    }

    private void updateScheduleTableForDate(Date selectedDate, List<Schedule> schedules) {
        // Clear the existing data
        for (int i = 0; i < courseData.length; i++) {
            Arrays.fill(courseData[i], null);
        }

        // Use Calendar to extract day of the week from the selected date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Sunday = 1, Monday = 2, ..., Saturday = 7
        int tableColumn = (dayOfWeek == Calendar.SUNDAY) ? 1 : dayOfWeek - 1; // Map Sunday to 1, etc.

        for (Schedule schedule : schedules) {
            // Check if the selected date is within the schedule's start and end dates
            if (schedule.isDateWithinSchedule(selectedDate)) {
                // Populate table rows based on time slots
                Time scheduleStart = schedule.getStartTime();
                Time scheduleEnd = schedule.getEndTime();

                for (Time time : Time.values()) {
                    if (time.after(scheduleStart) && time.before(scheduleEnd)) {
                        int rowIndex = time.ordinal(); // Map Time to table row
                        String details = String.format("%s (%s)\n%s",
                                schedule.getCourseID(),
                                schedule.getSectionID(),
                                schedule.getFacultyID());
                        courseData[rowIndex][tableColumn] = details;
                        tableModel.setValueAt(details, rowIndex + 1, tableColumn); // Update table model
                    }
                }
            }
        }

        // Refresh table
        scheduleTable.repaint();
    }
}

