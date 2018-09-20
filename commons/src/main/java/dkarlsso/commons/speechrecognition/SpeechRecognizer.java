package dkarlsso.commons.speechrecognition;

import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.apache.commons.lang.StringUtils;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


// TODO FIGURE OUT WTH TO DO WITH THIS UGLY ASS CLASS
public class SpeechRecognizer <E extends Enum>
{

    private final File voicerecognitionRootFolder;

    private Configuration activeConfiguration = new Configuration();

    private LiveSpeechRecognizer recognizer;

    private CommandInvoker<E> commandInvoker;

    private final Class<E> commandEnumerationClass;

    private final Path path;

    private final String BASE_PATH;

    public SpeechRecognizer(final File voicerecognitionRootFolder, final CommandInvoker<E> commandInvoker,
                            final Class<E> commandEnumerationClass) throws SpeechException {
        this.voicerecognitionRootFolder = voicerecognitionRootFolder;
        this.commandInvoker = commandInvoker;
        this.commandEnumerationClass = commandEnumerationClass;

        path = Paths.get(voicerecognitionRootFolder.getAbsolutePath() + File.separator);


        // TODO WTF IS THIS?
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
        activeConfiguration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        activeConfiguration.setDictionaryPath(BASE_PATH + "commands.dic");
        activeConfiguration.setLanguageModelPath(BASE_PATH + "commands.lm");

        //Recognizer object, Pass the Configuration object
        LiveSpeechRecognizer recognize = null;
        try {
             recognize = new LiveSpeechRecognizer(activeConfiguration);
        } catch (IOException e) {
            throw new SpeechException(e.getMessage(), e);
        }
        recognizer = recognize;

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
            final String hypothesis = result.getHypothesis();

            if(!StringUtils.isEmpty(hypothesis)) {
                final String[] commands = hypothesis.split(" ");
                for(final String command : commands) {
                    callInterfaces(command);
                }
            }
        }
    }

    private void callInterfaces(final String command) throws SpeechException {
        try {
            final Object commandEnum = E.valueOf(commandEnumerationClass,command);
            commandInvoker.executeCommand((E)commandEnum);
        } catch (final CommandActionException e) {
            throw new SpeechException("Exception from command invoker" + e.getMessage(), e);
        }

    }

}
