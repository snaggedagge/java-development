package dkarlsso.commons.weather.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonCity {

    private long id;

    private String name;

    private JsonCoordinates coord;

    private String country;

    // TODO: Unknown field
    private String timezone;

    private Date sunrise;

    private Date sunset;
}
