package shared.enums;

import java.util.List;

public enum Building {
	 // All rooms available
    MEIKLEJOHN_HALL(List.of(Room.values())),
    LIBRARY(List.of(Room.ROOM1, Room.ROOM2, Room.ROOM3)),
    SCIENCE_BUILDING(List.of(Room.values())),
    STUDENT_UNION(List.of(Room.ROOM1, Room.ROOM5, Room.ROOM10)),
    ENGINEERING_HALL(List.of(Room.values())),
    ARTS_BUILDING(List.of(Room.ROOM1, Room.ROOM2, Room.ROOM3, Room.ROOM4)),
    SPORTS_COMPLEX(List.of(Room.values())),
    BEHAVIORAL_SCIENCES(List.of(Room.ROOM1, Room.ROOM2, Room.ROOM3)),
    BUSINESS_HALL(List.of(Room.ROOM1, Room.ROOM2));

    private final List<Room> rooms;

    Building(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
