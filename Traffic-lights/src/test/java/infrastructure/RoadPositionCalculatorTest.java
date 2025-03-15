package infrastructure;

import elements.Direction;
import elements.Light;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadPositionCalculatorTest {

    private TrafficLane northToSouthTrafficLane;
    private TrafficLane eastToWestTrafficLane;
    private TrafficLane westToEastTrafficLane;
    private TrafficLane southToNorthTrafficLane;

    @BeforeEach
    void setUp() {
        northToSouthTrafficLane = new TrafficLane(Direction.North, Direction.South, Light.GREEN);
        eastToWestTrafficLane = new TrafficLane(Direction.East, Direction.West, Light.GREEN);
        westToEastTrafficLane = new TrafficLane(Direction.West, Direction.East, Light.GREEN);
        southToNorthTrafficLane = new TrafficLane(Direction.South, Direction.North, Light.GREEN);
    }

    @Test
    void initialPositionNorthToSouth() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(northToSouthTrafficLane);

        // then
        assertEquals(14, calculator.getX());
        assertEquals(11, calculator.getY());
    }

    @Test
    void initialPositionEastToWest() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(eastToWestTrafficLane);

        // then
        assertEquals(19, calculator.getX());
        assertEquals(14, calculator.getY());
    }

    @Test
    void initialPositionWestToEast() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(westToEastTrafficLane);

        // then
        assertEquals(11, calculator.getX());
        assertEquals(16, calculator.getY());
    }

    @Test
    void initialPositionSouthToNorth() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(southToNorthTrafficLane);

        // then
        assertEquals(16, calculator.getX());
        assertEquals(19, calculator.getY());
    }

    @Test
    void carPositionNorthToSouth() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(northToSouthTrafficLane);

        // when
        int carPositionX1 = calculator.getCarPositionX(1);
        int carPositionY1 = calculator.getCarPositionY(1);
        int carPositionX2 = calculator.getCarPositionX(2);
        int carPositionY2 = calculator.getCarPositionY(2);

        // then
        assertEquals(14, carPositionX1);
        assertEquals(10, carPositionY1);
        assertEquals(14, carPositionX2);
        assertEquals(9, carPositionY2);
    }

    @Test
    void carPositionEastToWest() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(eastToWestTrafficLane);

        // when
        int carPositionX1 = calculator.getCarPositionX(1);
        int carPositionY1 = calculator.getCarPositionY(1);
        int carPositionX2 = calculator.getCarPositionX(2);
        int carPositionY2 = calculator.getCarPositionY(2);

        // then
        assertEquals(20, carPositionX1);
        assertEquals(14, carPositionY1);
        assertEquals(21, carPositionX2);
        assertEquals(14, carPositionY2);
    }

    @Test
    void carPositionWestToEast() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(westToEastTrafficLane);

        // when
        int carPositionX1 = calculator.getCarPositionX(1);
        int carPositionY1 = calculator.getCarPositionY(1);
        int carPositionX2 = calculator.getCarPositionX(2);
        int carPositionY2 = calculator.getCarPositionY(2);

        // then
        assertEquals(10, carPositionX1);
        assertEquals(16, carPositionY1);
        assertEquals(9, carPositionX2);
        assertEquals(16, carPositionY2);
    }

    @Test
    void carPositionSouthToNorth() {
        // given
        RoadPositionCalculator calculator = new RoadPositionCalculator(southToNorthTrafficLane);

        // when
        int carPositionX1 = calculator.getCarPositionX(1);
        int carPositionY1 = calculator.getCarPositionY(1);
        int carPositionX2 = calculator.getCarPositionX(2);
        int carPositionY2 = calculator.getCarPositionY(2);

        // then
        assertEquals(16, carPositionX1);
        assertEquals(20, carPositionY1);
        assertEquals(16, carPositionX2);
        assertEquals(21, carPositionY2);
    }
}