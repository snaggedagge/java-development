package dkarlsso.commons.multimedia.alarm.impl;


import org.joda.time.DateTime;
import org.joda.time.Minutes;


import java.util.Calendar;
import java.util.List;

public class WeekdayAlarm extends AbstractAlarm {


    final List<AlarmTimeSetting<DayOfWeek>> alarmTimeSettings;

    // Fast and ugly, RPI is gonna ned all the CPU power it gets
    private int minutesAlarmHasBeenActive = 0;


    public WeekdayAlarm(int increasingVolumeDurationInMinutes, int durationInMinutes, int startVolume, int endVolume, final List<AlarmTimeSetting<DayOfWeek>> alarmTimeSettings) {
        super(increasingVolumeDurationInMinutes, durationInMinutes, startVolume, endVolume);
        this.alarmTimeSettings = alarmTimeSettings;
    }

    @Override
    public boolean shouldAlarmBeActive() {

        final DateTime timeNow = new DateTime();

        for (final AlarmTimeSetting<DayOfWeek> alarmTimeSetting : alarmTimeSettings) {
            if (alarmTimeSetting.getAlarmDay() == DayOfWeek.getDayOfWeek()) {
                DateTime timeOfAlarm = alarmTimeSetting.getAlarmTimeOnDay();

                // Since we are actually only interested in the time.
                timeOfAlarm = timeOfAlarm.withDate(timeNow.getYear(), timeNow.getMonthOfYear(), timeNow.getDayOfMonth());

                if (timeNow.isAfter(timeOfAlarm) && timeNow.isBefore(timeOfAlarm.plusMinutes(durationInMinutes))) {
                    minutesAlarmHasBeenActive = Minutes.minutesBetween(timeOfAlarm,timeNow).getMinutes();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getVolumeInPercentage() {
        return super.getVolumeInPercentage(minutesAlarmHasBeenActive);
    }


}
