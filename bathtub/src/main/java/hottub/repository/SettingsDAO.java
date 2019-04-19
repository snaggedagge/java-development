package hottub.repository;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "heater_settings")
public class SettingsDAO {

    @Id
    private long id = 1;

    @Column
    protected int returnTempLimit = 8;

    @Column
    protected int overTempLimit = returnTempLimit + 15;

    @Column
    protected boolean lightsOn = false;

    @Column
    protected boolean debug = false;

    @Column
    protected int circulationTimeCycle = 5;

    public SettingsDAO() {
    }

    public SettingsDAO(final SettingsDAO settingsDAO) {
        this.lightsOn = settingsDAO.lightsOn;
        this.debug = settingsDAO.debug;
        this.overTempLimit = settingsDAO.overTempLimit;
        this.returnTempLimit = settingsDAO.returnTempLimit;
        this.circulationTimeCycle = settingsDAO.circulationTimeCycle;
    }

    @Override
    protected SettingsDAO clone() {
        final SettingsDAO settingsDAO = new SettingsDAO();

        settingsDAO.setCirculationTimeCycle(circulationTimeCycle);
        settingsDAO.setDebug(debug);
        settingsDAO.setOverTempLimit(overTempLimit);
        settingsDAO.setReturnTempLimit(returnTempLimit);
        settingsDAO.setLightsOn(lightsOn);
        return settingsDAO;
    }
}
