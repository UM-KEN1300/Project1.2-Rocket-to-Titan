package group1.project12group1;
import helperFunction.HelperFunctions;

import java.util.ArrayList;

public class Probe extends PlanetObject{
    private  double[] initialPosition;
    private  double[]  initialVelocity;
    private double distanceToTitan;
    private final HelperFunctions helperFunctions=new HelperFunctions();
    PlanetObject Earth;
    PlanetObject Titan;

    public Probe(PlanetObject Earth, PlanetObject Titan,double[]velocity){
        this.Earth=Earth;
        this.Titan=Titan;
        super.positionalVector=helperFunctions.addition(calculateInitialPosition(),Earth.getPositionalVector());
        this.initialVelocity=velocity;
        super.velocityVector=velocity;
        super.mass=50000;
        distanceToTitan=helperFunctions.getDistanceBetween(this,Titan);
    }
@Override
    public void updatePosition(double[] acceleration, double step){
        for(int i = 0; i < 3; i++)
        {
            velocityVector[i] += acceleration[i] * step;
            positionalVector[i] += velocityVector[i] * step;
        }
        checkDistance();
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
        System.out.println(directionalVector);
        double mag=helperFunctions.getVectorMagnitude(directionalVector);
        for (int i = 0; i < 3; i++)
        {
         directionalVector[i]=directionalVector[i]*6370/mag;
         System.out.println(directionalVector[i]);
        }

        return directionalVector;
    }

    public double[] initialVelocity(double[] initialPosition, double[] target, double[] acc){
        double gx = acc[0];
        double gy = acc[1];
        double gz = acc[2];

        double dx = target[0] - initialPosition[0];
        double dy = target[1] - initialPosition[1];
        double dz = target[2] - initialPosition[2];
        double d = Math.sqrt(dx*dx + dy*dy + dz*dz);

        double v = Math.sqrt((d*gx)/(2*Math.sin(Math.PI/4)));
        double theta = Math.atan2(dy, Math.sqrt(dx*dx + dz*dz));

        double vx = v*Math.sqrt(2)/2;
        double vy = v*Math.sin(theta);
        double vz = v*Math.sqrt(2)/2;

        double[] initialVelocity = {vx, vy, vz};
        return initialVelocity;
    }

    public double[] getPosition()
    {
        return super.getPositionalVector();
    }

    public void setPosition(double[] position)
    {
        super.setPositionalVector( position);
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

    public double getDistanceToTitan()
    {
        return distanceToTitan;
    }

    public void setDistanceToTitan(double distanceToTitan)
    {
        this.distanceToTitan = distanceToTitan;
    }
}
