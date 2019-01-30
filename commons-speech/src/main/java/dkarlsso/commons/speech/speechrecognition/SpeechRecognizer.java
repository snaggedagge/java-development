package dkarlsso.commons.speech.speechrecognition;

import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.commandaction.CommandInvoker;
import dkarlsso.commons.commandaction.UnknownCommandException;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SpeechRecognizer <E extends Enum<E>>
{

    private final Configuration configuration = new Configuration();

    private final LiveSpeechRecognizer recognizer;

    private final CommandInvoker<E> commandInvoker;

    private final Class<E> commandEnumerationClass;


    public SpeechRecognizer(final CommandInvoker<E> commandInvoker,
                            final Class<E> commandEnumerationClass) throws SpeechException {
        this.commandInvoker = commandInvoker;
        this.commandEnumerationClass = commandEnumerationClass;

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

        configuration.setGrammarPath("resource:/speech");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (final IOException e) {
            throw new SpeechException(e.getMessage(), e);
        }
    }

    public void startRecognition() {
        recognizer.startRecognition(true);
    }


    public void getResult() throws SpeechException, UnknownCommandException {
        final SpeechResult result = recognizer.getResult();

        //Checking if recognizer has recognized the grammars
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

    private void callInterfaces(final String command) throws SpeechException, UnknownCommandException {
         try {
             if (!command.equals("<unk>")) {
                 commandInvoker.executeCommand(E.valueOf(commandEnumerationClass, command.toUpperCase()));
             }
         } catch (final CommandActionException e) {
             throw new SpeechException("Exception from command invoker" + e.getMessage(), e);
         }
         catch (final IllegalArgumentException e) {
             throw new UnknownCommandException("No enum command named : " + command);
         }
    }

}
