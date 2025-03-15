package elements;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JsonValidator {
    private JsonObject parseJsonFile(File jsonFile) throws IOException {
        try (FileReader reader = new FileReader(jsonFile)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    public boolean isValidJson(File file) {
        try {
            parseJsonFile(file);
            return true;
        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            return false;
        }
    }

    public boolean isValidCommandStructure(File jsonFile) {
        try {
            JsonObject jsonObject = parseJsonFile(jsonFile);
            if (!jsonObject.has("commands") || !jsonObject.get("commands").isJsonArray()) {
                return false;
            }

            JsonArray commands = jsonObject.getAsJsonArray("commands");
            for (JsonElement commandElement : commands) {
                JsonObject command = commandElement.getAsJsonObject();
                if (!isValidCommand(command)) {
                    return false;
                }
            }
            return true;
        } catch (IOException | JsonSyntaxException e) {
            return false;
        }
    }

    private boolean isValidCommand(JsonObject command) {
        if (!command.has("type")) {
            return false;
        }

        String type = command.get("type").getAsString();
        return switch (type) {
            case "addVehicle" -> isValidAddVehicleCommand(command);
            case "step" -> true;
            default -> false;
        };
    }

    private boolean isValidAddVehicleCommand(JsonObject command) {
        return command.has("vehicleId") &&
                command.has("startRoad") &&
                command.has("endRoad") &&
                isValidRoads(command.get("startRoad").getAsString(), command.get("endRoad").getAsString()) &&
                isValidGoodDriver(command.get("goodDriver").getAsString());
    }

    private boolean isValidRoads(String startRoad, String endRoad) {
        if (startRoad.equals(endRoad)) {
            return false;
        }

        String[] validDirections = {"north", "south", "east", "west"};

        boolean isStartRoadValid = false;
        boolean isEndRoadValid = false;

        for (String direction : validDirections) {
            if (startRoad.equals(direction)) {
                isStartRoadValid = true;
            }
            if (endRoad.equals(direction)) {
                isEndRoadValid = true;
            }
        }

        return isStartRoadValid && isEndRoadValid;
    }


    private boolean isValidGoodDriver(String goodDriver) {
        return "true".equals(goodDriver) || "false".equals(goodDriver);
    }
}
