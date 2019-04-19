package dkarlsso.commons.google;

import dkarlsso.commons.model.CommonsException;
import dkarlsso.commons.oauth2.Oauth2Credential;
import dkarlsso.commons.oauth2.Oauth2Scope;
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

    private Oauth2Scope lastScopeConnected;

    public GoogleConnector(final String clientId, final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


    public String getLoginUrl(final String redirectUri,
                              final GoogleAccessType accessType,
                              final Oauth2Scope scope) {
        lastScopeConnected = scope;
        return GOOGLE_OAUTH2_ENDPOINT + "?scope=" + scope.getApi()
                + "&access_type=" + accessType.toString()
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&client_id=" + clientId;
    }

    public Oauth2Credential authorizeLogin(String authorizationCode, final String redirectUrl) throws CommonsException {

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

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new CommonsException("Could not authorize: " + response.getStatusCodeValue());
        }

        return Oauth2Credential.builder()
                .oauth2Scopes(lastScopeConnected.name()) // TODO: Should probably allow multiple scopes at once...
                .accessToken(response.getBody().getAccessToken())
                .refreshToken(response.getBody().getRefreshToken())
                .expiresIn(response.getBody().getExpiresIn()).build();
    }


    public GoogleRefreshTokenResponse refreshAccessToken(final Oauth2Credential oldToken) throws CommonsException {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", oldToken.getRefreshToken());
        map.add("grant_type","refresh_token");

        final HttpEntity<MultiValueMap<String, String>> formRequest = new HttpEntity<>(map, headers);

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
}
