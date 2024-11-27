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
	private String address;
	private String phone;
	private Department department;
	private AccountType accountType;
	private String sessionToken;
	private boolean isAuthenticated;
	private Date date;  // Admission date
	private GenderIdentity genderIdentity;

	public User(Institutions institutionID) {
		this.userId = IDGenerator.getInstance().generateUniqueUserID(institutionID);
		this.username = null;
		this.password = null;
		this.firstName = null;
		this.lastName = null;
		this.institutionID = institutionID;
		this.address = null;
		this.phone = null;
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
		this.password = source.password;
		this.firstName = source.firstName;
		this.lastName = source.lastName;
		this.institutionID = source.institutionID;
		this.phone = source.phone;
		this.address = source.address;
		this.department = source.department;
		this.accountType = source.accountType;
		this.sessionToken = source.sessionToken;
		this.isAuthenticated = source.isAuthenticated;
		this.date = source.date != null ? new Date(source.date.getTime()) : null;
		this.genderIdentity = source.genderIdentity;
	}

	public User(String username, String firstName, String lastName, String password,
				Institutions institution, Department department, AccountType accountType,
				GenderIdentity genderIdentity, String phone, String address) {
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
		this.phone = phone;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userId, user.userId) && Objects.equals(username, user.username) &&
				Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) &&
				Objects.equals(lastName, user.lastName) && Objects.equals(institutionID, user.institutionID) &&
				department == user.department && accountType == user.accountType && Objects.equals(phone, user.phone) && Objects.equals(address, user.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, username, password, firstName, lastName, institutionID, accountType, department, phone, address);
	}

	@Override
	public String toString() {
		return "User {userId=" + userId + ", username=" + username + ", firstName=" + firstName + ", lastName="
				+ lastName + ", institutionID=" + institutionID + ", address=" + address + ", phone=" + phone
				+ ", department=" + department + ", accountType=" + accountType + ", admission date=" + date + ", genderIdentity="
				+ genderIdentity + "}";
	}


}