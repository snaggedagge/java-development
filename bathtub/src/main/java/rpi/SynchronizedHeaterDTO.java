package rpi;

import rpi.model.TimerDTO;

public class SynchronizedHeaterDTO {

    private int returnTemp = 0;
    private int overTemp = 0;

    private int circulationTimeCycle = 5;

    private int returnTempLimit = 8;
    private int overTempLimit = returnTempLimit + 15;
    private boolean lightsOn = false;
    private boolean heating = false;
    private boolean circulating = false;
    private boolean debug = false;
    private boolean settingsChanged = false;

    private TimerDTO timerDTO = null;

    public int getReturnTemp() {
        return returnTemp;
    }

    public void setReturnTemp(int returnTemp) {
        this.returnTemp = returnTemp;
    }

    public int getOverTemp() {
        return overTemp;
    }

    public void setOverTemp(int overTemp) {
        this.overTemp = overTemp;
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

    public boolean isLightsOn() {
        return lightsOn;
    }

    public void setLightsOn(boolean lightsOn) {
        this.lightsOn = lightsOn;
    }

    public boolean isHeating() {
        return heating;
    }

    public void setHeating(boolean heating) {
        this.heating = heating;
    }

    public boolean isCirculating() {
        return circulating;
    }

    public void setCirculating(boolean circulating) {
        this.circulating = circulating;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getCirculationTimeCycle() {
        return circulationTimeCycle;
    }

    public void setCirculationTimeCycle(int circulationTimeCycle) {
        this.circulationTimeCycle = circulationTimeCycle;
    }

    public boolean isSettingsChanged() {
        return settingsChanged;
    }

    public void setSettingsChanged(boolean settingsChanged) {
        this.settingsChanged = settingsChanged;
    }

    public TimerDTO getTimerDTO() {
        return timerDTO;
    }

    public void setTimerDTO(TimerDTO timerDTO) {
        this.timerDTO = timerDTO;
    }
}
