package dkarlsso.commons.raspberry.enums;

public enum GPIOPins {
    GPIO17(0),
    GPIO18(1),
    GPIO27(2),
    GPIO22(3),
    GPIO23(4),
    GPIO24(5),
    GPIO25(6),
    GPIO4(7),
    GPIO2_SDA1(8),
    GPIO3_SCL1(9),
    GPIO8_SPICEO0(10),
    GPIO7_SPICEO1(11),
    GPIO10_SPMOSI(12),
    GPIO9_SPMISO(13),
    GPIO11_SPISCLK(14),
    GPIO14_TXDO(15),
    GPIO15_RXDO(16),
    GPIO5(21),
    GPIO6(22),
    GPIO13(23),
    GPIO19(24),
    GPIO26(25),
    GPIO12(26),
    GPIO16(27),
    GPIO20(28),
    GPIO21(29),

    SDA1(8),
    SCL1(9),
    SPICEO0(10),
    SPICEO1(11),
    SPMOSI(12),
    SPMISO(13),
    SPISCLK(14),
    TXDO(15),
    RXDO(16);


    private int wiringPin;
    private String raspPinName;

    private GPIOPins(int wiringPin) {
        this.wiringPin = wiringPin;
    }

    public int getWiringPin() {
        return wiringPin;
    }

}
