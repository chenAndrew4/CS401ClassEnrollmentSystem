import java.awt.*;

import javax.swing.*;

class Client{
	public static void main(String[] args) {
		// Login Dialog
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
		
		
		loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("assets/icons/login_32.png"), loginChoices, loginChoices[1]);
		
		if (loginChoice == 0) {
			// User clicked 'Login'
			
			// While login credentials are invalid, keep asking user to login until they click 'Exit'
			if (username.getText().compareTo("test") != 0 && password.getText().compareTo("test") != 0) {
				do {
					JOptionPane.showMessageDialog(null, "Invalid username and/or password!", "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("assets/icons/invalid_creds_32.png"));
					loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("assets/icons/login_32.png"), loginChoices, loginChoices[1]);
				} while (username.getText().compareTo("test") != 0 && password.getText().compareTo("test") != 0 && loginChoice == 0);
				if (loginChoice == 1)
					// User clicked 'Exit'
					return;
			}
			
			// Generate and render portal
			// ...
			JFrame portal = new JFrame();
			portal.setTitle("College Course Enrollment System - User: test");
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