package dkarlsso.smartmirror.javafx;

import com.google.inject.Inject;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.multimedia.alarm.AlarmClock;
import dkarlsso.commons.multimedia.alarm.AlarmTimeSetting;
import dkarlsso.commons.raspberry.settings.SoundController;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.util.Arrays;

public class ApplicationManager {

    @Inject
    private MediaPlayer radioPlayer;

    @Inject
    private SoundController soundController;

    private AlarmClock alarmClock;

    public void start() {

        Arrays.asList(new AlarmTimeSetting(new DateTime(2018, 9, 17, 6, 45)
                , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 18, 6, 45)
                        , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 19, 6, 45)
                        , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 20, 6, 45)
                        , 30, 60, 100),

                new AlarmTimeSetting(new DateTime(2018, 9, 21, 6, 45)
                        , 30, 60, 100));



        alarmClock = new AlarmClock(soundController, radioPlayer,
                new AlarmTimeSetting(new DateTime(2018, 9, 16, 9, 0)
                        , 30, 60, 100));

        new Thread(alarmClock).start();
    }
}
