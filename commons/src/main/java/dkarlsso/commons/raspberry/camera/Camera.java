package dkarlsso.commons.raspberry.camera;

import dkarlsso.commons.model.CommonsException;

import java.io.File;

public interface Camera {

    public File takePicture() throws CommonsException;

    public File takePicture(final String pictureName, final ImageFormat format) throws CommonsException ;

    public File takePicture(final String pictureName) throws CommonsException ;

    public void setPreview(boolean preview);

    public void setWaitingTime(int waitingTimeInSeconds);
}
