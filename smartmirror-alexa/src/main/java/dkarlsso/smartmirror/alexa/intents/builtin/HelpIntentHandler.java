package dkarlsso.smartmirror.alexa.intents.builtin;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "There is a few different commands you can ask me to do. " +
                "For example, take a selfie, turn on lights, show the weather or turn on the radio. What do you want to do?";
        return input.getResponseBuilder()
                .withReprompt(speechText)
                .withSpeech(speechText)
                .withSimpleCard("Help", speechText)
                .build();
    }
}