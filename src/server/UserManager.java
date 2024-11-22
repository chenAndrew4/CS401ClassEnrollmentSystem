package server;

import server.dataManagers.UserDataManager;
import server.utils.Log;
import shared.enums.Institutions;
import shared.models.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class UserManager implements Serializable{
	private static UserManager instance;
	private Map<Institutions, Map<String, User>> userMap;
	private Log log;
	private UserDataManager userDataManager;
	private Map<Institutions, Boolean> imported;
	private Map<Institutions, Boolean> modified;

	private UserManager() {}

	public static synchronized UserManager getInstance(Log log, Institutions institutionID) throws IOException {
		if (instance == null) {
			instance = new UserManager(log, institutionID);
			instance.imported.put(institutionID, true);
		}
		return instance;
	}

//	public static synchronized UserManager getInstance() throws IOException {
//		if (instance == null) {
//			System.out.println("call getInstance(Log log, Institutions institution) first");
//			return null;
//		}
//		return instance;
//	}

	public void isImported(Institutions institutionID) {
//		if (!imported.containsKey(institutionID)) {
//			userMap.put(institutionID,userDataManager.getUsersByInstitution(institutionID));
//			imported.put(institutionID, true);
//		}
//		imported.get(institutionID);
	}

//	public void setImported(Institutions institutionID, boolean imported) {
//		this.imported.put(institutionID, true);
//	}

	private UserManager(Log log, Institutions institutionID) {
		this.userMap = new HashMap<>();
		this.log = log;
		this.userDataManager = UserDataManager.getInstance();
		imported = new HashMap<>();
		modified = new HashMap<>();
		isImported(institutionID);
	}
	
	public boolean addUserByInstitution(Institutions institutionID, User user) {
		isImported(institutionID);
		this.userMap.get(institutionID).put(user.getUsername(), user);
		this.modified.put(institutionID, true);
		return true;
	}
	
	public boolean doesUsernameExistByInstitution(Institutions institutionID, String username) {
//		ListIterator<User> listOfuserMap = this.userMap.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			if (listOfUsers.next().getUsername().equals(username))
//				return true;
//		}
//		return false;
		isImported(institutionID);
		return userMap.get(institutionID).containsKey(username);
	}

	public boolean doesUsernameAndPasswordEqualByInstitution(Institutions institutionID, String username, String password) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			User cur = listOfUsers.next();
//			if (cur.getUsername().equals(username) && cur.getPassword().equals(password)) {
//				return true;
//			}
//		}
		isImported(institutionID);
		Map<String, User> curMap = userMap.get(institutionID);
		return curMap.containsKey(username)&&curMap.get(username).getPassword().equals(password);
	}
	
//	public boolean doesUserIdExist(String id) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			if (listOfUsers.next().getUserId().equals(id))
//				return true;
//		}
//		return users.containsKey(username)&&users.get(username).getPassword().equals(password);
//	}
	
	public User getUserByInstitution(Institutions institutionID, String username) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			User user = listOfUsers.next();
//			if (user.getUsername().equals(username))
//				return user;
//		}
//
//		return new User();
		isImported(institutionID);
		return userMap.get(institutionID).get(username);
	}
	
	public List<User> getUsersByInstitution(Institutions institutionID){
		List<User> list = new ArrayList<User>();
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			User user = listOfUsers.next();
//			if (user.getInstitutionID().equals(institutionID))
//				list.add(user);
//		}
		
//		return list;
		isImported(institutionID);
		return new ArrayList<>(userMap.get(institutionID).values());
	}
	
	// This method is used to add a new user. You need to check if the username wanting to be associated with a user is already being used by another user.
	// This will return false if username already exists, otherwise true.
	public boolean isUsernameValidByInstitution(Institutions institutionID, String username) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			if (listOfUsers.next().getUsername().equals(username))
//				return false;
//		}
		
//		return true;
		isImported(institutionID);
		return !userMap.get(institutionID).containsKey(username);
	}
	
	public boolean removeUserByInstitution(Institutions institutionID, String username) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			if (listOfUsers.next().getUsername().equals(username))
