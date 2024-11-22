package shared.enums;

public enum Term {
    FALL_SEMESTER("Fall Semester", "August - December"),
    SPRING_SEMESTER("Spring Semester", "January - May"),
    SUMMER_SEMESTER("Summer Semester", "May - August"),
    FALL_QUARTER("Fall Quarter", "September - December"),
    WINTER_QUARTER("Winter Quarter", "January - March"),
    SPRING_QUARTER("Spring Quarter", "March - June"),
    SUMMER_QUARTER("Summer Quarter", "June - August");

    private final String displayName;
    private final String duration;

    // Constructor
    Term(String displayName, String duration) {
        this.displayName = displayName;
        this.duration = duration;
    }

    // Getter for display name
    public String getDisplayName() {
        return displayName;
    }

    // Getter for duration
    public String getDuration() {
        return duration;
    }

    // Static method to list all terms
    public static void printAllTerms() {
        System.out.println("Available Terms:");
        for (Term term : Term.values()) {
            System.out.printf("%s (%s)%n", term.getDisplayName(), term.getDuration());
        }
    }
}