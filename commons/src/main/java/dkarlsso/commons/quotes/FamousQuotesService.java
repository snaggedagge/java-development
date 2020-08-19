package dkarlsso.commons.quotes;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class FamousQuotesService {

    // TODO: Create this into a real implementation, storing in database perhaps and retrieve on an increasing index?

    public List<FamousQuoteDTO> getRandomQuotes() throws FamousQuoteException {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-RapidAPI-Host", "andruxnet-random-famous-quotes.p.rapidapi.com");
        headers.add("X-RapidAPI-Key", "YgYhUXpcANmshfQaeRZlVwnxTo5yp1gRj4tjsnhO9pDkmEoyBY");

        final HttpEntity entity = new HttpEntity(headers);

        final ResponseEntity<List<FamousQuoteDTO>> response = restTemplate.exchange(
        "https://andruxnet-random-famous-quotes.p.rapidapi.com/?cat=famous&count=10",
        HttpMethod.GET, entity, new ParameterizedTypeReference<List<FamousQuoteDTO>>(){});

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new FamousQuoteException("Could not retrieve quotes: " + response.getStatusCodeValue());
        }
        return response.getBody();
    }

}
