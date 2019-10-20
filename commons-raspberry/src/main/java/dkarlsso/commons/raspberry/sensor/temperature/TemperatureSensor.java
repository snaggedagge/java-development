package dkarlsso.commons.raspberry.sensor.temperature;

import dkarlsso.commons.raspberry.exception.NoConnectionException;

public interface TemperatureSensor {
    double readTemp() throws NoConnectionException;
}
