import java.util.ArrayList;

public class Users{
	private ArrayList<User> users;
	
	public Users() {
		users = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public boolean doesUsernameExist(User user) {
		boolean rtn = false;
		
		return rtn;
	}
	
	public ArrayList<User> getUsersByInstitution(String institutionID){
		return new ArrayList<User>();
	}
	
	public boolean isUsernameValid(String username) {
		boolean rtn = false;
		return rtn;
	}
	
	public void removeUser(String username) {
		
	}
	
	public boolean isValidLogin(String username, String password) {
		boolean rtn = false;
		
		
		
		return rtn;
	}
}