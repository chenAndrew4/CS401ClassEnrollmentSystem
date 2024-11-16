class Helpers{
	
	public static AccountType stringToAccountType(String str){
		if (str.equals(AccountType.Administrator))
			return AccountType.Administrator;
		if (str.equals(AccountType.Faculty))
			return AccountType.Faculty;
		if (str.equals(AccountType.Student))
			return AccountType.Student;
		
		// If we can't figure out what the account type is, the user will be considered a student for security reasons
		return AccountType.Student;
	}
}