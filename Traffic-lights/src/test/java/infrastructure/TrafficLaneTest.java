package infrastructure;

import elements.Car;
import elements.Direction;
import elements.Light;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLaneTest {

    @Test
    void getSize() {
        //given
        TrafficLane emptyTrafficLane = new TrafficLane(Direction.South,Direction.North,Light.YELLOW);
        TrafficLane notEmptyTrafficLane = new TrafficLane(Direction.South,Direction.North,Light.YELLOW);

        //when
        notEmptyTrafficLane.addCar(new Car("car",Direction.South,Direction.North,true));

        //then
        assertEquals(0, emptyTrafficLane.getSize());
        assertEquals(1, notEmptyTrafficLane.getSize());
    }

    @Test
    void getLight() {
        //given
        TrafficLane TrafficLane = new TrafficLane(Direction.South,Direction.North,Light.YELLOW);

        //then
        assertEquals(Light.YELLOW, TrafficLane.getLight());
    }

    @Test
    void getStartDirection() {
        //given
        TrafficLane TrafficLane = new TrafficLane(Direction.South,Direction.North,Light.YELLOW);

        //then
        assertEquals(Direction.South, TrafficLane.getStartDirection());
    }

    @Test
    void getEndDirection() {
        //given
        TrafficLane TrafficLane = new TrafficLane(Direction.South,Direction.North,Light.YELLOW);

        //then
        assertEquals(Direction.North, TrafficLane.getEndDirection());
    }

    @Test
    void stepWithGoodDriver() {
        //given
        TrafficLane trafficLaneWithGoodDriver = new TrafficLane(Direction.South, Direction.North, Light.RED);
        trafficLaneWithGoodDriver.addCar(new Car("car", Direction.South, Direction.North, true));

        // then
        for (int i = 0; i < 4; i++) {
            trafficLaneWithGoodDriver.step();
            assertFalse(trafficLaneWithGoodDriver.isBusy());
        }

        for (int i = 0; i < 5; i++) {
            trafficLaneWithGoodDriver.step();
            assertTrue(trafficLaneWithGoodDriver.isBusy());
        }
    }

    @Test
    void stepWithBadDriver() {
        //given
        TrafficLane trafficLaneWithNotGoodDriver = new TrafficLane(Direction.South, Direction.North, Light.RED);
        trafficLaneWithNotGoodDriver.addCar(new Car("car", Direction.South, Direction.North, false));

        // then
        for (int i = 0; i < 4; i++) {
            trafficLaneWithNotGoodDriver.step();
            assertFalse(trafficLaneWithNotGoodDriver.isBusy());
        }

        for (int i = 0; i < 4; i++) {
            trafficLaneWithNotGoodDriver.step();
            assertTrue(trafficLaneWithNotGoodDriver.isBusy());
        }
        // light is Yellow so notGoodDriver should not be able to drive
        trafficLaneWithNotGoodDriver.step();
        assertFalse(trafficLaneWithNotGoodDriver.isBusy());
    }

    @Test
    void isBusy() {
        //given
        TrafficLane greenLightTrafficLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);
        TrafficLane redLightTrafficLane = new TrafficLane(Direction.South,Direction.North,Light.RED);
        TrafficLane emptyTrafficLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);

        //when
        greenLightTrafficLane.addCar(new Car("car",Direction.South,Direction.North,true));
        redLightTrafficLane.addCar(new Car("car",Direction.South,Direction.North,true));

        //then
        assertTrue(greenLightTrafficLane.isBusy());
        assertFalse(redLightTrafficLane.isBusy());
        assertFalse(emptyTrafficLane.isBusy());
    }

    @Test
    void canProceedWithoutCar() {
        //given
        TrafficLane trafficLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);

        //then
        assertFalse(trafficLane.canProceed());
    }
    @Test
    void canProceedWithoutBlockingSegment() {
        //given
        TrafficLane trafficLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);

        //when
        trafficLane.addCar(new Car("car",Direction.South,Direction.North,true));

        //then
        assertTrue(trafficLane.canProceed());
    }

    @Test
    void canProceedWithBlockingSegment() {
        //given
        TrafficLane trafficLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);
        TrafficLane blockingLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);

        //when
        trafficLane.addCar(new Car("car",Direction.South,Direction.North,true));
        blockingLane.addCar(new Car("car",Direction.South,Direction.North,true));
        trafficLane.addBlockingSegment(blockingLane);

        //then
        assertFalse(trafficLane.canProceed());

    }

    @Test
    void canProceedWithEmptyBlockingSegment() {
        //given
        TrafficLane trafficLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);
        TrafficLane blockingLane = new TrafficLane(Direction.South,Direction.North,Light.GREEN);

        //when
        trafficLane.addCar(new Car("car",Direction.South,Direction.North,true));
        trafficLane.addBlockingSegment(blockingLane);

        //then
        assertTrue(trafficLane.canProceed());

    }

    @Test
    void removeCar() {
        //given
        TrafficLane trafficLane = new TrafficLane(Direction.South,Direction.North,Light.YELLOW);

        //when
        trafficLane.addCar(new Car("car",Direction.South,Direction.North,true));

        //then
        assertEquals(1, trafficLane.getSize());
        assertEquals("car", trafficLane.removeCar().getVehicleID() );
        assertEquals(0, trafficLane.getSize());
    }

    @Test
    void addCar() {
        //given
        TrafficLane trafficLane = new TrafficLane(Direction.South,Direction.North,Light.YELLOW);

        //when
        trafficLane.addCar(new Car("car",Direction.South,Direction.North,true));

        //then
        assertEquals(1, trafficLane.getSize());
    }

}