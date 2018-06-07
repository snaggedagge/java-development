package dkarlsso.commons.raspberry.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScreenHandler {

    private static final String POWER_PROGRAM = "vcgencmd";

    private static final String PROGRAM_POWER_SETTING = "display_power";

    private boolean isScreenActive = true;

    public void setScreenPowerMode(final boolean powerOn) throws ScreenHandlerException {
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

    public boolean isScreenActive() {
        return isScreenActive;
    }
}
