package elements;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction fromString(String string) {
        return switch (string){
            case "north" -> Direction.NORTH;
            case "east" -> Direction.EAST;
            case "west" -> Direction.WEST;
            case "south" -> Direction.SOUTH;
            default -> throw new IllegalStateException("Unexpected value: " + string);
        };
    }
}
