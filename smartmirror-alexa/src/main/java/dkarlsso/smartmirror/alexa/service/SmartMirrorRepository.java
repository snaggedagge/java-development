package dkarlsso.smartmirror.alexa.service;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class SmartMirrorRepository {

    public static String getAdress(final String mirrorId) throws IOException {
        URL url = new URL("http://dkarlsso.com/webportal/websites/" + mirrorId);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod("GET");
        httpCon.setDoOutput(true);
        httpCon.connect();
        if (httpCon.getResponseCode() != 200) {
            throw new IOException("Response code was " + httpCon.getResponseCode());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), Charset.defaultCharset()))) {
            final String response = br.lines().collect(Collectors.joining(System.lineSeparator()));
            DocumentContext jsonContext = JsonPath.parse(response);
            return jsonContext.read("$.websiteLink");
        }
    }

}
