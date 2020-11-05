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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Master {
    private static ModbusMaster master;
    private static DataModbus dm = new DataModbus();
    private static List<String> list = new ArrayList<String>();

    //Write Multiple Register (0x10)
    private static void inputRegister(int x){
        try {
            master.writeMultipleRegisters(dm.getSlaveID(), dm.getAddressOne(), new int[]{x});
            System.out.println("Write: " + x);
            Thread.sleep(1000);
            outputRegister(x);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

   /* private ArrayList<Integer> getIntegerArray() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(String stringValue : list) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch(NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                Log.w("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }*/

    // Read Input Registers (0x04)
    private static void outputRegister(int x){
        try {
            list.add(x, Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), dm.getAddressTwo(), dm.getQuality())));
            System.out.println(list.get(x));
            Thread.sleep(1000);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{
            SerialParameters serialParameters = new SerialParameters();
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

            for (int i = 0; i <= 2; i++){ inputRegister(i); }

            System.out.println(list);
            String x = list.get(1);

          /*  String y = "\\[(.*?)\\]";
            System.out.println(x);

            final Pattern pattern = Pattern.compile(y);
            final Matcher matcher = pattern.matcher(x);

            while (matcher.find()) {
                System.out.println("Full match: " + matcher.group(0));
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String symbol = matcher.group(i);
                  //  int z = Integer.parseInt(symbol);
                   // int[] array = new int[]{z};
                    System.out.println(arrayList);
                }
            }*/

         //   int[] y  = new int[]{Integer.parseInt(x)};
        //    System.out.println(Arrays.toString(y));
            master.disconnect();
        } catch(RuntimeException | ModbusIOException | SerialPortException e){
            e.printStackTrace();
        }
    }
}
