package Controller;

import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;

import java.io.File;

public interface ComPortImpl {

    void setComPort(SerialParameters comPort);

    void getDataFile(File file);
}
