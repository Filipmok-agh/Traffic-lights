package infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CrosswalkTest {

    @Test
    void crossWalk() {
        //given
        //Seed sets pedestrianCrossingTime = 1
        Crosswalk crosswalkOn = new Crosswalk(true, new Random(12345));
        Crosswalk crosswalkOff = new Crosswalk(false);

        //then
        for (int i = 0; i < 5; i++) {
            assertFalse(crosswalkOn.isBusy());
            assertFalse(crosswalkOff.isBusy());
            crosswalkOn.step();
            crosswalkOff.step();
        }

        // Expected: after 5 steps, crosswalkOn should be busy as pedestrianCrossingTime is 1
        assertTrue(crosswalkOn.isBusy());
        assertFalse(crosswalkOff.isBusy());

        // Now simulate one more step and check if it's no longer busy
        crosswalkOn.step();
        crosswalkOff.step();

        // Expected: both crosswalks should be not busy after the pedestrian crossing time expires
        assertFalse(crosswalkOn.isBusy());
        assertFalse(crosswalkOff.isBusy());
    }
}