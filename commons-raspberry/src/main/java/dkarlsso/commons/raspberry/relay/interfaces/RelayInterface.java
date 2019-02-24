package dkarlsso.commons.raspberry.relay.interfaces;

public interface RelayInterface {
    public void setHigh();
    public void setLow();
    public void setState(boolean state);
    public void switchState();
    public boolean isHigh();
}
