package dkarlsso.smartmirror.alexa.intents.action;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.CanFulfillIntentRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.canfulfill.CanFulfillIntent;
import com.amazon.ask.model.canfulfill.CanFulfillIntentRequest;
import com.amazon.ask.model.canfulfill.CanFulfillIntentValues;
import com.amazon.ask.request.Predicates;

import java.util.Optional;

public class TakeSelfieIntentHandler extends AbstractActionCaller implements CanFulfillIntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        return input.matches(Predicates.intentName("TakeSelfieIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        String speechText = "Smile pretty!";
        try {
            callAction("SELFIE");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            speechText = "You know shit's fucked";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Selfie", speechText)
                .withCanFulfillIntent(CanFulfillIntent.builder().withCanFulfill(CanFulfillIntentValues.YES).build())
                .build();
    }
}