import java.awt.*;
import javax.swing.*;

class Client{
	public static void main(String[] args) {
		// Build the login dialog
		Integer loginChoice;
		String loginChoices[] = {"Login", "Exit"};
		
		JPanel panel = new JPanel(new GridLayout(4, 2));		
		
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		JTextField hostname = new JTextField();
		hostname.setText("localhost");
		JTextField port = new JTextField();
		port.setText("25800");
		
		panel.add(new JLabel("Username:"));
		panel.add(username);
		panel.add(new JLabel("Password:"));
		panel.add(password);
		panel.add(new JLabel("Server Host:"));
		panel.add(hostname);
		panel.add(new JLabel("Server Port:"));
		panel.add(port);
		
		// Show the login dialog
		loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("assets/icons/login_32.png"), loginChoices, loginChoices[0]);
		
		// Do stuff according to what the user clicked on above
		if (loginChoice == 0) {
			// User clicked 'Login'
			
			//
			boolean invalidHostnamePort = false;
			
			// Create a user object for the message object
			User user = new User();
			user.setUsername(username.getText());
			user.setPassword(password.getText());
			
			// Create the message object to send to the server
			
			Message response = new Message();
			ServerHandler serverHandler = null;
			try {
				// Create a ServerHandler to help with repetitive networking serializer/deserializer
				// Also, check to see if there is a valid connection to the server
				serverHandler = new ServerHandler(hostname.getText(), port.getText());
				// Send the login message and get the response
				response = serverHandler.sendMessage(new Message(MessageType.LOGIN, user));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				invalidHostnamePort = true;
			}
			
			// Keep asking the user to input the correct hostname and port of the server until they do or exits
			while(invalidHostnamePort) {
				// Show invalid hostname/port pop-up
				JOptionPane.showMessageDialog(null, "Invalid hostname and/or port!", "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("assets/icons/invalid_host_port_32.png"));
				
				// Ask for credentials again
				loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("assets/icons/login_32.png"), loginChoices, loginChoices[0]);
				
				if (loginChoice == 0) {
					try {
						// Create a ServerHandler to help with repetitive networking serializer/deserializer
						serverHandler = new ServerHandler(hostname.getText(), port.getText());
						
						// We have to recreate the same object if we are sending it to the server multiple times.
						// If we don't do this, we see the same data that was sent initially on the server side despite changing it on the client side.
						user = new User();
						user.setUsername(username.getText());
						user.setPassword(password.getText());
						
						// Send the login message and get the response
						response = serverHandler.sendMessage(new Message(MessageType.LOGIN, user));
						// It got to this point without throwing an exception, it now has a valid hostname and port
						invalidHostnamePort = false;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						invalidHostnamePort = true;
					}
				} else
					return; // They clicked on 'Exit'
			}
			
			// Once we confirm that the server hostname and port is valid, disable the text fields from being changed
//			hostname.setEnabled(false);
//			port.setEnabled(false);

			
			// While login credentials are invalid, keep asking user to login until they click 'Exit'
			if (response.type == MessageType.LOGIN && response.status == MessageStatus.ERROR) {
				// Close the socket. We will create another.
				// We want to give the user the flexibility to change the server hostname and port any time during their login sequence
				serverHandler.closeSocket();
				do {
					if (invalidHostnamePort) {
						// Show invalid hostname/port pop-up
						JOptionPane.showMessageDialog(null, "Invalid hostname and/or port!", "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("assets/icons/invalid_host_port_32.png"));
					} else {
						// Show invalid credentials pop-up
						JOptionPane.showMessageDialog(null, "Invalid username and/or password!", "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("assets/icons/invalid_creds_32.png"));
					}
				
					// Ask for credentials again
					loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("assets/icons/login_32.png"), loginChoices, loginChoices[0]);
					
					// User attempted once again, let's send the new username and password to the server
					if (loginChoice == 0) {
						try {
							// Create a new server handler
							serverHandler = new ServerHandler(hostname.getText(), port.getText());
							
							// It got to this point without throwing an exception, it now has a valid hostname and port
							invalidHostnamePort = false;
							
							// We have to recreate the same object if we are sending it to the server multiple times.
							// If we don't do this, we see the same data that was sent initially on the server side despite changing it on the client side.
							user = new User();
							// Update the user object with the new username/password
							user.setUsername(username.getText());
							user.setPassword(password.getText());
							
							// Send the new login message and get the response
							response = serverHandler.sendMessage(new Message(MessageType.LOGIN, user));
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							invalidHostnamePort = true;
						}
					}
				} while (response.type == MessageType.LOGIN && response.status == MessageStatus.ERROR && loginChoice == 0 || invalidHostnamePort && loginChoice == 0);
				if (loginChoice == 1)
					// User clicked 'Exit'
					return;
			}
			
			// Get the actual user from the database. We created ones earlier as a means to communicate with the server with objects.
			user = response.getUser();
			
			// Generate and render portal
			// ...
			JFrame portal = new JFrame();
			portal.setTitle("College Course Enrollment System - User: " + user.getUsername() + " ID: " + user.getID());
			portal.setIconImage(new ImageIcon("assets/icons/app_32.png").getImage());
			portal.setSize(1000, 800);
			portal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			portal.setLocationRelativeTo(null);
			portal.setVisible(true);
			
		} else if (loginChoice == 1) {
			// User clicked 'Exit'
			return;
		}
	}
}