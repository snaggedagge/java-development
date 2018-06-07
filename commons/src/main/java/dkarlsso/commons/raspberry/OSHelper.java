package dkarlsso.commons.raspberry;

import java.io.File;

public final class OSHelper {


    private static boolean isPiOS = false;

    private OSHelper() {
    }

    public static boolean isRaspberryPi() {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            // Ugly hack but is working
            isPiOS = new File("/home/pi/").exists();
        }
        return isPiOS;
    }

}
