package dkarlsso.smartmirror.javafx.actions.impl;

import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.smartmirror.javafx.model.interfaces.DataService;
import javafx.application.Platform;

//@Action(commandName = CommandEnum.WEATHER)
public class WeatherAction implements CommandAction {


  //  @Inject
  //  private BasicContainer<ViewBuilder> viewBuilder;

    //@Inject
    private DataService dataService;

  //  @Inject
//    private ViewControllerInterface viewControllerInterface;

    @Override
    public void execute() {
        Platform.runLater(() -> {

            int updateSequenceMillis = 60000;

/*
            if (viewBuilder.get() instanceof HeavyViewBuilder) {
                viewBuilder.set(new LightViewBuilder(dataService));
            } else {
                viewBuilder.set(new HeavyViewBuilder(dataService));
                updateSequenceMillis = 5000;
            }
            viewControllerInterface.initAnimation(updateSequenceMillis, CommandEnum.WEATHER.prettyName());
 */
        });
    }
}
