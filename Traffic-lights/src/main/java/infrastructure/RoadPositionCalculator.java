package infrastructure;

import elements.Direction;
import infrastructure.TrafficLane;

public class RoadPositionCalculator
{
    private final int x;
    private final int y;
    private final int xIncrease;
    private final int yIncrease;
    public RoadPositionCalculator(TrafficLane trafficLane) {
        Direction startDirection = trafficLane.getStartDirection();
        Direction endDirection = trafficLane.getEndDirection();
        this.x = switch (startDirection)
        {
            case North -> 14 + (endDirection == Direction.East ? 1 : (endDirection == Direction.West ? -1 : 0));
            case East ->  19;
            case West ->  11;
            case South -> 16 + (endDirection == Direction.East ? 1 : (endDirection == Direction.West ? -1 : 0));
        };

        this.y = switch (trafficLane.getStartDirection())
        {
            case North -> 11;
            case East ->  14 + (endDirection == Direction.North ? -1 : (endDirection == Direction.South ? 1 : 0));
            case West ->  16 + (endDirection == Direction.North ? -1 : (endDirection == Direction.South ? 1 : 0));
            case South -> 19;
        };
        this.xIncrease = (startDirection == Direction.East) ? 1 : (startDirection == Direction.West) ? -1 : 0;
        this.yIncrease = (startDirection == Direction.North) ? -1 : (startDirection == Direction.South) ? 1 : 0;

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getCarPositionX(int i) {
        return x + this.xIncrease * i;
    }

    public int getCarPositionY(int i) {
        return y + this.yIncrease * i;
    }
}
