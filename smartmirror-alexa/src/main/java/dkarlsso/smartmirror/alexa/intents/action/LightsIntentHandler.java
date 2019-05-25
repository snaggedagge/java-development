package dkarlsso.smartmirror.alexa.intents.action;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

public class LightsIntentHandler extends AbstractActionCaller implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("LightsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        try {
            callAction("LIGHTS");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return input.getResponseBuilder()
                    .withSpeech("You know shit's fucked")
                    .withSimpleCard("Lights", "Shits fucked")
                    .build();
        }
        return input.getResponseBuilder()
                .withSimpleCard("Lights", "Activating lights").build();
    }

}