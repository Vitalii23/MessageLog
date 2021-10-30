package Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

public class WriteController {

    @FXML
    private ProgressBar dataProgressBarId;

    @FXML
    private ProgressIndicator dataProgressIndicatorId;

    @FXML
    private ProgressBar regStatusProgressBarId;

    @FXML
    private ProgressIndicator regStatusProgressIndicatorId;

    @FXML
    void initialize() {
        dataProgressBarId.setProgress(0);
        regStatusProgressBarId.setProgress(0);

    }
}
