package rpi;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;
import dkarlsso.commons.raspberry.exception.NoConnectionException;
import dkarlsso.commons.raspberry.relay.interfaces.RelayInterface;
import dkarlsso.commons.raspberry.sensor.MAX6675;
import rpi.controller.rpi.Heater;
import rpi.model.HeaterDataDTO;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Duplicates")
@RunWith(EasyMockRunner.class)
public class HeaterTest {
    private HeaterDataDTO heaterDTO = new HeaterDataDTO();

    @Mock
    RelayInterface relay;

    @Test
    public void testLowTemp() throws Exception {

        MAX6675 returnTempMax = EasyMock.mock(MAX6675.class);
        MAX6675 overTempMax = EasyMock.mock(MAX6675.class);
        EasyMock.expect(returnTempMax.readTemp()).andStubReturn(25.0);
        EasyMock.expect(overTempMax.readTemp()).andStubReturn(40.0);
        EasyMock.replay(overTempMax);
        EasyMock.replay(returnTempMax);
        Heater heater = new Heater(overTempMax,returnTempMax,heaterDTO,relay,relay,relay);

        heaterDTO.setReturnTempLimit(37);
        heaterDTO.setOverTempLimit(45);


        heater.loop();
        System.out.println(heaterDTO.getReturnTemp());

        assertEquals(heaterDTO.isCirculating(), false);
        assertEquals(heaterDTO.isHeating(), true);

    }



    @Test
    public void testHighTemp() throws Exception {

        MAX6675 returnTempMax = EasyMock.mock(MAX6675.class);
        MAX6675 overTempMax = EasyMock.mock(MAX6675.class);
        EasyMock.expect(returnTempMax.readTemp()).andStubReturn(15.0);
        EasyMock.expect(overTempMax.readTemp()).andStubReturn(15.0);
        EasyMock.replay(overTempMax);
        EasyMock.replay(returnTempMax);
        Heater heater = new Heater(overTempMax,returnTempMax,heaterDTO,relay,relay,relay);

        heaterDTO.setReturnTempLimit(12);
        heaterDTO.setOverTempLimit(20);

        heater.loop();
        heater.loop();

        assertEquals(heaterDTO.isCirculating(), false);
        assertEquals(heaterDTO.isHeating(), false);
    }


    @Test
    public void testHighOverTemp() throws Exception {

        MAX6675 returnTempMax = EasyMock.mock(MAX6675.class);
        MAX6675 overTempMax = EasyMock.mock(MAX6675.class);
        EasyMock.expect(returnTempMax.readTemp()).andStubReturn(37.0);
        EasyMock.expect(overTempMax.readTemp()).andStubReturn(59.0);
        EasyMock.replay(overTempMax);
        EasyMock.replay(returnTempMax);
        Heater heater = new Heater(overTempMax,returnTempMax,heaterDTO,relay,relay,relay);

        heaterDTO.setReturnTempLimit(35);
        heaterDTO.setOverTempLimit(45);
        heater.loop();

        assertEquals(heaterDTO.isCirculating(), true);
        assertEquals(heaterDTO.isHeating(), false);
    }

    //@Ignore
    @Test
    public void testOverTempNoSignal() throws Exception {

        MAX6675 returnTempMax = EasyMock.mock(MAX6675.class);
        MAX6675 overTempMax = EasyMock.mock(MAX6675.class);
        EasyMock.expect(returnTempMax.readTemp()).andStubReturn(37.0);
        EasyMock.expect(overTempMax.readTemp()).andThrow(new NoConnectionException());
        EasyMock.replay(overTempMax);
        EasyMock.replay(returnTempMax);
        Heater heater = new Heater(overTempMax,returnTempMax,heaterDTO,relay,relay,relay);

        heaterDTO.setReturnTempLimit(35);
        heaterDTO.setOverTempLimit(45);
        heater.loop();

        assertEquals(heaterDTO.isCirculating(), false);
        assertEquals(heaterDTO.isHeating(), false);
    }

    //@Ignore
    @Test
    public void testRetTempNoSignal() throws Exception {
        MAX6675 returnTempMax = EasyMock.mock(MAX6675.class);
        MAX6675 overTempMax = EasyMock.mock(MAX6675.class);
        EasyMock.expect(returnTempMax.readTemp()).andThrow(new NoConnectionException());
        EasyMock.expect(overTempMax.readTemp()).andStubReturn(59.0);
        EasyMock.replay(overTempMax);
        EasyMock.replay(returnTempMax);
        Heater heater = new Heater(overTempMax,returnTempMax,heaterDTO,relay,relay,relay);

        heaterDTO.setReturnTempLimit(35);
        heaterDTO.setOverTempLimit(45);
        heater.loop();

        assertEquals(heaterDTO.isCirculating(), false);
        assertEquals(heaterDTO.isHeating(), false);
    }

    @Test
    public void testHighOverTemp2() throws Exception {
        MAX6675 returnTempMax = EasyMock.mock(MAX6675.class);
        MAX6675 overTempMax = EasyMock.mock(MAX6675.class);
        EasyMock.expect(returnTempMax.readTemp()).andStubReturn(30.0);
        EasyMock.expect(overTempMax.readTemp()).andStubReturn(80.0);
        EasyMock.replay(overTempMax);
        EasyMock.replay(returnTempMax);
        Heater heater = new Heater(overTempMax,returnTempMax,heaterDTO,relay,relay,relay);

        heaterDTO.setReturnTempLimit(35);
        heaterDTO.setOverTempLimit(45);
        heater.loop();

        assertEquals(heaterDTO.isCirculating(), true);
        assertEquals(heaterDTO.isHeating(), false);
    }

    @Test
    public void testHeatingCirculating() throws Exception {
        MAX6675 returnTempMax = EasyMock.mock(MAX6675.class);
        MAX6675 overTempMax = EasyMock.mock(MAX6675.class);
        EasyMock.expect(returnTempMax.readTemp()).andStubReturn(30.0);
        EasyMock.expect(overTempMax.readTemp()).andStubReturn(55.0);
        EasyMock.replay(overTempMax);
        EasyMock.replay(returnTempMax);
        Heater heater = new Heater(overTempMax,returnTempMax,heaterDTO,relay,relay,relay);

        heaterDTO.setReturnTempLimit(37);
        heaterDTO.setOverTempLimit(45);
        heater.loop();

        assertEquals(heaterDTO.isCirculating(), true);
        assertEquals(heaterDTO.isHeating(), true);
    }



}