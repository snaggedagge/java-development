package dkarlsso.commons.raspberry.relay;

import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;

public class MockRelay implements RelayInterface {

    boolean isHigh = false;

    @Override
    public void setHigh() {
        isHigh = true;
    }

    @Override
    public void setLow() {
        isHigh = false;
    }

    @Override
    public void setState(boolean state) {
        isHigh = state;
    }

    @Override
    public void switchState() {
        isHigh = !isHigh;
    }

    @Override
    public boolean isHigh() {
        return isHigh;
    }
}
