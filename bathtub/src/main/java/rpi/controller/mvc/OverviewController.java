package rpi.controller.mvc;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rpi.HeaterThread;
import rpi.SynchronizedHeaterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import rpi.config.WebConfig;
import rpi.model.RunningTimeService;
import dkarlsso.commons.raspberry.OSHelper;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class OverviewController {

    private final static Logger log = LoggerFactory.getLogger(OverviewController.class);

    public static final String ACTIVE_URL_CLASS = "active";

    private final RunningTimeService runningTimeService;

    private final HeaterThread thread;

    private final SynchronizedHeaterDTO heaterDTO;


    @Autowired
    public OverviewController(final RunningTimeService runningTimeService) {
        this.runningTimeService = runningTimeService;
        log.info("Starting Overview Controller");




        ApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfig.class);
        this.heaterDTO = ctx.getBean(SynchronizedHeaterDTO.class);

        if(OSHelper.isRaspberryPi()) {
            GpioFactory.getInstance();
            thread = new HeaterThread(heaterDTO, runningTimeService);
            thread.start();
        }
        else {
            synchronized (heaterDTO) {
                heaterDTO.setReturnTemp(heaterDTO.getReturnTempLimit()-1);
                heaterDTO.setOverTemp(heaterDTO.getOverTempLimit()-1);
                heaterDTO.setLightsOn(true);
                heaterDTO.setHeating(true);
            }
            thread = null;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String overview(final ModelMap model, HttpSession session, final HttpServletResponse response,
                           final HttpServletRequest request,
                           @RequestParam(required = false, defaultValue = "lol") String turnLightOn,
                           @RequestParam(required = false, defaultValue = "lol") String returnTempLimit,
                           @RequestParam(required = false, defaultValue = "lol") String overTempLimit,
                           @RequestParam(required = false, defaultValue = "lol") String circulationTimeCycle,
                           @RequestParam(required = false, defaultValue = "lol") String debug) {

        if(!turnLightOn.equals("lol") || !returnTempLimit.equals("lol") || !overTempLimit.equals("lol") || !debug.equals("lol") || !circulationTimeCycle.equals("lol")) {

            if(session.getAttribute("ADMIN") != null && (boolean) session.getAttribute("ADMIN") ) {
                setInput(returnTempLimit,overTempLimit,turnLightOn,debug,circulationTimeCycle);
                return "redirect:/";
            } else {
                model.addAttribute("INFO","Assholes who are not superior or admins shall not pass");
            }
        }
        setOutput(model);

        return "overview";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(final ModelMap model, HttpSession session, final HttpServletResponse response,
                        final HttpServletRequest request,
                        @RequestParam(required = false, defaultValue = "lol") String password,
                        @RequestParam(required = false, defaultValue = "lol") String username) {

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

    private void setInput(final String returnTempLimit, final String overTempLimit,
                     final String turnLightOn, final String debug,final String circulationTimeCycle) {
        synchronized (heaterDTO) {
            if(!returnTempLimit.equals("lol")) {
                heaterDTO.setReturnTempLimit(Integer.parseInt(returnTempLimit));
            }
            if(!overTempLimit.equals("lol")){
                heaterDTO.setOverTempLimit(Integer.parseInt(overTempLimit));
            }
            if(!circulationTimeCycle.equals("lol")){
                heaterDTO.setCirculationTimeCycle(Integer.parseInt(circulationTimeCycle));
            }
            if(turnLightOn.equals("false")) {
                heaterDTO.setLightsOn(false);
            }
            else if(turnLightOn.equals("true")){
                heaterDTO.setLightsOn(true);
            }

            if(debug.equals("false")) {
                heaterDTO.setDebug(false);
            }
            else if(debug.equals("true")){
                heaterDTO.setDebug(true);
            }

            heaterDTO.setSettingsChanged(true);
        }
    }

    private SynchronizedHeaterDTO setOutput(final ModelMap model) {
        SynchronizedHeaterDTO output = new SynchronizedHeaterDTO();
        synchronized (heaterDTO) {

            output.setCirculating(heaterDTO.isCirculating());
            output.setDebug(heaterDTO.isDebug());
            output.setHeating(heaterDTO.isHeating());
            output.setLightsOn(heaterDTO.isLightsOn());
            output.setOverTemp(heaterDTO.getOverTemp());

            model.addAttribute("RETURN_TEMP",heaterDTO.getReturnTemp());
            model.addAttribute("OVER_TEMP",heaterDTO.getOverTemp());

            model.addAttribute("RETURN_TEMP_LIMIT",heaterDTO.getReturnTempLimit());
            model.addAttribute("OVER_TEMP_LIMIT",heaterDTO.getOverTempLimit());

            model.addAttribute("TIMER_CYCLE",heaterDTO.getCirculationTimeCycle());

            model.addAttribute("LED",heaterDTO.isLightsOn());
            model.addAttribute("HEATING",heaterDTO.isHeating());
            model.addAttribute("CIRCULATING",heaterDTO.isCirculating());
            model.addAttribute("DEBUG",heaterDTO.isDebug());
        }
        return output;
    }

}
