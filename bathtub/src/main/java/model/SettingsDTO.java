package model;

public class SettingsDTO {

    protected int returnTempLimit = 8;

    protected int overTempLimit = returnTempLimit + 15;

    protected boolean lightsOn = false;

    protected boolean debug = false;

    protected int circulationTimeCycle = 5;

    public SettingsDTO() {
    }

    public SettingsDTO(final SettingsDTO settingsDTO) {
        this.lightsOn = settingsDTO.lightsOn;
        this.debug = settingsDTO.debug;
        this.overTempLimit = settingsDTO.overTempLimit;
        this.returnTempLimit = settingsDTO.returnTempLimit;
        this.circulationTimeCycle = settingsDTO.circulationTimeCycle;
    }

    public boolean isLightsOn() {
        return lightsOn;
    }

    public void setLightsOn(boolean lightsOn) {
        this.lightsOn = lightsOn;
    }

    public int getReturnTempLimit() {
        return returnTempLimit;
    }

    public void setReturnTempLimit(int returnTempLimit) {
        this.returnTempLimit = returnTempLimit;
    }

    public int getOverTempLimit() {
        return overTempLimit;
    }

    public void setOverTempLimit(int overTempLimit) {
        this.overTempLimit = overTempLimit;
    }

    public int getCirculationTimeCycle() {
        return circulationTimeCycle;
    }

    public void setCirculationTimeCycle(int circulationTimeCycle) {
        this.circulationTimeCycle = circulationTimeCycle;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    protected SettingsDTO clone() {
        final SettingsDTO settingsDTO = new SettingsDTO();

        settingsDTO.setCirculationTimeCycle(circulationTimeCycle);
        settingsDTO.setDebug(debug);
        settingsDTO.setOverTempLimit(overTempLimit);
        settingsDTO.setReturnTempLimit(returnTempLimit);
        settingsDTO.setLightsOn(lightsOn);
        return settingsDTO;
    }
}
