package group1.project12group1;

import helperFunction.HelperFunctions;



public class PlanetObject {
    private int planetCode;
    final static double G = 6.6742e-20;
    //alternative g
    private double[] targetPosition;
    private double x;        // x coordinate in km
    private double y;        // y coordinate in km
    private double z;        // z coordinate in km
    private  double[] privousPosition;
    private double[] positionalVector = new double[3];
    private double[] velocityVector = new double[3];
    final static HelperFunctions helperFunctions=new HelperFunctions();
    private double vx = 0;       // velocity in x direction in km per second
    private double vy = 0;       // velocity in y direction in km per second
    private double vz = 0;       // velocity in z direction in km per second
    private double mass;     // mass in kilograms
    private double radius = 0;   // radius in km
    private double[] momentum=new double[3];
    private double[] forcePrevius=new double[3];

    // Constructor for creating PlanetObject with specified properties
    public PlanetObject (double x, double y, double z, double mass)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        positionalVector[0]=x;
        positionalVector[1]=y;
        positionalVector[2]=z;
        this.mass = mass;
    }

    //Second constructor with velocity
    public PlanetObject(double x, double y, double z, double vx, double vy, double vz, double mass)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        positionalVector[0]=x;
        positionalVector[1]=y;
        positionalVector[2]=z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        velocityVector[0] = vx;
        velocityVector[1] = vy;
        velocityVector[2] = vz;

        momentum[0]=velocityVector[0]/mass;
        momentum[1]=velocityVector[1]/mass;
        momentum[2]=velocityVector[2]/mass;

        this.mass = mass;
    }

    //planetCode only constructor
    public PlanetObject(int planetCode, double mass)
    {
        this.planetCode = planetCode;
        this.mass=mass;
    }

    // Getters and setters for the properties
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double[] getForcePrevius(PlanetObject other)
    {


            double[] force=new double[3];

            double[] threeDimensionalDistnace=helperFunctions.getDistanceBetweenPositionVectors(this.positionalVector,other.getPrivousPosition());
            double forceStrenght=-1000*G*this.mass*other.getMass()/Math.pow(helperFunctions.getVectorMagnitude(threeDimensionalDistnace),3);

            force[0]=threeDimensionalDistnace[0]*forceStrenght;
            force[1]=threeDimensionalDistnace[1]*forceStrenght;
            force[2]=threeDimensionalDistnace[2]*forceStrenght;



            return force;

    }

    public void setForcePrevius(double[] forcePrevius)
    {
        this.forcePrevius = forcePrevius;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVz() {
        return vz;
    }

    public double[] getTargetPosition()
    {
        return targetPosition;
    }

    public double[] getMomentum()
    {
        return momentum;
    }

    public void setMomentum(double[] momentum)
    {
        this.momentum = momentum;
    }

    public void setTargetPosition(double[] targetPosition)
    {
        this.targetPosition = targetPosition;
    }

    public void setVz(double vz) {
        this.vz = vz;
    }

    public double getRadius(){
        return radius;
    }


    public double[] getPrivousPosition()
    {
        return privousPosition;
    }

    public void setPrivousPosition(double[] privousPosition)
    {
        this.privousPosition = privousPosition;
    }

    public int getPlanetCode()
    {
        return planetCode;
    }

    public double[] getVelocityVector()
{
        return velocityVector;
    }

    public void setVelocityVector(double[] velocityVector)
    {
        this.velocityVector = velocityVector;
    }

    public void setPlanetCode(int planetCode)
    {
        this.planetCode = planetCode;
    }

    public void setRadius(double r){
        this.radius = r;
    }
    public double[] getPositionalVector() {return positionalVector;}

    public double getMass() {return mass;}

    public void setMass(double mass) {this.mass = mass;}

    public void setPositionalVector(double[] positionalVector) {this.positionalVector = positionalVector;}
    

    //todo fix units and make tests to see if it always works
    public double[] getForce(PlanetObject other)
    {

        double[] force=new double[3];

        double[] threeDimensionalDistnace=helperFunctions.getDistanceBetweenPositionVectors(this.positionalVector,other.getPositionalVector());
        double forceStrenght=-1000*G*this.mass*other.getMass()/Math.pow(helperFunctions.getVectorMagnitude(threeDimensionalDistnace),3);

        force[0]=threeDimensionalDistnace[0]*forceStrenght;
        force[1]=threeDimensionalDistnace[1]*forceStrenght;
        force[2]=threeDimensionalDistnace[2]*forceStrenght;



        return force;
    }


    public double[] getAcceleration(PlanetObject other)
    {

        double[] force=new double[3];
        double[] threeDimensionalDistnace=helperFunctions.getDistanceBetweenPositionVectors(this.positionalVector,other.getPositionalVector());
        double forceStrenght=-1000*G*this.mass*other.getMass()/Math.pow(helperFunctions.getVectorMagnitude(threeDimensionalDistnace),3);

        force[0]=threeDimensionalDistnace[0]*forceStrenght;
        force[1]=threeDimensionalDistnace[1]*forceStrenght;
        force[2]=threeDimensionalDistnace[2]*forceStrenght;

        this.acc[0]+=force[0]/this.mass;
        this.acc[1]+=force[1]/this.mass;
        this.acc[2]+=force[2]/this.mass;
        return acc;

    }
    public double[] getAccelerationWithOldPosition(PlanetObject other)
    {

        double[] force=new double[3];
        double[] threeDimensionalDistnace=helperFunctions.getDistanceBetweenPositionVectors(this.positionalVector,other.getPrivousPosition());
        double forceStrenght=-1000*G*this.mass*other.getMass()/Math.pow(helperFunctions.getVectorMagnitude(threeDimensionalDistnace),2);

        force[0]=threeDimensionalDistnace[0]*forceStrenght;
        force[1]=threeDimensionalDistnace[1]*forceStrenght;
        force[2]=threeDimensionalDistnace[2]*forceStrenght;

        this.acc[0]+=force[0]/this.mass;
        this.acc[1]+=force[1]/this.mass;
        this.acc[2]+=force[2]/this.mass;
        return acc;

    }

    public void updatePositionVelocity( double[] acc,double step){
        for(int i = 0; i < 3; i++){

            velocityVector[i] += acc[i] * step;
            positionalVector[i] += velocityVector[i] * step;

        }
    }

    public void updatePositionVelocityWithForce( double[] force,double step){
        for(int i = 0; i < 3; i++)
        {
            momentum[i]=momentum[i]+force[i]*step;
            positionalVector[i] += momentum[i]/mass * step;

        }
    }
    public void semiImplicitEulerSolver( double [] a, double h){

        for(int i = 0; i<3; i++)
        {
            velocityVector[i] = velocityVector[i] + (a[i] * h);
            positionalVector[i] = positionalVector[i] + (velocityVector[i] * h);
        }
    }
}