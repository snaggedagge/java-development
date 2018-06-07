package dkarlsso.commons.calendar.dto;

import java.util.Date;

public class EventDTO {

    private String eventName;

    private String eventId;

    private String description;

    private String status;

    private Date start;

    private Date end;

    public EventDTO(String eventName, String eventId, String description, String status, Date start, Date end) {
        this.eventName = eventName;
        this.eventId = eventId;
        this.description = description;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public String getEventName() {
        return eventName;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getEventId() {
        return eventId;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
