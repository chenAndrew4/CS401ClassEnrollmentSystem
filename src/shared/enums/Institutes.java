package shared.enums;

import java.util.List;
import java.util.Map;

public enum Institutes {
    CSUEB(Map.of(
            Campus.HAYWARD, List.of(Location.MEIKLEJOHN_HALL, Location.LIBRARY),
            Campus.CONCORD, List.of(Location.SCIENCE_BUILDING, Location.STUDENT_UNION)
    )),
    SJSU(Map.of(
            Campus.MAIN_CAMPUS, List.of(Location.ENGINEERING_HALL, Location.ARTS_BUILDING),
            Campus.SOUTH_CAMPUS, List.of(Location.SPORTS_COMPLEX)
    )),
    CSUF(Map.of(
            Campus.FULLERTON_MAIN, List.of(Location.BEHAVIORAL_SCIENCES, Location.BUSINESS_HALL)
    ));

    private final Map<Campus, List<Location>> campusLocations;

    Institutes(Map<Campus, List<Location>> campusLocations) {
        this.campusLocations = campusLocations;
    }

    public Map<Campus, List<Location>> getCampusLocations() {
        return campusLocations;
    }
}
