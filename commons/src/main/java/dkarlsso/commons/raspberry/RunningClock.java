package dkarlsso.commons.raspberry;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RunningClock {

    private boolean isRunning = false;

    private DateTime runningSince = new DateTime();

    private BigDecimal totalRunningHoursSinceReset = new BigDecimal(0);

    public void setStartedRunning(boolean isRunning) {

        if(this.isRunning) {
            int secondsRunning = Seconds.secondsBetween(runningSince, new DateTime()).getSeconds();
            totalRunningHoursSinceReset = totalRunningHoursSinceReset.add(new BigDecimal(secondsRunning).divide(new BigDecimal(60*60),20,RoundingMode.CEILING));
        }

        runningSince = new DateTime();
        this.isRunning = isRunning;
    }

    public BigDecimal getRunningTimeAndReset() {
        final BigDecimal runningTime = totalRunningHoursSinceReset;
        totalRunningHoursSinceReset = new BigDecimal(0);

        return runningTime;
    }

}
