package hottub.controller.mvc;

import dkarlsso.authentication.CustomAuthentication;
import dkarlsso.commons.raspberry.OSHelper;
import hottub.model.HeaterDataDTO;
import hottub.repository.RunningTimeService;
import hottub.repository.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OverviewController {

    private final static Logger log = LoggerFactory.getLogger(OverviewController.class);

    private final RunningTimeService runningTimeService;

    private final HeaterDataDTO heaterDTO;

    private final SettingsService settingsService;

    @Autowired
    public OverviewController(final RunningTimeService runningTimeService,
                              final HeaterDataDTO heaterDTO,
                              final SettingsService settingsService) {
        this.settingsService = settingsService;
        log.info("Starting Overview Controller");

        this.runningTimeService = runningTimeService;
        this.heaterDTO = heaterDTO;
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
    public String overviewPost(final ModelMap model,
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
