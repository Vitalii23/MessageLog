package Connection;

import Controller.MainController;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Master implements MasterModbusImpl {
    private ModbusMasterRTU master;
    private final XlsFile xls = new XlsFile();
    //private XmlFile xml = new XmlFile();
    private final DataModbus dm = new DataModbus();
    private final DataFile dataFile = new DataFile();
    private final MetaData metaData = new MetaData();
    private final DateTime dateTime = new DateTime();
    private final DecodingRegister decrypt = new DecodingRegister();
    private final DecodingStatus decoding = new DecodingStatus();
    private final List<String> list = new ArrayList<String>();
    private final ArrayList<DataFile> dataFiles = new ArrayList<DataFile>();
    private final ArrayList<MetaData> metaDataList = new ArrayList<MetaData>();
    static Logger logger = Logger.getLogger("MyLog");
    static FileHandler fileHandler;
    private ArrayList<Integer> arrayInt;
    private Integer[] element;
    private jssc.SerialPort port;
    private SerialParameters serialParameters;
    private int counter;
    private int record = 200;
    private int address;

    static {
        try {
            fileHandler = new FileHandler("./Exception/error.log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void errorCheck(Exception e, String function){
        File file = new File("./Exception/error.log");
        File directory = file.getParentFile();
        if (null != directory){
            directory.mkdir();
        }

        logger.log(Level.WARNING, "Function: " + function +"\n"+
                "Error: " + e);
    }

    @Override
    public void inputRegister(int x){
        try {
            master.writeMultipleRegisters(dm.getSlaveID(), dm.getAddressOne(), new int[]{x});
            System.out.println("Запись: " + x);
            Thread.sleep(30);
            outputRegister(x);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            System.err.println("inputRegister: Обрыв связи с контроллером");

            errorCheck(e, "inputRegister");

            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }
            restart();
        }
    }

    @Override
    public void outputRegister(int x){
        try {
            list.add(x, Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), address, dm.getQuality())));
            System.out.println(list.get(x) + "\n");
            Thread.sleep(30);
        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException | InterruptedException e) {
            System.err.println("outputRegister: Обрыв связи с контроллером");

            errorCheck(e, "outputRegister");

            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }

            restart();
        }
    }

    @Override
    public void nameRegister(){
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

            errorCheck(e, "nameRegister");

            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }

            restart();
        }
    }

    @Override
    public void versionRegister(){
        try {
            String majorString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 10, 1));
            majorString = majorString.replaceAll("[\\[\\]]", "");
            int majorInt = Integer.parseInt(majorString);

            Thread.sleep(30);

            String patchString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 12, 1));
            patchString = patchString.replaceAll("[\\[\\]]", "");
            int patchInt = Integer.parseInt(patchString);

            byte majorByte = (byte) majorInt;
            byte patchByte = (byte) patchInt;

            int major = Integer.parseInt(String.valueOf(majorByte));
            int patch = Integer.parseInt(String.valueOf(patchByte));

            dataFile.setVersion(major + "." + String.format("%02d", patch));

            switch (major){
                case 1:
                    address = 690;
                    break;
                case 2:
                    address = 700;
                    break;
                case 3:
                    address = 700;
                    break;
                default:
                    System.out.println("Error write address");
                    break;
            }
        } catch (ModbusProtocolException | ModbusIOException | ModbusNumberException | InterruptedException e) {
            System.err.println("versionRegister: Обрыв связи с контроллером");

            errorCheck(e, "versionRegister");

            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }
            restart();
        }
    }

    @Override
    public void dateRegister(){
        try {
            String numberString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 101, 1));
            numberString = numberString.replaceAll("[\\[\\]]", "");

            Thread.sleep(30);

            String monthString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 102, 1));
            monthString = monthString.replaceAll("[\\[\\]]", "");

            Thread.sleep(30);

            String yearString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 103, 1));
            yearString = yearString.replaceAll("[\\[\\]]", "");

            Thread.sleep(30);

            String dateSymbols = numberString + "." + monthString  + "." + yearString;

            dataFile.setDate(dateSymbols);

        } catch (ModbusProtocolException | ModbusIOException | ModbusNumberException | InterruptedException e) {
            System.err.println("dateRegister: Обрыв связи с контроллером");

            errorCheck(e, "dateRegister");

            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }
            restart();
        }
    }

    @Override
    public void typeRegister() {
        try {
            String typeString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 101, 1));
            typeString = typeString.replaceAll("[\\[\\]]", "");

            int typeInt = Integer.parseInt(typeString);

            dataFile.setType(typeInt);

        } catch (ModbusProtocolException | ModbusIOException | ModbusNumberException e) {
            System.err.println("dateRegister: Обрыв связи с контроллером");

            errorCheck(e, "dateRegister");

            try {
                master.disconnect();
            } catch (ModbusIOException ex) {
                ex.printStackTrace();
            }

            restart();
        }
    }

    @Override
    public void restart() {
       port = new jssc.SerialPort(serialParameters.getDevice());
        if (!port.isOpened()) {
            try {
                System.out.println("Процедура перезапущена" + "\n");
                SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
                master = new ModbusMasterRTU(serialParameters);
                master.setResponseTimeout(1000);
                master.connect();
                regVersion(false);
                regName(false);
                regDate(false);
                regType(false);
                start(false);
            } catch (SerialPortException | ModbusIOException ex) {
                ex.printStackTrace();
                restart();
            }
        }
    }

    @Override
    public void settingModbus(String device, int baudRate, int dataBits, int stopBits, SerialPort.Parity parity) {
        try{
            serialParameters = new SerialParameters();

            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
            Modbus.setLogLevel(Modbus.LogLevel.LEVEL_WARNINGS);

            serialParameters.setDevice(device);
            serialParameters.setBaudRate(SerialPort.BaudRate.getBaudRate(baudRate));
            serialParameters.setDataBits(dataBits);
            serialParameters.setParity(parity);
            serialParameters.setStopBits(stopBits);

            SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
            master = new ModbusMasterRTU(serialParameters);
            master.setResponseTimeout(1000);
            master.connect();

            regVersion(true);
            regName(true);
            start(true);

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

    @Override
    public void write() {
        //xml.writeXml(listArray, dataFile.getName());
        MainController controller = new MainController();
        controller.getData(dataFiles, metaDataList);
        //xls.writeXls(dataFiles, metaDataList, dataFile.getName(), dataFile.getVersion());
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

    private DataFile getDataFile(){
        return new DataFile(
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
                dataFile.getCycleCount());
    }


    public void arrayWrite(int i){
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

        dataFiles.add(getDataFile());
    }

    @Override
    public void regName(boolean flag){
        if (dataFile.getName() == 0){
            nameRegister();
        }
        if (flag){
            nameRegister();
        } else {
            System.out.println("Имя контроллера: " + dataFile.getName());
        }
    }

    @Override
    public void regVersion(boolean flag){
        if (dataFile.getVersion() == null){
            versionRegister();
        }
        if (flag){
            versionRegister();
        } else {
            System.out.println("Версия ПО: " + dataFile.getVersion());
        }
    }

    @Override
    public void regDate(boolean flag) {
        if (dataFile.getDate() == null){
            dateRegister();
        }
        if (flag){
            dateRegister();
        } else {
            System.out.println("Дата: " + dataFile.getDate());
        }
    }

    @Override
    public void regType(boolean flag) {
        if (dataFile.getType() == 0){
            typeRegister();
        }
        if (flag){
            typeRegister();
        } else {
            System.out.println("Тип блока: " + dataFile.getType());
        }
    }

    @Override
    public void start(boolean flag) {
        try {
            for (int i = 0; i <= record; i++) {
                if (flag){
                    arrayWrite(i);
                    if (counter == record){
                        write();
                        master.disconnect();
                        System.exit(0);
                    } else {
                        counter += 1;
                    }
                } else {
                    for (int k = counter; k <= record; k++){
                        arrayWrite(k);
                        if (counter == record){
                            write();
                            master.disconnect();
                            System.exit(0);
                        } else {
                            counter += 1;
                        }
                    }
                }
            }
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }
}
