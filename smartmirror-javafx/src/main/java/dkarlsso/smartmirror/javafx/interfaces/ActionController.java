package dkarlsso.smartmirror.javafx.interfaces;

import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.multimedia.settings.SoundController;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionController {

    private static final Logger LOG = LogManager.getLogger(ActionController.class);

    private final CommandInvoker<CommandEnum> commandInvoker;

    private final SoundController controller;

    @Autowired
    public ActionController(@Qualifier("ActionExecutor") CommandInvoker<CommandEnum> commandInvoker, SoundController controller) {
        this.commandInvoker = commandInvoker;
        this.controller = controller;
    }

    @PostMapping("/action/{action}")
    public void callAction(@PathVariable CommandEnum action) throws CommandActionException {
        System.out.println(commandInvoker.getClass().getName());
        commandInvoker.executeCommand(action);
    }

    @PostMapping("/volume/{volume}")
    public void setVolume(@PathVariable Integer volume) throws CommonsException, CommandActionException {
        controller.setVolume(volume);
        commandInvoker.executeCommand(CommandEnum.VOLUME);
        LOG.debug("Volume came in as " + volume);
    }

    @PostMapping("/adjust/{change}")
    public void adjust(@PathVariable Integer change) throws CommonsException, CommandActionException {
        controller.setVolume(controller.getVolumeInPercent() + change);
        commandInvoker.executeCommand(CommandEnum.VOLUME);
        LOG.debug("adjust came in as " + change);
    }


    @PostMapping("/mute/{mute}")
    public void mute(@PathVariable String mute) throws CommandActionException {
        LOG.error("mute came in as " + mute);
    }

    @PostMapping("/directive")
    public void directive(@RequestBody final String directive) {
        LOG.error("Directive came in");
        LOG.error(directive);
    }

}

