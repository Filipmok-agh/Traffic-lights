package infrastructure;

import elements.Car;
import elements.Direction;
import elements.Light;
import infrastructure.RoadSegment;
import javafx.util.Pair;

import java.util.*;

public class TrafficLane implements RoadSegment {
    private final Queue<Car> cars = new LinkedList<>();
    private int cycleTime;
    private final Direction startDirection;
    private final Direction endDirection;
    private Light light;
    private final ArrayList<RoadSegment> blockingSegments;
    private static final int MAX_CAPACITY = 10;
    private static final int DEFAULT_CYCLE_TIME = 5;


    public TrafficLane(Direction startDirection, Direction endDirection, Light light) {
        this.cycleTime = DEFAULT_CYCLE_TIME;
        this.startDirection = startDirection;
        this.endDirection = endDirection;
        this.light = light;
        this.blockingSegments = new ArrayList<>();
    }

    public int getSize()
    {
        return this.cars.size();
    }

    public Light getLight() {
        return this.light;
    }

    public Direction getStartDirection() {
        return this.startDirection;
    }

    public Direction getEndDirection() {
        return this.endDirection;
    }

    @Override
    public void step() {
        this.cycleTime--;
        if(this.cycleTime ==1) {
            this.light=this.light.next();
        }
        if (cycleTime == 0) {
            this.light = this.light.next();
            this.cycleTime = DEFAULT_CYCLE_TIME;
        }
    }

    @Override
    public boolean isBusy() {
        return !this.cars.isEmpty() && this.cars.peek().canProceed(this.light);
    }

    public boolean canProceed() {
        if (!this.cars.isEmpty() && this.cars.peek().canProceed(this.light)) {
            for (RoadSegment roadSegment : this.blockingSegments) {
                if (roadSegment.isBusy()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Car removeCar() {
        return this.cars.poll();
    }

    public void addCar(Car car) {
        if (this.cars.size() >= MAX_CAPACITY) {
            return;
        }
        this.cars.add(car);
    }

    public void addBlockingSegment(RoadSegment roadSegment) {
        this.blockingSegments.add(roadSegment);
    }
}
