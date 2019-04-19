package hottub.model;

import hottub.repository.SettingsDAO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeaterDataDTO extends SettingsDAO {

    private int returnTemp = 0;

    private int overTemp = 0;

    private boolean heating = false;

    private boolean circulating = false;

    private boolean settingsChanged = false;

    @Transient
    private TimerDTO timerDTO = null;

    private double heaterTimeSinceStarted = 0;

    public HeaterDataDTO() {

    }

    public HeaterDataDTO(final SettingsDAO settingsDAO) {
        super(settingsDAO);
    }

    public void applySettings(final SettingsDAO settingsDAO) {
        this.settingsChanged = true;
        this.circulationTimeCycle = settingsDAO.getCirculationTimeCycle();
        this.returnTempLimit = settingsDAO.getReturnTempLimit();
        this.overTempLimit = settingsDAO.getOverTempLimit();
        this.debug = settingsDAO.isDebug();
        this.lightsOn = settingsDAO.isLightsOn();
    }

    public SettingsDAO getSettings() {
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
