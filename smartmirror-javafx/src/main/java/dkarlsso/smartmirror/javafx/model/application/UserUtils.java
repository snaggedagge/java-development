package dkarlsso.smartmirror.javafx.model.application;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    /*
    I promise, this will be remade :)

    I used this for dev purpose, but I will keep it until i can replace my RPI to an orange pi,
    since i do not currently have enough ram memory left to have an database.
     */

    public static List<String> getUsers() {
        final List<String> userList = new ArrayList<>();
        userList.add("Dag");

        return userList;
    }

    public static List<String> getUserCalendars(final String user) {
        final List<String> calendarList = new ArrayList<>();
        if(user.equals("Dag")) {
            calendarList.add("dagPrivate");
        }

        return calendarList;
    }



}
