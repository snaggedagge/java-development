package hottub.controller.rpi;

import hottub.model.HeaterDataDTO;

public class MockHeater implements HeaterInterface {

    private final HeaterDataDTO synchronizedData;

    public MockHeater(HeaterDataDTO synchronizedData) {
        this.synchronizedData = synchronizedData;
    }

    @Override
    public void loop() {
        synchronized(synchronizedData) {
            synchronizedData.setHeating(synchronizedData.getReturnTempLimit() > synchronizedData.getReturnTemp());
            synchronizedData.setCirculating(synchronizedData.getOverTempLimit() > synchronizedData.getOverTemp());
        }

    }
}
