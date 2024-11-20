package shared.models;

import shared.enums.AcademicProgramTypes;

import java.util.Dictionary;
import java.util.Hashtable;

class AcademicProgramType{
	static Dictionary<AcademicProgramTypes, String> dict;
	
	public AcademicProgramType(){
		dict = new Hashtable<>();
		dict.put(AcademicProgramTypes.UGM, "Undergraduate Matriculated");
		dict.put(AcademicProgramTypes.NM, "Non-Matriculated");
	}

}