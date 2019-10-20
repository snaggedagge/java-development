package hottub.controller.rpi;


import dkarlsso.commons.raspberry.enums.GPIOPins;
import dkarlsso.commons.raspberry.relay.OptoRelay;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.sensor.temperature.DS18B20;
import dkarlsso.commons.raspberry.sensor.temperature.MAX6675;
import dkarlsso.commons.raspberry.sensor.temperature.TemperatureSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hottub.model.HeaterDataDTO;
import hottub.utils.CustomTimer;

/**
 * Refactored from C++ Arduino script. Ugly but working for now
 */
public class Heater implements HeaterInterface{

    private final static Logger log = LoggerFactory.getLogger(Heater.class);

    private final CustomTimer timer = new CustomTimer();

    private final HeaterDataDTO synchronizedData;

    private RelayInterface circulationRelay;
    private RelayInterface heatingRelay;
    private RelayInterface lightRelay;

    private boolean heat = false;
    private boolean circulate = false;
    private boolean circulateTimer = false; // Used to override circulation in case of cold winter
    private boolean lightsOn = false;
    private boolean debug = true;


    private final TemperatureSensor ovTemp;
    private final TemperatureSensor retTemp;

    private int circulatingTimeLimit = 5;

    private int returnTemp = 0;
    private int overTemp = 0;

    private int returnTempLimit = 15; // These will be overridden by synchronized data object
    private int overTempLimit = returnTempLimit + 10;

    private int heatingTemperatureDelta = 0;
    private int circulationTemperatureDelta = 0;

    /**
     * Real implementation. Terrible i know, should be dependency injected everything, but im lazy
     * @param synchronizedData synchronizedData
     */
    public Heater(final HeaterDataDTO synchronizedData) {
        retTemp = new DS18B20("28-030297942385");
        ovTemp = new DS18B20("28-030c979428d4");

        this.synchronizedData = synchronizedData;

        lightRelay = new OptoRelay(GPIOPins.GPIO19);
        heatingRelay = new OptoRelay(GPIOPins.GPIO13);
        circulationRelay = new OptoRelay(GPIOPins.GPIO26);

    }

    /**
     * For simplified unittesting...
     * @param overTemp
     * @param returnTemp
     * @param synchronizedData
     * @param heatingRelay
     * @param circulationRelay
     * @param lightRelay
     */
    public Heater(TemperatureSensor overTemp, TemperatureSensor returnTemp, final HeaterDataDTO synchronizedData,
                  RelayInterface heatingRelay,RelayInterface circulationRelay,RelayInterface lightRelay) {
        ovTemp = overTemp;
        retTemp = returnTemp;

        this.circulationRelay = circulationRelay;
        this.heatingRelay = heatingRelay;
        this.lightRelay = lightRelay;


        if(synchronizedData == null)
            this.synchronizedData = new HeaterDataDTO();
        else
            this.synchronizedData = synchronizedData;
    }


    protected void setPhysicalOutput() {
        boolean shouldCirculate = circulate || circulateTimer;

        heatingRelay.setState(heat);
        circulationRelay.setState(shouldCirculate);
        lightRelay.setState(lightsOn);
        if(debug) {
            log.debug("DEBUG:Heating " + heat);
            log.debug("DEBUG:Circulating " + circulate);
            log.debug("DEBUG:Circulating timer " + circulateTimer);
            log.debug("DEBUG:Lights " + lightsOn);
        }
    }


    private void setLogicalOutput(){

        if(returnTemp + heatingTemperatureDelta < returnTempLimit)
        {
            heatingTemperatureDelta = 0;
            heat = true;
        }
        else {
            heatingTemperatureDelta = 1;
            heat = false;
        }

        if(overTemp + circulationTemperatureDelta > overTempLimit)
        {
            circulationTemperatureDelta = 3;
            circulate = true;
        } else {
            circulationTemperatureDelta = 0;
            circulate = false;
        }

        // In case RPI gets rebooted while at correct temp, when limits are reset to much below correct value
        if (overTemp  > returnTempLimit + 10 && returnTemp  > returnTempLimit + 10) {
            if (debug) {
                log.warn("Turning off due to much higher temperatures");
            }
            heat = false;
            circulate = false;
        }

        this.checkHighTemperatures();
        this.setCirculationOnTimer();
    }

    @Override
    public void loop() {
        try {

            synchronized(synchronizedData) {
                lightsOn = synchronizedData.isLightsOn();
                debug = synchronizedData.isDebug();
            }

            returnTemp = (int) retTemp.readTemp();
            overTemp = (int) ovTemp.readTemp();

            synchronized(synchronizedData) {
                this.setReturnTempLimit(synchronizedData.getReturnTempLimit());
                this.setOverTempLimit(synchronizedData.getOverTempLimit());
                this.setCirculationTimeCycle(synchronizedData.getCirculationTimeCycle());
            }
            setLogicalOutput();
        } catch (Exception e) {
            log.error("TEMPERATURE ERROR: " + e.getMessage());
            turnAllOff();
        }

        setPhysicalOutput();
        synchronized(synchronizedData) {
            synchronizedData.setOverTemp(overTemp);
            synchronizedData.setReturnTemp(returnTemp);
            synchronizedData.setCirculating(circulate || circulateTimer);
            synchronizedData.setHeating(heat);
            synchronizedData.setCirculationTimeCycle(circulatingTimeLimit);
        }

        if(debug) {
            log.info("TEMP is " + returnTemp + " OVER " + overTemp );
            log.info("LIMITS RETURN is " + returnTempLimit + "  OVER " + overTempLimit );
        }
    }

    private void turnAllOff(){
        heat = false;
        circulate = false;
        setPhysicalOutput();
        log.error("TURN ALL OFF");
    }


    private void checkHighTemperatures() {
        if(overTemp > 60)
        {
            log.error("Over temp high: " + overTemp);
            heat = false;
            circulate = true;
        }
        if(returnTemp > 40)
        {
            log.error("Return temp high: " + returnTemp);
            turnAllOff();
        }
    }

    private void setCirculationOnTimer() {
        if(timer.hasTimePassed(circulatingTimeLimit *60))
        {
            circulateTimer = true;
            if(timer.hasTimePassed(circulatingTimeLimit *60+30))
                timer.reset();
        }
        else
        {
            circulateTimer = false;
        }
    }

    private void setReturnTempLimit(int returnTempLimit) {
        if(returnTempLimit > 5 && returnTempLimit < 45)
            this.returnTempLimit = returnTempLimit;
    }

    private void setCirculationTimeCycle(int circulationTimeCycle) {
        if(circulationTimeCycle > 4 && circulationTimeCycle < 120)
            this.circulatingTimeLimit = circulationTimeCycle;
    }

    private void setOverTempLimit(int overTempLimit) {
        if(overTempLimit < 61 && overTempLimit > returnTempLimit)
            this.overTempLimit = overTempLimit;
    }

}
