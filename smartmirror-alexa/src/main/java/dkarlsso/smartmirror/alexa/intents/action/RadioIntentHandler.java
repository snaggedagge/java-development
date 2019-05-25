package dkarlsso.smartmirror.alexa.intents.action;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class RadioIntentHandler extends AbstractActionCaller implements RequestHandler {
//    private static Logger LOG = getLogger(LegacyRadioIntentHandler.class);

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("RadioIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        try {
            callAction("RADIO");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return input.getResponseBuilder()
                    .withSpeech("You know shit's fucked")
                    .build();
        }
        return input.getResponseBuilder().build();
    }
}