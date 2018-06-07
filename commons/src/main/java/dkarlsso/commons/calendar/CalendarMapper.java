package dkarlsso.commons.calendar;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import dkarlsso.commons.calendar.dto.CalendarDTO;
import dkarlsso.commons.calendar.dto.EventDTO;

import java.util.Date;

public class CalendarMapper {


    public static EventDTO map(final Event event) {
        Date start = null;
        if(event.getStart().getDateTime() != null) {
            start = new Date(event.getStart().getDateTime().getValue());
        } else if(event.getStart().getDate() != null) {
            start = new Date(event.getStart().getDate().getValue());
        }

        Date end = null;
        if(event.getEnd().getDateTime() != null) {
            end = new Date(event.getEnd().getDateTime().getValue());
        } else if(event.getEnd().getDate() != null) {
            end = new Date(event.getEnd().getDate().getValue());
        }

        return new EventDTO(event.getSummary(),
                event.getId(),
                event.getDescription(),
                event.getStatus(),
                start,
                end);
    }

    public static CalendarDTO map(final CalendarListEntry entry) {

        boolean isPrimary;
        if(entry.getPrimary() == null) {
            isPrimary = false;
        }
        else {
            isPrimary = entry.getPrimary();
        }

        return new CalendarDTO(entry.getSummary(),entry.getId(),isPrimary);
    }


}
