package elements;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    void getVehicleID() {
        //given
        Car car = new Car("car",Direction.South,Direction.North,true);

        //then
        assertEquals("car",car.getVehicleID());

    }

    @Test
    void getStartRoad() {
        //given
        Car car = new Car("car",Direction.South,Direction.North,true);

        //then
        assertEquals(Direction.South,car.getStartRoad());
    }

    @Test
    void getEndRoad() {
        //given
        Car car = new Car("car",Direction.South,Direction.North,true);

        //then
        assertEquals(Direction.North,car.getEndRoad());
    }

    @Test
    void canProceed() {
        //given
        Light redLight =Light.RED;
        Light redYellowLight =Light.RED_YELLOW;
        Light greenLight =Light.GREEN;
        Light yellowLight =Light.YELLOW;
        Car goodDriver = new Car("goodDriver",Direction.South,Direction.North,true);
        Car notGoodDriver = new Car("notGoodDriver",Direction.South,Direction.North,false);

        //then
        assertTrue(goodDriver.canProceed(greenLight));
        assertTrue(goodDriver.canProceed(yellowLight));
        assertTrue(goodDriver.canProceed(greenLight));

        assertFalse(goodDriver.canProceed(redLight));
        assertFalse(goodDriver.canProceed(redYellowLight));
        assertFalse(notGoodDriver.canProceed(redLight));
        assertFalse(notGoodDriver.canProceed(redYellowLight));
        assertFalse(notGoodDriver.canProceed(yellowLight));
    }
}