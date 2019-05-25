package dkarlsso.commons.raspberry.screen;

public class EmptyScreenHandler implements ScreenHandler{
    private boolean active = false;

    @Override
    public void setScreenPowerMode(boolean powerOn) throws ScreenHandlerException {
        active = powerOn;
    }

    @Override
    public boolean isScreenActive() {
        return active;
    }
}
