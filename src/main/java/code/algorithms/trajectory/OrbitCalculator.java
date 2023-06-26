package code.algorithms.trajectory;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;

public class OrbitCalculator {
    private static final double GRAVITATIONAL_CONSTANT = PlanetObject.G;

    public static void enterTitanOrbit(PlanetObject rocket, PlanetObject titan, double desiredAltitude) {
        // Step 1: Determine Titan's current position and velocity
        double[] titanPosition = titan.getCoordinates();
        double[] titanVelocity = titan.getVelocity();

        // Step 2: Calculate the desired orbit parameters
        double orbitalRadius = titan.getRadius() + desiredAltitude;
        double desiredVelocity = Math.sqrt(GRAVITATIONAL_CONSTANT * titan.getMass() / orbitalRadius);

        // Step 3: Adjust the rocket's velocity to enter Titan's orbit
        double[] rocketPosition = rocket.getCoordinates();
        double[] rocketVelocity = rocket.getVelocity();

        double[] relativePosition = HelperFunctions.subtract(rocketPosition, titanPosition);
        double distance = vectorMagnitude(relativePosition);
        double deltaV = Math.sqrt(2 * GRAVITATIONAL_CONSTANT * (1 / distance - 1 / (2 * orbitalRadius)));

        double[] deltaVelocity = scalarMultiply(vectorNormalize(relativePosition), deltaV);
        rocket.setVelocity(HelperFunctions.addition(rocketVelocity, deltaVelocity));

        // Step 4: Adjust the rocket's velocity to achieve a circular orbit
        double[] desiredVelocityVector = scalarMultiply(vectorNormalize(relativePosition), desiredVelocity);
        double[] velocityCorrection = HelperFunctions.subtract(desiredVelocityVector, rocketVelocity);
        rocket.setVelocity(HelperFunctions.addition(rocketVelocity, velocityCorrection));
    }


    private static double vectorMagnitude(double[] vector) {
        double sumOfSquares = 0;
        for (double component : vector) {
            sumOfSquares += component * component;
        }
        return Math.sqrt(sumOfSquares);
    }

    private static double[] vectorNormalize(double[] vector) {
        double magnitude = vectorMagnitude(vector);
        double[] normalizedVector = new double[3];
        for (int i = 0; i < 3; i++) {
            normalizedVector[i] = vector[i] / magnitude;
        }
        return normalizedVector;
    }

    private static double[] scalarMultiply(double[] vector, double scalar) {
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = vector[i] * scalar;
        }
        return result;
    }
}
