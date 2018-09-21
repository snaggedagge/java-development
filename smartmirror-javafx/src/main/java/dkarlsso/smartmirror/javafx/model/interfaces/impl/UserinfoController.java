package dkarlsso.smartmirror.javafx.model.interfaces.impl;

import dkarlsso.smartmirror.javafx.model.application.ApplicationUtils;
import dkarlsso.smartmirror.javafx.model.application.UserUtils;
import dkarlsso.commons.calendar.CalendarException;
import dkarlsso.commons.calendar.GoogleCalendar;
import dkarlsso.commons.calendar.dto.EventDTO;
import dkarlsso.commons.date.DayUtils;
import dkarlsso.commons.userinfo.DayInfo;
import dkarlsso.commons.userinfo.UserWeekInfo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserinfoController {


    public static List<UserWeekInfo> getAllUsersInfo() throws ParseException {
        final List<String> users = UserUtils.getUsers();

        final List<UserWeekInfo> userWeekInfoList = new ArrayList<>();

        for(String user : users) {

            final UserWeekInfo userWeekInfo = new UserWeekInfo();
            userWeekInfo.setName(user);

            for(EventDTO eventDTO : getAllUserEvents(user)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(eventDTO.getStart());

                int index = DayUtils.daysBetween(Calendar.getInstance().getTime(),eventDTO.getStart());

                if(index < 7) {
                    DayInfo dayInfo = userWeekInfo.getDayInfos()[index];
                    dayInfo.setDay(eventDTO.getStart());
                    dayInfo.getEvents().add(eventDTO);
                }
            }
            userWeekInfoList.add(userWeekInfo);

        }
        return userWeekInfoList;
    }


    public static List<EventDTO> getAllUserEvents(final String user) {
        List<EventDTO> eventList = new ArrayList<>();


        boolean useReal = true;

        if(useReal) {
            List<String> userCalendars = UserUtils.getUserCalendars(user);
            for(String calendar : userCalendars) {
                try {
                    GoogleCalendar googleCalendar = new GoogleCalendar(ApplicationUtils.getRootFolder(), calendar);
                    eventList.addAll(googleCalendar.getEvents());
                } catch (CalendarException | IOException e) {
                    e.printStackTrace();
                }

            }
        }
        else {
            java.util.Calendar one = java.util.Calendar.getInstance();
            one.setTime(new Date());
            one.add(java.util.Calendar.DATE, 1);

            java.util.Calendar two = java.util.Calendar.getInstance();
            two.setTime(new Date());
            two.add(java.util.Calendar.DATE, 2);

            java.util.Calendar three = java.util.Calendar.getInstance();
            three.setTime(new Date());
            three.add(java.util.Calendar.DATE, 3);

            java.util.Calendar three2 = java.util.Calendar.getInstance();
            three2.setTime(three.getTime());
            three2.add(Calendar.HOUR, 3);

            java.util.Calendar four = java.util.Calendar.getInstance();
            four.setTime(new Date());
            four.add(java.util.Calendar.DATE, 4);


            java.util.Calendar six = java.util.Calendar.getInstance();
            six.setTime(new Date());
            six.add(java.util.Calendar.DATE, 6);


            if(user.equals("Dag")) {

                eventList.add(new EventDTO("Tandläkare","as","as","",Calendar.getInstance().getTime(),Calendar.getInstance().getTime()));
                //eventList.add(new EventDTO("Besiktning","as","as","",one.getTime(),one.getTime()));
                eventList.add(new EventDTO("Besiktning","as","as","",one.getTime(),two.getTime()));
                eventList.add(new EventDTO("Tentamen Matematisk Modellering","as","as","",three.getTime(),Calendar.getInstance().getTime()));
                eventList.add(new EventDTO("Kejf på byin","as","as","",three2.getTime(),Calendar.getInstance().getTime()));

            }
            else {

                eventList.add(new EventDTO("Doktorn","as","as","",Calendar.getInstance().getTime(),Calendar.getInstance().getTime()));
                //eventList.add(new EventDTO("Test2","as","as","",one.getTime(),one.getTime()));
                eventList.add(new EventDTO("Träning","as","as","",two.getTime(),two.getTime()));
                eventList.add(new EventDTO("Möte","as","as","",three.getTime(),Calendar.getInstance().getTime()));
                eventList.add(new EventDTO("Chillaxation","as","as","",three2.getTime(),Calendar.getInstance().getTime()));

            }
        }


        return eventList;
    }



}
