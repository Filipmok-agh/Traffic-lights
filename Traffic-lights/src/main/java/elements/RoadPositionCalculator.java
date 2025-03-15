package elements;

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
            case NORTH -> 14 + (endDirection == Direction.EAST ? 1 : (endDirection == Direction.WEST ? -1 : 0));
            case EAST ->  19;
            case WEST ->  11;
            case SOUTH -> 16 + (endDirection == Direction.EAST ? 1 : (endDirection == Direction.WEST ? -1 : 0));
        };

        this.y = switch (trafficLane.getStartDirection())
        {
            case NORTH -> 11;
            case EAST ->  14 + (endDirection == Direction.NORTH ? -1 : (endDirection == Direction.SOUTH ? 1 : 0));
            case WEST ->  16 + (endDirection == Direction.NORTH ? -1 : (endDirection == Direction.SOUTH ? 1 : 0));
            case SOUTH -> 19;
        };
        this.xIncrease = (startDirection == Direction.EAST) ? 1 : (startDirection == Direction.WEST) ? -1 : 0;
        this.yIncrease = (startDirection == Direction.NORTH) ? -1 : (startDirection == Direction.SOUTH) ? 1 : 0;

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
