package Controller;

import Model.DataFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {

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
    private TableView<?> dataTableViewId;

    @FXML
    private TableColumn<?, ?> numberTableColumnId;

    @FXML
    private TableColumn<?, ?> codeRegisterTableColumnId;

    @FXML
    private TableColumn<?, ?> timeTableColumnId;

    @FXML
    private TableColumn<?, ?> statusTableColumnId;

    @FXML
    private TableColumn<?, ?> phaseRTableColumnId;

    @FXML
    private TableColumn<?, ?> phaseSTableColumnId;

    @FXML
    private TableColumn<?, ?> phaseTTableColumnId;

    @FXML
    private TableColumn<?, ?> phaseUTableColumnId;

    @FXML
    private TableColumn<?, ?> phaseVTableColumnId;

    @FXML
    private TableColumn<?, ?> phaseWTableColumnId;

    @FXML
    private TableColumn<?, ?> momentTableColumnId;

    @FXML
    private TableColumn<?, ?> positionTableColumnId;

    @FXML
    private TableColumn<?, ?> counterTableColumnId;

    @FXML
    private AreaChart<Integer, Integer> dataAreaChartId;

    @FXML
    private CategoryAxis xAxisID;

    @FXML
    private NumberAxis yAxisID;

    @FXML
    private TableView<?> registerStatusTableViewId;

    @FXML
    private TableColumn<?, ?> numberRegistrTableColumnId;

    @FXML
    private TableColumn<?, ?> edTableColumnId;

    @FXML
    private TableColumn<?, ?> calibrationTableColumnId;

    @FXML
    private TableColumn<?, ?> sourceCommandTableColumnId;

    @FXML
    private TableColumn<?, ?> lastCommandTAbleColumnId;

    @FXML
    private TableColumn<?, ?> stoppingTableColumnId;

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

    }

    @FXML
    void searchButtonAction(ActionEvent event) {

    }

    @FXML
    void settingsMenuItemAction(ActionEvent event) {
        openWindows(event, "/settings_stage.fxml", "Параметры подключения", 340, 400);
    }

    @FXML
    void writeMenuItemAction(ActionEvent event) {

    }


    public void openWindows(ActionEvent event, String guiName, String title, int width, int height) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(guiName));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.setResizable(false);
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

    @FXML
    void initialize() {

    }

    public ArrayList<DataFile> dataFiles(ArrayList<DataFile> list){
        return list;
    }

}
