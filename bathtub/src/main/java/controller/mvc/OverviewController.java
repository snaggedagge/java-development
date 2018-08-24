package controller.mvc;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import controller.rpi.Heater;
import controller.rpi.HeaterInterface;
import controller.rpi.HeaterThread;
import controller.rpi.MockHeater;
import model.HeaterDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import repository.RunningTimeService;
import dkarlsso.commons.raspberry.OSHelper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class OverviewController {

    private final static Logger log = LoggerFactory.getLogger(OverviewController.class);

    private final RunningTimeService runningTimeService;

    private final HeaterThread thread;

    private final HeaterDataDTO heaterDTO;

    @Autowired
    public OverviewController(final RunningTimeService runningTimeService, final HeaterDataDTO heaterDTO) {
        log.info("Starting Overview Controller");

        this.runningTimeService = runningTimeService;
        this.heaterDTO = heaterDTO;

        final HeaterInterface heater;

        if(OSHelper.isRaspberryPi()) {
            heater = new Heater(heaterDTO);
            GpioFactory.getInstance();
        }
        else {
            heater = new MockHeater();
            synchronized (heaterDTO) {
//                heaterDTO.setReturnTemp(heaterDTO.getReturnTempLimit()-1);
//                heaterDTO.setOverTemp(heaterDTO.getOverTempLimit()-1);
//                heaterDTO.setLightsOn(true);
//                heaterDTO.setHeating(true);
            }
        }

        thread = new HeaterThread(heaterDTO, runningTimeService, heater);
        thread.start();
    }

    @GetMapping(value = "/")
    public String overview(final ModelMap model) {
        synchronized (heaterDTO) {
            model.addAttribute("settingsDTO", heaterDTO.clone());
        }
        return "overview";
    }

    @PostMapping(value = "/")
    public String overviewPost(final ModelMap model, HttpSession session, final HttpServletResponse response,
                           final HttpServletRequest request,
                           @ModelAttribute(value = "settingsDTO") final HeaterDataDTO settingsDTO) {
        if (settingsDTO != null) {
            if (session.getAttribute("ADMIN") != null && (boolean) session.getAttribute("ADMIN")) {
                synchronized (heaterDTO) {
                    heaterDTO.applySettings(settingsDTO);
                }
            }
            else {
                model.addAttribute("INFO","Assholes who are not superior or admins shall not pass");
            }
        }
        synchronized (heaterDTO) {
            model.addAttribute("settingsDTO", heaterDTO.clone());
        }

        return "overview";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final ModelMap model, HttpSession session, final HttpServletResponse response,
                        final HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "lol") String password,
                        @RequestParam(required = false, defaultValue = "lol") String username) {

        // Oh you cought me, too lazy to setup spring security right now
        if(password.equals("banan") && username.equals("admin"))
        {
            session.setAttribute("ADMIN",true);
            return "redirect:/";
        } else if(!password.equals("lol") || !username.equals("lol")) {
            model.addAttribute("INFO","Assholes who are not superior or admins shall not pass");
        }
        return "login";
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats(final ModelMap model) {
        model.addAttribute("runningTime", runningTimeService.getRunningTime());
        return "stats";
    }
}
