package dkarlsso.smartmirror.javafx.view;

import javafx.geometry.Pos;
import javafx.scene.Node;

public interface ViewInterface {

    ViewInterface clear();
    ViewInterface addClock(Pos position);
    Node getView();
    ViewInterface addWeatherData();
    ViewInterface addDayData();
    ViewInterface addEventData();
    ViewInterface showCommand(String command);
    ViewInterface showMessage(String message, int secondsToShow);
    ViewInterface addLockIcon(boolean locked);

}
