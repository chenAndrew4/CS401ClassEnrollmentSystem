package shared.enums;

public enum Department {
    CS("Computer Science"),
    MATH("Mathematics"),
    PHYS("Physics"),
    CHEM("Chemistry"),
    BIOL("Biology"),
    ENGR("Engineering"),
    BUS("Business Administration"),
    ECON("Economics"),
    PSYC("Psychology"),
    LIT("Literature"),
    HIST("History"),
    PHIL("Philosophy"),
    ART("Art"),
    MUS("Music"),
    EDUC("Education"),
    HLTH("Health Sciences"),
    ENV("Environmental Science"),
    POLS("Political Science"),
    SOC("Sociology"),
    ANTH("Anthropology");

    private final String fullName;

    // Constructor
    Department(String fullName) {
        this.fullName = fullName;
    }

    // Getter for full name
    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return name() + " (" + fullName + ")";
    }
}
