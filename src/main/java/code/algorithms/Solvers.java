package code.algorithms;

import code.model.objects.PlanetObject;

public class Solvers {
    private Solvers() {
    }

    /**
     * @param acceleration is the all the accelerations that affect a planet
     * @param step         is amount of time we move the planet 1 second is 1
     *                     <p>
     *                     This is the Euler's solver that updates the planet position with step in time
     */
    public static void eulerSolver(PlanetObject planetObject, double[] acceleration, double step) {
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        for (int i = 0; i < 3; i++) {
            velocityVector[i] += acceleration[i] * step;
            positionalVector[i] += velocityVector[i] * step;
        }
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
    }
}
