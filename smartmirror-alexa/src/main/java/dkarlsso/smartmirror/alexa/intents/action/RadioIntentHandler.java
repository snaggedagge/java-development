package dkarlsso.smartmirror.alexa.intents.action;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.dispatcher.request.handler.impl.CanFulfillIntentRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.canfulfill.CanFulfillIntent;
import com.amazon.ask.model.canfulfill.CanFulfillIntentRequest;
import com.amazon.ask.model.canfulfill.CanFulfillIntentValues;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class RadioIntentHandler extends AbstractActionCaller implements CanFulfillIntentRequestHandler {
//    private static Logger LOG = getLogger(LegacyRadioIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        return input.matches(intentName("RadioIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        try {
            callAction("RADIO");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return input.getResponseBuilder()
                    .withSpeech("You know shit's fucked")
                    .build();
        }
        return input.getResponseBuilder()
                .withCanFulfillIntent(CanFulfillIntent.builder().withCanFulfill(CanFulfillIntentValues.YES).build())
                .build();
    }
}