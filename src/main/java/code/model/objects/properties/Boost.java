package code.model.objects.properties;

import code.utils.HelperFunctions;
import code.utils.Time;

/**
 * Class whose objects contain information about a single boost added to the spacecraft (Probe object).
 *
 * <p>
 * Extends comparable as all boosts are later added to a PriorityQueue in the Probe object.
 * </p>
 */
public class Boost implements Comparable<Boost> {
    private final Time timeOfBoost;
    private final double[] velocityOfBoost;
    private final double fuel;


    /**
     * @param time            double representing the time of the boost in days
     * @param velocityOfBoost double array representing a vector containing values of
     *                        the boost's velocity in three dimensions
     */
    public Boost(Time time, double[] velocityOfBoost) {
        this.timeOfBoost = time;
        this.velocityOfBoost = velocityOfBoost;
        fuel = HelperFunctions.getVectorMagnitude(velocityOfBoost) * 50_000;
    }


    public Time getTimeOfBoost() {
        return timeOfBoost;
    }

    public double[] getVelocityOfBoost() {
        return velocityOfBoost;
    }

    public double getFuel() {
        return fuel;
    }

    @Override
    public int compareTo(Boost other) {
        return 1;
    }
}
