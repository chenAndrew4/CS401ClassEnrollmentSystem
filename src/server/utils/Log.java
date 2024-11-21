package server.utils;

import shared.models.Message;
import shared.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

public class Log {
	private JTextArea jtextArea;
	private LocalDateTime dateTime = null;
	private DateTimeFormatter dateTimeFormatter = null;
	private String formattedDateTime = null;
	
	public Log(JTextArea jtextArea) {
		this.dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss:SS");
		this.jtextArea = jtextArea;
	}
	
	public void print(String str) {
		updateDateTime();
		this.jtextArea.append("[" + this.formattedDateTime + "] " + str);
	}
	
	public void println(String str) {
		updateDateTime();
		this.jtextArea.append("[" + this.formattedDateTime + "] " + str + "\n");
	}
	
	private void updateDateTime() {
		this.formattedDateTime = LocalDateTime.now().format(this.dateTimeFormatter);
	}
	
	public void message(Message msg){
    	updateDateTime();
    	this.jtextArea.append("[" + this.formattedDateTime + "]     Type: " + msg.getType() + "\n");
    	updateDateTime();
    	this.jtextArea.append("[" + this.formattedDateTime + "]     Status: " + msg.getStatus() + "\n");
    	updateDateTime();
    	this.jtextArea.append("[" + this.formattedDateTime + "]     shared.models.User: " + msg.toString() + "\n");
    }
    
	public void receivedMessage(String clientIpAddrPort, Message msg) {
    	println("Received from: " + clientIpAddrPort);
    	message(msg);
    }
    
	public void sendingMessage(String clientIpAddrPort, Message msg) {
    	println("Sending to: " + clientIpAddrPort);
    	message(msg);
    }
	
	public void error(String str) {
    	println("ERROR: " + str);
    }
}