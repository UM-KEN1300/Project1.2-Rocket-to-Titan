package code.algorithms.solvers;

import code.model.objects.PlanetObject;

public class VelocityFunction implements Function{

    double velocity;

    public VelocityFunction(PlanetObject planetObject, int l){
        this.velocity = planetObject.getVelocity()[l];
    }

    public double evaluation(double y0, double t){
        return velocity;
    }
}
