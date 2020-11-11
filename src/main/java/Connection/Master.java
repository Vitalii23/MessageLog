package Connection;

import Catalog.DateTime;
import Catalog.ExcelFile;
import Model.DataFile;
import Model.DataModbus;
import Model.MetaData;
import com.intelligt.modbus.jlibmodbus.Modbus;
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
    private ModbusMasterRTU master;
    private ExcelFile file = new ExcelFile();
    private DataModbus dm = new DataModbus();
    private DataFile dataFile = new DataFile();
    private MetaData metaData = new MetaData();
    private DateTime dateTime = new DateTime();
    private List<String> list = new ArrayList<String>();
    private ArrayList<DataFile> listArray = new ArrayList<DataFile>();
    private ArrayList<Integer> arrayInt;
    private Integer[] element;

    //Write Multiple Register (0x10)
    private void inputRegister(int x){
        try {
            master.writeMultipleRegisters(dm.getSlaveID(), dm.getAddressOne(), new int[]{x});
            System.out.println("Запись: " + x);
            Thread.sleep(1000);
            outputRegister(x);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Read Input Registers (0x04)
    private void outputRegister(int x){
        try {
            list.add(x, Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), dm.getAddressTwo(), dm.getQuality())));
            System.out.println(list.get(x) + "\n");
            Thread.sleep(1000);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Name electric drive
    private void nameRegister(){
        try {

            String nameString = Arrays.toString(master.readInputRegisters(
                    dm.getSlaveID(), dm.getAddressName(), 1));
            nameString = nameString.replaceAll("[\\[\\]]", "");
            dataFile.setName(Integer.parseInt(nameString));
            System.out.println("Номер блока: " + dataFile.getName());
            Thread.sleep(1000);
        } catch (ModbusProtocolException | ModbusIOException | ModbusNumberException | InterruptedException e) {
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
        nameRegister();
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

            listArray.add(new DataFile(dataFile.getNumber(), dataFile.getCode(), dataFile.getPeriod(),
                    dataFile.getStatus(), dataFile.getR(), dataFile.getS(), dataFile.getT(),
                    dataFile.getU(), dataFile.getV(), dataFile.getW(), dataFile.getMoment(),
                    dataFile.getPosition(), dataFile.getCycleCount()));
        }
        file.writeExcel(listArray);
    }

    public void start(int device, String baudRate, int dataBits, String parity, int stopBits) {
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
            System.out.println("Процедура окончена" + "\n");
            try {
                master.disconnect();
            } catch (ModbusIOException e) {
                e.printStackTrace();
            }
        }
    }
}
