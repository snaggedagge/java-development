package dkarlsso.commons.google;

import dkarlsso.commons.model.CommonsException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class GoogleConnector {

    private static final String GOOGLE_OAUTH2_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";

    private static final String GOOGLE_OAUTH2_TOKEN_ENDPOINT = "https://www.googleapis.com/oauth2/v4/token";

    private final String clientId;

    private final String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();


    public GoogleConnector(final String clientId, final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


    public String getLoginUrl(final String redirectUri, final GoogleScope scope,
                              final GoogleAccessType accessType) {
        return GOOGLE_OAUTH2_ENDPOINT + "?scope=" + scope.getApi()
                + "&access_type=" + accessType.toString()
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&client_id=" + clientId;
    }

    public GoogleAuthorizationResponse authorizeLogin(String authorizationCode, final String redirectUrl) throws CommonsException {

        final GoogleAuthorizationRequest request =
                new GoogleAuthorizationRequest(authorizationCode, clientId, clientSecret, redirectUrl);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("code",request.getCode());
        map.add("client_id",request.getClient_id());
        map.add("client_secret",request.getClient_secret());
        map.add("redirect_uri",request.getRedirect_uri());
        map.add("grant_type",request.getGrant_type());

        final HttpEntity<MultiValueMap<String, String>> formRequest = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        final ResponseEntity<GoogleAuthorizationResponse> response =
                restTemplate.postForEntity( GOOGLE_OAUTH2_TOKEN_ENDPOINT, formRequest , GoogleAuthorizationResponse.class );

        verifyResponse(response);

        return response.getBody();
    }


    public GoogleRefreshTokenResponse refreshAccessToken(GoogleAuthorizationResponse oldToken) throws CommonsException {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", oldToken.getRefreshToken());
        map.add("grant_type","refresh_token");

        final HttpEntity<MultiValueMap<String, String>> formRequest = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        final ResponseEntity<GoogleRefreshTokenResponse> response =
                restTemplate.postForEntity( GOOGLE_OAUTH2_TOKEN_ENDPOINT, formRequest , GoogleRefreshTokenResponse.class );

        verifyResponse(response);

        return response.getBody();

    }

    private void verifyResponse(ResponseEntity response) throws CommonsException {
        if( !response.getStatusCode().is2xxSuccessful()) {
            throw new CommonsException("Could not authorize "
                    + response.getStatusCode().name() + "  "
                    + response.getStatusCode().getReasonPhrase());
        }
    }


    public enum GoogleAccessType {
        ONLINE,
        OFFLINE;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public enum GoogleScope {
        CALENDAR("https://www.googleapis.com/auth/calendar");

        private final String api;

        private GoogleScope(final String api) {
            this.api = api;
        }


        public String getApi() {
            return api;
        }
    }


}
