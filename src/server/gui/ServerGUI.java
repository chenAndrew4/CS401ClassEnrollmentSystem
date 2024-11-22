package server.gui;

import server.ServerManager;
import server.utils.Log;

import javax.swing.*;
import java.awt.*;

public class ServerGUI {
    private JFrame frame;
    public static JTextArea logTextArea;
    private Log log;

    public ServerGUI() {
        initializeGUI();
        log = Log.getInstance(logTextArea);
    }

    private void initializeGUI() {
        frame = new JFrame("College Course Enrollment System - Server");
        frame.setIconImage(new ImageIcon(ServerManager.SERVER_ICON_FILE_PATH).getImage());
        frame.setResizable(false);

        logTextArea = new JTextArea(25, 50);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        logTextArea.setEditable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Log getLog() {
        return log;
    }
}
