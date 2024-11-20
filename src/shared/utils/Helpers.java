package shared.utils;

import shared.enums.AccountType;

public class Helpers{
	public static AccountType stringToAccountType(String str){
		try {
			return AccountType.valueOf(str.trim());
		} catch (IllegalArgumentException | NullPointerException e) {
			System.err.println(e.getMessage());
		}
        return null;
    }
}