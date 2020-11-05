package Model;

public class DataModbus {
    private int slaveID = 1,
                addressOne = 430,
                addressTwo  = 700,
                quality = 14;

    public int getSlaveID() {
        return slaveID;
    }

    public int getAddressOne() {
        return addressOne;
    }

    public int getAddressTwo() {
        return addressTwo;
    }

    public int getQuality() {
        return quality;
    }
}
