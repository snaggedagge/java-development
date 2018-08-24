package dkarlsso.commons.raspberry.camera.impl;

import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.raspberry.camera.Camera;

public final class ThreadSafeCameraSingleton {

    private static ThreadSafeCamera threadSafeCamera;

    public static void setCamera(final Camera camera) {
        threadSafeCamera = new ThreadSafeCamera(camera);
    }

    public static Camera getCamera() throws CommonsException {
        if(threadSafeCamera == null) {
            throw new CommonsException("Camera has not been initialized");
        }
        return threadSafeCamera;
    }


}
