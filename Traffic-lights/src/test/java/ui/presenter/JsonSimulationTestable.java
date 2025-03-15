package ui.presenter;

import com.google.gson.*;
import elements.Car;
import elements.Direction;
import infrastructure.Intersection;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonSimulationTestable {
    private final File loadedJsonFile;
    private final String saveJsonName;
    private final ArrayList<ArrayList<String>> stepStatuses = new ArrayList<>();
    private final Intersection intersection;

    public JsonSimulationTestable(File loadedJsonFile, String saveJsonName, Intersection intersection) {
        this.loadedJsonFile = loadedJsonFile;
        this.saveJsonName = saveJsonName;
        this.intersection = intersection;
    }

    public void runSimulation() {
        try (FileReader reader = new FileReader(loadedJsonFile)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            if (jsonObject.has("commands")) {
                JsonArray commands = jsonObject.getAsJsonArray("commands");
                processCommands(commands);
                saveJsonStepStatusesToFile();
            } else {
                System.out.println("Error: No commands found in JSON file.");
            }
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    private void processCommands(JsonArray commands) {
        for (JsonElement element : commands) {
            JsonObject command = element.getAsJsonObject();
            String type = command.get("type").getAsString();

            switch (type) {
                case "addVehicle" -> handleAddVehicle(command);
                case "step" -> handleStep();
            }

            printIntersectionState();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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

    private void printIntersectionState() {
        System.out.println("Current intersection state:");
        System.out.println(intersection);
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
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonOutput);
    }

    private void saveJsonStepStatusesToFile() {
        String jsonString = generateJsonStepStatuses();
        try (FileWriter fileWriter = new FileWriter("src/main/resources/" + saveJsonName + ".json")) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            System.out.println("Error saving JSON file: " + e.getMessage());
        }
    }
}
