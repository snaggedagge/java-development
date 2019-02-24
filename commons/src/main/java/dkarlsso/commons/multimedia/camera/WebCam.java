package dkarlsso.commons.multimedia.camera;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.raspberry.camera.Camera;
import dkarlsso.commons.raspberry.camera.ImageFormat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebCam implements Camera {

    private static final String FORMAT_PICTURE = "jpg";

    private static final String FORMAT_DATE_REGEXP = "yyyy.MM.dd HH-mm-ss";

    private static final String EXTENSION_IMAGE = "." + FORMAT_PICTURE;



    // NOT NEEDED
    private int waitingTime = 3000;
    private boolean preview = true;



    private final File pictureFolder;

    private Webcam webcam = null;


    public WebCam(final File pictureFolder) {

        boolean isOpened = false;

        final Date initialStart = new Date();

        do {
            try {
                Webcam.setDriver(new V4l4jDriver());
                webcam = Webcam.getDefault(10000);
                if(webcam != null) {
                    webcam.setViewSize(new Dimension(1280,960));
                    if (!webcam.isOpen()) {
                        webcam.open();
                    }
                } else {
                    Thread.sleep(10000);
                }
                isOpened = webcam != null;
            } catch (Exception e) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e1) {
                }
            }

            if(new Date().getTime() - initialStart.getTime() > 60 * 1000) {
                break;
            }

        } while (!isOpened);



        this.pictureFolder = pictureFolder;
        if(!pictureFolder.exists() || !pictureFolder.isDirectory()) {
            pictureFolder.mkdir();
        }
    }

    public File takePicture() throws CommonsException {
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_REGEXP) ;
        return takePicture(dateFormat.format(date));
    }


    public File takePicture(final String pictureName, final ImageFormat format) throws CommonsException {
        final File pictureFile = new File(pictureFolder, pictureName + "." + format.getFormat());
        try {
            ImageIO.write(webcam.getImage(), format.getFormat(), pictureFile);
        } catch (Exception e) {
            throw new CommonsException("Could not take image " + e.getMessage(), e);
        }
        return pictureFile;
    }

    public File takePicture(final String pictureName) throws CommonsException {
        return takePicture(pictureName, ImageFormat.FORMAT_JPG);
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public void setWaitingTime(int waitingTimeInSeconds) {
        this.waitingTime = waitingTimeInSeconds*1000;
    }
}
