package dkarlsso.commons.calendar;

import dkarlsso.commons.calendar.dto.CalendarDTO;
import dkarlsso.commons.calendar.dto.EventDTO;

import java.io.IOException;
import java.util.List;

public interface CalendarService {

    List<EventDTO> getEvents() throws IOException;

    List<CalendarDTO> getCalendars() throws IOException;

    void setCalendars(List<String> newCalendarIds);

    void setDaysInFuture(int days);
}
