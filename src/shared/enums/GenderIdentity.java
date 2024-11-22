package shared.enums;

public enum GenderIdentity {
    MALE("Identifies as Male"),
    FEMALE("Identifies as Female"),
    TRANSGENDER("Identifies as Transgender"),
    GENDER_NEUTRAL("Identifies as Gender Neutral"),
    NON_BINARY("Identifies as Non-Binary"),
    AGENDER("Identifies as Agender"),
    PANGENDER("Identifies as Pangender"),
    GENDERQUEER("Identifies as Genderqueer"),
    TWO_SPIRIT("Identifies as Two-Spirit"),
    THIRD_GENDER("Identifies as Third Gender"),
    ALL("Identifies as All Genders"),
    NONE("Does not identify with any gender"),
    COMBINATION("Identifies with a combination of genders");

    private final String description;

    GenderIdentity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
