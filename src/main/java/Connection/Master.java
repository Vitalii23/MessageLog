package Connection;

import Catalog.StatusFile;
import Function.DateTime;
import Catalog.XlsFile;
import Function.DecodingRegister;
import Function.DecodingStatus;
import Model.DataFile;
import Model.DataModbus;
import Model.MetaData;
import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterRTU;
import com.intelligt.modbus.jlibmodbus.serial.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Master {
    private ModbusMasterRTU master;
    private StatusFile status = new StatusFile();
    private XlsFile xls = new XlsFile();
    //private XmlFile xml = new XmlFile();
    private DataModbus dm = new DataModbus();
    private DataFile dataFile = new DataFile();
    private MetaData metaData = new MetaData();
    private DateTime dateTime = new DateTime();
    private DecodingRegister decrypt = new DecodingRegister();
    private DecodingStatus decoding = new DecodingStatus();
    private List<String> list = new ArrayList<String>();
    private ArrayList<DataFile> dataFiles = new ArrayList<DataFile>();
    private ArrayList<MetaData> metaDataList = new ArrayList<MetaData>();
    private ArrayList<Integer> arrayInt;
    private Integer[] element;
    private jssc.SerialPort port;
    private SerialParameters serialParameters;
    private int counter, record = 200;

    //Write Multiple Register (0x10)
    private void inputRegister(int x){
        try {
            master.writeMultipleRegisters(dm.getSlaveID(), dm.getAddressOne(), new int[]{x});
            System.out.println("Запись: " + x);
            Thread.sleep(30);
            outputRegister(x);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            System.err.println("inputRegister: Обрыв связи с контроллером");
            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }
            restart();
        }
    }

    // Read Input Registers (0x04)
    private void outputRegister(int x){
        try {
            list.add(x, Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), dm.getAddressTwo(), dm.getQuality())));
            System.out.println(list.get(x) + "\n");
            Thread.sleep(30);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            System.err.println("outputRegister: Обрыв связи с контроллером");
            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }
            restart();
        }
    }

    // Name electric drive
    private void nameRegister(){
        try {
            String nameString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), dm.getAddressName(), 1));
            nameString = nameString.replaceAll("[\\[\\]]", "");
            dataFile.setName(Integer.parseInt(nameString));
            System.out.println("Номер блока: " + dataFile.getName());
            Thread.sleep(30);
        } catch (ModbusProtocolException | ModbusIOException | ModbusNumberException | InterruptedException e) {
            System.err.println("nameRegister: Обрыв связи с контроллером");
            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }
            restart();
        }
    }

    // Restart connect program
    private void restart() {
       port = new jssc.SerialPort(serialParameters.getDevice());
        if (!port.isOpened()) {
            try {
                System.out.println("Процедура перезапущена" + "\n");
                SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
                master = new ModbusMasterRTU(serialParameters);
                master.setResponseTimeout(1000);
                master.connect();
                run(false);
                runName(false);
            } catch (SerialPortException | ModbusIOException ex) {
                ex.printStackTrace();
                restart();
            }
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

    public void start(String device,  SerialPort.BaudRate baudRate,
                                   int dataBits, int stopBits, SerialPort.Parity parity) {
        try{
            serialParameters = new SerialParameters();

            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_WARNINGS);

            serialParameters.setDevice(device);
            serialParameters.setBaudRate(baudRate);
            serialParameters.setDataBits(dataBits);
            serialParameters.setParity(parity);
            serialParameters.setStopBits(stopBits);

            SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
            master = new ModbusMasterRTU(serialParameters);
            master.setResponseTimeout(1000);
            master.connect();

            runName(true);
            run(true);

        } catch(RuntimeException | ModbusIOException | SerialPortException e){
            e.getMessage();
        } finally {
            System.out.println("Процедура окончена" + "\n");
            try {
                master.disconnect();
            } catch (ModbusIOException e) {
                e.printStackTrace();
            }
        }
    }

    //Write file data
    private void writeFile() {
        //xml.writeXml(listArray, dataFile.getName());
        xls.writeXls(dataFiles, dataFile.getName());
        status.writeStatus(metaDataList, dataFile.getName());
    }

    // Array write data
    private void arrayWrite(int i){
        inputRegister(i);
        arrayInt = arrayStringToIntegerArrayList(list.get(i));

        element = arrayInt.toArray(new Integer[201]);

        dataFile.setNumber(element[i] = arrayInt.get(0)); // Number
        metaData.setDecipher(decrypt.decryption((element[i] = arrayInt.get(1))));

        dataFile.setCode(metaData.getDecipher()); // Code Registry
        metaData.setHigh(element[i] = arrayInt.get(2));
        metaData.setLow(element[i] = arrayInt.get(3));

        metaData.setDate(dateTime.time(metaData.getHigh(), metaData.getLow()));
        dataFile.setPeriod(metaData.getDate()); // Time

        metaData.setStatusHex(element[i] = arrayInt.get(4));
        decoding.arrayWriteStatus(metaData.getStatusHex().replaceAll("0x", ""));
        dataFile.setStatus(metaData.getStatusHex()); // Status

        dataFile.setR(element[i] = arrayInt.get(5)); // R
        dataFile.setS(element[i] = arrayInt.get(6)); // S
        dataFile.setT(element[i] = arrayInt.get(7)); // T
        dataFile.setU(element[i] = arrayInt.get(8)); // U
        dataFile.setV(element[i] = arrayInt.get(9)); // V
        dataFile.setW(element[i] = arrayInt.get(10)); // W
        dataFile.setMoment(element[i] = arrayInt.get(11)); // Moment
        dataFile.setPosition(element[i] = arrayInt.get(12)); // Position
        dataFile.setCycleCount(element[i] = arrayInt.get(13)); // Cycle Counter


        metaDataList.add(decoding.getMetaData());

        dataFiles.add(
                new DataFile(
                        dataFile.getNumber(),
                        dataFile.getCode(),
                        dataFile.getPeriod(),
                        dataFile.getStatus(),
                        dataFile.getR(),
                        dataFile.getS(),
                        dataFile.getT(),
                        dataFile.getU(),
                        dataFile.getV(),
                        dataFile.getW(),
                        dataFile.getMoment(),
                        dataFile.getPosition(),
                        dataFile.getCycleCount()));
    }

    private void runName(boolean flag){
        if (flag == true){
            nameRegister();
        } else {
            nameRegister();
        }
    }

    // Сycle for normal and for restart
    private void run(boolean flag){
        for (int i = 0; i <= record; i++) {
            if (flag== true){
                arrayWrite(i);
                if (counter == record){
                    writeFile();
                    System.exit(0);
                } else {
                    counter += 1;
                }
            } else {
                for (int k = counter; k <= record; k++){
                    arrayWrite(k);
                    if (counter == record){
                        writeFile();
                        System.exit(0);
                    } else {
                        counter += 1;
                    }
                }
            }
        }
    }
}
