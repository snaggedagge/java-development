package dkarlsso.smartmirror.alexa.intents;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.response.ResponseBuilder;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class LegacyRadioIntentHandler implements RequestHandler {
//    private static Logger LOG = getLogger(LegacyRadioIntentHandler.class);

    private static final String RADIO_STEEL = "steel fm";

    private static final String RADIO_EXTREME = "extreme";

    private boolean radioOn = false;

    private String activeRadio = RADIO_EXTREME;


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("LegacyRadioIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        if (input.getRequest() instanceof IntentRequest) {
            IntentRequest intentRequest = (IntentRequest) input.getRequest();

            if(intentRequest.getIntent().getSlots().get("RADIO_MODE").getResolutions()
                    .getResolutionsPerAuthority().get(0).getValues() != null || !intentRequest.getIntent().getSlots().get("RADIO_MODE").getResolutions()
                    .getResolutionsPerAuthority().get(0).getValues().isEmpty()) {
                radioOn = intentRequest.getIntent().getSlots().get("RADIO_MODE").getResolutions()
                        .getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName().equals("on");
            }
            else {
                radioOn = !radioOn;
            }


            if(intentRequest.getIntent().getSlots().get("RADIO_CHANNEL").getResolutions()
                    .getResolutionsPerAuthority().get(0).getValues() != null || !intentRequest.getIntent().getSlots().get("RADIO_CHANNEL").getResolutions()
                    .getResolutionsPerAuthority().get(0).getValues().isEmpty()) {
                activeRadio = intentRequest.getIntent().getSlots().get("RADIO_CHANNEL").getResolutions()
                        .getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();
            }
            else if (radioOn){
                String speechText = "Could you repeat that?";
                return new ResponseBuilder()
                        .withSpeech(speechText)
                        .withSimpleCard("Radio", speechText)
                        .build();
            }

            String speechText = String.format("Turning radio %s %s", activeRadio, radioOn ? "on" : "off");
            return new ResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard("Radio", speechText)
                    .build();

        }

        String speechText = "Not valid " + input.getRequest().getType() + input.getRequest().getClass();
        return new ResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Radio", speechText)
                .build();

    }

}

// Alexa, ask mirror to turn on radio extreme