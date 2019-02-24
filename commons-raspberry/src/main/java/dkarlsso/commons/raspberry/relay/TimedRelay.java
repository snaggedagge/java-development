package dkarlsso.commons.raspberry.relay;

import dkarlsso.commons.raspberry.RunningClock;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.relay.interfaces.TimedRelayInterface;

public class TimedRelay implements TimedRelayInterface {

    private final RelayInterface relayInterface;

    private final RunningClock runningClock = new RunningClock();

    public TimedRelay(final RelayInterface relayInterface) {
        this.relayInterface = relayInterface;
    }

    @Override
    public void setHigh() {
        relayInterface.setHigh();
        runningClock.setStartedRunning(relayInterface.isHigh());
    }

    @Override
    public void setLow() {
        relayInterface.setLow();
        runningClock.setStartedRunning(relayInterface.isHigh());
    }

    @Override
    public void setState(boolean state) {
        relayInterface.setState(state);
        runningClock.setStartedRunning(relayInterface.isHigh());
    }

    @Override
    public void switchState() {
        relayInterface.switchState();
        runningClock.setStartedRunning(relayInterface.isHigh());
    }

    @Override
    public boolean isHigh() {
        return relayInterface.isHigh();
    }

    @Override
    public RunningClock getRunningClock() {
        return runningClock;
    }
}
