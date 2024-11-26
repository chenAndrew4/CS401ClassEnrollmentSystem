package shared.enums;

public enum InstructionModeType {
	InPerson("In Person"),
	Hybrid("Hybrid"),
	Online("Online"),
	OnlineAS("Online Asynchronous");

	private final String description;

	InstructionModeType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return name() + " (" + description + ")";
	}
}
