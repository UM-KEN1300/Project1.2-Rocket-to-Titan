package code.model.objects;

import code.utils.HelperFunctions;

public class Boost implements Comparable<Boost> {
    private double timeOfBoost;
    private double[] velocityOfBoost;
    private double fuel;


    public Boost(double time, double[] velocityOfBoost) {
        this.timeOfBoost = time;
        this.velocityOfBoost = velocityOfBoost;
        fuel = HelperFunctions.getVectorMagnitude(velocityOfBoost) * 50_000;
    }


    public double getTimeOfBoost() {
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
        return Double.compare(this.timeOfBoost, other.timeOfBoost);
    }
}
