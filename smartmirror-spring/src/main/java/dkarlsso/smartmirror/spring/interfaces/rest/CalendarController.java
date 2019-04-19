package dkarlsso.smartmirror.spring.interfaces.rest;


import dkarlsso.commons.calendar.CalendarException;
import dkarlsso.commons.calendar.GoogleCalendar;
import dkarlsso.commons.calendar.dto.CalendarDTO;
import dkarlsso.commons.calendar.dto.EventDTO;
import dkarlsso.smartmirror.spring.infrastructure.repository.Oauth2UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CalendarController {

    @Autowired
    private Oauth2UserRepository oauth2UserRepository;


    @RequestMapping("/API/getCalendars")
    public List<CalendarDTO> getCalendars() throws IOException, CalendarException {
        final GoogleCalendar schoolCalendar = new GoogleCalendar(new File("/"),"dagSchool");
        return schoolCalendar.getCalendars();
    }

    @RequestMapping("/API/getEvents")
    public List<EventDTO> getEvents() throws IOException, CalendarException {
        final GoogleCalendar schoolCalendar = new GoogleCalendar(new File("/"), "dagSchool");
        schoolCalendar.setCalendars(schoolCalendar.getCalendars().stream().map(CalendarDTO::getCalendarId).collect(Collectors.toList()));

        List<EventDTO> list = schoolCalendar.getEvents();


        for(EventDTO dto : list) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(dto.getStart());
            System.out.println("Name " + dto.getEventName() + "Report Date: " + reportDate);
        }


        return list;
    }
}
