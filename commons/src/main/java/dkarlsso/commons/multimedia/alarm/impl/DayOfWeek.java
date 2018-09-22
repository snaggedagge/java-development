package dkarlsso.commons.multimedia.alarm.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public enum DayOfWeek {

    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;

    public static DayOfWeek getDayOfWeek() {
        // Day starts from 1 - Sunday
        return DayOfWeek.values()[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static List<DayOfWeek> getWeekdays() {
        return Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
    }

    public static List<DayOfWeek> getWeekenddays() {
        return Arrays.asList(SATURDAY, SUNDAY);
    }

    public static boolean isWeekDay(final DayOfWeek dayOfWeek) {
        return getWeekdays().contains(dayOfWeek);
    }

    public static boolean isWeekendDay(final DayOfWeek dayOfWeek) {
        return getWeekenddays().contains(dayOfWeek);
    }
}
