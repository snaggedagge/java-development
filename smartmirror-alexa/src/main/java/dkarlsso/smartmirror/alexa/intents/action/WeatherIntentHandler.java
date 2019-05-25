package dkarlsso.smartmirror.alexa.intents.action;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class WeatherIntentHandler extends AbstractActionCaller implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("WeatherIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        try {
            callAction("WEATHER");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return input.getResponseBuilder()
                    .withSpeech("You know shit's fucked")
                    .build();
        }
        return input.getResponseBuilder().build();
    }
}