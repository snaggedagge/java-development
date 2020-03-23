package dkarlsso.smartmirror.javafx.actions;

import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.smartmirror.javafx.model.CommandEnum;

import java.util.Set;

public interface ActionAwareInvoker extends CommandInvoker<CommandEnum> {

    Set<CommandEnum> supports();
}
