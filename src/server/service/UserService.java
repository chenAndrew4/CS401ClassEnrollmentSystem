package server.service;

import server.dataManagers.UserDataManager;
import server.utils.Log;
import shared.enums.Institutions;
import shared.models.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class UserService implements Serializable{
	private static UserService instance;
	private Log log;
	private UserDataManager userDataManager;

	private UserService() {}

	public static synchronized UserService getInstance(Log log, Institutions institutionID) throws IOException {
		if (instance == null) {
			instance = new UserService(log, institutionID);
		}
		return instance;
	}

	private UserService(Log log, Institutions institutionID) {
		this.log = log;
		userDataManager = UserDataManager.getInstance();
	}
	
	public boolean addUserByInstitution(Institutions institutionID, User user) {
		return userDataManager.addUserByInstitution(institutionID, user);
	}
	
	public boolean doesUsernameExistByInstitution(Institutions institutionID, String username) {
		return userDataManager.getUserByInstitutionAndUserName(institutionID, username).getUsername().equals(username);
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
	
	public void updateUserByInstitutions(Institutions institutionID, User user) {
		userDataManager.updateUserByInstitutions(institutionID, user);
	}
	
	public void commitDBByInstitution(Institutions institutionID) {
		userDataManager.commitDBByInstitution(institutionID);
	}

	public void commitDBALl() {
		userDataManager.saveAllUsers();
	}
	
}