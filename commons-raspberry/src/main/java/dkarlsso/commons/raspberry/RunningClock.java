package dkarlsso.commons.raspberry;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RunningClock {

    private boolean isRunning = false;

    private DateTime runningSince = new DateTime();

    private BigDecimal totalRunningHoursSinceReset = new BigDecimal(0);

    // How long it has been on since program started
    private BigDecimal totalRunningTime = new BigDecimal(0);

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
        totalRunningTime = totalRunningTime.add(runningTime);

        return runningTime;
    }

    public BigDecimal getTotalRunningTime() {
        return totalRunningTime;
    }

}
