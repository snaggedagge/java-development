package dkarlsso.commons.application;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {


    public static List<String> getUsers() {
        final List<String> userList = new ArrayList<>();
        userList.add("Dag");
        userList.add("Linda");

        return userList;
    }

    public static List<String> getUserCalendars(final String user) {
        final List<String> calendarList = new ArrayList<>();
        if(user.equals("Dag")) {
            calendarList.add("dagPrivate");
            calendarList.add("dagSchool");
        }

        return calendarList;
    }



}
