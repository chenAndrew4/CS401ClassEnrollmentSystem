import java.util.Dictionary;
import java.util.Hashtable;

class LevelType{
	static Dictionary<LevelTypes, String> dict;
	
	public LevelType(){
		dict = new Hashtable<>();
		dict.put(LevelTypes.Upper, "Upper Division");
		dict.put(LevelTypes.Lower, "Lower Division");
	}

}