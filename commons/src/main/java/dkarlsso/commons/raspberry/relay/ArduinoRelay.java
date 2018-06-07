package dkarlsso.commons.raspberry.relay;

import com.pi4j.io.gpio.*;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.enums.GPIOPins;

public class ArduinoRelay implements RelayInterface {

    private int pin;

    private boolean isHigh;

    private GpioPinDigitalMultipurpose multiPurposePin;

    public ArduinoRelay(int pin) {
        this.pin = pin;

        multiPurposePin = GpioFactory.getInstance().provisionDigitalMultipurposePin(RaspiPin.getPinByAddress(pin), PinMode.DIGITAL_INPUT);
        multiPurposePin.setPullResistance(PinPullResistance.PULL_UP);

        isHigh = false;
    }

    public ArduinoRelay(GPIOPins gpioPin) {
        this(gpioPin.getWiringPin());
    }

    @Override
    public void setHigh() {
        multiPurposePin.setMode( PinMode.DIGITAL_OUTPUT);
        isHigh = true;
    }

    @Override
    public void setLow() {
        multiPurposePin.setMode( PinMode.DIGITAL_INPUT);
        isHigh = false;
        //multiPurposePin.setPullResistance(PinPullResistance.PULL_UP);
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
