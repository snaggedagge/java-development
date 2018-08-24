package rpi.model;

public class HeaterDataDTO extends SettingsDTO {

    private int returnTemp = 0;

    private int overTemp = 0;

    private boolean heating = false;

    private boolean circulating = false;

    private boolean settingsChanged = false;

    private TimerDTO timerDTO = null;

    public HeaterDataDTO() {

    }

    public HeaterDataDTO(final SettingsDTO settingsDTO) {
        super(settingsDTO);
    }

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

    public void applySettings(final SettingsDTO settingsDTO) {
        this.settingsChanged = true;
        this.circulationTimeCycle = settingsDTO.getCirculationTimeCycle();
        this.returnTempLimit = settingsDTO.getReturnTempLimit();
        this.overTempLimit = settingsDTO.getOverTempLimit();
        this.debug = settingsDTO.isDebug();
        this.lightsOn = settingsDTO.isLightsOn();
    }

    public SettingsDTO getSettings() {
        return super.clone();
    }

    @Override
    public HeaterDataDTO clone() {
        final HeaterDataDTO heaterDataDTO = new HeaterDataDTO(super.clone());
        heaterDataDTO.returnTemp = returnTemp;
        heaterDataDTO.overTemp = overTemp;
        heaterDataDTO.heating = heating;
        heaterDataDTO.circulating = circulating;
        heaterDataDTO.settingsChanged = settingsChanged;
        heaterDataDTO.timerDTO = timerDTO;

        return heaterDataDTO;
    }
}
