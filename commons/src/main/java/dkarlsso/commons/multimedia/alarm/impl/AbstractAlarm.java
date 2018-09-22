package dkarlsso.commons.multimedia.alarm.impl;

import dkarlsso.commons.multimedia.alarm.Alarm;

public abstract class AbstractAlarm implements Alarm {

    protected final int increasingVolumeDurationInMinutes;

    protected final int durationInMinutes;

    protected final int startVolume;

    protected final int endVolume;


    public AbstractAlarm(int increasingVolumeDurationInMinutes, int durationInMinutes, int startVolume, int endVolume) {
        this.increasingVolumeDurationInMinutes = increasingVolumeDurationInMinutes;
        this.durationInMinutes = durationInMinutes;
        this.startVolume = startVolume;
        this.endVolume = endVolume;
    }


    public int getVolumeInPercentage(final int minutesAlarmHasBeenActive) {
        final double alarmQuoteElapsed = (double) minutesAlarmHasBeenActive / (double) increasingVolumeDurationInMinutes;
        final int increaseVolumeInterval = endVolume - startVolume;

        return (int)(startVolume + (increaseVolumeInterval * alarmQuoteElapsed));
    }
}
