package dkarlsso.commons.speechrecognition;

public interface CommandInterface<T extends Enum> {

    void weather();

    void selfie();

    void radio();

    void start();

    void lights();

    void volume();

    void sleep();

    boolean shouldCallFunction(T commandEnum);
}
