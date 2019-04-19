package dkarlsso.commons.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import dkarlsso.commons.calendar.dto.CalendarDTO;
import dkarlsso.commons.calendar.dto.EventDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleGoogleCalendar implements CalendarService {

    private com.google.api.services.calendar.Calendar calendarService;

    private final String applicationName;

    private final List<String> calendarIdList = new ArrayList<>();

    private DateTime futureTime;

    public SimpleGoogleCalendar(final String applicationName, final Credential credential) throws CalendarException {
        this.applicationName = applicationName;
        try {
            calendarService = getCalendarService(credential);
            setCalendars(getCalendars().stream().map(CalendarDTO::getCalendarId).collect(Collectors.toList()));
        } catch (IOException | GeneralSecurityException e) {
            throw new CalendarException(e.getMessage(), e);
        }
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     */
    private com.google.api.services.calendar.Calendar
    getCalendarService(final Credential credential) throws GeneralSecurityException, IOException {
        return new com.google.api.services.calendar.Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(applicationName)
                .build();
    }

    @Override
    public List<EventDTO> getEvents() throws IOException {
        final DateTime now = new DateTime(System.currentTimeMillis());

        final List<Event> eventsList = new ArrayList<>();
        for(String id : calendarIdList) {
            Events even = calendarService.events().list(id)
                    .setMaxResults(20)
                    .setTimeMin(now)
                    .setTimeMax(futureTime)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();

            eventsList.addAll(even.getItems());
        }

        final List<EventDTO> eventDTOList = new ArrayList<>();
        for(Event event : eventsList) {
            eventDTOList.add(CalendarMapper.map(event));
        }

        return eventDTOList;
    }

    @Override
    public List<CalendarDTO> getCalendars() throws IOException {
        final List<CalendarListEntry> entryList =  calendarService.calendarList().list()
                .setMaxResults(10)
                .setShowDeleted(true)
                .setShowHidden(true)
                .execute().getItems();

        final List<CalendarDTO> calendarList = new ArrayList<>();
        for(CalendarListEntry entry : entryList) {
            calendarList.add(CalendarMapper.map(entry));
        }

        return calendarList;
    }

    @Override
    public void setCalendars(final List<String> newCalendarIds) {
        if(newCalendarIds != null && !newCalendarIds.isEmpty()) {
            calendarIdList.clear();
            calendarIdList.addAll(newCalendarIds);
        }
    }

    @Override
    public void setDaysInFuture(final int days) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        c.add(java.util.Calendar.DATE, days);
        futureTime = new DateTime(c.getTime().getTime());
    }
}
