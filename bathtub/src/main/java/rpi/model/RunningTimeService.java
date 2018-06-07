package rpi.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RunningTimeService {


    @Autowired
    private RunningTimeRepository runningTimeRepository;

    public void saveTime(RunningTime timeInHours) {

        if(runningTimeRepository.existsById(timeInHours.getId())) {
            RunningTime oldTime = runningTimeRepository.findById(timeInHours.getId());
            runningTimeRepository.delete(oldTime);
            timeInHours.setBathTotalTimeHours(oldTime.getBathTotalTimeHours().add(timeInHours.getBathTotalTimeHours()));
            timeInHours.setRunningTimeCirculationHours(oldTime.getRunningTimeCirculationHours().add(timeInHours.getRunningTimeCirculationHours()));
            timeInHours.setRunningTimeHeaterHours(oldTime.getRunningTimeHeaterHours().add(timeInHours.getRunningTimeHeaterHours()));
            runningTimeRepository.save(timeInHours);
        }
        else {
            runningTimeRepository.save(timeInHours);
        }
    }

    public RunningTime getRunningTime() {
        // Same id, supposed to be only one post
        final RunningTime runningTime = new RunningTime();
        if (!runningTimeRepository.existsById(runningTime.getId())) {
            this.saveTime(runningTime);
            return runningTime;
        }
        return runningTimeRepository.findById(runningTime.getId());
    }

}
