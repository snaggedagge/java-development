package dkarlsso.alexa.intents;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import org.apache.commons.codec.binary.StringUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class TakeSelfieIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.intentName("TakeSelfieIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Smile pretty!";
        try {
            URL url = new URL("http://212.17.164.24:8080/action/LIGHTS");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("POST");
            httpCon.setDoOutput(true);
            httpCon.connect();
            if(httpCon.getResponseCode() != 200) {
                speechText = "Message is " + httpCon.getResponseCode();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            speechText = "You know shit's fucked";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Selfie", speechText)
                .build();
    }

}