package server;

import server.dataManagers.UserDataManager;
import server.utils.Log;
import shared.enums.Institutes;
import shared.models.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class UserManager implements Serializable{
	private static UserManager instance;
	private Map<Institutes, Map<String, User>> userMap;
	private Log log;
	private UserDataManager userDataManager;
	private Map<Institutes, Boolean> imported;
	private Map<Institutes, Boolean> modified;

	private UserManager() {}

	public static synchronized UserManager getInstance(Log log, Institutes instituteID) throws IOException {
		if (instance == null) {
			instance = new UserManager(log, instituteID);
			instance.imported.put(instituteID, true);
		}
		return instance;
	}

//	public static synchronized UserManager getInstance() throws IOException {
//		if (instance == null) {
//			System.out.println("call getInstance(Log log, Institutes institutes) first");
//			return null;
//		}
//		return instance;
//	}

	public void isImported(Institutes instituteID) {
		if (!imported.containsKey(instituteID)) {
			userMap.put(instituteID,userDataManager.loadUsersByInstitute(instituteID));
			imported.put(instituteID, true);
		}
//		imported.get(instituteID);
	}

//	public void setImported(Institutes instituteID, boolean imported) {
//		this.imported.put(instituteID, true);
//	}

	private UserManager(Log log, Institutes instituteID) {
		this.userMap = new HashMap<>();
		this.log = log;
		this.userDataManager = UserDataManager.getInstance();
		imported = new HashMap<>();
		modified = new HashMap<>();
		isImported(instituteID);
	}
	
	public boolean addUserByInstitute(Institutes instituteID, User user) {
		isImported(instituteID);
		this.userMap.get(instituteID).put(user.getUsername(), user);
		this.modified.put(instituteID, true);
		return true;
	}
	
	public boolean doesUsernameExistByInstitute(Institutes instituteID, String username) {
//		ListIterator<User> listOfuserMap = this.userMap.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			if (listOfUsers.next().getUsername().equals(username))
//				return true;
//		}
//		return false;
		isImported(instituteID);
		return userMap.get(instituteID).containsKey(username);
	}

	public boolean doesUsernameAndPasswordEqualByInstitute(Institutes instituteID, String username, String password) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			User cur = listOfUsers.next();
//			if (cur.getUsername().equals(username) && cur.getPassword().equals(password)) {
//				return true;
//			}
//		}
		isImported(instituteID);
		Map<String, User> curMap = userMap.get(instituteID);
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
	
	public User getUserByInstitute(Institutes instituteID, String username) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			User user = listOfUsers.next();
//			if (user.getUsername().equals(username))
//				return user;
//		}
//
//		return new User();
		isImported(instituteID);
		return userMap.get(instituteID).get(username);
	}
	
	public List<User> getUsersByInstitution(Institutes instituteID){
		List<User> list = new ArrayList<User>();
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			User user = listOfUsers.next();
//			if (user.getInstitutionID().equals(institutionID))
//				list.add(user);
//		}
		
//		return list;
		isImported(instituteID);
		return new ArrayList<>(userMap.get(instituteID).values());
	}
	
	// This method is used to add a new user. You need to check if the username wanting to be associated with a user is already being used by another user.
	// This will return false if username already exists, otherwise true.
	public boolean isUsernameValidByInstitute(Institutes instituteID, String username) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			if (listOfUsers.next().getUsername().equals(username))
//				return false;
//		}
		
//		return true;
		isImported(instituteID);
		return !userMap.get(instituteID).containsKey(username);
	}
	
	public boolean removeUserByInstitute(Institutes instituteID, String username) {
//		ListIterator<User> listOfUsers = this.users.listIterator();
//
//		while(listOfUsers.hasNext()) {
//			if (listOfUsers.next().getUsername().equals(username))
//				this.users.remove(listOfUsers.nextIndex());
//		}
		isImported(instituteID);
		if (this.userMap.containsKey(instituteID)){
			Map<String, User> curMap =  userMap.get(instituteID);
			if (curMap.containsKey(username)) {
				curMap.remove(username);
				this.modified.put(instituteID, true);
				return true;
			}
		}
		return false;
	}
	
	public void updateUserByInstitutes(Institutes instituteID, User user) {
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
		isImported(instituteID);
		if (this.userMap.containsKey(instituteID)){
			Map<String, User> curMap =  userMap.get(instituteID);
			if (curMap.containsKey(user.getUsername())) {
				curMap.put(user.getUsername(), user);
				this.modified.put(instituteID, true);
			}
		}
	}
	
	public void importDBByInstitute(Institutes instituteID) {
			isImported(instituteID);
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
//				user.setInstitutionID(Institutes.valueOf(parsed[5]));
//				user.setAccountType(Helpers.stringToAccountType(parsed[6]));
//
//				this.log.println("Adding user: " + user.getUsername());
//				this.users.put(user.getUsername(), user);
//			}
//
//		} catch(FileNotFoundException ex){
//		}
	}
	
	public void commitDBByInstitute(Institutes instituteID) {
		isImported(instituteID);
		if (modified.containsKey(instituteID) && modified.get(instituteID)) {
			userDataManager.saveUsersByInstitute(instituteID, userMap.get(instituteID));
		}
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