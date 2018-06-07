package rpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpi.model.RunningTime;
import rpi.model.RunningTimeService;
import dkarlsso.commons.raspberry.OSHelper;
import dkarlsso.commons.raspberry.RunningClock;

import java.util.Date;

public class HeaterThread extends Thread{

    private final RunningTimeService runningTimeService;

    private final RunningClock circulationClock = new RunningClock();

    private final RunningClock heaterClock = new RunningClock();

    private final RunningClock bathClock = new RunningClock();

    private final SynchronizedHeaterDTO heaterDTO;

    private final Heater heater;

    private boolean running = true;

    private boolean isBathtime = false;

    private final static Logger log = LoggerFactory.getLogger(HeaterThread.class);

    public HeaterThread(final SynchronizedHeaterDTO heaterDTO,final RunningTimeService runningTimeService) {
        super();
        this.heaterDTO = heaterDTO;
        this.runningTimeService = runningTimeService;
        if(OSHelper.isRaspberryPi()) {
            heater = new Heater(heaterDTO);
        }
        else {
            heater = null;
        }
    }


    @Override
    public void run() {

        log.info("Starting heating thread");

        while(running) {
            try {
                heater.loop();


                synchronized (heaterDTO) {
                    if(heaterDTO.getReturnTemp() > 35 && heaterDTO.getReturnTempLimit() > 35) {
                        isBathtime = true;
                    }
                    if(heaterDTO.getReturnTemp() < 35 && heaterDTO.getReturnTempLimit() < 35) {
                        isBathtime = false;
                    }

                    heaterClock.setStartedRunning(heaterDTO.isHeating());
                    circulationClock.setStartedRunning(heaterDTO.isCirculating());
                    bathClock.setStartedRunning(isBathtime);
                    
                    runningTimeService.saveTime(new RunningTime(heaterClock.getRunningTimeAndReset(),
                            circulationClock.getRunningTimeAndReset(), bathClock.getRunningTimeAndReset()));


                    if(heaterDTO.getTimerDTO() != null && heaterDTO.getTimerDTO().getStartHeatingTime().toDate().getTime() < new Date().getTime()) {
                        log.error("ACTIVATING TIMER");
                        heaterDTO.setReturnTempLimit(heaterDTO.getTimerDTO().getHottubTemperature());
                        heaterDTO.setReturnTempLimit(heaterDTO.getTimerDTO().getCirculationTemperature());
                        heaterDTO.setTimerDTO(null);
                    }

                }


                boolean settingsChanged = false;
                for(int i=0;i<20 && !settingsChanged ;i++) {
                    Thread.sleep(500);
                    synchronized (heaterDTO) {
                        settingsChanged = heaterDTO.isSettingsChanged();
                        heaterDTO.setSettingsChanged(false);
                    }
                }
            } catch (InterruptedException e) {
            }
        }
        log.info("Ending heating thread");
    }
    @Override
    public void interrupt() {
        running = false;
    }
}
