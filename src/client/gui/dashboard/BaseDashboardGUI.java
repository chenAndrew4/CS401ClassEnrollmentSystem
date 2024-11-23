package client.gui.dashboard;

import client.ClientConfig;
import client.utils.ImageUtils;
import shared.models.User;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class BaseDashboardGUI extends JFrame implements Runnable {

    private static final int PERSONAL_IMAGE_SIZE = 60; // Fixed size for the personal image icon
    private static final int OPTION_ICON_SIZE = 80; // Fixed size for the option icons

    private JLabel universityImageLabel; // Background image for the top panel
    private JLabel personalImageLabel;
    private JTextArea infoArea;
    private JPanel optionsPanel;
    private User user; // The user associated with the dashboard

    public BaseDashboardGUI(String title, User user) {
        super(title);
        this.user = user;

        // Set up the main frame
        initializeFrame();

        // Initialize the top panel with the university image, personal image, and user info
        initializeTopPanel();

        // Initialize the options panel for dashboard options
        initializeOptionsPanel();

        // Load and set user-specific details
        initializeUserDetails();
    }

    private void initializeFrame() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void initializeTopPanel() {
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (universityImageLabel != null && universityImageLabel.getIcon() != null) {
                    Image image = ((ImageIcon) universityImageLabel.getIcon()).getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        topPanel.setLayout(null); // Absolute layout for custom positioning
        topPanel.setPreferredSize(new Dimension(800, 200));
        add(topPanel, BorderLayout.NORTH);

        // Add the university image (background)
        universityImageLabel = new JLabel();
        universityImageLabel.setBounds(0, 0, 800, 200);
        topPanel.add(universityImageLabel);

        // Add the personal image
        personalImageLabel = new JLabel();
        personalImageLabel.setBounds(20, 20, PERSONAL_IMAGE_SIZE, PERSONAL_IMAGE_SIZE);
        personalImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.add(personalImageLabel);

        // Add the user info area
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent background
        infoArea.setBounds(100, 20, 650, 100);
        topPanel.add(infoArea);
    }

    private void initializeOptionsPanel() {
        optionsPanel = new JPanel(new GridLayout(1, 5, 10, 10)); // Example layout for options
        add(optionsPanel, BorderLayout.CENTER);
    }

    private void initializeUserDetails() {
        if (user != null) {
            // Set the university image based on the institution
            switch (user.getInstitutionID()) {
                case SJSU -> setUniversityImage(new ImageIcon(ClientConfig.SJSU_DASH_BACKGROUND_FILE_PATH));
                case CSUEB -> setUniversityImage(new ImageIcon(ClientConfig.CSUEB_DASH_BACKGROUND_FILE_PATH));
                case CSUF -> setUniversityImage(new ImageIcon(ClientConfig.CSUF_DASH_BACKGROUND_FILE_PATH));
                default -> setUniversityImage(new ImageIcon(ClientConfig.DEFAULT_DASH_BACKGROUND_PATH));
            }
            // Set personal image (if available) or default
            setPersonalImage(new ImageIcon(ClientConfig.MANAGE_USERS_ICON));

            // Set user info
            setInfo(String.format(
                    "Name: %s %s\nInstitution: %s\nAccount Type: %s",
                    user.getFirstName(),
                    user.getLastName(),
                    user.getInstitutionID(),
                    user.getAccountType()
            ));
        }
    }

    public void setUniversityImage(ImageIcon universityImage) {
        if (universityImageLabel != null) {
            universityImageLabel.setIcon(universityImage);
            repaint(); // Repaint to apply the new image
        }
    }

    public void setPersonalImage(ImageIcon personalImage) {
        Image scaledImage = personalImage.getImage().getScaledInstance(
                PERSONAL_IMAGE_SIZE, PERSONAL_IMAGE_SIZE, Image.SCALE_SMOOTH
        );
        if (personalImageLabel != null) {
            personalImageLabel.setIcon(new ImageIcon(scaledImage));
        }
    }

    public void setInfo(String info) {
        if (infoArea != null) {
            infoArea.setText(info);
        }
    }

    public void addOption(String label, ImageIcon icon, Runnable action) {
        JPanel optionCell = new JPanel();
        optionCell.setLayout(new BorderLayout());

        JLabel iconLabel = new JLabel(ImageUtils.resizeImageIcon(icon, OPTION_ICON_SIZE, OPTION_ICON_SIZE));
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);

        optionCell.add(iconLabel, BorderLayout.CENTER);
        optionCell.add(textLabel, BorderLayout.SOUTH);

        // Add click listener
        optionCell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        optionCell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                new Thread(action).start();
            }
        });

        optionsPanel.add(optionCell);

        // Refresh options panel layout
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    @Override
    public void run() {
        setVisible(true);
    }
}

