package infrastructure;

import elements.Car;
import elements.Direction;
import elements.Light;
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
        this.trafficLanes.add(new TrafficLane(Direction.SOUTH, Direction.WEST, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.SOUTH, Direction.EAST, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.NORTH, Direction.WEST, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.NORTH, Direction.EAST, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.WEST, Direction.NORTH, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.WEST, Direction.SOUTH, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.EAST, Direction.NORTH, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.EAST, Direction.SOUTH, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.SOUTH, Direction.NORTH, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.NORTH, Direction.SOUTH, Light.RED));
        this.trafficLanes.add(new TrafficLane(Direction.WEST, Direction.EAST, Light.GREEN));
        this.trafficLanes.add(new TrafficLane(Direction.EAST, Direction.WEST, Light.GREEN));
    }
    private void initializeLaneMapping() {
        this.laneMapping.put(new Pair<>(Direction.SOUTH, Direction.WEST), 0);
        this.laneMapping.put(new Pair<>(Direction.SOUTH, Direction.EAST), 1);
        this.laneMapping.put(new Pair<>(Direction.NORTH, Direction.WEST), 2);
        this.laneMapping.put(new Pair<>(Direction.NORTH, Direction.EAST), 3);
        this.laneMapping.put(new Pair<>(Direction.WEST, Direction.NORTH), 4);
        this.laneMapping.put(new Pair<>(Direction.WEST, Direction.SOUTH), 5);
        this.laneMapping.put(new Pair<>(Direction.EAST, Direction.NORTH), 6);
        this.laneMapping.put(new Pair<>(Direction.EAST, Direction.SOUTH), 7);
        this.laneMapping.put(new Pair<>(Direction.SOUTH, Direction.NORTH), 8);
        this.laneMapping.put(new Pair<>(Direction.NORTH, Direction.SOUTH), 9);
        this.laneMapping.put(new Pair<>(Direction.WEST, Direction.EAST), 10);
        this.laneMapping.put(new Pair<>(Direction.EAST, Direction.WEST), 11);
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
