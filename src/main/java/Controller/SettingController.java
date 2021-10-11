package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingController {
    private String namePort, baudRate, dataBits, parity, stopBits;

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
            File file = new File("./Comport/", "config.ini");
            File directory = file.getParentFile();
            if (null != directory){
                directory.mkdir();
            }

            FileOutputStream fos = new FileOutputStream(file);
            Wini ini = new Wini(file);

            ini.put("Setting", "name", namePort);
            ini.put("Setting", "baudRate", baudRate);
            ini.put("Setting", "dataBits", dataBits);
            ini.put("Setting", "parity", parity);
            ini.put("Setting", "stopBits", stopBits);

            fos.flush();
            fos.close();
            ini.store();

        } catch (IOException e) {
            e.printStackTrace();
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
        File file = new File("./Comport/", "config.ini");

        nameComboBoxId.getItems().addAll("COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8",
                "COM9", "COM10", "COM11", "COM12", "COM13", "COM14", "COM15");
        baudRateComboBoxId.getItems().addAll(2400, 4800, 9600, 19200, 57600, 115200);
        dataBitsComboBoxId.getItems().addAll(4, 5, 6, 7, 8);
        parityComboBoxId.getItems().addAll("NONE", "ODD", "EVEN", "MARK", "SPACE");
        stopBitsComboBoxId.getItems().addAll(1, 2);

        if (file.getAbsoluteFile().length() != 0){
            System.out.println("true");
            try {
                Wini ini = new Wini(file);

                String namePort = ini.get("Setting", "name", String.class);
                int baudRate = ini.get("Setting", "baudRate", int.class);
                int dataBits = ini.get("Setting", "dataBits", int.class);
                String parity = ini.get("Setting", "parity", String.class);
                int stopBits = ini.get("Setting", "stopBits", int.class);

                nameComboBoxId.setValue(namePort);
                baudRateComboBoxId.setValue(baudRate);
                dataBitsComboBoxId.setValue(dataBits);
                parityComboBoxId.setValue(parity);
                stopBitsComboBoxId.setValue(stopBits);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
