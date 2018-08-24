package controller.rpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import model.HeaterDataDTO;
import repository.RunningTimeDAO;
import repository.RunningTimeService;
import dkarlsso.commons.raspberry.RunningClock;
import model.TimerDTO;

import java.util.Date;

public class HeaterThread extends Thread{

    private final static Logger log = LoggerFactory.getLogger(HeaterThread.class);

    private final RunningTimeService runningTimeService;

    private final RunningClock circulationClock = new RunningClock();

    private final RunningClock heaterClock = new RunningClock();

    private final RunningClock bathClock = new RunningClock();

    private final HeaterDataDTO heaterDTO;

    private final HeaterInterface heater;

    private boolean running = true;


    public HeaterThread(final HeaterDataDTO heaterDTO, final RunningTimeService runningTimeService, final HeaterInterface heaterInterface) {
        super();
        this.heaterDTO = heaterDTO;
        this.runningTimeService = runningTimeService;
        heater = heaterInterface;
    }


    @Override
    public void run() {
        log.info("Starting heating thread");

        while(running) {
            try {
                heater.loop();

                synchronized (heaterDTO) {
                    heaterClock.setStartedRunning(heaterDTO.isHeating());
                    circulationClock.setStartedRunning(heaterDTO.isCirculating());

                    boolean isBathtime = heaterDTO.getReturnTemp() > 35 && heaterDTO.getReturnTempLimit() > 35;
                    bathClock.setStartedRunning(isBathtime);

                    runningTimeService.saveTime(new RunningTimeDAO(heaterClock.getRunningTimeAndReset(),
                            circulationClock.getRunningTimeAndReset(), bathClock.getRunningTimeAndReset()));



                    final TimerDTO timerDTO = heaterDTO.getTimerDTO();
                    if(timerDTO != null && timerDTO.getStartHeatingTime().toDate().getTime() < new Date().getTime()) {
                        log.warn("ACTIVATING TIMER");
                        heaterDTO.setReturnTempLimit(timerDTO.getHottubTemperature());
                        heaterDTO.setOverTempLimit(timerDTO.getCirculationTemperature());
                        heaterDTO.setTimerDTO(null);
                    }
                }

                // Only too see if settings changes so they will take affect straight away
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