//				this.users.remove(listOfUsers.nextIndex());
//		}
		isImported(institutionID);
		if (this.userMap.containsKey(institutionID)){
			Map<String, User> curMap =  userMap.get(institutionID);
			if (curMap.containsKey(username)) {
				curMap.remove(username);
				this.modified.put(institutionID, true);
				return true;
			}
		}
		return false;
	}
	
	public void updateUserByInstitutions(Institutions institutionID, User user) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			User userIt = listOfUsers.next();
//			if (userIt.getUserId().equals(user.getUserId())) {
//				userIt.setUsername(user.getUsername());
//				userIt.setPassword(user.getPassword());
//				userIt.setFirstName(user.getFirstName());
//				userIt.setLastName(user.getLastName());
//				userIt.setInstitutionID(user.getInstitutionID());
//				userIt.setAccountType(user.getAccountType());
//
//			}
//		}
		isImported(institutionID);
		if (this.userMap.containsKey(institutionID)){
			Map<String, User> curMap =  userMap.get(institutionID);
			if (curMap.containsKey(user.getUsername())) {
				curMap.put(user.getUsername(), user);
				this.modified.put(institutionID, true);
			}
		}
	}
	
	public void importDBByInstitution(Institutions institutionID) {
			isImported(institutionID);
//		try {
//			// Open file by creating a handle
//			File file = new File(dbFileNamePath);
//			// Check to see if the file exists
//			if (!file.exists()) {
//				// Create an empty file if it doesn't exist
//				try {
//					file.createNewFile();
//				} catch (IOException ex) {
//					ex.printStackTrace();
//				}
//			}
//
//			// Scanner to get line by line
//			Scanner scanner = new Scanner(new File(dbFileNamePath));
//			// For error msg on which line it detected corruption
//			int line = 1;
//			while (scanner.hasNextLine()) {
//				String[] parsed = scanner.nextLine().split("[,]", 0);
//				// There is always a new line. It doesn't mean the file is corrupted.
//				if (parsed.length == 1 && parsed[0] == "") {
//					break;
//				}
//				// It's corrupted at this point. So stop.
//				if(parsed.length != 7) {
//					this.log.error("Corrupted Users.db file detected at line: " + line);
//					return;
//				}
//				line++;
//
//				// Insert into array
//				// private Integer id; // Column 1 in users.db
//				// private String username; // Column 2 in users.db
//				// private String password; // Column 3 in users.db
//				// private String firstName; // Column 4 in users.db
//				// private String lastName; // Column 5 in users.db
//				// private String institutionID; // Column 6 in users.db
//				// private AccountType accountType; // Column 7 in users.db
//
//				User user = new User();
//				user.setUserId(parsed[0]);
//				user.setUsername(parsed[1]);
//				user.setPassword(parsed[2]);
//				user.setFirstName(parsed[3]);
//				user.setLastName(parsed[4]);
//				user.setInstitutionID(Institutions.valueOf(parsed[5]));
//				user.setAccountType(Helpers.stringToAccountType(parsed[6]));
//
//				this.log.println("Adding user: " + user.getUsername());
//				this.users.put(user.getUsername(), user);
//			}
//
//		} catch(FileNotFoundException ex){
//		}
	}
	
	public void commitDBByInstitution(Institutions institutionID) {
//		isImported(institutionID);
//		if (modified.containsKey(institutionID) && modified.get(institutionID)) {
//			userDataManager.saveUsersByInstitution(institutionID, userMap.get(institutionID));
//		}
//		try {
//			PrintWriter writer = new PrintWriter();
////			ListIterator<User> listOfUsers = this.users.listIterator();
//
//			// Clear the file
////			while(listOfUsers.hasNext()) {
////				writer.println(listOfUsers.next().toString());
////			}
//			for (User u : userMap.values()) {
//				writer.println(u.toString());
//			}
//
//			writer.close();
//		} catch (FileNotFoundException ex) {
//			ex.printStackTrace();
//		}
	}
	
}