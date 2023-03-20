package group1.project12group1;

import helperFunction.HelperFunctions;



public class PlanetObject {
    final static double G = 6.6742e-20;
    //alternative g
    final  double Gs = 6.6742e-11;

    private double x;        // x coordinate in km
    private double y;        // y coordinate in km
    private double z;        // z coordinate in km

    private double[] positionalVector = new double[3];
    private double[] velocityVector = new double[3];
    HelperFunctions helperFunctions=new HelperFunctions();
    private double vx = 0;       // velocity in x direction in km per second
    private double vy = 0;       // velocity in y direction in km per second
    private double vz = 0;       // velocity in z direction in km per second
    private double mass;     // mass in kilograms
    private double radius = 0;   // radius in km

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
        this.mass = mass;
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

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVz() {
        return vz;
    }

    public void setVz(double vz) {
        this.vz = vz;
    }

    public double getRadius(){
        return radius;
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
        double[] acc=new double[3];

        acc[0]=force[0]/this.mass;
        acc[1]=force[0]/this.mass;
        acc[2]=force[0]/this.mass;
        return acc;

    }
    public static void updatePositionVelocity(double[] position, double[] velocity, double[] acceleration ,double step){
        for(int i = 0; i < 3; i++){
            velocity[i] += acceleration[i] * step;
            position[i] += velocity[i] * step;
        }
    }
    public double [] semiImplicitEulerSolver(double [] x, double [] v, double [] a, double h){
        double [] newV = new double[3];
        double [] newX = new double[3];
        for(int i = 0; i<3; i++){
            newV[i] = v[i] + (a[i] * h);
            newX[i] = x[i] + (newV[i] * h);
        }
        return newX;
    }
}