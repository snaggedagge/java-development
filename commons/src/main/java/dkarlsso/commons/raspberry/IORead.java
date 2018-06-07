package dkarlsso.commons.raspberry;

import com.pi4j.wiringpi.Gpio;

public class IORead {

    private int pin;

    public IORead(final int pin) {
        this.pin = pin;

        Gpio.pinMode(pin,Gpio.INPUT);
    }

    public int digitalRead() {
        return Gpio.digitalRead(pin);
    }
    public int analogRead() {
        return Gpio.analogRead(pin);
    }

}
