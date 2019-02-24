package dkarlsso.commons.raspberry.camera.impl;

import dkarlsso.commons.raspberry.camera.Camera;
import dkarlsso.commons.raspberry.exception.CommonsRaspberryException;

public final class ThreadSafeCameraSingleton {

    private static ThreadSafeCamera threadSafeCamera;

    public static void setCamera(final Camera camera) {
        threadSafeCamera = new ThreadSafeCamera(camera);
    }

    public static Camera getCamera() throws CommonsRaspberryException {
        if(threadSafeCamera == null) {
            throw new CommonsRaspberryException("Camera has not been initialized");
        }
        return threadSafeCamera;
    }


}
