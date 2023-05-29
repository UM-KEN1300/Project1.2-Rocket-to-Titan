package code.model.objects;

import code.model.Model;
import code.utils.HelperFunctions;

import java.util.*;

//TODO boost velocity to fuel
//TODO fix the queue
//TODO add the limits to the boost from the req in manual


public class Probe extends PlanetObject {
    private double shortestDistanceToTitan;
    private int probeNumber;
    private static int probeCounter = 0;
    private final Queue<Boost> listOfBoosts;
    private double fuelUsed;


    public Probe() {
        super(new double[3], new double[]{0, 0, 0});
        probeNumber = probeCounter;
        probeCounter++;
        listOfBoosts = new PriorityQueue<Boost>();
        setCoordinates(initialPosition());
        setMass(50_000);
        shortestDistanceToTitan = getDistanceToTitan();
    }


    @Override
    public void setCoordinates(double[] coordinates) {
        super.setCoordinates(coordinates);

        double distanceToTitan = getDistanceToTitan();
        if (distanceToTitan < shortestDistanceToTitan)
            shortestDistanceToTitan = distanceToTitan;
    }

    private double[] initialPosition() {
        double[] coordinates = HelperFunctions.subtract(Model.getPlanetObjects().get("Earth").getCoordinates(),
                Model.getPlanetObjects().get("Titan").getCoordinates());

        double magnitude = HelperFunctions.getVectorMagnitude(coordinates);
        for (int i = 0; i <= 2; i++)
            coordinates[i] = coordinates[i] * 6370 / magnitude;

        return HelperFunctions.addition(coordinates, Model.getPlanetObjects().get("Earth").getCoordinates());
    }

    public double getDistanceToTitan() {
        return HelperFunctions.getDistanceBetween(this, Model.getPlanetObjects().get("Titan"));
    }

    public double getShortestDistanceToTitan() {
        return shortestDistanceToTitan;
    }

    public boolean areBoostsValid(double step) {
        double maxImpulse = 3 * (Math.pow(10, 7)) * step;
        ArrayList<Boost> list = new ArrayList<Boost>(listOfBoosts);
        for (Boost boost : list) {
            if (boost.getFuel() > maxImpulse)
                return false;
        }
        return true;
    }

    public void addBoost(Boost boost) {
        listOfBoosts.add(boost);
        fuelUsed += boost.fuel;
    }

    public void BoosterMECH(double time) {
        if (listOfBoosts.peek() != null) {
            if (time == listOfBoosts.peek().getTimeOfBoost()) {
                double[] probeVelocity = getVelocity();
                double[] boostVelocity = listOfBoosts.poll().getVelocityOfBoost();
                setVelocity(HelperFunctions.addition(probeVelocity, boostVelocity));
            }
        }
    }

    public int getProbeNumber() {
        return probeNumber;
    }

    public double getFuelUsed() {
        return fuelUsed;
    }

    @Override
    public String toString() {
        return "Probe{" +
                "shortestDistanceToTitan=" + shortestDistanceToTitan +
                ", listOfBoosts=" + listOfBoosts +
                ", fuelUsed=" + fuelUsed +
                '}';
    }

    public static class Boost implements Comparable {
        private double timeOfBoost;
        private double[] velocityOfBoost;
        private double fuel;

        public Boost(double time, double[] velocityOfBoost) {
            this.timeOfBoost = time;
            this.velocityOfBoost = velocityOfBoost;
            //calculate the fuel used by the boost
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
        public int compareTo(Object o) {
            return 0;
        }
    }
}
