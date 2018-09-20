package dkarlsso.smartmirror.javafx.view;

import dkarlsso.smartmirror.javafx.model.CommandEnum;
import javafx.scene.Node;

public interface ViewControllerInterface {

    void initAnimation(int updateSequenceMillis, String command);

    void displayView(Node node);

    void displayStandardView(String command);

    void refreshView();

}
