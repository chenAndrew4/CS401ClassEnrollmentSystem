package client.gui.dashboard;

import shared.models.User;

import javax.swing.*;
import java.awt.*;

public class BaseDashboardGUI extends JFrame implements Runnable {

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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel with a background image
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (universityImageLabel.getIcon() != null) {
                    Image image = ((ImageIcon) universityImageLabel.getIcon()).getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        topPanel.setLayout(null); // Use absolute layout to overlay personal image and info
        topPanel.setPreferredSize(new Dimension(800, 200));
        add(topPanel, BorderLayout.NORTH);

        // University image label (placeholder for painting background)
        universityImageLabel = new JLabel();
        universityImageLabel.setBounds(0, 0, 800, 200);
        topPanel.add(universityImageLabel);

        // Personal image
        personalImageLabel = new JLabel();
        personalImageLabel.setBounds(20, 20, PERSONAL_IMAGE_SIZE, PERSONAL_IMAGE_SIZE); // Position and size
        personalImageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.add(personalImageLabel);

        // Info area
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent background
        infoArea.setBounds(100, 20, 650, 100); // Position and size
        topPanel.add(infoArea);

        // Options panel with grid layout
        optionsPanel = new JPanel(new GridLayout(1, 5, 10, 10)); // Example: 5 cells
        add(optionsPanel, BorderLayout.CENTER);

        // Initialize user details
        initializeUserDetails();
    }

    // Initialize user-specific details (personal image and info)
    private void initializeUserDetails() {
        if (user != null) {
            // Set personal image if available
            String filepath = "src/client/assets.icons/login_32.png";
//            if (user.getPersonalImagePath() != null) {
//                setPersonalImage(new ImageIcon(user.getPersonalImagePath()));
//            }
            setPersonalImage(new ImageIcon(filepath));

            // Set user info
            String info = String.format(
                    "Name: %s %s\nInstitution: %s\nAccount Type: %s",
                    user.getFirstName(),
                    user.getLastName(),
                    user.getInstitutionID(),
                    user.getAccountType()
            );
            setInfo(info);
        }
    }

    // Add the university image (background for the top panel)
    public void setUniversityImage(ImageIcon universityImage) {
        universityImageLabel.setIcon(universityImage);
        repaint(); // Repaint the top panel with the new image
    }

    // Add the personal image (scaled)
    public void setPersonalImage(ImageIcon personalImage) {
        Image scaledImage = personalImage.getImage().getScaledInstance(
                PERSONAL_IMAGE_SIZE, PERSONAL_IMAGE_SIZE, Image.SCALE_SMOOTH
        );
        personalImageLabel.setIcon(new ImageIcon(scaledImage));
    }

    // Set user information
    public void setInfo(String info) {
        infoArea.setText(info);
    }

    // Add an option cell with a resized image and label
    public void addOption(String label, ImageIcon icon, Runnable action) {
        JPanel optionCell = new JPanel();
        optionCell.setLayout(new BorderLayout());

        JLabel iconLabel = new JLabel(resizeIcon(icon, OPTION_ICON_SIZE, OPTION_ICON_SIZE));
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);

        optionCell.add(iconLabel, BorderLayout.CENTER);
        optionCell.add(textLabel, BorderLayout.SOUTH);

        // Add click listener
        optionCell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        optionCell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new Thread(action).start();
            }
        });

        optionsPanel.add(optionCell);
    }

    // Resize an ImageIcon to the desired width and height
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        if (icon == null) return null;
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    @Override
    public void run() {
        setVisible(true);
    }
}
