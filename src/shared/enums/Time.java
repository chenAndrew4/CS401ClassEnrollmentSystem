package shared.enums;

public enum Time {
    TIME_700("7:00 AM"),
    TIME_715("7:15 AM"),
    TIME_730("7:30 AM"),
    TIME_745("7:45 AM"),
    TIME_800("8:00 AM"),
    TIME_815("8:15 AM"),
    TIME_830("8:30 AM"),
    TIME_845("8:45 AM"),
    TIME_900("9:00 AM"),
    TIME_915("9:15 AM"),
    TIME_930("9:30 AM"),
    TIME_945("9:45 AM"),
    TIME_1000("10:00 AM"),
    TIME_1015("10:15 AM"),
    TIME_1030("10:30 AM"),
    TIME_1045("10:45 AM"),
    TIME_1100("11:00 AM"),
    TIME_1115("11:15 AM"),
    TIME_1130("11:30 AM"),
    TIME_1145("11:45 AM"),
    TIME_1200("12:00 PM"),
    TIME_1215("12:15 PM"),
    TIME_1230("12:30 PM"),
    TIME_1245("12:45 PM"),
    TIME_1300("1:00 PM"),
    TIME_1315("1:15 PM"),
    TIME_1330("1:30 PM"),
    TIME_1345("1:45 PM"),
    TIME_1400("2:00 PM"),
    TIME_1415("2:15 PM"),
    TIME_1430("2:30 PM"),
    TIME_1445("2:45 PM"),
    TIME_1500("3:00 PM"),
    TIME_1515("3:15 PM"),
    TIME_1530("3:30 PM"),
    TIME_1545("3:45 PM"),
    TIME_1600("4:00 PM"),
    TIME_1615("4:15 PM"),
    TIME_1630("4:30 PM"),
    TIME_1700("5:00 PM"),
    TIME_1715("5:15 PM"),
    TIME_1730("5:30 PM"),
    TIME_1745("5:45 PM"),
    TIME_1800("6:00 PM"),
    TIME_1815("6:15 PM"),
    TIME_1830("6:30 PM"),
    TIME_1845("6:45 PM"),
    TIME_1900("7:00 PM"),
    TIME_1915("7:15 PM"),
    TIME_1930("7:30 PM"),
    TIME_1945("7:45 PM"),
    TIME_2000("8:00 PM"),
    TIME_2015("8:15 PM"),
    TIME_2030("8:30 PM"),
    TIME_2045("8:45 PM"),
    TIME_2100("9:00 PM"),
    TIME_2115("9:15 PM"),
    TIME_2130("9:30 PM"),
    TIME_2145("9:45 PM"),
    TIME_2200("10:00 PM");

    private final String formattedTime;

    Time(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public static Time fromString(String time) {
        for (Time t : Time.values()) {
            if (t.formattedTime.equals(time)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid time format: " + time);
    }

    public boolean before(Time otheTime) {
        return this.ordinal() < otheTime.ordinal();
    }

    public boolean after(Time other) {
        return this.ordinal() > other.ordinal();
    }
}