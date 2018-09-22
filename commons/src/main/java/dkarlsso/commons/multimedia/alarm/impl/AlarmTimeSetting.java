package dkarlsso.commons.multimedia.alarm.impl;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class AlarmTimeSetting<T> {

    private final T alarmDay;

    private final DateTime alarmTimeOnDay;

    private final boolean isReoccuring;

    public AlarmTimeSetting(final T alarmDay, final DateTime alarmTimeOnDay) {
        this.alarmDay = alarmDay;
        this.alarmTimeOnDay = alarmTimeOnDay;
        this.isReoccuring = true;
    }

    public AlarmTimeSetting(final T alarmDay, final DateTime alarmTimeOnDay, final boolean isReoccuring) {
        this.alarmDay = alarmDay;
        this.alarmTimeOnDay = alarmTimeOnDay;
        this.isReoccuring = isReoccuring;
    }

    public T getAlarmDay() {
        return alarmDay;
    }

    public DateTime getAlarmTimeOnDay() {
        return alarmTimeOnDay;
    }

    public static <T> List<AlarmTimeSetting<T>> getAsList(final List<T> listOfDays, final DateTime alarmTimeOnDay) {

        final List<AlarmTimeSetting<T>> alarmTimeSettings = new ArrayList<>();

        for (final T alarmDay : listOfDays) {
            alarmTimeSettings.add(new AlarmTimeSetting<>(alarmDay, alarmTimeOnDay));
        }
        return alarmTimeSettings;
    }

    public boolean isReoccuring() {
        return isReoccuring;
    }
}
