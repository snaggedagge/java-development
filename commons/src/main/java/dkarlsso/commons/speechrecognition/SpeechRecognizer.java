package dkarlsso.commons.speechrecognition;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SpeechRecognizer
{

    private final File voicerecognitionRootFolder;

    private Configuration activeConfiguration = new Configuration();

    private LiveSpeechRecognizer recognizer;

    private CommandInterface commandInterface;

    private PlayInterface playInterface;

    private ListenerInterface listenerInterface;

    private boolean usingCommandConfig = true;


    private final Path path;

    private final String BASE_PATH;

    public SpeechRecognizer(final File voicerecognitionRootFolder) throws SpeechException {
        this.voicerecognitionRootFolder = voicerecognitionRootFolder;

        path = Paths.get(voicerecognitionRootFolder.getAbsolutePath() + File.separator);

        BASE_PATH = File.separator + path.subpath(0,path.getNameCount()).toString() + File.separator;

/*
        commandConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        commandConfiguration.setDictionaryPath("\\Users\\Dag Karlsson\\Desktop\\Speech test\\commands.dic");
        commandConfiguration.setLanguageModelPath("\\Users\\Dag Karlsson\\Desktop\\Speech test\\commands.lm");
        activeConfiguration = commandConfiguration;

        playConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        playConfiguration.setDictionaryPath("\\Users\\Dag Karlsson\\Desktop\\Speech test\\play.dic");
        playConfiguration.setLanguageModelPath("\\Users\\Dag Karlsson\\Desktop\\Speech test\\play.lm");
 */


        /*
        commandConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        commandConfiguration.setDictionaryPath(BASE_PATH + "commands.dic");
        commandConfiguration.setLanguageModelPath(BASE_PATH + "commands.lm");
        activeConfiguration = commandConfiguration;

        playConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        playConfiguration.setDictionaryPath(BASE_PATH + "play.dic");
        playConfiguration.setLanguageModelPath(BASE_PATH + "play.lm");
         */
        setCommandConfiguration();

        //Recognizer object, Pass the Configuration object
        LiveSpeechRecognizer recognize = null;
        try {
             recognize = new LiveSpeechRecognizer(activeConfiguration);
        } catch (IOException e) {
            throw new SpeechException(e.getMessage(), e);
        }
        recognizer = recognize;

    }

    public SpeechRecognizer(final File applicationRootFolder, CommandInterface commandInterface) throws SpeechException {
        this(applicationRootFolder);
        this.commandInterface = commandInterface;
    }

    public SpeechRecognizer(final File applicationRootFolder, PlayInterface playInterface) throws SpeechException {
        this(applicationRootFolder);
        this.playInterface = playInterface;
    }

    public SpeechRecognizer(final File applicationRootFolder, ListenerInterface listenerInterface) throws SpeechException {
        this(applicationRootFolder);
        this.listenerInterface = listenerInterface;
    }

    public SpeechRecognizer(final File applicationRootFolder, CommandInterface commandInterface, ListenerInterface listenerInterface) throws SpeechException {
        this(applicationRootFolder);
        this.commandInterface = commandInterface;
        this.listenerInterface = listenerInterface;
    }


    public void startRecognition() {
        //Start Recognition Process (The bool parameter clears the previous cache if true)
        recognizer.startRecognition(true);
    }


    public void getResult() throws SpeechException {
        //Create SpeechResult Object
        final SpeechResult result = recognizer.getResult();

        //Checking if recognizer has recognized the speech
        if (result != null ) {
            String command = result.getHypothesis();
            if(command != null && !command.isEmpty() && !command.contains(" ")) {
                callInterfaces(command);
                System.out.println(command);
            }
        }
    }

    public void setActiveConfiguration(ConfigurationEnum choice) throws IOException {

        if(choice.equals(ConfigurationEnum.COMMANDS)) {
            setCommandConfiguration();
            usingCommandConfig = true;
        } else {
            setPlayConfiguration();
            usingCommandConfig = false;
        }

        recognizer.stopRecognition();
        recognizer = new LiveSpeechRecognizer(activeConfiguration);
    }


    private void callInterfaces(final String command) throws SpeechException {
        try{
            if(usingCommandConfig) {
                final CommandEnum commandEnum = CommandEnum.valueOf(command);
                if(commandInterface != null) {
                    if(commandInterface.shouldCallFunction(commandEnum)) {
                        final Method menuMethod = CommandInterface.class.getMethod(commandEnum.name().toLowerCase());
                        menuMethod.invoke(commandInterface);
                    }
                }
                if(listenerInterface != null) {
                    listenerInterface.menuCommand(commandEnum);
                }
            }
            else {
                final PlayEnum playEnum = PlayEnum.valueOf(command);
                if(playInterface != null) {
                    final Method menuMethod = PlayInterface.class.getMethod(playEnum.name().toLowerCase());
                    menuMethod.invoke(playInterface);
                }
                if(listenerInterface != null) {
                    listenerInterface.playCommand(playEnum);
                }
            }

        } catch (ReflectiveOperationException | IllegalArgumentException e) {
            throw new SpeechException("Error during command :" + command + e.getMessage(), e);
        }
    }

    private void setCommandConfiguration() {
        activeConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        activeConfiguration.setDictionaryPath(BASE_PATH + "commands.dic");
        activeConfiguration.setLanguageModelPath(BASE_PATH + "commands.lm");
    }
    private void setPlayConfiguration() {
        activeConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        activeConfiguration.setDictionaryPath(BASE_PATH + "play.dic");
        activeConfiguration.setLanguageModelPath(BASE_PATH + "play.lm");
    }
}
