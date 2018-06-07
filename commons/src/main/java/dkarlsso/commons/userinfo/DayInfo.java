package dkarlsso.commons.userinfo;

import dkarlsso.commons.calendar.dto.EventDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayInfo {

    private List<EventDTO> events = new ArrayList<>();

    private Date day;

    public DayInfo() {
    }

    public DayInfo(List<EventDTO> events, Date day) {
        this();
        this.events = events;
        this.day = day;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public Date getDay() {
        return day;
    }


    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    public void setDay(Date day) {
        this.day = day;
    }
}
