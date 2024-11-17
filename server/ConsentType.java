import java.util.Dictionary;
import java.util.Hashtable;

class ConsentType{
	static Dictionary<ConsentTypes, String> dict;
	
	public ConsentType(){
		dict = new Hashtable<>();
		dict.put(ConsentTypes.Deptartment, "Requires Department Consent");
		dict.put(ConsentTypes.None, "No Consent Required");
	}

}