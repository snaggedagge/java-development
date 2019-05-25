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

public class WeatherIntentHandler extends AbstractActionCaller implements CanFulfillIntentRequestHandler {

    private static final Logger LOG = LogManager.getLogger(WeatherIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        return input.matches(intentName("WeatherIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, CanFulfillIntentRequest canFulfillIntentRequest) {
        LOG.error("Device id " + input.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId() );

        LOG.error("User id " + input.getRequestEnvelope().getContext().getSystem().getUser().getUserId());

        try {
            callAction("WEATHER");
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