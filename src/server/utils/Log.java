package server.utils;

import server.dataManagers.ServerLogDataManager;
import shared.enums.Institutions;
import shared.models.Message;
import shared.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

import javax.swing.*;

public class Log {
	private static Log instance; // Singleton instance
	private JTextArea jtextArea;
	private LocalDateTime dateTime = null;
	private DateTimeFormatter dateTimeFormatter = null;
	private String formattedDateTime = null;

	// Private constructor for Singleton
	private Log(JTextArea jtextArea) {
		this.dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss:SS");
		this.jtextArea = jtextArea;
	}

	// Public method to get the singleton instance
	public static synchronized Log getInstance(JTextArea jtextArea) {
		if (instance == null) {
			instance = new Log(jtextArea);
		}
		return instance;
	}

	// Print without a new line
	public void print(String str) {
		updateDateTime();
		if (jtextArea != null) {
			this.jtextArea.append("[" + this.formattedDateTime + "] " + str);
		}
	}

	// Print with a new line
	public void println(String str) {
		updateDateTime();
		if (jtextArea != null) {
			this.jtextArea.append("[" + this.formattedDateTime + "] " + str + "\n");
		}
	}

	// Update the current timestamp
	private void updateDateTime() {
		this.formattedDateTime = LocalDateTime.now().format(this.dateTimeFormatter);
	}

	// Log a message object
	public void message(Message msg) {
		updateDateTime();
		if (jtextArea != null) {
			this.jtextArea.append("[" + this.formattedDateTime + "]     Type: " + msg.getType() + "\n");
			updateDateTime();
			this.jtextArea.append("[" + this.formattedDateTime + "]     Status: " + msg.getStatus() + "\n");
			updateDateTime();
			this.jtextArea.append("[" + this.formattedDateTime + "]     Message: " + msg.toString() + "\n");
		}
	}

	// Log a received message
	public void receivedMessage(String clientIpAddrPort, Message msg) {
		println("Received from: " + clientIpAddrPort);
		message(msg);
	}

	// Log a sent message
	public void sendingMessage(String clientIpAddrPort, Message msg) {
		println("Sending to: " + clientIpAddrPort);
		message(msg);
	}

	public void debug(String str) {
		println("DEBUG: " + str);
	}
	
	// Log an error
	public void error(String str) {
		println("ERROR: " + str);
	}
	
	public void exception(String str) {
		println("EXCEPTION: " + str);
	}
	
	public void warn(String str) {
		println("WARNING: " + str);
	}

	// Optional: Reset the JTextArea if needed
	public void setJTextArea(JTextArea jtextArea) {
		this.jtextArea = jtextArea;
	}
}