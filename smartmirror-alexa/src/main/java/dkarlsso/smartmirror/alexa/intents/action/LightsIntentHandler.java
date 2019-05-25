package dkarlsso.smartmirror.alexa.intents.action;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.CanFulfillIntentRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.canfulfill.CanFulfillIntent;
import com.amazon.ask.model.canfulfill.CanFulfillIntentRequest;
import com.amazon.ask.model.canfulfill.CanFulfillIntentValues;
import com.amazon.ask.request.Predicates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LightsIntentHandler extends AbstractActionCaller implements CanFulfillIntentRequestHandler {

    private static final Logger LOG = LogManager.getLogger(LightsIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        return input.matches(Predicates.intentName("LightsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {

        LOG.error("Enter lights handler");
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
                .withCanFulfillIntent(CanFulfillIntent.builder().withCanFulfill(CanFulfillIntentValues.YES).build())
                .withSimpleCard("Lights", "Activating lights").build();
    }
}