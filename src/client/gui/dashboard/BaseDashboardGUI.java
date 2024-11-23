package client.gui.dashboard;

import client.ClientConfig;
import client.utils.ImageUtils;
import shared.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public abstract class BaseDashboardGUI extends JFrame implements Runnable {

    private static final int MAIN_WINDOW_WIDTH = 1600;
    private static final int MAIN_WINDOW_HEIGHT = 1200;
    private static final int MARGIN = 40; // Margin for layout and options spacing
    private static final int PERSONAL_IMAGE_SIZE = 120; // Fixed size for the personal image icon
    private static final int OPTION_ICON_SIZE = 80; // Fixed size for the option icons
    private static final Color SEMI_TRANS = new Color(255, 255, 255, 150); // Semi-transparent white

    private JLabel personalImageLabel;
    private JLabel userNameLabel;
    private JTextArea infoArea1; // First column of user info
    private JTextArea infoArea2; // Second column of user info
    private JPanel optionsPanel;
    private User user;
    private JPanel topPanel;
    private ImageIcon backgroundIcon;

    public BaseDashboardGUI(String title, User user) {
        super("");
        this.user = user;

        // Set up the main frame
        initializeFrame();

        // Initialize the top panel with the university image, personal image, and user info
        initializeTopPanel();

        // Initialize the options panel for dashboard options
        initializeOptionsPanel();

        // Load and set user-specific details
        initializeUserDetails();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustComponents();
            }
        });
    }

    private void initializeFrame() {
        setSize(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(MARGIN, MARGIN)); // Add margin to layout
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background
    }

    private void initializeTopPanel() {
        topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundIcon != null) {
                    Image image = backgroundIcon.getImage();
                    Graphics2D g2d = (Graphics2D) g.create();

                    // Scale the background image to fill the panel
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();
                    int imageWidth = backgroundIcon.getIconWidth();
                    int imageHeight = backgroundIcon.getIconHeight();

                    double panelAspect = (double) panelWidth / panelHeight;
                    double imageAspect = (double) imageWidth / imageHeight;

                    int scaledWidth, scaledHeight;
                    if (panelAspect > imageAspect) {
                        scaledWidth = panelWidth;
                        scaledHeight = (int) (panelWidth / imageAspect);
                    } else {
                        scaledHeight = panelHeight;
                        scaledWidth = (int) (panelHeight * imageAspect);
                    }

                    int x = (panelWidth - scaledWidth) / 2;
                    int y = (panelHeight - scaledHeight) / 2;

                    // Draw the background with transparency
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f)); // 80% transparency
                    g2d.drawImage(image, x, y, scaledWidth, scaledHeight, this);
                    g2d.dispose();
                }
            }
        };
        topPanel.setLayout(null); // Absolute layout for custom positioning
        topPanel.setPreferredSize(new Dimension(MAIN_WINDOW_WIDTH, 400));
        add(topPanel, BorderLayout.NORTH);

        // Add the personal image with circular shape
        personalImageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(SEMI_TRANS); // Semi-transparent white
                g2d.fillOval(0, 0, width, height); // Fill the circle
                if (getIcon() != null) {
                    int inset = 10; // Add space between the icon and the circle border
                    Image image = ((ImageIcon) getIcon()).getImage();
                    g2d.setClip(new Ellipse2D.Double(inset, inset, width - 2 * inset, height - 2 * inset)); // Clip the image into a circle
                    g2d.drawImage(image, inset, inset, width - 2 * inset, height - 2 * inset, this);
                }
                g2d.dispose();
            }
        };
        personalImageLabel.setBounds(20, 20, PERSONAL_IMAGE_SIZE, PERSONAL_IMAGE_SIZE);
        topPanel.add(personalImageLabel);

        // Add the user name label below the personal image
        userNameLabel = new JLabel("", SwingConstants.CENTER);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userNameLabel.setForeground(Color.WHITE);
        topPanel.add(userNameLabel);

        // Add the user info areas
        infoArea1 = new JTextArea();
        infoArea1.setEditable(false);
        infoArea1.setLineWrap(true);
        infoArea1.setWrapStyleWord(true);
        infoArea1.setOpaque(false); // Transparent background
        infoArea1.setForeground(Color.WHITE); // White text color
        infoArea1.setFont(new Font("Arial", Font.BOLD, 16)); // Font size and style
        topPanel.add(infoArea1);

        infoArea2 = new JTextArea();
        infoArea2.setEditable(false);
        infoArea2.setLineWrap(true);
        infoArea2.setWrapStyleWord(true);
        infoArea2.setOpaque(false); // Transparent background
        infoArea2.setForeground(Color.WHITE); // White text color
        infoArea2.setFont(new Font("Arial", Font.BOLD, 16)); // Font size and style
        topPanel.add(infoArea2);
    }

    private void initializeOptionsPanel() {
        optionsPanel = new JPanel(new GridLayout(0, 4, MARGIN, MARGIN)); // Dynamic rows, max 4 columns
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN)); // Space around options panel
        optionsPanel.setOpaque(false);
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

            // Set user name below the icon
            userNameLabel.setText(user.getFirstName() + " " + user.getLastName());

            // Format the admission date
            String formattedDate = user.getDate().toString().substring(4, 10); // Keep only year and month

            // Split user info into two columns if needed
            String[] userInfo = {
                    "User ID: " + user.getUserId() + "\n",
                    "Account Type: " + user.getAccountType() + "\n",
                    "Admission Date: " + formattedDate + "\n"
            };

            infoArea1.setText(String.join("\n", userInfo));
            infoArea2.setText(""); // Empty for now; populate if needed
        }
        adjustComponents();
    }

    public void setUniversityImage(ImageIcon universityImage) {
        this.backgroundIcon = universityImage;
        if (topPanel != null) {
            topPanel.repaint(); // Repaint to apply the new image
        }
    }

    public void setPersonalImage(ImageIcon personalImage) {
        if (personalImageLabel != null) {
            personalImageLabel.setIcon(personalImage);
        }
    }

    public void addOption(String label, ImageIcon icon, Runnable action) {
        JPanel optionCell = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Glass-like background color
                g2d.setColor(SEMI_TRANS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2d.dispose();
            }
        };

        // Configure optionCell layout
        optionCell.setLayout(new BoxLayout(optionCell, BoxLayout.Y_AXIS));
        optionCell.setOpaque(false);
        optionCell.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionCell.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Top, left, bottom, right padding

        // Icon label
        JLabel iconLabel = new JLabel(ImageUtils.resizeImageIcon(icon, OPTION_ICON_SIZE, OPTION_ICON_SIZE));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text label
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Larger and bold font
        textLabel.setForeground(Color.BLACK);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to optionCell
        optionCell.add(Box.createVerticalGlue()); // Add vertical space at the top
        optionCell.add(iconLabel);
        optionCell.add(Box.createRigidArea(new Dimension(0, 5))); // Space between icon and text
        optionCell.add(textLabel);
        optionCell.add(Box.createVerticalGlue()); // Add vertical space at the bottom

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

    private void adjustComponents() {
        // Adjust personal image, name, and info areas to fit and align properly
        int panelWidth = topPanel.getWidth();
        int panelHeight = topPanel.getHeight();

        // Personal image
        int personalImageX = (panelWidth - PERSONAL_IMAGE_SIZE) / 2;
        int personalImageY = (panelHeight - 200) / 4;

        personalImageLabel.setBounds(personalImageX, personalImageY, PERSONAL_IMAGE_SIZE, PERSONAL_IMAGE_SIZE);

        // User name
        int userNameWidth = panelWidth / 2;
        int userNameX = (panelWidth - userNameWidth) / 2;
        int userNameY = personalImageY + PERSONAL_IMAGE_SIZE + 10;

        userNameLabel.setBounds(userNameX, userNameY, userNameWidth, 30);

        // Info areas
        int infoWidth = panelWidth / 3;
        int infoHeight = 100;

        int info1X = (panelWidth - (2 * infoWidth + MARGIN)) / 2;
        int info1Y = userNameY + 40;

        int info2X = info1X + infoWidth + MARGIN;
        int info2Y = info1Y;

        infoArea1.setBounds(info1X, info1Y, infoWidth, infoHeight);
        infoArea2.setBounds(info2X, info2Y, infoWidth, infoHeight);
    }

    @Override
    public void run() {
        setVisible(true);
    }
}

