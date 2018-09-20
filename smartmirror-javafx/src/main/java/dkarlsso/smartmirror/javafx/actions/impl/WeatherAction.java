package dkarlsso.smartmirror.javafx.actions.impl;

import com.google.inject.Inject;
import dkarlsso.commons.container.BasicContainer;
import dkarlsso.smartmirror.javafx.model.CommandEnum;
import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.actions.Action;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import dkarlsso.smartmirror.javafx.view.ViewBuilder;
import dkarlsso.smartmirror.javafx.view.ViewControllerInterface;
import dkarlsso.smartmirror.javafx.view.impl.HeavyViewBuilder;
import dkarlsso.smartmirror.javafx.view.impl.LightViewBuilder;
import javafx.application.Platform;

@Action(commandName = CommandEnum.WEATHER)
public class WeatherAction implements CommandAction {


    @Inject
    private BasicContainer<ViewBuilder> viewBuilder;

    @Inject
    private DataService dataService;

    @Inject
    private ViewControllerInterface viewControllerInterface;

    @Override
    public void execute() {
        Platform.runLater(() -> {

            int updateSequenceMillis = 60000;

            if (viewBuilder.get() instanceof HeavyViewBuilder) {
                viewBuilder.set(new LightViewBuilder(dataService));
            } else {
                viewBuilder.set(new HeavyViewBuilder(dataService));
                updateSequenceMillis = 5000;
            }
            viewControllerInterface.initAnimation(updateSequenceMillis, CommandEnum.WEATHER.prettyName());
        });
    }
}
