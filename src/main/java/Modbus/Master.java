package Modbus;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterRTU;
import com.intelligt.modbus.jlibmodbus.serial.*;
import jssc.SerialPortList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Master {
    private static SerialParameters serialParameters;
    private static ModbusMaster master;
    private static int slaveID = 1,
                       addressOne = 430,
                       addressTwo = 700,
                       quality = 14;

    public static void main(String[] args) {
        serialParameters = new SerialParameters();
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
        try{

            String[] dev_list = SerialPortList.getPortNames();
            if (dev_list.length > 0) {
                serialParameters.setDevice(dev_list[0]);
                serialParameters.setBaudRate(SerialPort.BaudRate.BAUD_RATE_19200);
                serialParameters.setDataBits(8);
                serialParameters.setParity(SerialPort.Parity.NONE);
                serialParameters.setStopBits(1);
            }

            SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
            master = new ModbusMasterRTU(serialParameters);
            master.setResponseTimeout(1000);

            master.connect();

            Queue<O> queue = new LinkedList<ModbusMaster>();


            for (int i = 0; i < 200; i++){
                master.writeMultipleRegisters(slaveID, addressOne, new int[]{i});
                System.out.println("Data input " + i + " "
                        + Arrays.toString(master.readInputRegisters(slaveID, addressTwo, quality)));
                Thread.sleep(1000);
            }

            master.disconnect();
        } catch(RuntimeException |
                ModbusIOException |
                SerialPortException |
                ModbusNumberException |
                ModbusProtocolException |
                InterruptedException e){
            e.printStackTrace();
        }
    }
}
