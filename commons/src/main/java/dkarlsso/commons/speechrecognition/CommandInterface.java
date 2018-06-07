package dkarlsso.commons.speechrecognition;

public interface CommandInterface {

    void weather();

    void selfie();

    void radio();

//    void spotify();

    void youtube();

    void activate();

    void sleep();

    void shutdown();

//    void facebook();

//    void select();

    boolean shouldCallFunction(CommandEnum commandEnum);
}
