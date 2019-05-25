package dkarlsso.smartmirror.javafx.model.interfaces;

import org.joda.time.DateTime;

public interface StateService {

    boolean isRadioPlaying();

    int getVolumeInPercent();

    DateTime getLastActivated();

    void setLastActivated(DateTime lastActivated);

}
