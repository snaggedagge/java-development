package dkarlsso.commons.calendar.dto;

public class CalendarDTO {

    private final String calendarName;

    private final String calendarId;

    private final boolean isPrimary;

    public CalendarDTO(String calendarName, String calendarId, boolean isPrimary) {
        this.calendarName = calendarName;
        this.calendarId = calendarId;
        this.isPrimary = isPrimary;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public boolean isPrimary() {
        return isPrimary;
    }
}
