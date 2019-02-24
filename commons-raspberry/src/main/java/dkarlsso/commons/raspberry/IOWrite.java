package dkarlsso.commons.raspberry;

import com.pi4j.wiringpi.Gpio;

public class IOWrite {


    private int pin;

    private boolean isHigh;

    public IOWrite(final int pin) {
        this.pin = pin;
        this.isHigh = false;

        Gpio.pinMode(pin,Gpio.OUTPUT);
        Gpio.digitalWrite(pin,Gpio.LOW);
    }


    public void setHigh() {
        Gpio.digitalWrite(pin,Gpio.HIGH);
        isHigh = true;
    }
    public void setLow() {
        Gpio.digitalWrite(pin,Gpio.LOW);
        isHigh = false;
    }

    public void switchState() {

        if(isHigh) {
            Gpio.digitalWrite(pin,Gpio.LOW);
            isHigh = false;
        }
        else {
            Gpio.digitalWrite(pin,Gpio.HIGH);
            isHigh = true;
        }
    }

    public boolean isHigh() {
        return isHigh;
    }

}
