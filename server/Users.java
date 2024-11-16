import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Users{
	private ArrayList<User> users;
	private String dbFileNamePath;
	private Log log;
	
	public Users(Log log) {
		this.users = new ArrayList<User>();
		this.dbFileNamePath = "./db/users.db";
		this.log = log;
	}
	
	public boolean addUser(User user) {
		if(!doesUsernameExist(user.getUsername()) && !doesIdExist(user.getID())) {
			this.users.add(user);
			return true;
		}
		return false;
	}
	
	public boolean doesUsernameExist(String username) {
		ListIterator<User> listOfUsers = this.users.listIterator();
		
		while(listOfUsers.hasNext()) {
			if (listOfUsers.next().getUsername().equals(username))
				return true;
		}
		
		return false;
	}
	
	public boolean doesIdExist(Integer id) {
		ListIterator<User> listOfUsers = this.users.listIterator();
		
		while(listOfUsers.hasNext()) {
			if (listOfUsers.next().getID().equals(id))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<User> getUsersByInstitution(String institutionID){
		ArrayList<User> list = new ArrayList<User>();
		ListIterator<User> listOfUsers = this.users.listIterator();
		
		while(listOfUsers.hasNext()) {
			User user = listOfUsers.next();
			if (user.getInstitutionID().equals(institutionID))
				list.add(user);
		}
		
		return list;
	}
	
	// This method is used to add a new user. You need to check if the username wanting to be associated with a user is already being used by another user.
	// This will return false if username already exists, otherwise true.
	public boolean isUsernameValid(String username) {
		ListIterator<User> listOfUsers = this.users.listIterator();

		while(listOfUsers.hasNext()) {
			if (listOfUsers.next().getUsername().equals(username))
				return false;
		}
		
		return true;
	}
	
	public void removeUser(String username) {
		ListIterator<User> listOfUsers = this.users.listIterator();

		while(listOfUsers.hasNext()) {
			if (listOfUsers.next().getUsername().equals(username))
				this.users.remove(listOfUsers.nextIndex());
		}
	}
	
	public boolean isValidLogin(String username, String password) {
		ListIterator<User> listOfUsers = this.users.listIterator();

		while(listOfUsers.hasNext()) {
			User user = listOfUsers.next();
			if (user.getUsername().equals(username))
				if (user.getPassword().equals(password))
					return true;
		}
		
		return false;
	}
	
	public void editUser(User user) {
		ListIterator<User> listOfUsers = this.users.listIterator();

		while(listOfUsers.hasNext()) {
			User userIt = listOfUsers.next();
			if (userIt.getID().equals(user.getID())) {
				userIt.setUsername(user.getUsername());
				userIt.setPassword(user.getPassword());
				userIt.setFirstName(user.getFirstName());
				userIt.setLastName(user.getLastName());
				userIt.setInstitutionID(user.getInstitutionID());
				userIt.setAccountType(user.getAccountType());
				
			}
		}
	}
	
	public void importDB() {
		try {
			// Open file by creating a handle
			File file = new File(dbFileNamePath);
			// Check to see if the file exists
			if (!file.exists()) {
				// Create an empty file if it doesn't exist
				try {
					file.createNewFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
				
			// Scanner to get line by line
			Scanner scanner = new Scanner(new File(dbFileNamePath));
			// For error msg on which line it detected corruption
			int line = 1;
			while (scanner.hasNextLine()) {
				String[] parsed = scanner.nextLine().split("[,]", 0);
				// There is always a new line. It doesn't mean the file is corrupted.
				if (parsed.length == 1 && parsed[0] == "") {
					break;
				}
				// It's corrupted at this point. So stop.
				if(parsed.length != 7) {
					this.log.error("Corrupted Users.db file detected at line: " + line);
					return;
				}
				line++;
				
				// Insert into array
				// private Integer id; // Column 1 in users.db
				// private String username; // Column 2 in users.db
				// private String password; // Column 3 in users.db
				// private String firstName; // Column 4 in users.db
				// private String lastName; // Column 5 in users.db
				// private String institutionID; // Column 6 in users.db
				// private AccountType accountType; // Column 7 in users.db
				
				User user = new User();
				user.setID(Integer.parseInt(parsed[0]));
				user.setUsername(parsed[1]);
				user.setPassword(parsed[2]);
				user.setFirstName(parsed[3]);
				user.setLastName(parsed[4]);
				user.setInstitutionID(parsed[5]);
				user.setAccountType(Helpers.stringToAccountType(parsed[6]));
				
				this.log.println("Adding user: " + user.getUsername());
				this.users.add(user);
			}
			
		} catch(FileNotFoundException ex){
		}
	}
	
	public void commitDB() {
		try {
			PrintWriter writer = new PrintWriter(this.dbFileNamePath);
			ListIterator<User> listOfUsers = this.users.listIterator();
			
			// Clear the file
			while(listOfUsers.hasNext()) {
				writer.println(listOfUsers.next().toString());
			}
			
			writer.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
}