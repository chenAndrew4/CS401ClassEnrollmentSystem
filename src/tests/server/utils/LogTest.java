package tests.server.utils;

import org.junit.jupiter.api.Test;
import server.dataManagers.ServerLogDataManager;
import server.utils.Log;
import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;

import javax.swing.*;
import java.awt.*;

public class LogTest {
//    private Log log;
    @Test
    public void logtest() throws InterruptedException {

        JFrame frame = new JFrame("Log Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create JTextArea
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Make it read-only

        // Add JScrollPane for better usability
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create an instance of the Log class
        Log log = Log.getInstance(textArea);

        // Use the log instance to log messages
        log.println("Application started");
        log.error("An example error occurred", Institutions.CSUEB,this.getClass());
        log.print("This is a log message without a new line.");

//        // Example message class usage (implementing your Message class)
//        Message exampleMessage = new Message(MessageType.LOGIN, MessageStatus.SUCCESS, "This is an example message.");
//        log.receivedMessage("127.0.0.1:8080", exampleMessage);
//        log.sendingMessage("192.168.1.100:9090", exampleMessage);
        ServerLogDataManager.getInstance().saveAllLogs();

        // Show the JFrame
        frame.setVisible(true);
        Thread.sleep(3000);
    }
}
