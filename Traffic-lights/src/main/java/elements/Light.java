package elements;

import javafx.scene.paint.Color;

public enum Light {
    RED,
    RED_YELLOW,
    GREEN,
    YELLOW;

    public Color getColor() {
        return switch (this) {
            case RED -> Color.RED;
            case RED_YELLOW, YELLOW -> Color.YELLOW;
            case GREEN -> Color.GREEN;
        };
    }

    public Light next() {
        return switch (this) {
            case RED -> RED_YELLOW;
            case RED_YELLOW -> GREEN;
            case GREEN -> YELLOW;
            case YELLOW -> RED;
        };
    }
}
