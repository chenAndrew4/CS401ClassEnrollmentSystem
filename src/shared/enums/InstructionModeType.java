package shared.enums;

public enum InstructionModeType {
	InPerson("In Person"),
	Hybrid("Hybrid"),
	Online("Online"),
	OnlineAS("Online Asynchronous");

	private final String description;

	// Constructor
	InstructionModeType(String description) {
		this.description = description;
	}

	// Getter for description
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return name() + " (" + description + ")";
	}
}
