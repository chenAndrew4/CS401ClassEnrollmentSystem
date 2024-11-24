package client.gui.dashboard;

import client.ClientConfig;
import client.gui.GUIConfig;
import client.utils.ImageUtils;
import shared.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;

public abstract class BaseDashboardGUI extends JFrame implements Runnable {

    private JLabel personalImageLabel;
    private JLabel userNameLabel;
    private JTextArea userInfoArea; // For user information
    private JPanel optionsPanel;
    private User user;
    private JPanel topPanel;
    private ImageIcon backgroundIcon;
    private JPanel mainContentPanel;
    private JScrollPane scrollPane;

    public BaseDashboardGUI(String title, User user) {
        super(title);
        this.user = user;

        // Set up the main frame
        initializeFrame();

        // Initialize the main content panel
        initializeMainContentPanel();

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
        setSize(GUIConfig.MAIN_WINDOW_WIDTH, GUIConfig.MAIN_WINDOW_HEIGHT);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0)); // No gaps between panels
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background
    }

    private void initializeMainContentPanel() {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(null); // Absolute layout for precise positioning
        mainContentPanel.setPreferredSize(new Dimension(GUIConfig.MAIN_WINDOW_WIDTH, GUIConfig.MAIN_WINDOW_HEIGHT));
        mainContentPanel.setBackground(new Color(240, 240, 240));

        initializeTopPanel();
        mainContentPanel.add(topPanel);

        initializeOptionsPanel();
        mainContentPanel.add(optionsPanel);

        // Wrap the main content panel in a scrollable pane
        scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeTopPanel() {
        topPanel = new JPanel(null) {
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

                    g2d.drawImage(image, x, y, scaledWidth, scaledHeight, this);
                    g2d.dispose();
                }
            }
        };
        topPanel.setBounds(0, 0, GUIConfig.MAIN_WINDOW_WIDTH, 400);

        // Personal image (centered)
        personalImageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                g2d.setColor(GUIConfig.SEMI_TRANS);
                g2d.fillOval(0, 0, width, height);
                if (getIcon() != null) {
                    int inset = 10;
                    Image image = ((ImageIcon) getIcon()).getImage();
                    g2d.setClip(new Ellipse2D.Double(inset, inset, width - 2 * inset, height - 2 * inset));
                    g2d.drawImage(image, inset, inset, width - 2 * inset, height - 2 * inset, this);
                }
                g2d.dispose();
            }
        };
        topPanel.add(personalImageLabel);

        // User name label (centered under the icon)
        userNameLabel = new JLabel("", SwingConstants.CENTER);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userNameLabel.setForeground(Color.WHITE);
        topPanel.add(userNameLabel);

        // User info area (aligned left)
        userInfoArea = new JTextArea();
        userInfoArea.setEditable(false);
        userInfoArea.setLineWrap(true);
        userInfoArea.setWrapStyleWord(true);
        userInfoArea.setOpaque(false); // Transparent background
        userInfoArea.setForeground(Color.WHITE);
        userInfoArea.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(userInfoArea);
    }

    private void initializeOptionsPanel() {
        optionsPanel = new JPanel(new GridLayout(0, 4, GUIConfig.MARGIN, GUIConfig.MARGIN));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(GUIConfig.MARGIN, GUIConfig.MARGIN, GUIConfig.MARGIN, GUIConfig.MARGIN));
        optionsPanel.setOpaque(false);
        optionsPanel.setBounds(0, 400, GUIConfig.MAIN_WINDOW_WIDTH, GUIConfig.MAIN_WINDOW_HEIGHT - 400);
    }

    private void initializeUserDetails() {
        if (user != null) {
            switch (user.getInstitutionID()) {
                case SJSU -> setUniversityImage(new ImageIcon(ClientConfig.SJSU_DASH_BACKGROUND_FILE_PATH));
                case CSUEB -> setUniversityImage(new ImageIcon(ClientConfig.CSUEB_DASH_BACKGROUND_FILE_PATH));
                case CSUF -> setUniversityImage(new ImageIcon(ClientConfig.CSUF_DASH_BACKGROUND_FILE_PATH));
                default -> setUniversityImage(new ImageIcon(ClientConfig.DEFAULT_DASH_BACKGROUND_PATH));
            }
            setPersonalImage(new ImageIcon(ClientConfig.MANAGE_USERS_ICON));
            userNameLabel.setText(user.getFirstName() + " " + user.getLastName());

            String formattedDate = user.getDate().toString().substring(4, 10);
            String userInfo = """
                    User ID: %s
                    Account Type: %s
                    Admission Date: %s
                    """.formatted(user.getUserId(), user.getAccountType(), formattedDate);

            userInfoArea.setText(userInfo);
        }
        adjustComponents();
    }

    public void setUniversityImage(ImageIcon universityImage) {
        this.backgroundIcon = universityImage;
        if (topPanel != null) {
            topPanel.repaint();
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
                g2d.setColor(GUIConfig.SEMI_TRANS);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
            }
        };

        optionCell.setLayout(new BoxLayout(optionCell, BoxLayout.Y_AXIS));
        optionCell.setOpaque(false);
        optionCell.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionCell.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JLabel iconLabel = new JLabel(ImageUtils.resizeImageIcon(icon, GUIConfig.OPTION_ICON_SIZE, GUIConfig.OPTION_ICON_SIZE));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
        textLabel.setForeground(Color.BLACK);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        optionCell.add(Box.createVerticalGlue());
        optionCell.add(iconLabel);
        optionCell.add(Box.createRigidArea(new Dimension(0, 5)));
        optionCell.add(textLabel);
        optionCell.add(Box.createVerticalGlue());

        optionCell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        optionCell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                new Thread(action).start();
            }
        });

        optionsPanel.add(optionCell);
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }


    private void adjustComponents() {
        int panelWidth = topPanel.getWidth();
        int panelHeight = topPanel.getHeight();

        // Center personal image
        int personalImageSize = GUIConfig.PERSONAL_IMAGE_SIZE;
        personalImageLabel.setBounds((panelWidth - personalImageSize) / 2, 50, personalImageSize, personalImageSize);

        // Center user name below the personal image
        userNameLabel.setBounds(0, 50 + personalImageSize + 10, panelWidth, 30);

        // Align user info to the left of the personal image
        userInfoArea.setBounds(20, userNameLabel.getY() + 40, panelWidth - 40, 100);

        mainContentPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
        topPanel.setBounds(0, 0, getWidth(), 400);
        optionsPanel.setBounds(0, 400, getWidth(), getHeight() - 400);

        scrollPane.revalidate();
        scrollPane.repaint();
    }

    @Override
    public void run() {
        setVisible(true);
    }
}

