package shared.enums;

public enum LevelType {
	Upper("Upper Division"),
	Lower("Lower Division");

	private final String description;

	// Constructor
	LevelType(String description) {
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
