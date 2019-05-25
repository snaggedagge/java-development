package dkarlsso.smartmirror.alexa.intents.action;

import dkarlsso.smartmirror.alexa.service.SmartMirrorRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractActionCaller {


    protected void callAction(final String action) throws IOException {
        URL url = new URL(SmartMirrorRepository.getAdress("smartmirror-javafx-dag") + "/action/" + action);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("POST");
        httpCon.setDoOutput(true);
        httpCon.connect();
        if(httpCon.getResponseCode() != 200) {
            throw new IOException("Response code was " + httpCon.getResponseCode());
        }
    }

}
