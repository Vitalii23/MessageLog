package Model;

public class DataModbus {
    private final int slaveID = 1;
    private final int addressOne = 430;
    private final int quality = 14;
    private final int addressName = 100;

    public int getSlaveID() {
        return slaveID;
    }

    public int getAddressOne() {
        return addressOne;
    }

    public int getQuality() {
        return quality;
    }

    public int getAddressName() {
        return addressName;
    }
}
