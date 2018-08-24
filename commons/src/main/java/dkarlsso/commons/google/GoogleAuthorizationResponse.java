package dkarlsso.commons.google;

public class GoogleAuthorizationResponse {

    private String access_token;

    private String refresh_token;

    private long expires_in;

    private String token_type;

    public GoogleAuthorizationResponse() {
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public long getExpiresIn() {
        return expires_in;
    }

    public String getTokenType() {
        return token_type;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
