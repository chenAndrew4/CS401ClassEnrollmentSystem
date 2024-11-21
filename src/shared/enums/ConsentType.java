package shared.enums;

public enum ConsentType {
	Department("Requires Department Consent"),
	None("No Consent Required");

	private final String description;

	// Constructor
	ConsentType(String description) {
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