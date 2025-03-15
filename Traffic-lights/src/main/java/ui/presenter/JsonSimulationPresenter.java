package ui.presenter;

import com.google.gson.*;
import elements.Car;
import elements.Direction;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonSimulationPresenter extends SimulationPresenter {
    private final File loadedJsonFile;
    private final String saveJsonName;
    private final ArrayList<ArrayList<String>> stepStatuses = new ArrayList<>();

    @FXML private Button startButton;

    public JsonSimulationPresenter(File loadedJsonFile, String saveJsonName) {
        this.loadedJsonFile = loadedJsonFile;
        this.saveJsonName = saveJsonName;
    }

    public void initialize() {
        super.initialize();

        startButton.setOnAction(event -> {

            startButton.setDisable(true);

            Task<Void> simulationTask = new Task<>() {
                @Override
                protected Void call() {

                        try (FileReader reader = new FileReader(loadedJsonFile)) {
                            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                            if (jsonObject.has("commands")) {
                                JsonArray commands = jsonObject.getAsJsonArray("commands");
                                processCommands(commands);
                                saveJsonStepStatusesToFile();
                            } else {
                                showAllert("No commands found");
                            }
                        } catch (IOException e) {
                            showAllert("Error reading JSON file: " + e.getMessage());
                        }
                    return null;
                }
            };

            Thread simulationThread = new Thread(simulationTask);
            simulationThread.setDaemon(true);
            simulationThread.start();
        });
    }

    private void processCommands(JsonArray commands) {
        for (int i = 0; i < commands.size(); i++) {
            JsonObject command = commands.get(i).getAsJsonObject();
            String type = command.get("type").getAsString();

            switch (type) {
                case "addVehicle" -> handleAddVehicle(command);

                case "step" -> handleStep();
            }

            javafx.application.Platform.runLater(() -> {
                drawGridWithColors();
                drawLines();
                drawPeople();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAddVehicle(JsonObject command) {
        String vehicleId = command.get("vehicleId").getAsString();
        String startRoadString = command.get("startRoad").getAsString();
        String endRoadString = command.get("endRoad").getAsString();
        boolean goodDriver = command.get("goodDriver").getAsBoolean();

        Direction startRoad = Direction.fromString(startRoadString);
        Direction endRoad = Direction.fromString(endRoadString);

        Car car = new Car(vehicleId, startRoad, endRoad, goodDriver);
        intersection.addCarToLane(car);
    }

    private void handleStep() {
        intersection.step();
        ArrayList<String> carsLeft = intersection.moveCarsFromLanes();
        stepStatuses.add(carsLeft);
    }

    private String generateJsonStepStatuses() {
        JsonObject jsonOutput = new JsonObject();
        JsonArray stepStatusesArray = new JsonArray();

        for (ArrayList<String> leftVehicles : stepStatuses) {
            JsonObject stepStatusObject = new JsonObject();
            JsonArray leftVehiclesArray = new JsonArray();

            for (String vehicle : leftVehicles) {
                leftVehiclesArray.add(vehicle);
            }

            stepStatusObject.add("leftVehicles", leftVehiclesArray);

            stepStatusesArray.add(stepStatusObject);
        }

        jsonOutput.add("stepStatuses", stepStatusesArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonOutput);
    }

    private void saveJsonStepStatusesToFile() {
        String jsonString = generateJsonStepStatuses();
        try {
            File file = new File("src/main/resources/" + saveJsonName + ".json");
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(jsonString);
            }
        } catch (IOException e) {
            showAllert("Error saving JSON file: " + e.getMessage());
        }
    }

    private void showAllert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error: Bad JSON format");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
