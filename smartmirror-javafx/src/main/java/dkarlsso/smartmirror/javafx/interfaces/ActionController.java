package dkarlsso.smartmirror.javafx.interfaces;

import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionController {

    @Autowired
    private CommandInvoker commandInvoker;

    @PostMapping("/action/{action}")
    public void callAction(@PathVariable CommandEnum action) throws CommandActionException {
        System.out.println(commandInvoker.getClass().getName());
        commandInvoker.executeCommand(action);
    }

}

