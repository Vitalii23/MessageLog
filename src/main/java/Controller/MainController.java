package Controller;

import Connection.Master;
import Logging.LogWriter;
import Model.DataFile;
import Model.MetaData;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainController implements ComPortImpl {

    private final FileChooser fileChooser = new FileChooser();
    private final ArrayList<DataFile> dataFile = new ArrayList<>();
    private final ArrayList<MetaData> metaData = new ArrayList<>();
    private Master master = new Master();
    private SerialParameters sp;
    private jssc.SerialPort port;
    private LogWriter logWriter = new LogWriter();
    private String namePort;
    private Integer baudRate;
    private Integer dataBits;
    private String parity;
    private Integer stopBits;

    @FXML
    private MenuItem writeMenuItemId;

    @FXML
    private MenuItem saveMenuItemId;

    @FXML
    private MenuItem exitMenuItemId;

    @FXML
    private MenuItem settingsMenuItemId;

    @FXML
    private RadioMenuItem connectRadioMenuItemId;

    @FXML
    private MenuItem referenceMenuItemId;

    @FXML
    private TableView<DataFile> dataTableViewId;

    @FXML
    private TableColumn<DataFile, Integer> numberTableColumnId;

    @FXML
    private TableColumn<DataFile, String> codeRegisterTableColumnId;

    @FXML
    private TableColumn<DataFile, String> timeTableColumnId;

    @FXML
    private TableColumn<DataFile, String> statusTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> phaseRTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> phaseSTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> phaseTTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> phaseUTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> phaseVTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> phaseWTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> momentTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> positionTableColumnId;

    @FXML
    private TableColumn<DataFile, Integer> counterTableColumnId;

    @FXML
    private AreaChart<Integer, Integer> dataAreaChartId;

    @FXML
    private CategoryAxis xAxisID;

    @FXML
    private NumberAxis yAxisID;

    @FXML
    private TableView<MetaData> registerStatusTableViewId;

    @FXML
    private TableColumn<MetaData, Integer> numberRegisterTableColumnId;

    @FXML
    private TableColumn<MetaData, String> edTableColumnId;

    @FXML
    private TableColumn<MetaData, String> calibrationTableColumnId;

    @FXML
    private TableColumn<MetaData, String> sourceCommandTableColumnId;

    @FXML
    private TableColumn<MetaData, String> lastCommandTAbleColumnId;

    @FXML
    private TableColumn<MetaData, String> stoppingTableColumnId;

    @FXML
    private Spinner<?> numberSpinnerId;

    @FXML
    private Button searchButtonId;

    @FXML
    private Label nameLabelId;

    @FXML
    private Label versionLabelId;

    @FXML
    private Label dateLabelId;

    @FXML
    private Label typeLabelId;

    @FXML
    private Label codeLabelId;

    @FXML
    private Label timeLabelId;

    @FXML
    private Label regStatusLabelId;

    @FXML
    private Label rLabelId;

    @FXML
    private Label sLabelId;

    @FXML
    private Label tLabelId;

    @FXML
    private Label uLabelId;

    @FXML
    private Label vLabelId;

    @FXML
    private Label wLabelId;

    @FXML
    private Label momentLabelId;

    @FXML
    private Label positionLabelId;

    @FXML
    private Label counterLabelId;

    @FXML
    private Label statusLabelId;

    @FXML
    void connectRadioMenuItemAction(ActionEvent event) {
        if (connectRadioMenuItemId.isSelected()){

            connectRadioMenuItemId.setText("Подключено");

            statusLabelId.setText("Подключено");

            File file = new File("./Comport/", "config.ini");

            if (file.getAbsoluteFile().length() != 0){
                getDataFile(file);

                setComPort(new SerialParameters(namePort,
                        SerialPort.BaudRate.getBaudRate(baudRate),
                        dataBits,
                        stopBits,
                        SerialPort.Parity.valueOf(parity)));

                try {
                    port.openPort();
                } catch (jssc.SerialPortException e){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошибка подключения протокола RS-485");
                    alert.setHeaderText("Ошибка: " + e.getMessage() + "\n");
                    alert.setContentText("Не найдено устройство. Проверьте подключения оборудования");

                    alert.showAndWait();
                }

                if (port.isOpened()){

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Подключения протокола RS-485");
                    alert.setHeaderText(null);
                    alert.setContentText("Оборудование найдено (" + port.getPortName() + ")");

                    alert.showAndWait();

                } else {

                    try {
                        connectRadioMenuItemId.setSelected(false);
                        statusLabelId.setText("Отключено");
                        connectRadioMenuItemId.setText("Отключено");
                        port.closePort();
                    } catch (jssc.SerialPortException e){
                        logWriter.write(e);
                    }

                }
            } else {

                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка последовательного порта");
                    alert.setHeaderText("Внимание");
                    alert.setContentText("Настройте параметры последовательного порта");

                    alert.showAndWait();

                    connectRadioMenuItemId.setSelected(false);
                    statusLabelId.setText("Отключено");
                    connectRadioMenuItemId.setText("Отключено");
                    port.closePort();
                } catch (jssc.SerialPortException e){
                    logWriter.write(e);
                }
            }

        } else {

            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Подключения протокола RS-485");
                alert.setHeaderText(null);
                alert.setContentText("Оборудование отключено");

                alert.showAndWait();

                statusLabelId.setText("Отключено");
                connectRadioMenuItemId.setText("Отключено");
                port.closePort();
            } catch (jssc.SerialPortException e){
                logWriter.write(e);
            }
        }
    }

    @FXML
    void dataTableViewOnSort(ActionEvent event) {

    }

    @FXML
    void exitMenuItemAction(ActionEvent event) {

    }

    @FXML
    void referenceMenuItemAction(ActionEvent event) {

    }

    @FXML
    void registerStatusTableViewOnSort(ActionEvent event) {

    }

    @FXML
    void saveMenuItemAction(ActionEvent event) {
        fileChooser.setTitle("Сохранить как");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Microsoft Office Excel 97-2003", ".xsl"));
        //File file = fileChooser.showSaveDialog(primaryStage);
    }

    @FXML
    void searchButtonAction(ActionEvent event) {

    }

    @FXML
    void settingsMenuItemAction(ActionEvent event) {
        openWindows(event, "/settings_stage.fxml", "Параметры подключения", 340, 400, false);
    }

    @FXML
    void writeMenuItemAction(ActionEvent event) {
        // Data

        master.settingModbus(sp.getDevice(), sp.getBaudRate(), sp.getDataBits(), sp.getStopBits(), sp.getParity());

        openWindows(event, "/data_write_stage.fxml", "Запись данных", 400, 100, false);

        //master.settingModbus();
        numberTableColumnId.setCellValueFactory(new PropertyValueFactory<>("number"));
        codeRegisterTableColumnId.setCellValueFactory(new PropertyValueFactory<>("code"));
        timeTableColumnId.setCellValueFactory(new PropertyValueFactory<>("period"));
        statusTableColumnId.setCellValueFactory(new PropertyValueFactory<>("status"));
        phaseRTableColumnId.setCellValueFactory(new PropertyValueFactory<>("r"));
        phaseSTableColumnId.setCellValueFactory(new PropertyValueFactory<>("s"));
        phaseTTableColumnId.setCellValueFactory(new PropertyValueFactory<>("t"));
        phaseUTableColumnId.setCellValueFactory(new PropertyValueFactory<>("u"));
        phaseVTableColumnId.setCellValueFactory(new PropertyValueFactory<>("v"));
        phaseWTableColumnId.setCellValueFactory(new PropertyValueFactory<>("w"));
        momentTableColumnId.setCellValueFactory(new PropertyValueFactory<>("moment"));
        positionTableColumnId.setCellValueFactory(new PropertyValueFactory<>("position"));
        counterTableColumnId.setCellValueFactory(new PropertyValueFactory<>("counter"));

        dataTableViewId.getColumns().addAll(numberTableColumnId,
                                            codeRegisterTableColumnId,
                                            timeTableColumnId,
                                            statusTableColumnId,
                                            phaseRTableColumnId,
                                            phaseSTableColumnId,
                                            phaseTTableColumnId,
                                            phaseUTableColumnId,
                                            phaseVTableColumnId,
                                            phaseWTableColumnId,
                                            momentTableColumnId,
                                            positionTableColumnId,
                                            counterTableColumnId);

        for (DataFile list: dataFile){
            dataTableViewId.getItems().addAll(list);
        }

        //Data driven graph
        chartLine(dataFile);

        // Data decryption register status
        //numberRegisterTableColumnId.setCellValueFactory(new PropertyValueFactory<>());
        edTableColumnId.setCellValueFactory(new PropertyValueFactory<>("ed"));
        calibrationTableColumnId.setCellValueFactory(new PropertyValueFactory<>("calibration"));
        sourceCommandTableColumnId.setCellValueFactory(new PropertyValueFactory<>("sourceCommand"));
        lastCommandTAbleColumnId.setCellValueFactory(new PropertyValueFactory<>("lastCommand"));
        stoppingTableColumnId.setCellValueFactory(new PropertyValueFactory<>("stopping"));


        registerStatusTableViewId.getColumns().addAll(numberRegisterTableColumnId,
                                                      edTableColumnId,
                                                      calibrationTableColumnId,
                                                      sourceCommandTableColumnId,
                                                      lastCommandTAbleColumnId,
                                                      stoppingTableColumnId);

        for (MetaData list: metaData){
            registerStatusTableViewId.getItems().addAll(list);
        }
    }


    public void openWindows(ActionEvent event, String guiName, String title,
                            int width, int height, boolean flagResizable) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(guiName));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));

            if (flagResizable){
                stage.setResizable(true);
            } else {
                stage.setResizable(false);
            }

            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chartLine(ArrayList<DataFile> arrayList){

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Запись");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Напряжение фаз");

        AreaChart areaChart = new AreaChart(xAxis, yAxis);

        XYChart.Series RLine = new XYChart.Series();
        RLine.setName("R");
        for (int i = 0; i <= arrayList.size(); i++){
            RLine.getData().add(i, arrayList.get(i).getR());
        }
        areaChart.getData().add(RLine);

        XYChart.Series SLine = new XYChart.Series();
        SLine.setName("S");
        for (int i = 0; i <= arrayList.size(); i++){
            RLine.getData().add(i, arrayList.get(i).getS());
        }
        areaChart.getData().add(SLine);

        XYChart.Series TLine = new XYChart.Series();
        TLine.setName("T");
        for (int i = 0; i <= arrayList.size(); i++){
            RLine.getData().add(i, arrayList.get(i).getT());
        }
        areaChart.getData().add(TLine);
    }

    public void getData (ArrayList<DataFile> dataFiles, ArrayList<MetaData> metaDates){
        dataFile.addAll(dataFiles);
        metaData.addAll(metaDates);
    }

    @FXML
    void initialize() {

    }

    @Override
    public void setComPort(SerialParameters comPort) {
        comPort.setDevice(namePort);
        comPort.setBaudRate(SerialPort.BaudRate.getBaudRate(baudRate));
        comPort.setDataBits(dataBits);
        comPort.setParity(SerialPort.Parity.valueOf(parity));
        comPort.setStopBits(stopBits);

        port = new jssc.SerialPort(comPort.getDevice());

        sp = new SerialParameters(comPort.getDevice(),
                SerialPort.BaudRate.getBaudRate(comPort.getBaudRate()),
                comPort.getDataBits(),
                comPort.getStopBits(),
                comPort.getParity());
    }

    @Override
    public void getDataFile(File file) {
        try {
            Wini ini = new Wini(file);

            namePort = ini.get("Setting", "name", String.class);
            baudRate = ini.get("Setting", "baudRate", int.class);
            dataBits = ini.get("Setting", "dataBits", int.class);
            parity = ini.get("Setting", "parity", String.class);
            stopBits = ini.get("Setting", "stopBits", int.class);
        } catch (IOException e) {
            logWriter.write(e);
        }
    }
}
