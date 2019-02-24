package dkarlsso.commons.raspberry.camera;

import dkarlsso.commons.raspberry.exception.CommonsRaspberryException;

import java.io.File;

public interface Camera {

    public File takePicture() throws CommonsRaspberryException, CommonsRaspberryException;

    public File takePicture(final String pictureName, final ImageFormat format) throws CommonsRaspberryException;

    public File takePicture(final String pictureName) throws CommonsRaspberryException;

    public void setPreview(boolean preview);

    public void setWaitingTime(int waitingTimeInSeconds);
}
