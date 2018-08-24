package dkarlsso.commons.google;

public class GoogleAuthorizationRequest {

    // AuthorizationCode
    private String code;

    private String client_id;

    private String client_secret;

    private String redirect_uri;

    private String grant_type = "authorization_code";

    public GoogleAuthorizationRequest() {
    }

    public GoogleAuthorizationRequest(String code, String client_id, String client_secret, String redirect_uri) {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
    }

    public String getCode() {
        return code;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
