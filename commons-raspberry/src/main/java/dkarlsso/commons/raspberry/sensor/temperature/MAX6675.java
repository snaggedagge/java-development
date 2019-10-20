package dkarlsso.commons.raspberry.sensor.temperature;


import com.pi4j.wiringpi.Gpio;
import dkarlsso.commons.raspberry.enums.GPIOPins;
import dkarlsso.commons.raspberry.exception.NoConnectionException;


public class MAX6675 implements TemperatureSensor {

    private int pic_cs;
    private int pin_sck;
    private int pin_so;


    public MAX6675() {
        //Empty initializer, should not be used for anything other than testing
    }

    public MAX6675(final GPIOPins soPin, final GPIOPins csPin, final GPIOPins sckPin) {
        this(soPin.getWiringPin(),csPin.getWiringPin(),sckPin.getWiringPin());
    }


    public MAX6675(int PIN_SO, int PIN_CS, int PIN_SCK) {
        pic_cs = PIN_CS;
        pin_sck = PIN_SCK;
        pin_so = PIN_SO;

        Gpio.pinMode(pin_sck,Gpio.OUTPUT);
        Gpio.pinMode(pin_so,Gpio.INPUT);
        Gpio.pinMode(pic_cs,Gpio.OUTPUT);

        //pinMode(pin_sck,OUTPUT);
        //pinMode(pin_so,INPUT);
        //pinMode(pic_cs,OUTPUT);

        Gpio.digitalWrite(pic_cs,Gpio.HIGH);
        Gpio.digitalWrite(pin_sck,Gpio.LOW);

        //digitalWrite(pic_cs, HIGH);
        //digitalWrite(pin_sck, LOW);
    }

    @Override
    public double readTemp() throws NoConnectionException {
        double temp = readTempOnce();

        int i=0;
        while(temp<1 && i<20 ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            temp = readTempOnce();
            i++;
        }
        if(temp<1) {
            throw new NoConnectionException("Lost connection to MAX6675 with pins: CS:" + pic_cs+" SCK:" + pin_sck + " SO:" + pin_so);
        }

        return temp;
    }


    private double readTempOnce()   {
        try {
        int value = 0;
        // init sensor
        Gpio.digitalWrite(pic_cs,Gpio.LOW);
        Thread.sleep(2);
        Gpio.digitalWrite(pic_cs,Gpio.HIGH);
        Thread.sleep(200);

        //digitalWrite(pic_cs, LOW);
        //delay(2);
        //digitalWrite(pic_cs, HIGH);
        //delay(200);

    /* Read the chip and return the raw temperature value
    Bring CS pin low to allow us to read the data from
    the conversion process */

        Gpio.digitalWrite(pic_cs,Gpio.LOW);
        Gpio.digitalWrite(pin_sck,Gpio.HIGH);
        Gpio.digitalWrite(pin_sck,Gpio.LOW);


        //digitalWrite(pic_cs, LOW);
    /* Cycle the clock for dummy bit 15 */
        //digitalWrite(pin_sck, HIGH);
        // delay(1);
        //digitalWrite(pin_sck, LOW);

    /*
    Read bits 14-3 from MAX6675 for the Temp.
    Loop for each bit reading
    the value and storing the final value in 'temp' */


        int i;
        for (i = 14; i >= 0; i--) {

            Gpio.digitalWrite(pin_sck,Gpio.HIGH);
            Thread.sleep(1);
            value += Gpio.digitalRead(pin_so) << i;
            Gpio.digitalWrite(pin_sck,Gpio.LOW);


            //digitalWrite(pin_sck, HIGH);
            //delay(1);
            //value += digitalRead(pin_so) << i;
            //digitalWrite(pin_sck, LOW);
        }

        // check bit D2 if HIGH no sensor
        if ((value & 0x04) == 0x04) return -1;
        // shift right three places
        return (value >> 3)*0.25;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return -200;

    }

}
