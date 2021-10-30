package Connection;

import com.intelligt.modbus.jlibmodbus.serial.SerialPort;

public interface MasterModbusImpl {

    void inputRegister(int x); //Write Multiple Register (0x10)

    void outputRegister(int x); // Read Input Registers (0x04)

    void nameRegister(); // Name control unit

    void versionRegister(); // Version control unit

    void dateRegister(); // Date control unit

    void typeRegister(); // Type control unit

    void restart(); // Restart procedure

    void settingModbus(String device, int baudRate,
               int dataBits, int stopBits, SerialPort.Parity parity);

    void write();   //Write file data

    void arrayWrite(int i); // Array write data

    void regName(boolean flag);

    void regVersion(boolean flag);

    void regDate(boolean flag);

    void regType(boolean flag);

    void start(boolean flag);  // Сycle for normal and for restart
}