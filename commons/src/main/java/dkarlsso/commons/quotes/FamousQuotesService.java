package dkarlsso.commons.quotes;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class FamousQuotesService {

    // TODO: Create this into a real implementation, storing in database perhaps and retrieve on an increasing index?

    public FamousQuoteDTO getRandomQuote() throws FamousQuoteException {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-Mashape-Key", "Zcs2VgU3CZmsh4LNY7qYzwQKURH1p1HhuqHjsneiBZSttC49mU");
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Accept", "application/json");

        final HttpEntity entity = new HttpEntity(headers);

        final ResponseEntity<List<FamousQuoteDTO>> response = restTemplate.exchange(
        "https://andruxnet-random-famous-quotes.p.mashape.com/?cat=famous&count=10",
        HttpMethod.GET, entity, new ParameterizedTypeReference<List<FamousQuoteDTO>>(){});

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new FamousQuoteException("Could not retrieve quotes: " + response.getStatusCodeValue());
        }
        return response.getBody().get(0);
    }

}
