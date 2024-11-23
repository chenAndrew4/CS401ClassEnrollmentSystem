package client;

import client.handlers.EnrollCourseResponseHandler;
import client.handlers.LoginResponseHandler;
import shared.models.requests.EnrollCourseRequest;
import shared.models.requests.LoginRequest;
import shared.enums.*;

import javax.swing.*;

import shared.models.User;
import shared.enums.MessageType;
import shared.enums.MessageStatus;
import shared.models.responses.EnrollCourseResponse;
import shared.models.responses.LoginResponse;

import java.io.IOException;

public class Client {
	private String hostname;
	private String port;
	private ServerHandler serverHandler;
	private User currentUser;

	public Client(String hostname, String port) {
		this.hostname = hostname;
		this.port = port;
		initializeHandlers();
	}
	private void initializeHandlers() {
		try {
			serverHandler = new ServerHandler(hostname, port);
			serverHandler.registerResponseHandler(MessageType.LOGIN, new LoginResponseHandler());
			serverHandler.registerResponseHandler(MessageType.ENROLL_COURSE, new EnrollCourseResponseHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean login(String username, String password, Institutions institutionID) {
		try {
			currentUser = new User();
			currentUser.setUsername(username);
			currentUser.setPassword(password);
			currentUser.setInstitutionID(institutionID);

			serverHandler = new ServerHandler(hostname, port);

			LoginRequest loginRequest = new LoginRequest( MessageType.LOGIN, null,institutionID, currentUser);
			LoginResponse loginResponse = (LoginResponse)serverHandler.sendRequest(loginRequest);

			if (loginResponse.getStatus() == MessageStatus.SUCCESS) {
				currentUser = loginResponse.getUser();
				return true;
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
	}
	public boolean enrollInCourse(String courseId, String sectionID) {
        EnrollCourseRequest enrollRequest = new EnrollCourseRequest(MessageType.ENROLL_COURSE, null, currentUser.getInstitutionID(), currentUser.getSessionToken(), courseId, sectionID, currentUser.isAuthenticated());
        EnrollCourseResponse response = (EnrollCourseResponse) serverHandler.sendRequest(enrollRequest);
        if (response.getStatus() == MessageStatus.SUCCESS) {
            System.out.println("Enrolled successfully.");
			// add course to courses
			return true;
        } else {
            System.out.println("Enrollment failed: " + response);
        }
		return false;
    }

	public void showPortal(String username) {
		JFrame portal = new JFrame();
		portal.setTitle("CCES Portal - User: " + username);
		portal.setIconImage(new ImageIcon("src/client/assets/icons/app_32.png").getImage());
		portal.setSize(1000, 800);
		portal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		portal.setLocationRelativeTo(null);
		portal.setVisible(true);
	}
}
//class Client{
//	public static void main(String[] args) {
//		// Build the login dialog
//		Integer loginChoice;
//		String loginChoices[] = {"Login", "Exit"};
//
//		JPanel panel = new JPanel(new GridLayout(4, 2));
//
//		JTextField username = new JTextField();
//		JTextField password = new JPasswordField();
//		JTextField hostname = new JTextField();
//		hostname.setText("localhost");
//		JTextField port = new JTextField();
//		port.setText("25800");
//
//		panel.add(new JLabel("Username:"));
//		panel.add(username);
//		panel.add(new JLabel("Password:"));
//		panel.add(password);
//		panel.add(new JLabel("Server Host:"));
//		panel.add(hostname);
//		panel.add(new JLabel("Server Port:"));
//		panel.add(port);
//
//		// Show the login dialog
//		loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("assets/icons/login_32.png"), loginChoices, loginChoices[0]);
//
//		// Do stuff according to what the user clicked on above
//		if (loginChoice == 0) {
//			// shared.models.User clicked 'Login'
//
//			//
//			boolean invalidHostnamePort = false;
//
//			// Create a user object for the message object
//			User user = new User();
//			user.setUsername(username.getText());
//			user.setPassword(password.getText());
//
//			// Create the message object to send to the server
//
//			Message response = new Message();
//			ServerHandler serverHandler = null;
//			try {
//				// Create a ServerHandler to help with repetitive networking serializer/deserializer
//				// Also, check to see if there is a valid connection to the server
//				serverHandler = new ServerHandler(hostname.getText(), port.getText());
//				// Send the login message and get the response
//				response = serverHandler.sendMessage(new Message(MessageType.LOGIN, user));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				invalidHostnamePort = true;
//			}
//
//			// Keep asking the user to input the correct hostname and port of the server until they do or exits
//			while(invalidHostnamePort) {
//				// Show invalid hostname/port pop-up
//				JOptionPane.showMessageDialog(null, "Invalid hostname and/or port!", "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("assets/icons/invalid_host_port_32.png"));
//
//				// Ask for credentials again
//				loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("assets/icons/login_32.png"), loginChoices, loginChoices[0]);
//
//				if (loginChoice == 0) {
//					try {
//						// Create a ServerHandler to help with repetitive networking serializer/deserializer
//						serverHandler = new ServerHandler(hostname.getText(), port.getText());
//
//						// We have to recreate the same object if we are sending it to the server multiple times.
//						// If we don't do this, we see the same data that was sent initially on the server side despite changing it on the client side.
//						user = new User();
//						user.setUsername(username.getText());
//						user.setPassword(password.getText());
//
//						// Send the login message and get the response
//						response = serverHandler.sendMessage(new Message(MessageType.LOGIN, user));
//						// It got to this point without throwing an exception, it now has a valid hostname and port
//						invalidHostnamePort = false;
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						invalidHostnamePort = true;
//					}
//				} else
//					return; // They clicked on 'Exit'
//			}
//
//			// Once we confirm that the server hostname and port is valid, disable the text fields from being changed
////			hostname.setEnabled(false);
////			port.setEnabled(false);
//
//
//			// While login credentials are invalid, keep asking user to login until they click 'Exit'
//			if (response.getType() == MessageType.LOGIN && response.getStatus() == MessageStatus.FAILURE) {
//				// Close the socket. We will create another.
//				// We want to give the user the flexibility to change the server hostname and port any time during their login sequence
//				serverHandler.closeSocket();
//				do {
//					if (invalidHostnamePort) {
//						// Show invalid hostname/port pop-up
//						JOptionPane.showMessageDialog(null, "Invalid hostname and/or port!", "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/client/assets/icons/invalid_host_port_32.png"));
//					} else {
//						// Show invalid credentials pop-up
//						JOptionPane.showMessageDialog(null, "Invalid username and/or password!", "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("src/client/assets/icons/invalid_creds_32.png"));
//					}
//
//					// Ask for credentials again
//					loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/client/assets/icons/login_32.png"), loginChoices, loginChoices[0]);
//
//					// shared.models.User attempted once again, let's send the new username and password to the server
//					if (loginChoice == 0) {
//						try {
//							// Create a new server handler
//							serverHandler = new ServerHandler(hostname.getText(), port.getText());
//
//							// It got to this point without throwing an exception, it now has a valid hostname and port
//							invalidHostnamePort = false;
//
//							// We have to recreate the same object if we are sending it to the server multiple times.
//							// If we don't do this, we see the same data that was sent initially on the server side despite changing it on the client side.
//							user = new User();
//							// Update the user object with the new username/password
//							user.setUsername(username.getText());
//							user.setPassword(password.getText());
//
//							// Send the new login message and get the response
//							response = serverHandler.sendMessage(new Message(MessageType.LOGIN, user));
//
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							invalidHostnamePort = true;
//						}
//					}
//				} while (response.getType() == MessageType.LOGIN && response.getStatus() == MessageStatus.FAILURE && loginChoice == 0 || invalidHostnamePort && loginChoice == 0);
//				if (loginChoice == 1)
//					// shared.models.User clicked 'Exit'
//					return;
//			}
//
//			// Get the actual user from the database. We created ones earlier as a means to communicate with the server with objects.
//			user = (User) response.getContent();
//
//			// Generate and render portal
//			// ...
//			JFrame portal = new JFrame();
//			portal.setTitle("College shared.models.Course Enrollment System - shared.models.User: " + user.getUsername() + " ID: " + user.getUserId());
//			portal.setIconImage(new ImageIcon("src/client/assets/icons/app_32.png").getImage());
//			portal.setSize(1000, 800);
//			portal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			portal.setLocationRelativeTo(null);
//			portal.setVisible(true);
//
//		} else if (loginChoice == 1) {
//			// shared.models.User clicked 'Exit'
//			return;
//		}
//	}
//}