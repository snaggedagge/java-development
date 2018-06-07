package dkarlsso.commons.weather.json;

public class JsonSys {

    private String pod;

    public JsonSys(String pod) {
        this.pod = pod;
    }

    public JsonSys() {
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }
}
