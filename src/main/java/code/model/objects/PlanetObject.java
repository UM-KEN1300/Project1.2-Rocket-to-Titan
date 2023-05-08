package code.model.objects;

import code.utils.HelperFunctions;

public class PlanetObject implements ModeledObject {
    private final double[] COORDINATES;
    private final double[] VELOCITY;
    private final long MASS;
    //planetCode is Nasa object code
    private int planetCode;
    //Position for end position of planet(only used for tests)
    private double[] targetPosition;


    //planetCode only constructor
    // for the api that later adds the values
    public PlanetObject(int planetCode, long mass)
    {
        this.planetCode = planetCode;
        this.MASS=mass;
        COORDINATES= new double[]{0, 0, 0};
        VELOCITY= new double[]{0, 0, 0};
    }


    public PlanetObject(double[] COORDINATES, double[] VELOCITY, long MASS) {
        this.COORDINATES = COORDINATES;
        this.VELOCITY = VELOCITY;
        this.MASS = MASS;
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

    @Override
    public long getMass() {
        return MASS;
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
            force[i] = -G * MASS * planetObject.getMass() * distance * distance * distance * positionalVector[i];
            acceleration[i] = force[i] / MASS;
        }
        return acceleration;
    }

//GETTER and SETTERS
    public int getPlanetCode()
    {
        return planetCode;
    }

    public void setPlanetCode(int planetCode)
    {
        this.planetCode = planetCode;
    }

    public double[] getTargetPosition()
    {
        return targetPosition;
    }

    public void setTargetPosition(double[] targetPosition)
    {
        this.targetPosition = targetPosition;
    }

    public double getX(){return this.COORDINATES[0];}
    public double getY(){return this.COORDINATES[1];}
    public double getZ(){return this.COORDINATES[2];}
}
