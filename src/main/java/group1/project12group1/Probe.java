package group1.project12group1;
import helperFunction.HelperFunctions;
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
