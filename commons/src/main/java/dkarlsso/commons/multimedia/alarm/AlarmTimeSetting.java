package dkarlsso.commons.multimedia.alarm;

import org.joda.time.DateTime;

public class AlarmTimeSetting {

    private final DateTime startTime;

    private final int durationInMinutes;

    private final int startVolume;

    private final int endVolume;

    public AlarmTimeSetting(final DateTime startTime, final int durationInMinutes,
                            final int startVolume, final int endVolume) {
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
        this.startVolume = startVolume;
        this.endVolume = endVolume;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public int getStartVolume() {
        return startVolume;
    }

    public int getEndVolume() {
        return endVolume;
    }
}
