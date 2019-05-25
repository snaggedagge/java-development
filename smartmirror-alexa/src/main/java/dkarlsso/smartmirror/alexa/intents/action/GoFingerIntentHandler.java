package dkarlsso.smartmirror.alexa.intents.action;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.CanFulfillIntentRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.canfulfill.CanFulfillIntent;
import com.amazon.ask.model.canfulfill.CanFulfillIntentRequest;
import com.amazon.ask.model.canfulfill.CanFulfillIntentValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GoFingerIntentHandler extends AbstractActionCaller implements CanFulfillIntentRequestHandler {

    private static final Logger LOG = LogManager.getLogger(GoFingerIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        return input.getResponseBuilder()
                .withCanFulfillIntent(CanFulfillIntent.builder().withCanFulfill(CanFulfillIntentValues.YES).build())
                .withSpeech("But i already did that today")
                .withSimpleCard("Finger", "But i already did that")
                .build();
    }

//    @Override
//    public boolean canHandle(HandlerInput input) {
//        return input.matches(intentName("GoFingerIntent"));
//    }
//
//    @Override
//    public Optional<Response> handle(HandlerInput input) {
//        return input.getResponseBuilder()
//                .withSpeech("But i already did that today")
//                .withSimpleCard("Finger", "But i already did that")
//                .build();
//    }
}