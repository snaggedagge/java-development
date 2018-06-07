package dkarlsso.commons.raspberry.relay;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.Gpio;
import dkarlsso.commons.raspberry.enums.GPIOPins;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;

import static com.pi4j.wiringpi.Gpio.HIGH;
import static com.pi4j.wiringpi.Gpio.LOW;
import static com.pi4j.wiringpi.Gpio.OUTPUT;

public class OptoRelay implements RelayInterface {

    private int pin;

    private boolean isHigh;

    public OptoRelay(int pin) {
        this.pin = pin;
        Gpio.pinMode(pin,OUTPUT);
        Gpio.digitalWrite(pin,HIGH);
        isHigh = false;
    }

    public OptoRelay(GPIOPins gpioPin) {
        this(gpioPin.getWiringPin());
    }

    @Override
    public void setHigh() {
        Gpio.digitalWrite(pin,LOW);
        isHigh = true;
    }

    @Override
    public void setLow() {
        isHigh = false;
        Gpio.digitalWrite(pin,HIGH);
    }

    @Override
    public void setState(boolean state) {
        if(state) {
            this.setHigh();
        } else {
            this.setLow();
        }
    }

    @Override
    public void switchState() {
        if(isHigh) {
            this.setLow();
        } else {
            this.setHigh();
        }
    }

    @Override
    public boolean isHigh() {
        return isHigh;
    }
}
