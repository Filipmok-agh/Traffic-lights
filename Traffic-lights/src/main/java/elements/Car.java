package elements;

public class Car {
    private final boolean goodDriver;
    private final String vehicleID;
    private final Direction startRoad;
    private final Direction endRoad;

    public Car(String vehicleID, Direction startRoad, Direction endRoad, boolean goodDriver) {
        this.vehicleID = vehicleID;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
        this.goodDriver = goodDriver;
    }

    public String getVehicleID() {
        return this.vehicleID;
    }

    public Direction getStartRoad() {
        return startRoad;
    }

    public Direction getEndRoad() {
        return endRoad;
    }

    public boolean canProceed(Light light) {
        return light == Light.GREEN || (light== Light.YELLOW && goodDriver);
    }
}
