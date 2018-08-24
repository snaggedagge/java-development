package dkarlsso.commons.speechrecognition;

public interface CommandInterface {

    void weather();

    void selfie();

    void radio();

    void start();

    void lights();

    void volume();

    void sleep();

    boolean shouldCallFunction(CommandEnum commandEnum);
}
