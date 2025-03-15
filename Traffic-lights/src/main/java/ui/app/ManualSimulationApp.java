package ui.app;

import javafx.stage.Stage;
import ui.app.BaseApp;
import ui.presenter.ManualSimulationPresenter;

public class ManualSimulationApp extends BaseApp {

    @Override
    public void start(Stage stage) throws Exception {
        ManualSimulationPresenter presenter = new ManualSimulationPresenter();
        launchScene("/ManualSimulation.fxml", presenter, "Traffic-Lights", stage);
    }
}
