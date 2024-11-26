package shared.enums;

public enum AcademicProgramType {
	UGM("Undergraduate Matriculated"),
	NM("Non-Matriculated");

	private final String description;

	AcademicProgramType(String description) {
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