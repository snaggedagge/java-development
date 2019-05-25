package dkarlsso.smartmirror.alexa.intents.builtin;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class FallbackIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "The amazing smart mirror did not understand you.You can repeat or try saying help!";
        return input.getResponseBuilder()
                .withReprompt(speechText)
                .withSpeech(speechText)
                .withSimpleCard("Help", speechText)
                .build();
    }
}