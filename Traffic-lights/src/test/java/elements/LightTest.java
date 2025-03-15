package elements;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightTest {

    @Test
    void next() {
        //given
        Light redLight = Light.RED;
        Light redYellowLight = Light.RED_YELLOW;
        Light greenLight = Light.GREEN;
        Light yellowLight = Light.YELLOW;

        //then
        assertEquals(Light.RED_YELLOW, redLight.next());
        assertEquals(Light.GREEN, redYellowLight.next());
        assertEquals(Light.YELLOW, greenLight.next());
        assertEquals(Light.RED, yellowLight.next());
    }

    @Test
    void getColor() {
        //given
        Light redLight = Light.RED;
        Light redYellowLight = Light.RED_YELLOW;
        Light greenLight = Light.GREEN;
        Light yellowLight = Light.YELLOW;

        //then
        assertEquals(Color.RED,redLight.getColor());
        assertEquals(Color.YELLOW,redYellowLight.getColor());
        assertEquals(Color.GREEN,greenLight.getColor());
        assertEquals(Color.YELLOW,yellowLight.getColor());
    }
}