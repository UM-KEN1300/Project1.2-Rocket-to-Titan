package code.algorithms;

import code.model.Model;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

public class OrbitCalculator {
    private OrbitCalculator() { //empty constructor
    }
    private static final double G = PlanetObject.G;

    public static double[] enterTitanOrbit(Probe rocket, PlanetObject titan, double desiredAltitude) {
        //determine Titan's current position and velocity
        double[] titanPosition = titan.getCoordinates();
        //double[] titanVelocity = titan.getVelocity();

        //calculate the desired orbit parameters
        double orbitalRadius = titan.getRadius() + desiredAltitude;
        double desiredVelocity = Math.sqrt(G * titan.getMass() / orbitalRadius);

        //adjust the rocket's velocity to enter Titan's orbit
        double[] rocketPosition = rocket.getCoordinates();
        double[] rocketVelocity = rocket.getVelocity();

        double[] relativePosition = HelperFunctions.subtract(rocketPosition, titanPosition);
        //double distance = HelperFunctions.vectorMagnitude(relativePosition);
        double[] directionVector = HelperFunctions.vectorNormalize(relativePosition);
        double deltaV = calculateDeltaV(rocket, titan, desiredAltitude);
        //double deltaV_alternative = Math.sqrt(Math.abs(2 * G * (1 / distance - 1 / (2 * orbitalRadius))));

        double[] deltaVelocity = HelperFunctions.scalarMultiply(HelperFunctions.vectorNormalize(relativePosition), deltaV);
        double[] updatedVelocity = HelperFunctions.addition(rocketVelocity, deltaVelocity);

        //adjust the rocket's velocity to achieve a circular orbit
        double[] desiredVelocityVector = HelperFunctions.scalarMultiply(directionVector, desiredVelocity);
        double[] velocityCorrection = HelperFunctions.subtract(desiredVelocityVector, updatedVelocity);
        double[] requiredVelocity = HelperFunctions.addition(updatedVelocity, velocityCorrection);

        return requiredVelocity;
    }

    private static double calculateDeltaV(Probe rocket, PlanetObject Titan, double desiredAltitude){
        double distance = HelperFunctions.getDistanceBetween(rocket, Titan);
        double centralBodyMass = Titan.getMass();
        double orbitalRadius = Titan.getRadius();

        double mu = G * centralBodyMass;
        double a = orbitalRadius + desiredAltitude ;


        double deltaV = Math.sqrt(Math.abs(mu * (2d / distance - 1d / a)));
        return deltaV;
    }



    public static void main(String[] args){ //for testing
        PlanetObject titan = Model.getPlanetObjects().get("Titan");
        PlanetObject saturn = Model.getPlanetObjects().get("Saturn");
        Probe rocket = new Probe();
    }
}
