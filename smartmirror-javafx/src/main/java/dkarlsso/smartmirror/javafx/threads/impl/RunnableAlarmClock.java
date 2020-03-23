package dkarlsso.smartmirror.javafx.threads.impl;

import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.alarm.Alarm;
import dkarlsso.commons.multimedia.alarm.impl.AlarmTimeSetting;
import dkarlsso.commons.multimedia.alarm.impl.DayOfWeek;
import dkarlsso.commons.multimedia.alarm.impl.WeekdayAlarm;
import dkarlsso.commons.multimedia.settings.SoundController;
import dkarlsso.smartmirror.javafx.threads.AutostartedRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;


public class RunnableAlarmClock implements AutostartedRunnable {

    private final Logger LOG = LogManager.getLogger(RunnableAlarmClock.class);

    private final List<Alarm> alarms = new ArrayList<>();

  //  @Inject
    private SoundController soundController;

  //  @Inject
    private MediaPlayer radioPlayer;

    private boolean hasActiveAlarm = false;


    public RunnableAlarmClock() {

        // Will be replaced by stored alarms in database.

        final List<AlarmTimeSetting<DayOfWeek>> alarmTimeSettings = new ArrayList<>();
        alarmTimeSettings.addAll(AlarmTimeSetting.getAsList(DayOfWeek.getWeekdays(),
                new DateTime(0,1,1,7,15,0)));

        alarmTimeSettings.addAll(AlarmTimeSetting.getAsList(DayOfWeek.getWeekenddays(),
                new DateTime(0,1,1,10,0,0)));

        Alarm weekDayAlarms = new WeekdayAlarm(30,45,60,100, alarmTimeSettings);


        alarms.add(weekDayAlarms);
    }




    @Override
    public void run() {
        LOG.warn("Starting alarm thread");
        boolean isRunning = true;

        while (isRunning) {
            try {
                LOG.info("Checking for active alarms. Alarms in list: " + alarms.size());

                for(final Alarm alarm : alarms) {
                    if(alarm.shouldAlarmBeActive()) {
                        LOG.info("Starting alarm");
                        soundController.setVolume(alarm.getVolumeInPercentage());
                        radioPlayer.play();
                        hasActiveAlarm = true;
                    }
                    else if (hasActiveAlarm) {
                        LOG.info("Stopping alarm");
                        radioPlayer.stop();
                        hasActiveAlarm = false;
                    }
                }
                Thread.sleep(1000 * 60);
            } catch (final CommonsException e) {
                LOG.error("Error in alarm thread", e);
            } catch (final InterruptedException e) {
                isRunning = false;
            }
        }
    }
}
