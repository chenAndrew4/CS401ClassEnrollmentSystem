package shared.enums;

import java.util.List;
import java.util.Map;

public enum Institutions {
    CSUEB(Map.of(
            Campus.HAYWARD, List.of(Building.MEIKLEJOHN_HALL, Building.LIBRARY),
            Campus.CONCORD, List.of(Building.SCIENCE_BUILDING, Building.STUDENT_UNION)
    )),
    SJSU(Map.of(
            Campus.MAIN_CAMPUS, List.of(Building.ENGINEERING_HALL, Building.ARTS_BUILDING),
            Campus.SOUTH_CAMPUS, List.of(Building.SPORTS_COMPLEX)
    )),
    CSUF(Map.of(
            Campus.FULLERTON_MAIN, List.of(Building.BEHAVIORAL_SCIENCES, Building.BUSINESS_HALL)
    ));

    private final Map<Campus, List<Building>> campusLocations;

    Institutions(Map<Campus, List<Building>> campusLocations) {
        this.campusLocations = campusLocations;
    }

    public Map<Campus, List<Building>> getCampusLocations() {
        return campusLocations;
    }
}
