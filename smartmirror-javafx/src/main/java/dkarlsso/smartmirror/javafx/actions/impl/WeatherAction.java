package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.commons.speechrecognition.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.annotation.Action;

@Action(commandName = CommandEnum.WEATHER)
public class WeatherAction implements CommandAction {


    @Override
    public void execute() {
/*        Platform.runLater(() -> {
            if (viewBuilder instanceof ViewBuilder) {
                viewBuilder = new LightViewBuilder(dataService);
                updateSequenceMillis = 60000;
            } else {
                viewBuilder = new ViewBuilder(dataService);
                updateSequenceMillis = 5000;
            }
            initAnimation();
            Platform.runLater(() -> viewBuilder.showCommand(CommandEnum.WEATHER.prettyName()));
        });*/
    }
}
