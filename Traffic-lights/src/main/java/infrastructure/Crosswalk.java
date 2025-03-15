package infrastructure;

import java.util.Random;

public class Crosswalk implements RoadSegment {
    private int pedestrianCrossingTime;
    private static final int DEFAULT_CYCLE_TIME = 10;
    private int cycleTime;
    private final boolean isCrossWalkOn;
    private final Random random;


    public Crosswalk(boolean isCrossWalkOn, Random random) {
        this.random = random;
        this.pedestrianCrossingTime = 0;
        this.cycleTime = 5;
        this.isCrossWalkOn = isCrossWalkOn;
    }

    public Crosswalk(boolean isCrossWalkOn) {
        this(isCrossWalkOn, new Random());
    }

    @Override
    public boolean isBusy() {
        return this.isCrossWalkOn && this.pedestrianCrossingTime > 0;
    }

    @Override
    public void step() {
        if (!isCrossWalkOn) return;

        if(this.pedestrianCrossingTime >0) {
            this.pedestrianCrossingTime--;
        }

        this.cycleTime--;

        if (this.cycleTime == 0) {
            this.setPeopleOn();
            this.cycleTime = DEFAULT_CYCLE_TIME;
        }
    }

    private void setPeopleOn() {
        this.pedestrianCrossingTime = this.random.nextInt(3);
    }
}
