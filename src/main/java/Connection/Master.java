package Connection;

import Model.DataModbus;
import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterRTU;
import com.intelligt.modbus.jlibmodbus.serial.*;
import jssc.SerialPortList;

import java.util.Arrays;

public class Master {
    private static SerialParameters serialParameters;
    private static ModbusMaster master;
    private static DataModbus dm = new DataModbus();

    //Write Multiple Register (0x10)
    public static int inputRegister(int x){
        try {
            master.writeMultipleRegisters(dm.getSlaveID(), dm.getAddressOne(), new int[]{x});
            System.out.println("Write: " + x);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            e.getMessage();
        }
        return x;
    }

    // Read Input Registers (0x04)
    public static void outputRegister(){
        try {
            System.out.println("Data output "
                    + Arrays.toString(master.readInputRegisters(dm.getSlaveID(), dm.getAddressTwo(), dm.getQuality())));
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        try{
            serialParameters = new SerialParameters();
            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_WARNINGS);
            String[] devList = SerialPortList.getPortNames();
            if (devList.length > 0) {
                serialParameters.setDevice(devList[0]);
                serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_19200);
                serialParameters.setDataBits(8);
                serialParameters.setParity(SerialPort.Parity.NONE);
                serialParameters.setStopBits(1);
            }

            SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
            master = new ModbusMasterRTU(serialParameters);

            master.setResponseTimeout(1000);

            master.connect();

            for (int i = 0; i <= 200; i++){
                inputRegister(i);
                Thread.sleep(1000);
                outputRegister();
                Thread.sleep(1000);
            }
            master.disconnect();
        } catch(RuntimeException | ModbusIOException | SerialPortException | InterruptedException e){
            e.getMessage();
        }
    }
}
