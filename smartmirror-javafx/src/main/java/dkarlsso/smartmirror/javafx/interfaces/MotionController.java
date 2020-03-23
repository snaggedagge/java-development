package dkarlsso.smartmirror.javafx.interfaces;

import dkarlsso.commons.raspberry.screen.ScreenHandler;
import dkarlsso.commons.raspberry.screen.ScreenHandlerException;
import dkarlsso.smartmirror.javafx.model.MotionUpdateRequest;
import dkarlsso.smartmirror.javafx.model.interfaces.StateService;
import dkarlsso.smartmirror.javafx.services.MotionService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MotionController {

    private final StateService stateService;

    private final ScreenHandler screenHandler;

    private final MotionService motionService;

    private final SimpMessagingTemplate template;

    @Autowired
    public MotionController(StateService stateService,
                            ScreenHandler screenHandler,
                            MotionService motionService,
                            SimpMessagingTemplate template) {
        this.stateService = stateService;
        this.screenHandler = screenHandler;
        this.motionService = motionService;
        this.template = template;
    }

    @RequestMapping("/motion/{motion}")
    public void notifyMotion(@PathVariable final String motion) {

        // Just for now...
        try {
            stateService.setLastActivated(new DateTime());
            screenHandler.setScreenPowerMode(true);
        } catch (ScreenHandlerException e) {
            log.error(e.getMessage(), e);
        }
        System.out.println("Motion " + motion);
    }

    // Seem to follow camera size, eg. X 0 - 320 and Y 0 - 240 (Didnt get y below 120 but max cap was 240)
    // Ugly as shit, fix this...
    @CrossOrigin(origins = "*")
    @PostMapping("/motion/hand")
    public void notifyMotion(@RequestBody final MotionUpdateRequest motionUpdateRequest) {
        motionService.notifyHandMotion(motionUpdateRequest, (motion) -> template.convertAndSend("/topic/motion", motion));
    }


}
