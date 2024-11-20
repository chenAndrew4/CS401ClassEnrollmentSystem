package shared.models;

import shared.enums.AccountType;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class User implements Serializable{
	private String userId; // Column 1 in users.db
	private String username; // Column 2 in users.db
	private String password; // Column 3 in users.db
	private String firstName; // Column 4 in users.db
	private String lastName; // Column 5 in users.db
	private String institutionID; // Column 6 in users.db
	private AccountType accountType; // Column 7 in users.db
	private String sessionToken;
	private boolean isAuthenicated;
	
	public User() {
		Random random = new Random();
		this.userId = UUID.randomUUID().toString();
		this.username = null;
		this.password = null;
		this.firstName = null;
		this.lastName = null;
		this.institutionID = null;
		this.accountType = null;
		this.sessionToken = null;
		this.isAuthenicated = false;
	}

	public boolean isAuthenicated() {
		return isAuthenicated;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getSessionToken() {
		return sessionToken;
	}
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getInstitutionID() {
		return this.institutionID;
	}
	
	public AccountType getAccountType() {
		return this.accountType;
	}
	
	public void setUserId(String id) {
		this.userId = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	public void setInstitutionID(String institutionID) {
		this.institutionID = institutionID;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public void setAuthenicated(boolean authenicated) {
		isAuthenicated = authenicated;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(institutionID, user.institutionID) && accountType == user.accountType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, username, password, firstName, lastName, institutionID, accountType);
	}

	public String toString() {
		return this.userId.toString() + "," + this.username + "," + this.firstName + "," + this.lastName + "," + this.institutionID + "," + this.accountType;
	}
}