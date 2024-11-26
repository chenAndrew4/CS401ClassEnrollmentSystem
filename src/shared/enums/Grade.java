package shared.enums;

public enum Grade {
    // (point Value, grading type)
    A(4.0, GradingType.Letter),
    A_MINUS(3.7, GradingType.Letter),
    B_PLUS(3.3, GradingType.Letter),
    B(3.0, GradingType.Letter),
    B_MINUS(2.7, GradingType.Letter),
    C_PLUS(2.3, GradingType.Letter),
    C(2.0, GradingType.Letter),
    C_MINUS(1.7, GradingType.Letter),
    D_PLUS(1.3, GradingType.Letter),
    D(1.0, GradingType.Letter),
    F(0.0, GradingType.Letter),

    PASS(4.0, GradingType.PassFail),
    FAIL(0.0, GradingType.PassFail),

    CREDIT(4.0, GradingType.CreditNoCredit),
    NO_CREDIT(0.0, GradingType.CreditNoCredit);

    private final double pointValue;
    private final GradingType gradingType;

    Grade(double pointValue, GradingType gradingType) {
        this.pointValue = pointValue;
        this.gradingType = gradingType;
    }

    public double getPointValue() {
        return pointValue;
    }

    public GradingType getGradingType() {
        return gradingType;
    }

    @Override
    public String toString() {
        return name() + " (" + pointValue + ")";
    }
}
