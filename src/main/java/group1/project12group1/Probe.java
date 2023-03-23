package group1.project12group1;

import helperFunction.HelperFunctions;

public class Probe extends PlanetObject{

    public static double fitness;
    private double[] position;
    private  double[] initialPosition;
    private  double[]  initialVelocity;
    private   double [] velocity;
    private final double mass = 50000;
    private double distanceToTitan;
    private final HelperFunctions helperFunctions=new HelperFunctions();
    PlanetObject Earth;
    PlanetObject Titan;

    public Probe(PlanetObject Earth, PlanetObject Titan,double[]velocity){
        this.Earth=Earth;
        this.Titan=Titan;
        initialPosition = calculateInitialPosition();
        this.velocity=velocity;
        this.initialVelocity=velocity;
        distanceToTitan=helperFunctions.getDistanceBetween(this,Titan);
    }

    public void
    checkDistance()
    {
        double newDistance=HelperFunctions.getDistanceBetween(this,Titan);
        if(newDistance<distanceToTitan)
        distanceToTitan=newDistance;

    }

    public double[] calculateInitialPosition()
    {
        double[] directionalVector=helperFunctions.subtract(Earth.getPositionalVector(),Titan.getPositionalVector());
        double mag=helperFunctions.getVectorMagnitude(directionalVector);
        for (int i = 0; i < 3; i++)
        {
         directionalVector[i]=directionalVector[i]*6370/mag;
        }

        return directionalVector;
    }

    public double[] getPosition()
    {
        return position;
    }

    public void setPosition(double[] position)
    {
        this.position = position;
    }

    public double[] getInitialPosition()
    {
        return initialPosition;
    }

    public void setInitialPosition(double[] initialPosition)
    {
        this.initialPosition = initialPosition;
    }

    public double[] getInitialVelocity()
    {
        return initialVelocity;
    }

    public void setInitialVelocity(double[] initialVelocity)
    {
        this.initialVelocity = initialVelocity;
    }

    public double[] getVelocity()
    {
        return velocity;
    }

    public void setVelocity(double[] velocity)
    {
        this.velocity = velocity;
    }

    @Override
    public double getMass()
    {
        return mass;
    }

    public double getDistanceToTitan()
    {
        return distanceToTitan;
    }

    public void setDistanceToTitan(double distanceToTitan)
    {
        this.distanceToTitan = distanceToTitan;
    }
}
