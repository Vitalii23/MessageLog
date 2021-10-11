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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Master {
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
    private ArrayList<Integer> arrayInt;
    private Integer[] element;
    private jssc.SerialPort port;
    private SerialParameters serialParameters;
    private int counter, record = 200, majorInt, patchInt, address;

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
                            dm.getSlaveID(), address, dm.getQuality())));
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

    public void versionRegister(){
        try {
            String majorString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 10, 1));
            majorString = majorString.replaceAll("[\\[\\]]", "");
            majorInt = Integer.parseInt(majorString);

            Thread.sleep(30);

            String patchString = Arrays.toString(
                    master.readInputRegisters(
                            dm.getSlaveID(), 12, 1));
            patchString = patchString.replaceAll("[\\[\\]]", "");
            patchInt = Integer.parseInt(patchString);

            byte majorByte = (byte) majorInt;
            byte patchByte = (byte) patchInt;

            int major = Integer.parseInt(String.valueOf(majorByte));
            int patch = Integer.parseInt(String.valueOf(patchByte));

            dataFile.setVersion(major + "." + patch);

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
                runVersion(false);
                runName(false);
                run(false);
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

    public void start(String device, int baudRate,
                      int dataBits, int stopBits, SerialPort.Parity parity) {
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

            runVersion(true);
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
        MainController main = new MainController();
        main.dataFiles(dataFiles);
        xls.writeXls(dataFiles, metaDataList, dataFile.getName(), dataFile.getVersion());
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

        dataFiles.add(getDataFile());
    }

    private void runName(boolean flag){
        if (dataFile.getName() == 0){
            nameRegister();
        }

        if (flag){
            nameRegister();
        } else {
            System.out.println("Имя контроллера: " + dataFile.getName());
        }
    }

    private void runVersion(boolean flag){
        if (dataFile.getVersion() == null){
            versionRegister();
        }

        if (flag){
            versionRegister();
        } else {
            System.out.println("Версия ПО: " + dataFile.getVersion());
        }
    }

    // Сycle for normal and for restart
    private void run(boolean flag){
        try {
            for (int i = 0; i <= record; i++) {
                if (flag== true){
                    arrayWrite(i);
                    if (counter == record){
                        writeFile();
                        master.disconnect();
                        System.exit(0);
                    } else {
                        counter += 1;
                    }
                } else {
                    for (int k = counter; k <= record; k++){
                        arrayWrite(k);
                        if (counter == record){
                            writeFile();
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