//public abstract class BaseDashboardGUI extends JFrame implements Runnable {
//
//    private JLabel personalImageLabel;
//    private JLabel userNameLabel;
//    private JTextArea infoArea1;
//    private JTextArea infoArea2;
//    private JTextArea userInfoArea;
//    private JPanel optionsPanel;
//    private User user;
//    private JPanel topPanel;
//    private ImageIcon backgroundIcon;
//    private JPanel mainContentPanel;
//    private JScrollPane scrollPane;
//
//    public BaseDashboardGUI(String title, User user) {
//        super(title);
//        this.user = user;
//
//        // Set up the main frame
//        initializeFrame();
//
//        // Initialize the main content panel
//        initializeMainContentPanel();
//
//        // Load and set user-specific details
//        initializeUserDetails();
//
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                adjustComponents();
//            }
//        });
//    }
//
//    private void initializeFrame() {
//        setSize(GUIConfig.MAIN_WINDOW_WIDTH, GUIConfig.MAIN_WINDOW_HEIGHT);
//        setMinimumSize(new Dimension(800, 600));
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout(0, 0)); // No gaps between panels
//        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background
//    }
//
//    private void initializeMainContentPanel() {
//        mainContentPanel = new JPanel();
//        mainContentPanel.setLayout(null); // Absolute layout for precise positioning
//        mainContentPanel.setPreferredSize(new Dimension(GUIConfig.MAIN_WINDOW_WIDTH, GUIConfig.MAIN_WINDOW_HEIGHT));
//        mainContentPanel.setBackground(new Color(240, 240, 240));
//
//        initializeTopPanel();
//        mainContentPanel.add(topPanel);
//
//        initializeOptionsPanel();
//        mainContentPanel.add(optionsPanel);
//
//        // Wrap the main content panel in a scrollable pane
//        scrollPane = new JScrollPane(mainContentPanel);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
//        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//
//        add(scrollPane, BorderLayout.CENTER);
//    }
//
//    private void initializeTopPanel() {
//        topPanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                if (backgroundIcon != null) {
//                    Image image = backgroundIcon.getImage();
//                    Graphics2D g2d = (Graphics2D) g.create();
//
//                    // Scale the background image to fill the panel
//                    int panelWidth = getWidth();
//                    int panelHeight = getHeight();
//                    int imageWidth = backgroundIcon.getIconWidth();
//                    int imageHeight = backgroundIcon.getIconHeight();
//
//                    double panelAspect = (double) panelWidth / panelHeight;
//                    double imageAspect = (double) imageWidth / imageHeight;
//
//                    int scaledWidth, scaledHeight;
//                    if (panelAspect > imageAspect) {
//                        scaledWidth = panelWidth;
//                        scaledHeight = (int) (panelWidth / imageAspect);
//                    } else {
//                        scaledHeight = panelHeight;
//                        scaledWidth = (int) (panelHeight * imageAspect);
//                    }
//
//                    int x = (panelWidth - scaledWidth) / 2;
//                    int y = (panelHeight - scaledHeight) / 2;
//
//                    g2d.drawImage(image, x, y, scaledWidth, scaledHeight, this);
//                    g2d.dispose();
//                }
//            }
//        };
//        topPanel.setLayout(null); // Absolute layout
//        topPanel.setBounds(0, 0, GUIConfig.MAIN_WINDOW_WIDTH, 400);
//
//        // Personal image (centered)
//        personalImageLabel = new JLabel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g.create();
//                int width = getWidth();
//                int height = getHeight();
//                g2d.setColor(GUIConfig.SEMI_TRANS);
//                g2d.fillOval(0, 0, width, height);
//                if (getIcon() != null) {
//                    int inset = 10;
//                    Image image = ((ImageIcon) getIcon()).getImage();
//                    g2d.setClip(new Ellipse2D.Double(inset, inset, width - 2 * inset, height - 2 * inset));
//                    g2d.drawImage(image, inset, inset, width - 2 * inset, height - 2 * inset, this);
//                }
//                g2d.dispose();
//            }
//        };
//        personalImageLabel.setBounds((GUIConfig.MAIN_WINDOW_WIDTH - GUIConfig.PERSONAL_IMAGE_SIZE) / 2, 50, GUIConfig.PERSONAL_IMAGE_SIZE, GUIConfig.PERSONAL_IMAGE_SIZE);
//        topPanel.add(personalImageLabel);
//
//        // User name label (centered under the icon)
//        userNameLabel = new JLabel("", SwingConstants.CENTER);
//        userNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        userNameLabel.setForeground(Color.WHITE);
//        userNameLabel.setBounds(0, 180, GUIConfig.MAIN_WINDOW_WIDTH, 30);
//        topPanel.add(userNameLabel);
//
//        // User info area (below name, transparent background)
//        userInfoArea = new JTextArea();
//        userInfoArea.setEditable(false);
//        userInfoArea.setLineWrap(true);
//        userInfoArea.setWrapStyleWord(true);
//        userInfoArea.setOpaque(false); // Transparent background
//        userInfoArea.setForeground(Color.WHITE);
//        userInfoArea.setFont(new Font("Arial", Font.BOLD, 16));
//        userInfoArea.setBounds(50, 220, GUIConfig.MAIN_WINDOW_WIDTH - 100, 100);
//        topPanel.add(userInfoArea);
//    }
//
//    private void initializeOptionsPanel() {
//        optionsPanel = new JPanel(new GridLayout(0, 4, GUIConfig.MARGIN, GUIConfig.MARGIN));
//        optionsPanel.setBorder(BorderFactory.createEmptyBorder(GUIConfig.MARGIN, GUIConfig.MARGIN, GUIConfig.MARGIN, GUIConfig.MARGIN));
//        optionsPanel.setOpaque(false);
//        optionsPanel.setBounds(0, 400, GUIConfig.MAIN_WINDOW_WIDTH, GUIConfig.MAIN_WINDOW_HEIGHT - 400);
//    }
//
//    private void initializeUserDetails() {
//        if (user != null) {
//            switch (user.getInstitutionID()) {
//                case SJSU -> setUniversityImage(new ImageIcon(ClientConfig.SJSU_DASH_BACKGROUND_FILE_PATH));
//                case CSUEB -> setUniversityImage(new ImageIcon(ClientConfig.CSUEB_DASH_BACKGROUND_FILE_PATH));
//                case CSUF -> setUniversityImage(new ImageIcon(ClientConfig.CSUF_DASH_BACKGROUND_FILE_PATH));
//                default -> setUniversityImage(new ImageIcon(ClientConfig.DEFAULT_DASH_BACKGROUND_PATH));
//            }
//            setPersonalImage(new ImageIcon(ClientConfig.MANAGE_USERS_ICON));
//            userNameLabel.setText(user.getFirstName() + " " + user.getLastName());
//
//            String formattedDate = user.getDate().toString().substring(4, 10);
//            String[] userInfo = {
//                    "User ID: " + user.getUserId(),
//                    "Account Type: " + user.getAccountType(),
//                    "Admission Date: " + formattedDate
//            };
//
//            userInfoArea.setText(String.join("\n", userInfo));
//        }
//        adjustComponents();
//    }
//
//    public void setUniversityImage(ImageIcon universityImage) {
//        this.backgroundIcon = universityImage;
//        if (topPanel != null) {
//            topPanel.repaint();
//        }
//    }
//
//    public void setPersonalImage(ImageIcon personalImage) {
//        if (personalImageLabel != null) {
//            personalImageLabel.setIcon(personalImage);
//        }
//    }
//
//    public void addOption(String label, ImageIcon icon, Runnable action) {
//        JPanel optionCell = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2d = (Graphics2D) g.create();
//                g2d.setColor(GUIConfig.SEMI_TRANS);
//                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
//                g2d.dispose();
//            }
//        };
//
//        optionCell.setLayout(new BoxLayout(optionCell, BoxLayout.Y_AXIS));
//        optionCell.setOpaque(false);
//        optionCell.setAlignmentX(Component.CENTER_ALIGNMENT);
//        optionCell.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
//
//        JLabel iconLabel = new JLabel(ImageUtils.resizeImageIcon(icon, GUIConfig.OPTION_ICON_SIZE, GUIConfig.OPTION_ICON_SIZE));
//        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
//        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
//        textLabel.setForeground(Color.BLACK);
//        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        optionCell.add(Box.createVerticalGlue());
//        optionCell.add(iconLabel);
//        optionCell.add(Box.createRigidArea(new Dimension(0, 5)));
//        optionCell.add(textLabel);
//        optionCell.add(Box.createVerticalGlue());
//
//        optionCell.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        optionCell.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                new Thread(action).start();
//            }
//        });
//
//        optionsPanel.add(optionCell);
//        optionsPanel.revalidate();
//        optionsPanel.repaint();
//    }
//
//    private void adjustComponents() {
//        mainContentPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));
//        topPanel.setBounds(0, 0, getWidth(), 400);
//        optionsPanel.setBounds(0, 400, getWidth(), getHeight() - 400);
//        mainContentPanel.revalidate();
//        mainContentPanel.repaint();
//    }
//
//    @Override
//    public void run() {
//        setVisible(true);
//    }
//}
