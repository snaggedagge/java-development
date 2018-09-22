package dkarlsso.commons.multimedia.alarm;

import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.multimedia.MediaPlayer;
import dkarlsso.commons.raspberry.settings.SoundController;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlarmClock implements Runnable{

//    private final Logger LOG = LogManager.getLogger(AlarmClock.class);

    private SoundController soundController;

    private MediaPlayer mediaPlayer;




    @Override
    public void run() {
//        while (isRunning) {
//            try {
//                Thread.sleep(10000);
//                boolean shouldPlayAlarm = false;
//                boolean alarmDurationOver = false;
//
//                for (int i=0; i<alarmTimeSettings.size(); i++) {
//                    final AlarmTimeSetting alarmTimeSetting = alarmTimeSettings.get(i);
//
//                    final DateTime timeNow = new DateTime();
//                    final int minutesBetween = Minutes.minutesBetween(alarmTimeSetting.getStartTime(),timeNow).getMinutes();
//                    if (timeNow.isAfter(alarmTimeSetting.getStartTime())
//                            && minutesBetween < alarmTimeSetting.getDurationInMinutes()) {
//
//                        final double alarmQuoteElapsed = (double)minutesBetween / (double)alarmTimeSetting.getDurationInMinutes();
//                        final int increaseVolumeInterval = alarmTimeSetting.getEndVolume() - alarmTimeSetting.getStartVolume();
//                        try {
//                            soundController.setVolume((int)(alarmTimeSetting.getStartVolume() + (increaseVolumeInterval * alarmQuoteElapsed)));
//                        } catch (CommonsException e) {
////                            LOG.error("Error while changing volume " + e.getMessage(), e);
//                        }
//                        shouldPlayAlarm = true;
//                    }
//                    else if (timeNow.isAfter(alarmTimeSetting.getStartTime())
//                            && minutesBetween >= alarmTimeSetting.getDurationInMinutes()) {
//                        alarmTimeSettings.remove(i--);
//                        alarmDurationOver = true;
//                    }
//
//                    System.out.println("Minutes between " + minutesBetween + "  " + alarmTimeSetting.getDurationInMinutes());
//                }
//
//                if(shouldPlayAlarm) {
//                    mediaPlayer.play();
//                }
//                else if(alarmDurationOver) {
//                    mediaPlayer.stop();
//                }
//            } catch (final InterruptedException e) {
////                LOG.error("Error in alarm thread: " + e.getMessage(), e);
//            }
//        }
    }
}
