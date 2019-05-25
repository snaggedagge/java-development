package dkarlsso.commons.raspberry.screen;

public interface ScreenHandler {

    public void setScreenPowerMode(final boolean powerOn) throws ScreenHandlerException;

    public boolean isScreenActive();
}
