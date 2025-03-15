package elements;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    void getVehicleID() {
        //given
        Car car = new Car("car",Direction.SOUTH,Direction.NORTH,true);

        //then
        assertEquals("car",car.getVehicleID());

    }

    @Test
    void getStartRoad() {
        //given
        Car car = new Car("car",Direction.SOUTH,Direction.NORTH,true);

        //then
        assertEquals(Direction.SOUTH,car.getStartRoad());
    }

    @Test
    void getEndRoad() {
        //given
        Car car = new Car("car",Direction.SOUTH,Direction.NORTH,true);

        //then
        assertEquals(Direction.NORTH,car.getEndRoad());
    }

    @Test
    void canProceed() {
        //given
        Light redLight =Light.RED;
        Light redYellowLight =Light.RED_YELLOW;
        Light greenLight =Light.GREEN;
        Light yellowLight =Light.YELLOW;
        Car goodDriver = new Car("goodDriver",Direction.SOUTH,Direction.NORTH,true);
        Car notGoodDriver = new Car("notGoodDriver",Direction.SOUTH,Direction.NORTH,false);

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