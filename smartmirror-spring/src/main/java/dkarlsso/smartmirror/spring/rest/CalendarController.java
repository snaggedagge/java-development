package dkarlsso.smartmirror.spring.rest;


import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.commons.calendar.dto.CalendarDTO;
import dkarlsso.commons.calendar.dto.EventDTO;
import dkarlsso.commons.calendar.CalendarException;
import dkarlsso.commons.calendar.GoogleCalendar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class CalendarController {

    @RequestMapping("/API/getCalendars")
    public List<CalendarDTO> getCalendars() throws IOException, CalendarException {
        final GoogleCalendar schoolCalendar = new GoogleCalendar(ApplicationUtils.getRootFolder(),"dagSchool");
        return schoolCalendar.getCalendars();
    }

    @RequestMapping("/API/getEvents")
    public List<EventDTO> getEvents() throws IOException, CalendarException {
        final GoogleCalendar schoolCalendar = new GoogleCalendar(ApplicationUtils.getRootFolder(), "dagSchool");
        schoolCalendar.setCalendars(schoolCalendar.getCalendars());

        List<EventDTO> list = schoolCalendar.getEvents();


        for(EventDTO dto : list) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(dto.getStart());
            System.out.println("Name " + dto.getEventName() + "Report Date: " + reportDate);
        }


        return list;
    }
}
