package dkarlsso.smartmirror.javafx.view;

import dkarlsso.commons.quotes.FamousQuoteDTO;
import javafx.geometry.Pos;
import javafx.scene.Node;

public interface ViewBuilder {

    ViewBuilder clear();
    ViewBuilder addClock(Pos position);
    ViewBuilder addDailyQuote(FamousQuoteDTO quote);
    ViewBuilder addImageBelowClock(final String image, double reducementScale);
    ViewBuilder addImageBelowClock(final String image, int targetWidthPercent, int targetHeightPercent, double reducementScale);
    Node getView();
    ViewBuilder addWeatherData();
    ViewBuilder addDayData();
    ViewBuilder addEventData();
    ViewBuilder showCommand(String command);
    ViewBuilder showMessage(String message, int secondsToShow);
    ViewBuilder addLockIcon(boolean locked);

}
