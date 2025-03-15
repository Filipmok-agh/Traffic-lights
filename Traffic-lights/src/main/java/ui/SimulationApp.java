package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import ui.SimulationPresenter;

public class SimulationApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Simulation.fxml"));
        SimulationPresenter presenter = new SimulationPresenter();
        fxmlLoader.setController(presenter);
        Parent root = fxmlLoader.load();
        stage.setTitle("Lights");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
