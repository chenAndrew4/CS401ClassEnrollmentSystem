package client.gui;

import client.ClientConfig;
import client.gui.dashboard.StudentDashboardGUI;
import client.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ManageWaitlistGUI {
    private JPanel manageWaitlistsPanel; // Main panel for this GUI
    private JPanel optionsPanel; // Options for Search, Enroll, and Drop
    private JLabel termLabel; // Displays the current term
    private JButton backArrow; // Back to main options
    private JButton nextTermButton; // Navigate to the next term
    private int termOffset; // Tracks term navigation
    private String currentTerm; // Current term
    private int currentYear; // Current year
    private final StudentDashboardGUI parentDashboard;
    private JPanel topRowPanel; // Top row of the options panel

    public ManageWaitlistGUI(StudentDashboardGUI parentDashboard) {
        this.parentDashboard = parentDashboard;
        initializeManageWaitlistsPanel();
    }

    public JPanel getPanel() {
        return manageWaitlistsPanel;
    }

    private void initializeManageWaitlistsPanel() {
        manageWaitlistsPanel = new JPanel(new BorderLayout());
        manageWaitlistsPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN * 2, GUIConfig.MARGIN * 2)); // Increase spacing
        optionsPanel.setOpaque(false);

        parentDashboard.addOptionToPanel(optionsPanel, "Search Waitlists", new ImageIcon(ClientConfig.SEARCH_ICON), this::handleSearchWaitlists);
        parentDashboard.addOptionToPanel(optionsPanel, "Enroll Waitlists", new ImageIcon(ClientConfig.ENROLL_ICON), this::handleEnroll);
        parentDashboard.addOptionToPanel(optionsPanel, "Drop Waitlists", new ImageIcon(ClientConfig.DROP_ICON), this::handleDrop);

        initializeTopRow(true); // Back and next buttons

        assert topRowPanel != null;
        manageWaitlistsPanel.add(topRowPanel, BorderLayout.NORTH);
        manageWaitlistsPanel.add(optionsPanel, BorderLayout.CENTER);
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
        manageWaitlistsPanel.add(topRowPanel, BorderLayout.NORTH);
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

    private void handleSearchWaitlists() {
        JOptionPane.showMessageDialog(manageWaitlistsPanel, "Search Waitlists clicked!");
    }

    private void handleEnroll() {
        JOptionPane.showMessageDialog(manageWaitlistsPanel, "Enroll Waitlists clicked!");
    }

    private void handleDrop() {
        JOptionPane.showMessageDialog(manageWaitlistsPanel, "Drop Waitlists clicked!");
    }
}
