package Algorithms;

import Model.PlanetObject;

public class Solvers
{
    public Solvers() {}



    /**
     * @param acceleration is the all the accelerations that affect a planet
     * @param step is amount of time we move the planet 1 second is 1
     *
     * This is the Euler's solver that updates the planet position with step in time
     */
    public static void eulerSolver(PlanetObject planetObject, double[] acceleration, double step){
        double[] velocityVector=planetObject.getVelocityVector();
        double[] positionalVector= planetObject.getPositionalVector();
        for(int i = 0; i < 3; i++)
        {
            velocityVector[i] += acceleration[i] * step;
            positionalVector[i] += velocityVector[i] * step;
        }
        planetObject.setVelocityVector(velocityVector);
        planetObject.setPositionalVector(positionalVector);
    }

}
