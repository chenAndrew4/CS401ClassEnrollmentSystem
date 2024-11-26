package shared.enums;

public enum GradingType {
	Letter("Standard Letter Grading (A-F)"),
	PassFail("Pass/Fail"),
	CreditNoCredit("Credit/No Credit");

	private final String description;

	GradingType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return name() + " (" + description + ")";
	}

	public double calculateGrade(Grade grade) {
		return grade.getPointValue();
	}
}