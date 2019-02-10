package hottub.controller.mvc;

import hottub.model.HeaterDataDTO;
import hottub.model.TimerDTO;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class TimerController {

    private final HeaterDataDTO heaterDTO;

    @Autowired
    public TimerController(final HeaterDataDTO heaterDTO) {
        this.heaterDTO = heaterDTO;
    }

    @GetMapping("/setTimer")
    public String getTimer(final Model model) {
        TimerDTO timerDTO;
        synchronized (heaterDTO) {
            if(heaterDTO.getTimerDTO() != null) {
                timerDTO = heaterDTO.getTimerDTO();
                model.addAttribute("alreadyOnTimer",true);

                int minutesUntilStart = Minutes.minutesBetween(new DateTime(), timerDTO.getStartHeatingTime()).getMinutes();

                model.addAttribute("minutesUntilStart",minutesUntilStart%60);
                model.addAttribute("hoursUntilStart",minutesUntilStart/60);
            }
            else {
                timerDTO = new TimerDTO();
            }
        }
        model.addAttribute("timer",timerDTO);
        return "timer";
    }

    @PostMapping("/setTimer")
    public String setTimer(final Model model, HttpSession session, @ModelAttribute TimerDTO timerDTO) {

        final List<String> errorList = new ArrayList<>();

        if(timerDTO != null) {
            if(timerDTO.getStartHeatingTime().toDate().getTime() < new Date().getTime()) {
                errorList.add("Need to set a correct date");
            }
            if(errorList.isEmpty()) {
                if (session.getAttribute("ADMIN") != null && (boolean) session.getAttribute("ADMIN")) {
                    synchronized (heaterDTO) {
                        model.addAttribute("timerIsSet",true);
                        heaterDTO.setTimerDTO(timerDTO);
                    }
                }
                else {
                    errorList.add("Need to be logged in");
                }
            }
        }
        else {
            timerDTO = new TimerDTO();
        }

        model.addAttribute("timer",timerDTO);
        model.addAttribute("errorList",errorList);
        return "timer";
    }

}
