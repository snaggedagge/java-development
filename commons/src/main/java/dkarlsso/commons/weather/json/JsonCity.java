package dkarlsso.commons.weather.json;

public class JsonCity {

    private long id;

    private String name;

    private JsonCoordinates coord;

    private String country;


    public JsonCity() {
    }

    public JsonCity(int id, String name, JsonCoordinates coord, String country) {
        this.id = id;
        this.name = name;
        this.coord = coord;
        this.country = country;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonCoordinates getCoord() {
        return coord;
    }

    public void setCoord(JsonCoordinates coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "JsonCity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coord=" + coord +
                ", country='" + country + '\'' +
                '}';
    }
}
