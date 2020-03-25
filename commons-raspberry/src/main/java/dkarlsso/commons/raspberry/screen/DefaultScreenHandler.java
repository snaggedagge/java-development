package dkarlsso.commons.raspberry.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DefaultScreenHandler implements ScreenHandler{

    /*
        WARNING: Apperently this also kills sound to raspberry pi so this is not the best solution.
     */
    private static final String POWER_PROGRAM = "vcgencmd";

    private static final String PROGRAM_POWER_SETTING = "display_power";

    private boolean isScreenActive = false;

    public void setScreenPowerMode(final boolean powerOn) throws ScreenHandlerException {
        synchronized (this) {
            try {
                final List<String> command = new ArrayList<String>();
                command.add(POWER_PROGRAM);
                command.add(PROGRAM_POWER_SETTING);
                command.add(powerOn ? "1" : "0");

                final ProcessBuilder builder = new ProcessBuilder(command);
                final Process process = builder.start();
                process.waitFor();
                isScreenActive = powerOn;
            } catch (IOException | InterruptedException e) {
                throw new ScreenHandlerException(e.getMessage(), e);
            }
        }
    }

    public boolean isScreenActive() {
        synchronized (this) {
            return isScreenActive;
        }
    }
}
