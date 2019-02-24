package hottub.controller.mvc;

import com.pi4j.io.gpio.*;
import dkarlsso.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import hottub.controller.rpi.Heater;
import hottub.controller.rpi.HeaterInterface;
import hottub.controller.rpi.HeaterThread;
import hottub.controller.rpi.MockHeater;
import hottub.model.HeaterDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import hottub.repository.RunningTimeService;
import dkarlsso.commons.raspberry.OSHelper;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            heater = new MockHeater(heaterDTO);
            synchronized (heaterDTO) {
                heaterDTO.setReturnTemp(heaterDTO.getReturnTempLimit()-1);
                heaterDTO.setOverTemp(heaterDTO.getOverTempLimit()-1);
                heaterDTO.setLightsOn(true);
                heaterDTO.setHeating(true);
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
                               final HttpServletRequest request, final CustomAuthentication authentication,
                               @ModelAttribute(value = "settingsDTO") final HeaterDataDTO settingsDTO) {
        if (settingsDTO != null) {
            synchronized (heaterDTO) {
                heaterDTO.applySettings(settingsDTO);
            }
        }
        synchronized (heaterDTO) {
            model.addAttribute("settingsDTO", heaterDTO.clone());
        }
        if (!authentication.isAuthenticated()) {
            model.addAttribute("infoList", Arrays.asList("Needs to be logged in to change temperature settings"));
        }

        return "overview";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final ModelMap model, HttpSession session, final HttpServletResponse response,
                        final HttpServletRequest request) {
        synchronized (heaterDTO) {
            model.addAttribute("settingsDTO", heaterDTO.clone());
        }
        final List<String> errorList = new ArrayList<>();
        errorList.add("Need to have access to do that!");

        model.addAttribute("errorList", errorList);
        return "overview";
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats(final ModelMap model) {
        model.addAttribute("runningTime", runningTimeService.getRunningTime());
        synchronized (heaterDTO) {
            model.addAttribute("timeHeatingSinceStarted", heaterDTO.getHeaterTimeSinceStarted());
        }
        return "stats";
    }
}
