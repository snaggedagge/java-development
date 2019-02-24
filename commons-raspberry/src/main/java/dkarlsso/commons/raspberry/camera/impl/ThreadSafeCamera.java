package dkarlsso.commons.raspberry.camera.impl;

import dkarlsso.commons.raspberry.camera.Camera;
import dkarlsso.commons.raspberry.camera.ImageFormat;
import dkarlsso.commons.raspberry.exception.CommonsRaspberryException;

import java.io.File;

public final class ThreadSafeCamera implements Camera {

    private final Camera camera;

    public ThreadSafeCamera(final Camera camera) {
        this.camera = camera;
    }


    @Override
    public File takePicture() throws CommonsRaspberryException {
        synchronized (camera) {
            return camera.takePicture();
        }
    }

    @Override
    public File takePicture(String pictureName, ImageFormat format) throws CommonsRaspberryException {
        synchronized (camera) {
            return camera.takePicture(pictureName, format);
        }
    }

    @Override
    public File takePicture(String pictureName) throws CommonsRaspberryException {
        synchronized (camera) {
            return camera.takePicture(pictureName);
        }
    }

    @Override
    public void setPreview(boolean preview) {
        synchronized (camera) {
            camera.setPreview(preview);
        }
    }

    @Override
    public void setWaitingTime(int waitingTimeInSeconds) {
        synchronized (camera) {
            camera.setWaitingTime(waitingTimeInSeconds);
        }
    }
}
