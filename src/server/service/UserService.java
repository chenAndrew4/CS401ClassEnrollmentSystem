package server.service;

import server.dataManagers.UserDataManager;
import server.gui.ServerGUI;
import server.utils.Log;
import shared.enums.Institutions;
import shared.models.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class UserService implements Serializable{
	private static Log log;
	private static UserService instance;
	private UserDataManager userDataManager;

	private UserService() {
		userDataManager = UserDataManager.getInstance();
	}

	public static synchronized UserService getInstance() throws IOException {
		if (instance == null) {
			instance = new UserService();
		}
		log = Log.getInstance(ServerGUI.logTextArea);
		return instance;
	}

	public boolean addUserByInstitution(Institutions institutionID, User user) {
		return userDataManager.addUserByInstitution(institutionID, user);
	}
	
	public boolean doesUsernameExistByInstitution(Institutions institutionID, String username) {
		User user = userDataManager.getUserByInstitutionAndUserName(institutionID, username);
		if (user == null)
			return false;
		return true;
	}

	public boolean doesUsernameAndPasswordEqualByInstitution(Institutions institutionID, String username, String password) {
		Map<String, User> curMap = userDataManager.getUsersByInstitution(institutionID);
		for (User u : curMap.values()) {
			if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	public User getUserByInstitutionAndUsername(Institutions institutionID, String username) {
		return userDataManager.getUserByInstitutionAndUserName(institutionID, username);
	}

	public List<User> getUsersByInstitutionAndUserIDs(Institutions institutionID, List<String> userIDs) {
		List<User> result = new ArrayList<>();
		for (String id : userIDs) {
			result.add(userDataManager.getUserByInstitution(institutionID, id));
		}
		return result;
	}
	
	public List<User> getUsersByInstitution(Institutions institutionID){
		return new ArrayList<>(userDataManager.getUsersByInstitution(institutionID).values());
	}
	
	// This method is used to add a new user. You need to check if the username wanting to be associated with a user is already being used by another user.
	// This will return false if username already exists, otherwise true.
	public boolean isUsernameValidByInstitution(Institutions institutionID, String username) {
		return userDataManager.getUserByInstitution(institutionID, username) != null;
	}
	
	public boolean removeUserByInstitution(Institutions institutionID, String username) {
		return userDataManager.removeUserByInstitution(institutionID, username);
	}
	
	public boolean updateUserByInstitutions(Institutions institutionID, User user) {
		return userDataManager.updateUserByInstitutions(institutionID, user);
	}

	public void commitDBByInstitution(Institutions institutionID) {
		userDataManager.commitDBByInstitution(institutionID);
	}

	public void commitDBALl() {
		userDataManager.saveAllUsers();
	}
	
}