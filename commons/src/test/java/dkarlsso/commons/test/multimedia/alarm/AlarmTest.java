package dkarlsso.commons.test.multimedia.alarm;


import dkarlsso.commons.multimedia.alarm.Alarm;
import dkarlsso.commons.multimedia.alarm.impl.AlarmTimeSetting;
import dkarlsso.commons.multimedia.alarm.impl.DayOfWeek;
import dkarlsso.commons.multimedia.alarm.impl.WeekdayAlarm;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AlarmTest {


    @Test
    public void testWeekdayAlarmIsActive() {
        final AlarmTimeSetting<DayOfWeek> alarmTimeSetting = new AlarmTimeSetting<>(DayOfWeek.getDayOfWeek(), new DateTime().minusMinutes(5));


        final int startVolume = 0;
        final int endVolume = 100;

        Alarm alarm = new WeekdayAlarm(10,10,startVolume,endVolume, Arrays.asList(alarmTimeSetting));

        Assert.assertTrue(alarm.shouldAlarmBeActive());
        Assert.assertEquals(alarm.getVolumeInPercentage(), endVolume/2);

    }


    @Test
    public void testWeekdayAlarmIsNotActiveOnRightTimeWrongDay() {
        DayOfWeek dayOfWeek = DayOfWeek.TUESDAY == DayOfWeek.getDayOfWeek() ? DayOfWeek.THURSDAY : DayOfWeek.TUESDAY;

        final AlarmTimeSetting<DayOfWeek> alarmTimeSetting = new AlarmTimeSetting<>(dayOfWeek, new DateTime().minusMinutes(5));

        final int startVolume = 0;
        final int endVolume = 100;

        Alarm alarm = new WeekdayAlarm(10,10,startVolume,endVolume, Arrays.asList(alarmTimeSetting));

        Assert.assertFalse(alarm.shouldAlarmBeActive());
    }

    @Test
    public void testWeekdayAlarmIsNotActiveOnWrongTimeRightDay() {
        final AlarmTimeSetting<DayOfWeek> alarmTimeSetting = new AlarmTimeSetting<>(DayOfWeek.getDayOfWeek(), new DateTime().minusMinutes(15));

        Alarm alarm = new WeekdayAlarm(10,10,0,100, Arrays.asList(alarmTimeSetting));

        Assert.assertFalse(alarm.shouldAlarmBeActive());
    }

    @Test
    public void testWeekdayAlarmHasCorrectVolume() {
        final AlarmTimeSetting<DayOfWeek> alarmTimeSetting = new AlarmTimeSetting<>(DayOfWeek.getDayOfWeek(), new DateTime().minusMinutes(10));

        final int startVolume = 0;
        final int endVolume = 100;

        Alarm alarm = new WeekdayAlarm(10,20,startVolume,endVolume, Arrays.asList(alarmTimeSetting));

        Assert.assertTrue(alarm.shouldAlarmBeActive());
        Assert.assertEquals(alarm.getVolumeInPercentage(), endVolume);
    }

    @Test
    public void testAllDaysAdded() {
        final List<AlarmTimeSetting<DayOfWeek>> alarmTimeSettings = new ArrayList<>();

        final DateTime weekDayTime = new DateTime(0,1,1,7,0,0);
        final DateTime weekendDayTime = new DateTime(0,1,1,10,0,0);

        alarmTimeSettings.addAll(AlarmTimeSetting.getAsList(DayOfWeek.getWeekdays(), weekDayTime));
        alarmTimeSettings.addAll(AlarmTimeSetting.getAsList(DayOfWeek.getWeekenddays(), weekendDayTime));

        Assert.assertEquals(alarmTimeSettings.size(), DayOfWeek.values().length);

        for (AlarmTimeSetting<DayOfWeek> alarmTimeSetting :alarmTimeSettings) {
            if(DayOfWeek.isWeekDay(alarmTimeSetting.getAlarmDay())) {
                Assert.assertEquals(alarmTimeSetting.getAlarmTimeOnDay(), weekDayTime);
            }
            if(DayOfWeek.isWeekendDay(alarmTimeSetting.getAlarmDay())) {
                Assert.assertEquals(alarmTimeSetting.getAlarmTimeOnDay(), weekendDayTime);
            }
        }
    }

}
