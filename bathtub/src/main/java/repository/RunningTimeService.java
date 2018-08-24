package repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunningTimeService {


    @Autowired
    private RunningTimeRepository runningTimeRepository;

    public void saveTime(RunningTimeDAO timeInHours) {

        if(runningTimeRepository.existsById(timeInHours.getId())) {
            RunningTimeDAO oldTime = runningTimeRepository.findById(timeInHours.getId());
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

    public RunningTimeDAO getRunningTime() {
        // Same id, supposed to be only one post
        final RunningTimeDAO runningTimeDAO = new RunningTimeDAO();
        if (!runningTimeRepository.existsById(runningTimeDAO.getId())) {
            this.saveTime(runningTimeDAO);
            return runningTimeDAO;
        }
        return runningTimeRepository.findById(runningTimeDAO.getId());
    }

}
