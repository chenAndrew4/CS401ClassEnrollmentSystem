import java.io.Serializable;
import java.util.Random;

public class User implements Serializable{
	private Integer id; // Column 1 in users.db
	private String username; // Column 2 in users.db
	private String password; // Column 3 in users.db
	private String firstName; // Column 4 in users.db
	private String lastName; // Column 5 in users.db
	private String institutionID; // Column 6 in users.db
	private AccountType accountType; // Column 7 in users.db
	
	public User() {
		Random random = new Random();
		this.id = random.nextInt(100000);
		this.username = null;
		this.password = null;
		this.firstName = null;
		this.lastName = null;
		this.institutionID = null;
		this.accountType = null;
	}
	
	public Integer getID() {
		return this.id;
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
	
	public void setID(Integer id) {
		this.id = id;
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
	
	public String toString() {
		return this.id.toString() + "," + this.username + "," + this.password + "," + this.firstName + "," + this.lastName + "," + this.institutionID + "," + this.accountType;
	}
}