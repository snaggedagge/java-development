package dkarlsso.commons.raspberry.relay.interfaces;

import dkarlsso.commons.raspberry.RunningClock;

public interface TimedRelayInterface extends RelayInterface {

    RunningClock getRunningClock();

}
