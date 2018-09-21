package dkarlsso.smartmirror.javafx.model.application;

import java.io.File;

public final class ApplicationUtils {

    public static final String APPLICATION_NAME="SmartMirror";

    public static File getRootFolder() {

        final File rootFolder = new File(System.getProperty("user.home"), APPLICATION_NAME);
        if( !rootFolder.exists() || !rootFolder.isDirectory()) {
            rootFolder.mkdir();
        }
        return rootFolder;
    }

    public static File getSubfolder(final String subfolder) {
        final File subFolder = new File(getRootFolder(), subfolder);
        if( !subFolder.exists() || !subFolder.isDirectory()) {
            subFolder.mkdir();
        }
        return subFolder;
    }

    public static int getShowDayCount() {
        return 5;
    }

 /*
     static {
        final File file = new File(System.getProperty("user.home"), APPLICATION_NAME);

        if(!file.exists() || !file.isDirectory()) {
            boolean test = file.mkdir();
            if(!test && !file.isDirectory()) {
                //throw new RuntimeException("Could not create directory");
            }
        }
        final List<File> files = new ArrayList<>();
        files.addAll(Arrays.asList(file.listFiles()));
        files.add(file);

        for(File f : files) {
            if(f.isDirectory()) {
                f.setReadable(true);
                f.setWritable(true);
            }
        }

    }
  */
}
