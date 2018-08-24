package dkarlsso.smartmirror.javafx.view;

import javafx.geometry.Pos;
import javafx.scene.Node;

public interface ViewInterface {

    ViewInterface clear();
    ViewInterface addClock(Pos position);
    ViewInterface addImageBelowClock(final String image, double reducementScale);
    ViewInterface addImageBelowClock(final String image, int targetWidthPercent, int targetHeightPercent, double reducementScale);
    Node getView();
    ViewInterface addWeatherData();
    ViewInterface addDayData();
    ViewInterface addEventData();
    ViewInterface showCommand(String command);
    ViewInterface showMessage(String message, int secondsToShow);
    ViewInterface addLockIcon(boolean locked);

}
