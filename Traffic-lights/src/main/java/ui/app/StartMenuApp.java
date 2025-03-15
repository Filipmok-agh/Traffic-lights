package ui.app;

import javafx.stage.Stage;
import ui.app.BaseApp;
import ui.presenter.StartMenuPresenter;

public class StartMenuApp extends BaseApp {

    @Override
    public void start(Stage stage) throws Exception {
        StartMenuPresenter presenter = new StartMenuPresenter();
        launchScene("/StartMenu.fxml", presenter, "Start", stage);
    }
}
