package server;

import java.awt.HeadlessException;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public final class ServerManager{
	private String ipAddress;
	private Integer port;
	private static Integer sessionTimeout;
	private String serverConfigIni;

	// Paths to various data files
	public static final String SERVER_CONFIG_FILE_PATH = "resources"+File.separator+"config.ini";
	public static final String DB_FILE_PATH_PREFIX = "resources"+File.separator+"database" + File.separator;
	public static final String USERS_DB_FILE_PATH_SUFFIX = "users.db";
//	public static final String USERS_DB_FILE_PATH = "resources/database/users.db";
	public static final String COURSES_DB_FILE_PATH_SUFFIX = "courses.db";
//	public static final String COURSES_DB_FILE_PATH = "resources/database/courses.db";
	public static final String SCHEDULES_DB_FILE_PATH_SUFFIX = "schedules.db";
	public static final String WAITLISTS_DB_FILE_PATH_SUFFIX = "waitlists.db";
//	public static final String SESSION_FILE_PATH_SUFFIX = "sessions.db";
//	public static final String LOGS_FILE_PATH_SUFFIX = "server.log";
//	public static final String SCHEDULES_DB_FILE_PATH = "resources/database/schedules.db";
//	public static final String WAITLISTS_DB_FILE_PATH = "resources/database/waitlists.db";
	public static final String SESSION_FILE_PATH = "resources"+File.separator+"database"+File.separator+"sessions.db";
	public static final String LOGS_FILE_PATH_SUFFIX = "serverLog.log";
	public static final String SERVER_ICON_FILE_PATH = "src"+File.separator+"server"+File.separator+"assets"+File.separator+"icons"+File.separator+"server_32.png";


	public ServerManager() {
		serverConfigIni = SERVER_CONFIG_FILE_PATH;
		
		if (!doesServerConfigExist())
			createServerConfig();
		
		readServerConfig();
	}
	
	private boolean doesServerConfigExist() {
		return Files.exists(Path.of(serverConfigIni));
	}
	
	private void createServerConfig() {
		String defaultServerConfig = "ipAddress=0.0.0.0\r\nport=25800\r\nsessionTimeout=30\r\n";
		try {
			PrintWriter configFile = new PrintWriter(serverConfigIni);
			configFile.print(defaultServerConfig);
			configFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readServerConfig() {
		try (// Scanner to get line by line
		Scanner scanner = new Scanner(new File(serverConfigIni))) {	
			// For error msg on which line it detected corruption
			int line = 1;
			while (scanner.hasNextLine()) {
				String[] parsed = scanner.nextLine().split("[=]", 0);
				// There is always a new line. It doesn't mean the file is corrupted.
				if (parsed.length == 1 && parsed[0] == "") {
					break;
				}
				// It's corrupted at this point. So stop.
				if(parsed.length != 2) {
					JOptionPane.showMessageDialog(null, "Corrupted config file", "Corrupted " + serverConfigIni + " detected at line: " + line, JOptionPane.ERROR_MESSAGE);
					return;
				}
				line++;

				// Load into our object
				if (parsed[0].contentEquals("ipAddress"))
					ipAddress = parsed[1];
				if (parsed[0].contentEquals("port"))
					port = Integer.parseInt(parsed[1]);
				if (parsed[0].contentEquals("sessionTimeout"))
					sessionTimeout = Integer.parseInt(parsed[1]);
			}
			
			scanner.close();
			
//			System.out.println("ipAddress = " + ipAddress);
//			System.out.println("port = " + port);
//			System.out.println("sessionTimeout = " + sessionTimeout);
			
		} catch(FileNotFoundException ex){
			JOptionPane.showMessageDialog(null, "File Not Found", ex.toString(), JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveServerConfig() {
		try {
			PrintWriter writer = new PrintWriter(serverConfigIni);
			writer.println("ipAddress=" + ipAddress);
			writer.println("port=" + port);
			writer.println("sessionTimeout=" + sessionTimeout);
			writer.close();
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "File Not Found", ex.toString(), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public InetAddress getIpAddress() throws UnknownHostException {
		try {
			return InetAddress.getByName(this.ipAddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getPort() {
		return port;
	}
	
	public static Integer getSessionTimeout() {
		return sessionTimeout;
	}
	
	public void setIpAddress(String ip) throws java.lang.Exception {
		if (! ServerManager.ipv4Pattern.matcher(ip).matches())
			throw new Exception("This is not a valid IPv4 address!");
		else {
			ipAddress = ip;
			saveServerConfig();
		}
	}
	
	public void setPort(Integer number) throws java.lang.Exception {
		if (number > 0 || number <= 65535)
			throw new Exception("Port must be greater than 0 but less than 65,535!");
		else {
			port = number;
			saveServerConfig();
		}
	}
	
	public void setSessionTimeout(Integer minutes) throws java.lang.Exception {
		if (minutes < 0)
			throw new Exception("Timeout must be grater than 0!");
		else {
			sessionTimeout = minutes;
			saveServerConfig();
		}
	}
	
	private static final Pattern ipv4Pattern = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
}