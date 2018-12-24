package dkarlsso.commons.raspberry.relay.interfaces;

import dkarlsso.commons.raspberry.RunningClock;

import java.math.BigDecimal;

public interface TimedRelayInterface extends RelayInterface {

    RunningClock getRunningClock();

}
