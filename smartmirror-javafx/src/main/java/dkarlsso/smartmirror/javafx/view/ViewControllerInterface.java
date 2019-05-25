package dkarlsso.smartmirror.javafx.view;

public interface ViewControllerInterface {

    void initAnimation(int updateSequenceMillis, String command);

    void displayStandardView(String command);

    void showMessage(String command, int timeToShow);
}
