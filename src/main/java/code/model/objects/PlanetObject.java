package code.model.objects;

import code.utils.HelperFunctions;

public class PlanetObject implements ModeledObject {
    private final double[] COORDINATES = new double[3];
    private final double[] VELOCITY = new double[3];
    private long mass;
    private long radius;
    //planetCode is Nasa object code
    private int planetCode;
    //Position for end position of planet(only used for tests)
    private double[] targetPosition;


    public PlanetObject(double[] coordinates, double[] velocity) {
        setCoordinates(coordinates);
        setVelocity(velocity);
    }


    //planetCode only constructor
    // for the api that later adds the values
    public PlanetObject(int planetCode) {
        this.planetCode = planetCode;
    }


    @Override
    public double[] getCoordinates() {
        return COORDINATES;
    }

    @Override
    public void setCoordinates(double[] coordinates) {
        COORDINATES[0] = coordinates[0];
        COORDINATES[1] = coordinates[1];
        COORDINATES[2] = coordinates[2];
    }

    @Override
    public double[] getVelocity() {
        return VELOCITY;
    }

    @Override
    public void setVelocity(double[] velocity) {
        VELOCITY[0] = velocity[0];
        VELOCITY[1] = velocity[1];
        VELOCITY[2] = velocity[2];
    }

    public void setMass(long mass) {
        this.mass = mass;
    }

    @Override
    public long getMass() {
        return mass;
    }

    @Override
    public double[] accelerationBetween(PlanetObject planetObject) {
        double[] force = new double[3];
        double[] positionalVector;
        double[] acceleration = new double[3];

        positionalVector = HelperFunctions.subtract(COORDINATES, planetObject.getCoordinates());

        double distance = 1 / HelperFunctions.getDistanceBetweenWithVectors(COORDINATES, planetObject.getCoordinates());
        if (distance == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException("The two celestial bodies are at the same location");

        for (int i = 0; i <= 2; i++) {
            force[i] = -G * mass * planetObject.getMass() * distance * distance * distance * positionalVector[i];
            acceleration[i] = force[i] / mass;
        }
        return acceleration;
    }

//GETTER and SETTERS

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public int getPlanetCode() {
        return planetCode;
    }

    public double[] getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(double[] targetPosition) {
        this.targetPosition = targetPosition;
    }
}
