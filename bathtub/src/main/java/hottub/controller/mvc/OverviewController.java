package hottub.controller.mvc;

import com.pi4j.io.gpio.*;
import dkarlsso.authentication.CustomAuthentication;
import hottub.repository.SettingsService;
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
import java.util.List;

@Controller
public class OverviewController {

    private final static Logger log = LoggerFactory.getLogger(OverviewController.class);

    private final RunningTimeService runningTimeService;

    private final HeaterThread thread;

    private final HeaterDataDTO heaterDTO;

    private final SettingsService settingsService;

    @Autowired
    public OverviewController(final RunningTimeService runningTimeService, final HeaterDataDTO heaterDTO, SettingsService settingsService) {
        this.settingsService = settingsService;
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
    public String overview(final ModelMap model, final CustomAuthentication authentication) {
        synchronized (heaterDTO) {
            model.addAttribute("settingsDAO", heaterDTO.clone());
        }

        final List<String> infoList = new ArrayList<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            infoList.add("Needs to be logged in to change temperature settings");
        }
        if (!OSHelper.isRaspberryPi()) {
            infoList.add("This instance is a mocked website, Eg. It is not running on the actual bathtub, so it aint controlling shit");
        }
        model.addAttribute("infoList", infoList);
        return "overview";
    }

    @PostMapping(value = "/")
    public String overviewPost(final ModelMap model, HttpSession session, final HttpServletResponse response,
                               final HttpServletRequest request,
                               @ModelAttribute(value = "settingsDAO") final HeaterDataDTO settingsDAO) {
        if (settingsDAO != null) {
            synchronized (heaterDTO) {
                heaterDTO.applySettings(settingsDAO);
                settingsService.saveSettings(settingsDAO.getSettings());
            }
        }
        synchronized (heaterDTO) {
            model.addAttribute("settingsDAO", heaterDTO.clone());
        }

        return "overview";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final ModelMap model, HttpSession session, final HttpServletResponse response,
                        final HttpServletRequest request) {
        synchronized (heaterDTO) {
            model.addAttribute("settingsDAO", heaterDTO.clone());
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
