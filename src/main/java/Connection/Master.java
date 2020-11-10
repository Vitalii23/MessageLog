package Connection;

import Catalog.DateTime;
import Catalog.ExcelFile;
import Model.DataFile;
import Model.DataFiles;
import Model.DataModbus;
import Model.MetaData;
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
    private static ExcelFile file = new ExcelFile();
    private static DataModbus dm = new DataModbus();
    private static DataFile dataFile = new DataFile();
    private static DataFiles dataFiles = new DataFiles();
    private static MetaData metaData = new MetaData();
    private static DateTime dateTime = new DateTime();
    private static List<String> list = new ArrayList<String>();
    private static ArrayList<DataFile> listArray = new ArrayList<DataFile>();
    private static List<String> arrayOne;
    private static List<String> arrayTwo;
    private static ArrayList<Integer> arrayInt;
    private static ArrayList<String> arrayString = new ArrayList<String>();
    private static Integer[] element;
    private static String[] text;
    private static int size = 13;

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

    // Converter it ArrayList
    private ArrayList<Integer> arrayStringToIntegerArrayList(String arrayString){
        String removedBrackets = arrayString.substring(1, arrayString.length() - 1);
        String[] individualNumbers = removedBrackets.split(",");
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for(String numberString : individualNumbers){
            integerArrayList.add(Integer.parseInt(numberString.trim()));
        }
        return integerArrayList;
    }

    public void run(){
        for (int i = 0; i <= 200; i++) {
            inputRegister(i);

            arrayInt = arrayStringToIntegerArrayList(list.get(i));
            element = arrayInt.toArray(new Integer[201]);

            dataFile.setNumber(element[i] = arrayInt.get(0));
            dataFile.setCode(element[i] = arrayInt.get(1));
            metaData.setHigh(element[i] = arrayInt.get(2));
            metaData.setLow(element[i] = arrayInt.get(3));
            metaData.setDate(dateTime.time(metaData.getHigh(), metaData.getLow()));
            dataFile.setPeriod(metaData.getDate());
            metaData.setStatusHex(element[i] = arrayInt.get(4));
            dataFile.setStatus(metaData.getStatusHex());
            dataFile.setR(element[i] = arrayInt.get(5));
            dataFile.setS(element[i] = arrayInt.get(6));
            dataFile.setT(element[i] = arrayInt.get(7));
            dataFile.setU(element[i] = arrayInt.get(8));
            dataFile.setV(element[i] = arrayInt.get(9));
            dataFile.setW(element[i] = arrayInt.get(10));
            dataFile.setMoment(element[i] = arrayInt.get(11));
            dataFile.setPosition(element[i] = arrayInt.get(12));
            dataFile.setCycleCount(element[i] = arrayInt.get(13));

            arrayString.add(0, String.valueOf(dataFile.getNumber()));
            arrayString.add(1, String.valueOf(dataFile.getCode()));
            arrayString.add(2, dataFile.getPeriod());
            arrayString.add(3, dataFile.getStatus());
            arrayString.add(4, String.valueOf(dataFile.getR()));
            arrayString.add(5, String.valueOf(dataFile.getS()));
            arrayString.add(6, String.valueOf(dataFile.getT()));
            arrayString.add(7, String.valueOf(dataFile.getU()));
            arrayString.add(8, String.valueOf(dataFile.getV()));
            arrayString.add(9, String.valueOf(dataFile.getW()));
            arrayString.add(10, String.valueOf(dataFile.getMoment()));
            arrayString.add(11, String.valueOf(dataFile.getPosition()));
            arrayString.add(12, String.valueOf(dataFile.getCycleCount()));

            //text = new String[size];

           /* for (int j = 0; j < size; j++){
                text[j] = arrayString.get(j);
            }*/

             /*if (arrayString.size() > 13){
                arrayOne = arrayString.subList(0, 12);
                arrayTwo = arrayString.subList(13, 25);
                System.out.println("First: " + arrayOne);
                System.out.println("Two: " + arrayTwo);
            }*/

            listArray.add(new DataFile(dataFile.getNumber(), dataFile.getCode(), dataFile.getPeriod(),
                    dataFile.getStatus(), dataFile.getR(), dataFile.getS(), dataFile.getT(),
                    dataFile.getU(), dataFile.getV(), dataFile.getW(), dataFile.getMoment(),
                    dataFile.getPosition(), dataFile.getCycleCount()));

            /*text = Arrays.asList(arrayString.get(0), arrayString.get(1), arrayString.get(2),
                    arrayString.get(3), arrayString.get(4), arrayString.get(5), arrayString.get(6),
                    arrayString.get(7), arrayString.get(8), arrayString.get(9), arrayString.get(10),
                    arrayString.get(11), arrayString.get(12));*/
        }
        //System.out.println(arrayString);
        file.writeExcel(listArray);
    }

    public void start(int device, String baudRate, int dataBits, String parity, int stopBits) throws ModbusIOException {
        try{
            SerialParameters serialParameters = new SerialParameters();

            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_WARNINGS);

            String[] devList = SerialPortList.getPortNames();
            if (devList.length > 0) {
                serialParameters.setDevice(devList[device]);
                serialParameters.setBaudRate(SerialPort.BaudRate.valueOf(baudRate));
                serialParameters.setDataBits(dataBits);
                serialParameters.setParity(SerialPort.Parity.valueOf(parity));
                serialParameters.setStopBits(stopBits);
            }

            SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
            master = new ModbusMasterRTU(serialParameters);

            master.setResponseTimeout(1000);

            master.connect();

            run();

        } catch(RuntimeException | ModbusIOException | SerialPortException e){
            e.printStackTrace();
        } finally {
            master.disconnect();
        }
    }
}
