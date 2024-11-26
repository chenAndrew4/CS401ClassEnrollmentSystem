package shared.enums;

import java.time.DayOfWeek;

public enum Days{
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;
	public boolean matches(DayOfWeek dayOfWeek) {
		return this.name().equalsIgnoreCase(dayOfWeek.name());
	}
}