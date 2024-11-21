package shared.enums;

public enum AcademicProgramType {
	UGM("Undergraduate Matriculated"),
	NM("Non-Matriculated");

	private final String description;

	// Constructor
	AcademicProgramType(String description) {
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