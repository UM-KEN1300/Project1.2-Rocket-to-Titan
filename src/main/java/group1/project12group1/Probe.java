package group1.project12group1;

import helperFunction.HelperFunctions;

public class Probe extends PlanetObject{

    public static double fitness;
    private double[] position;
    public  double [] velocity;
    public final double mass = 50000;

    public Probe(double [] position, double [] velocity){
        this.position = position;
        this.velocity = velocity;

    }

    public double checkDistance(PlanetObject Titan){
        return HelperFunctions.getDistanceBetween(this, Titan);
    }




}
