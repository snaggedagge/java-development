package dkarlsso.commons.oauth2;

public enum Oauth2Scope {
    GOOGLE_CALENDAR("https://www.googleapis.com/auth/calendar");

    private final String api;

    private Oauth2Scope(final String api) {
        this.api = api;
    }


    public String getApi() {
        return api;
    }
}
