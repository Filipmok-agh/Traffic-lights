package ui;

import javafx.scene.text.Text;
import infrastructure.Crosswalk;
import infrastructure.RoadPositionCalculator;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import infrastructure.Intersection;
import elements.Car;
import elements.Direction;
import infrastructure.TrafficLane;

import java.util.ArrayList;

public class SimulationPresenter {

    @FXML private ToggleButton startNorth, startSouth, startEast, startWest;
    @FXML private ToggleButton endNorth, endSouth, endEast, endWest;
    @FXML private CheckBox goodDriverCheckBox;
    @FXML private Button addCarButton, stepButton;
    @FXML private GridPane intersectionGrid;
    @FXML private StackPane overlayPane;
    @FXML private Label carsLeftLabel;

    private Intersection intersection;
    private Direction startDirection;
    private Direction endDirection;
    private Integer cnt = 0;

    @FXML
    public void initialize() {
        intersection = new Intersection(true);
        initializeGroups();
        initializeEventHandlers();

        drawGridWithColors();
        drawLines();
        drawPeople();
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

        north.setOnAction(event -> setDirection(Direction.North, isStartDirection));
        south.setOnAction(event -> setDirection(Direction.South, isStartDirection));
        east.setOnAction(event -> setDirection(Direction.East, isStartDirection));
        west.setOnAction(event -> setDirection(Direction.West, isStartDirection));
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

    private void drawGridWithColors() {
        int gridSize = 31;
        int cellSize = 27;

        intersectionGrid.getChildren().clear();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                StackPane cell = new StackPane();
                cell.setAlignment(Pos.CENTER);

                Color cellColor = getCellColor(row, col);

                Rectangle tile = new Rectangle(cellSize, cellSize, cellColor);
                tile.setStroke(Color.BLACK);
                tile.setStrokeWidth(1);

                cell.getChildren().add(tile);
                intersectionGrid.add(cell, col, row);
            }
        }
    }

    private Color getCellColor(int row, int col) {
        if (row <= 12 && col <= 12 || row >= 18 && col <= 12 || row <= 12 && col >= 18 || row >= 18 && col >= 18) {
            return Color.web("#00FF00");
        }
        return Color.web("#808080");
    }

    private void drawLines() {
        int cellSize = 27;
        ArrayList<TrafficLane> trafficLanes = intersection.getLanes();
        for (TrafficLane trafficLane : trafficLanes) {
            RoadPositionCalculator roadPositionCalculator = new RoadPositionCalculator(trafficLane);

            StackPane circleCell = new StackPane();
            circleCell.setAlignment(Pos.CENTER);

            Circle circle = new Circle((double) cellSize / 3, trafficLane.getLight().getColor());
            circleCell.getChildren().add(circle);

            String symbol = getDirectionSymbol(trafficLane);
            Text text = new Text(symbol);
            text.setFill(Color.BLACK);
            text.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            circleCell.getChildren().add(text);

            intersectionGrid.add(circleCell, roadPositionCalculator.getX(), roadPositionCalculator.getY());

            drawCarsInLane(trafficLane, roadPositionCalculator, cellSize);
        }
    }

    private void drawCarsInLane(TrafficLane trafficLane, RoadPositionCalculator roadPositionCalculator, int cellSize) {
        for (int j = 1; j <= trafficLane.getSize(); j++) {
            StackPane squareCell = new StackPane();
            squareCell.setAlignment(Pos.CENTER);

            Rectangle square = new Rectangle(cellSize, cellSize, Color.BLUE);
            square.setStroke(Color.BLACK);
            square.setStrokeWidth(1);

            squareCell.getChildren().add(square);
            intersectionGrid.add(squareCell, roadPositionCalculator.getCarPositionX(j), roadPositionCalculator.getCarPositionY(j));
        }
    }
    //TODO switch
    private String getDirectionSymbol(TrafficLane trafficLane) {
        if (trafficLane.getEndDirection() == Direction.North) {
            return "^";
        } else if (trafficLane.getEndDirection() == Direction.South) {
            return "v";
        } else if (trafficLane.getEndDirection() == Direction.West) {
            return "<";
        } else if (trafficLane.getEndDirection() == Direction.East) {
            return ">";
        }
        return "";
    }

    private void drawPeople() {
        drawPeopleOnCrosswalk(intersection.getEastCross(), 18);
        drawPeopleOnCrosswalk(intersection.getWestCross(), 12);
    }

    private void drawPeopleOnCrosswalk(Crosswalk crosswalk, int startX) {
        if (crosswalk.isBusy()) {
            for (int i = 0; i < 5; i++) {
                StackPane squareCell = new StackPane();
                squareCell.setAlignment(Pos.CENTER);

                Rectangle square = new Rectangle(15, 5, Color.WHITE);
                square.setStroke(Color.BLACK);
                square.setStrokeWidth(1);

                squareCell.getChildren().add(square);
                intersectionGrid.add(squareCell, startX, 13 + i);
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
