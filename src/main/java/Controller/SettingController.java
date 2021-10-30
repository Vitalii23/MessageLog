package Controller;

import Logging.LogWriter;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingController implements ComPortImpl{
    private String namePort, baudRate, dataBits, parity, stopBits;
    private SerialParameters serialParameters = new SerialParameters();
    private LogWriter logWriter = new LogWriter();
    private final File file = new File("./Comport/", "config.ini");

    @FXML
    private ComboBox<String> nameComboBoxId;

    @FXML
    private ComboBox<Integer> baudRateComboBoxId;

    @FXML
    private ComboBox<Integer> dataBitsComboBoxId;

    @FXML
    private ComboBox<String> parityComboBoxId;

    @FXML
    private ComboBox<Integer> stopBitsComboBoxId;

    @FXML
    private Button acceptButtonId;

    @FXML
    private Button cancelButtonId;

    @FXML
    void acceptButtonAction(ActionEvent event) {
        try {
            File directory = file.getParentFile();
            if (null != directory){
                directory.mkdir();
            }

            FileOutputStream fos = new FileOutputStream(file);
            Wini ini = new Wini(file);

            ini.put("Setting", "name", namePort);
            ini.put("Setting", "baudRate", String.valueOf(baudRate));
            ini.put("Setting", "dataBits", String.valueOf(dataBits));
            ini.put("Setting", "parity", parity);
            ini.put("Setting", "stopBits", String.valueOf(stopBits));

            setComPort(serialParameters);

            fos.flush();
            fos.close();
            ini.store();

        } catch (IOException e) {
            logWriter.write(e);
        }
        Stage stage = (Stage) acceptButtonId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButtonId.getScene().getWindow();
        stage.close();
    }

    @FXML
    void nameComboBoxAction(ActionEvent event) {
        namePort = nameComboBoxId.getValue();
    }

    @FXML
    void baudRateComboBoxAction(ActionEvent event) {
        baudRate = String.valueOf(baudRateComboBoxId.getValue());
    }

    @FXML
    void dataBitsComboBoxAction(ActionEvent event) {
        dataBits = String.valueOf(dataBitsComboBoxId.getValue());
    }

    @FXML
    void parityComboBoxAction(ActionEvent event) {
        parity = parityComboBoxId.getValue();
    }

    @FXML
    void stopBitsComboBoxAction(ActionEvent event) {
        stopBits = String.valueOf(stopBitsComboBoxId.getValue());
    }

    @FXML
    void initialize(){

        nameComboBoxId.getItems().addAll("COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8",
                "COM9", "COM10", "COM11", "COM12", "COM13", "COM14", "COM15");
        baudRateComboBoxId.getItems().addAll(2400, 4800, 9600, 19200, 57600, 115200);
        dataBitsComboBoxId.getItems().addAll(4, 5, 6, 7, 8);
        parityComboBoxId.getItems().addAll("NONE", "ODD", "EVEN", "MARK", "SPACE");
        stopBitsComboBoxId.getItems().addAll(1, 2);

        if (file.getAbsoluteFile().length() != 0){

            getDataFile(file);

            nameComboBoxId.setValue(namePort);
            baudRateComboBoxId.setValue(Integer.parseInt(baudRate));
            dataBitsComboBoxId.setValue(Integer.parseInt(dataBits));
            parityComboBoxId.setValue(parity);
            stopBitsComboBoxId.setValue(Integer.parseInt(stopBits));

            setComPort(serialParameters);
        }
    }

    @Override
    public void setComPort(SerialParameters comPort) {
        comPort.setDevice(namePort);
        comPort.setBaudRate(SerialPort.BaudRate.getBaudRate(Integer.parseInt(baudRate)));
        comPort.setDataBits(Integer.parseInt(dataBits));
        comPort.setParity(SerialPort.Parity.valueOf(parity));
        comPort.setStopBits(Integer.parseInt(stopBits));
    }

    @Override
    public void getDataFile(File file) {
        try {
            Wini ini = new Wini(file);

            namePort = ini.get("Setting", "name", String.class);
            baudRate = ini.get("Setting", "baudRate", String.class);
            dataBits = ini.get("Setting", "dataBits", String.class);
            parity = ini.get("Setting", "parity", String.class);
            stopBits = ini.get("Setting", "stopBits", String.class);
        } catch (IOException e) {
            logWriter.write(e);
        }
    }
}
