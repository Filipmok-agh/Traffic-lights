package ui.app;

import javafx.stage.Stage;
import ui.app.BaseApp;
import ui.presenter.JsonSimulationPresenter;

import java.io.File;

public class JsonSimulationApp extends BaseApp {

    @Override
    public void start(Stage stage) throws Exception {
    }

    public void start(Stage stage, File jsonFile, String jsonFileName) throws Exception {
        JsonSimulationPresenter presenter = new JsonSimulationPresenter(jsonFile, jsonFileName);
        launchScene("/JsonSimulation.fxml", presenter, "Traffic-Lights", stage);
    }
}
