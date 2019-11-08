package dkarlsso.commons.raspberry.sensor.temperature;


import com.pi4j.component.Component;
import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import dkarlsso.commons.raspberry.exception.NoConnectionException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Relies on the one wire interface. Multiple one wires can be configured, following https://pinout.xyz/pinout/1_wire
 *
 * See devices
 * ls /sys/bus/w1/devices/
 *
 * Enable new pin for 1W
 * sudo dtoverlay w1-gpio gpiopin=17 pullup=0 # header pin 11
 */
public class DS18B20 implements dkarlsso.commons.raspberry.sensor.temperature.TemperatureSensor {

    private final TemperatureSensor sensor;

    private final String id;

    public DS18B20(final String id) {
        this.id = id;
        TemperatureSensor selectedDevice = null;
        final List<TemperatureSensor> temperatureSensors = new W1Master().getDevices(TemperatureSensor.class);
        for (final TemperatureSensor device : temperatureSensors) {
            if (device.getName().equals(id)) {
                selectedDevice = device;
            }
        }
        if (selectedDevice == null) {
            throw new IllegalArgumentException("Temperature sensor with id " + id + " was not found. Available devices is " + temperatureSensors.stream()
            .map(Component::getName)
            .collect(Collectors.toList()));
        }
        this.sensor = selectedDevice;
    }

    @Override
    public double readTemp() throws NoConnectionException {
        try {
            final double temp = sensor.getTemperature(TemperatureScale.CELSIUS);
            if (!Double.isNaN(temp)) {
                return temp;
            }
        } catch (final Exception e) {
            throw new NoConnectionException("Connection was lost to device with id " + id, e);
        }
        throw new NoConnectionException("Connection was lost to device with id " + id);
    }
}
