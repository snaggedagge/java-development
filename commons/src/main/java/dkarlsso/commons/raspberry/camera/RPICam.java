package dkarlsso.commons.raspberry.camera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RPICam {

    private static final String PICTURE_PROGRAM = "raspistill";

    private static final String COMMAND_PICTURE_FORMAT = "-e";

    private static final String COMMAND_FILENAME = "-o";

    private static final String COMMAND_NO_PREVIEW = "-n";

    private static final String COMMAND_FULLSCREEN_PREVIEW = "-f";

    private static final String COMMAND_VERTICAL_FLIP = "-vf";

    private static final String COMMAND_HORIZONTAL_FLIP = "-hf";

    private static final String COMMAND_WAITING_TIME = "-t";

    private static final String FORMAT_PICTURE = "jpg";

    private static final String FORMAT_DATE_REGEXP = "yyyy.MM.dd HH-mm-ss";

    private static final String EXTENSION_IMAGE = "." + FORMAT_PICTURE;

    private final File pictureFolder;

    private boolean isVerticallyFlipped = true;

    private boolean isHorizontallyFlipped = true;

    private int waitingTime = 3000;

    private boolean preview = true;

    public RPICam(final File pictureFolder) {
        this.pictureFolder = pictureFolder;
        if(!pictureFolder.exists() || !pictureFolder.isDirectory()) {
            pictureFolder.mkdir();
        }
    }

    public File takePicture() throws RPICameraException {
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_REGEXP) ;
        return takePicture(dateFormat.format(date));
    }

    public File takePicture(final String pictureName) throws RPICameraException {
        try {
            final String fullImagePath = pictureFolder.getAbsolutePath()
                    + File.separator + pictureName + EXTENSION_IMAGE;

            final List<String> command = new ArrayList<String>();

            command.add(PICTURE_PROGRAM);

            if (isVerticallyFlipped) {
                command.add(COMMAND_VERTICAL_FLIP);
            }
            if (isHorizontallyFlipped) {
                command.add(COMMAND_HORIZONTAL_FLIP);
            }
            if (preview) {
                command.add(COMMAND_FULLSCREEN_PREVIEW);
                command.add(COMMAND_WAITING_TIME);
                command.add("" + waitingTime);
            }
            else {
                command.add(COMMAND_NO_PREVIEW);
                command.add(COMMAND_WAITING_TIME);
                command.add("1");
            }

            command.add(COMMAND_PICTURE_FORMAT);
            command.add(FORMAT_PICTURE);
            command.add(COMMAND_FILENAME);
            command.add(fullImagePath);

            final ProcessBuilder builder = new ProcessBuilder(command);
            final Process process = builder.start();
            process.waitFor();

            return new File(fullImagePath);
        } catch (IllegalMonitorStateException | InterruptedException | IOException e) {
            throw new RPICameraException(e.getMessage(), e);
        }
    }

    public boolean isVerticallyFlipped() {
        return isVerticallyFlipped;
    }

    public boolean isHorizontallyFlipped() {
        return isHorizontallyFlipped;
    }

    public void setVerticallyFlipped(boolean verticallyFlipped) {
        isVerticallyFlipped = verticallyFlipped;
    }

    public void setHorizontallyFlipped(boolean horizontallyFlipped) {
        isHorizontallyFlipped = horizontallyFlipped;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public void setWaitingTime(int waitingTimeInSeconds) {
        this.waitingTime = waitingTimeInSeconds*1000;
    }
}
