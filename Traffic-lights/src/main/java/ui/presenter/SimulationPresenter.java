package ui.presenter;

import infrastructure.Intersection;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import infrastructure.Crosswalk;
import elements.RoadPositionCalculator;
import javafx.scene.shape.Circle;
import infrastructure.TrafficLane;

import java.util.ArrayList;

public class SimulationPresenter {

    @FXML GridPane intersectionGrid;
    Intersection intersection;

    public void initialize() {
        intersection = new Intersection(true);
        drawGridWithColors();
        drawLines();
        drawPeople();
    }
    void drawGridWithColors() {
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

    void drawLines() {
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

    private String getDirectionSymbol(TrafficLane trafficLane) {
        return switch (trafficLane.getEndDirection()) {
            case NORTH -> "^";
            case SOUTH -> "v";
            case WEST -> "<";
            case EAST -> ">";
        };
    }

    void drawPeople() {
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

}
