package ui.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import elements.Car;
import elements.Direction;

import java.util.ArrayList;

public class ManualSimulationPresenter extends SimulationPresenter {
    @FXML private ToggleButton startNorth, startSouth, startEast, startWest;
    @FXML private ToggleButton endNorth, endSouth, endEast, endWest;
    @FXML private CheckBox goodDriverCheckBox;
    @FXML private Button addCarButton, stepButton;
    @FXML private StackPane overlayPane;
    @FXML private Label carsLeftLabel;

    private Direction startDirection;
    private Direction endDirection;
    private Integer cnt = 0;

    @Override
    public void initialize() {
        super.initialize();

        initializeGroups();
        initializeEventHandlers();
    }

    private void initializeGroups() {
        initializeDirectionGroup(startNorth, startSouth, startEast, startWest, true);
        initializeDirectionGroup(endNorth, endSouth, endEast, endWest, false);
    }

    private void initializeDirectionGroup(ToggleButton north, ToggleButton south, ToggleButton east, ToggleButton west, boolean isStartDirection) {
        ToggleGroup group = new ToggleGroup();
        north.setToggleGroup(group);
        south.setToggleGroup(group);
        east.setToggleGroup(group);
        west.setToggleGroup(group);

        north.setOnAction(event -> setDirection(Direction.NORTH, isStartDirection));
        south.setOnAction(event -> setDirection(Direction.SOUTH, isStartDirection));
        east.setOnAction(event -> setDirection(Direction.EAST, isStartDirection));
        west.setOnAction(event -> setDirection(Direction.WEST, isStartDirection));
    }

    private void initializeEventHandlers() {
        addCarButton.setOnAction(event -> addCar());
        stepButton.setOnAction(event -> stepSimulation());
    }

    private void setDirection(Direction direction, boolean isStartDirection) {
        if (isStartDirection) {
            startDirection = direction;
        } else {
            endDirection = direction;
        }
    }

    private void addCar() {
        if(startDirection == null || endDirection == null) {
            showAlert("Error","Both Directions must be chosen");
            return;
        }
        if (startDirection == endDirection) {
            showAlert("Error", "Start Direction cannot be the same as the End Direction");
            return;
        }

        boolean isGoodDriver = goodDriverCheckBox.isSelected();
        Car newCar = new Car("vehicle" + cnt.toString(), startDirection, endDirection, isGoodDriver);
        cnt += 1;
        intersection.addCarToLane(newCar);

        drawGridWithColors();
        drawLines();
        drawPeople();
    }

    private void stepSimulation() {
        intersection.step();
        ArrayList<String> carsLeft = intersection.moveCarsFromLanes();
        String carsLeftText = "Cars that left the intersection:\n" + String.join("\n", carsLeft);
        carsLeftLabel.setText(carsLeftText);
        drawGridWithColors();
        drawLines();
        drawPeople();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
