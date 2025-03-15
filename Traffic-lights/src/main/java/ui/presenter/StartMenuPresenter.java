package ui.presenter;

import elements.JsonValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.app.JsonSimulationApp;
import ui.app.ManualSimulationApp;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartMenuPresenter {

    @FXML private TextField fileNameTextField;
    public Button manualSimulationButton;
    public Button loadJsonButton;

    private JsonValidator jsonValidator;

    @FXML
    private void initialize() {
        this.jsonValidator = new JsonValidator();

        manualSimulationButton.setOnAction(event -> startManualSimulation());

        loadJsonButton.setOnAction(event -> loadJsonSimulation());
    }

    private void startManualSimulation() {
        ManualSimulationApp app = new ManualSimulationApp();
        try {
            app.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeCurrentStage();
    }

    private void loadJsonSimulation() {
        String fileName = fileNameTextField.getText().trim();

        if (fileName.isEmpty()) {
            showAlert("File name is empty", "Please enter a file name.");
            return;
        }

        String filePath = "src/main/resources/" + fileName + ".json";
        if (Files.exists(Paths.get(filePath))) {
            showAlert("File already exists", "A file with this name already exists in the resources.");
            return;
        }

        File selectedFile = showFileChooser();
        if (selectedFile != null) {
            if(jsonValidator.isValidJson(selectedFile)) {
                if(jsonValidator.isValidCommandStructure(selectedFile)) {
                    openJsonSimulationApp(selectedFile, fileName);
                }
                else {
                    showAlert("Invalid JSON file", "Json commands structure is invalid.");
                }
            }
            else {
                showAlert("Invalid JSON file", "The file is not a valid JSON.");
            }
        }
    }

    private File showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        return fileChooser.showOpenDialog(new Stage());
    }

    private void showAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR, contentText, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private void openJsonSimulationApp(File selectedFile, String jsonFileName) {
        JsonSimulationApp app = new JsonSimulationApp();
        try {
            app.start(new Stage(), selectedFile, jsonFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeCurrentStage();
    }

    private void closeCurrentStage() {
        Stage currentStage = (Stage) loadJsonButton.getScene().getWindow();
        currentStage.close();
    }
}