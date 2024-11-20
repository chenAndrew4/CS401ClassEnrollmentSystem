package shared.models;

import shared.enums.InstructionModeTypes;

import java.util.Dictionary;
import java.util.Hashtable;

class InstructionModeType{
	static Dictionary<InstructionModeTypes, String> dict;
	
	public InstructionModeType(){
		dict = new Hashtable<>();
		dict.put(InstructionModeTypes.InPerson, "In Person");
		dict.put(InstructionModeTypes.Hybrid, "Hybrid");
		dict.put(InstructionModeTypes.Online, "Online");
		dict.put(InstructionModeTypes.OnlineAS, "Online Asynchronous");
	}

}