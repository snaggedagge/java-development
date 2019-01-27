package dkarlsso.smartmirror.javafx.view;

import dkarlsso.commons.quotes.FamousQuoteDTO;
import javafx.scene.Node;

public interface ViewBuilder2 {


    ViewBuilder clear();
    ViewBuilder addNode(Node node, ViewPosition viewPosition, ViewPlacement viewPlacement);
    ViewBuilder addComponent(ViewComponent viewComponent, ViewPosition viewPosition, ViewPlacement viewPlacement);
    Node getView();

//    Node getClock();
//    Node getDailyQuote(FamousQuoteDTO quote);
//    Node getImageBelowClock(final String image, double reducementScale);
//    Node getImageBelowClock(final String image, int targetWidthPercent, int targetHeightPercent, double reducementScale);
//    Node getView();
//    Node getWeatherData();
//    Node getDayData();
//    Node getEventData();
//    ViewBuilder showCommand(String command);
//    ViewBuilder showMessage(String message, int secondsToShow);
//    Node getLockIcon(boolean locked);
    
    
    
}
