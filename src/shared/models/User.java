package shared.models;

import shared.enums.AccountType;
import shared.enums.Department;
import shared.enums.GenderIdentity;
import shared.enums.Institutions;
import shared.utils.IDGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class User implements Serializable{
	private String userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private Institutions institutionID;
	private Department department;
	private AccountType accountType;
	private String sessionToken;
	private boolean isAuthenticated;
	private Date date;  // Admission date
	private GenderIdentity genderIdentity; // Gender identity

	public User(Institutions institutionID) {
		this.userId = IDGenerator.getInstance().generateUniqueUserID(institutionID);
		this.username = null;
		this.password = null;
		this.firstName = null;
		this.lastName = null;
		this.institutionID = institutionID;
		this.department = null;
		this.accountType = null;
		this.sessionToken = null;
		this.isAuthenticated = false;
		this.date = new Date();
		this.genderIdentity = GenderIdentity.NONE;
	}

	// Copy Constructor
	public User(User source) {
		this.userId = source.userId;
		this.username = source.username;
		this.password = source.password; // Consider encrypting or masking passwords for safety.
		this.firstName = source.firstName;
		this.lastName = source.lastName;
		this.institutionID = source.institutionID; // Assuming Institutions is immutable or handles its own deep copy.
		this.department = source.department; // Assuming Department is immutable or handles its own deep copy.
		this.accountType = source.accountType; // Assuming AccountType is immutable.
		this.sessionToken = source.sessionToken;
		this.isAuthenticated = source.isAuthenticated;
		this.date = source.date != null ? new Date(source.date.getTime()) : null; // Defensive copy for mutable Date.
		this.genderIdentity = source.genderIdentity; // Assuming GenderIdentity is immutable.
	}

	public User(String username, String firstName, String lastName, String password,
				Institutions institution, Department department, AccountType accountType,
				GenderIdentity genderIdentity) {
		this.userId = IDGenerator.getInstance().generateUniqueUserID(institutionID);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.institutionID = institution;
		this.department = department;
		this.accountType = accountType;
		this.sessionToken = null;
		this.isAuthenticated = false;
		this.date = new Date();
		this.genderIdentity = genderIdentity;
	}

	public boolean isAuthenticated() {
		return this.isAuthenticated;
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
	
	public Institutions getInstitutionID() {
		return this.institutionID;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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
	
	public void setInstitutionID(Institutions institutionID) {
		this.institutionID = institutionID;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public void setAuthenticated(boolean authenticated) {
		isAuthenticated = authenticated;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public GenderIdentity getGenderIdentity() {
		return genderIdentity;
	}

	public void setGenderIdentity(GenderIdentity genderIdentity) {
		this.genderIdentity = genderIdentity;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) &&
				Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) &&
				Objects.equals(lastName, user.lastName) && Objects.equals(institutionID, user.institutionID) &&
				department == user.department && accountType == user.accountType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, username, password, firstName, lastName, institutionID, accountType, department);
	}

	@Override
	public String toString() {
		return "User{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", institutionID=" + institutionID +
				", department=" + department +
				", accountType=" + accountType +
				", genderIdentity=" + genderIdentity +
				", username='" + username + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}