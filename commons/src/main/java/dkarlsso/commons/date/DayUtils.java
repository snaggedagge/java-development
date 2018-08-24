package dkarlsso.commons.date;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DayUtils {


    public static List<String> getDaysForAWeek() {
        List<String> daysList = new ArrayList<>();
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        // Index 0 is empty apperently
        for(int i = dayOfWeek;i<dayOfWeek+8;i++) {
            final String day = dayNames[i%8];
            if(day != null && !day.isEmpty()) {
                daysList.add(StringUtils.capitalize(day));
            }
        }
        return daysList;
    }

    public static List<Date> getDateForAWeek() {
        List<Date> daysList = new ArrayList<>();

        for(int i=0;i<7;i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(java.util.Calendar.DATE, i);
            daysList.add(calendar.getTime());
        }
        return daysList;
    }


    public static String getTime(final Date date) {
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
        return ft.format(date);
    }

    public static String getDate(final Date date) {
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM");
        return ft.format(date);
    }

    public static String getDay(final Date date) {
        SimpleDateFormat ft = new SimpleDateFormat("EEEE");
        return ft.format(date);
    }

    public static int daysBetween(final Date firstDate, final Date secondDate) throws ParseException {

        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        final String firstDateString = formatter.format(firstDate);
        final String secondDateString = formatter.format(secondDate);

        final Date firstDateClean = formatter.parse(firstDateString);
        final Date secondDateClean = formatter.parse(secondDateString);

        long diff = secondDateClean.getTime() - firstDateClean.getTime();
        return ((Long)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)).intValue();

    }

    public static int hoursBetween(final Date firstDate, final Date secondDate) throws ParseException{
        long diff = secondDate.getTime() - firstDate.getTime();
        return ((Long)TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)).intValue();
    }

    public static boolean isTimeBetween(final Date date, int hours) throws ParseException {
        if(hoursBetween(date,Calendar.getInstance().getTime()) < hours) {
            return true;
        }
        return false;
    }



}
