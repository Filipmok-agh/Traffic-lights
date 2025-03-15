package infrastructure;

import elements.Car;
import elements.Direction;
import elements.Light;
import infrastructure.Crosswalk;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Intersection {

    private final ArrayList<TrafficLane> trafficLanes;
    private final Crosswalk westCrosswalk;
    private final Crosswalk eastCrosswalk;
    private final Map<Pair<Direction, Direction>, Integer> laneMapping;

    public Intersection(boolean people) {
        this.westCrosswalk = new Crosswalk(people);
        this.eastCrosswalk = new Crosswalk(people);
        this.trafficLanes = new ArrayList<>();
        this.laneMapping = new HashMap<>();
        initializeLaneMapping();
        initializeTrafficLanes();
        initializeBlockingSegments();
    }

    private void initializeTrafficLanes() {
        this.trafficLanes.add(new TrafficLane(Direction.South, Direction.West, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.South, Direction.East, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.North, Direction.West, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.North, Direction.East, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.West, Direction.North, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.West, Direction.South, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.East, Direction.North, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.East, Direction.South, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.South, Direction.North, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.North, Direction.South, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.West, Direction.East, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.East, Direction.West, Light.GREEN));
    }
    //TODO fo it in fors
    private void initializeLaneMapping() {
        this.laneMapping.put(new Pair<>(Direction.South, Direction.West), 0);
        this.laneMapping.put(new Pair<>(Direction.South, Direction.East), 1);
        this.laneMapping.put(new Pair<>(Direction.North, Direction.West), 2);
        this.laneMapping.put(new Pair<>(Direction.North, Direction.East), 3);
        this.laneMapping.put(new Pair<>(Direction.West, Direction.North), 4);
        this.laneMapping.put(new Pair<>(Direction.West, Direction.South), 5);
        this.laneMapping.put(new Pair<>(Direction.East, Direction.North), 6);
        this.laneMapping.put(new Pair<>(Direction.East, Direction.South), 7);
        this.laneMapping.put(new Pair<>(Direction.South, Direction.North), 8);
        this.laneMapping.put(new Pair<>(Direction.North, Direction.South), 9);
        this.laneMapping.put(new Pair<>(Direction.West, Direction.East), 10);
        this.laneMapping.put(new Pair<>(Direction.East, Direction.West), 11);
    }

    private void initializeBlockingSegments() {
        this.trafficLanes.get(0).addBlockingSegment(this.trafficLanes.get(9));
        this.trafficLanes.get(0).addBlockingSegment(this.westCrosswalk);
        this.trafficLanes.get(1).addBlockingSegment(this.eastCrosswalk);
        this.trafficLanes.get(2).addBlockingSegment(this.westCrosswalk);
        this.trafficLanes.get(3).addBlockingSegment(this.trafficLanes.get(8));
        this.trafficLanes.get(3).addBlockingSegment(this.eastCrosswalk);
        this.trafficLanes.get(4).addBlockingSegment(this.trafficLanes.get(11));
        this.trafficLanes.get(7).addBlockingSegment(this.westCrosswalk);
    }

    public void step() {
        for (TrafficLane trafficLane : this.trafficLanes) {
            trafficLane.step();
        }
        this.westCrosswalk.step();
        this.eastCrosswalk.step();
    }

    // Dodaj optionala
    public ArrayList<String> moveCarsFromLanes() {
        ArrayList<String> result = new ArrayList<>();
        for (TrafficLane trafficLane : this.trafficLanes)
        {
            String newResult = this.processCar(trafficLane);
            if(newResult != null)
            {
                result.add(newResult);
            }
        }
        return result;
    }

    public void addCarToLane(Car car) {
        int laneIndex = this.laneMapping.get(new Pair<>(car.getStartRoad(), car.getEndRoad()));
        this.trafficLanes.get(laneIndex).addCar(car);
    }

    private String processCar(TrafficLane trafficLane) {
        if (trafficLane.canProceed()) {
            return trafficLane.removeCar().getVehicleID();
        }
        return null;
    }

    public ArrayList<TrafficLane> getLanes() {
        return this.trafficLanes;
    }

    public Crosswalk getEastCross() {
        return this.eastCrosswalk;
    }

    public Crosswalk getWestCross() {
        return this.westCrosswalk;
    }
}
