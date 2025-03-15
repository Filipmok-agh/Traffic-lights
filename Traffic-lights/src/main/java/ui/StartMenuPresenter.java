package ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.application.Application;

public class StartMenuPresenter {

    public Button manualSimulationButton;

    public Button loadJsonButton;

    @FXML
    private void initialize() {
        manualSimulationButton.setOnAction(event -> {
            // Start the SimulationApp in a new stage
            SimulationApp app = new SimulationApp();
            try {
                app.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Close the current stage (StartMenu)
            Stage currentStage = (Stage) manualSimulationButton.getScene().getWindow();
            currentStage.close();
        });

        loadJsonButton.setOnAction(event -> {
            System.out.println("Load JSON button clicked");
        });
    }
}
