package code.algorithms;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;

public class OrbitCalculator {
    private OrbitCalculator() {
    }

    private static final double G = PlanetObject.G;

    public static double[] enterTitanOrbit(PlanetObject rocket, PlanetObject titan, double desiredAltitude) {
        //determine Titan's current position and velocity
        double[] titanPosition = titan.getCoordinates();
        double[] titanVelocity = titan.getVelocity();

        //calculate the desired orbit parameters
        double orbitalRadius = titan.getRadius() + desiredAltitude;
        double desiredVelocity = Math.sqrt(G * titan.getMass() / orbitalRadius);

        //adjust the rocket's velocity to enter Titan's orbit
        double[] rocketPosition = rocket.getCoordinates();
        double[] rocketVelocity = rocket.getVelocity();

        double[] relativePosition = HelperFunctions.subtract(rocketPosition, titanPosition);
        double distance = vectorMagnitude(relativePosition);
        double deltaV = Math.sqrt(2 * G * (1 / distance - 1 / (2 * orbitalRadius)));

        double[] deltaVelocity = scalarMultiply(vectorNormalize(relativePosition), deltaV);
        double[] updatedVelocity = HelperFunctions.addition(rocketVelocity, deltaVelocity);

        //adjust the rocket's velocity to achieve a circular orbit
        double[] desiredVelocityVector = scalarMultiply(vectorNormalize(relativePosition), desiredVelocity);
        double[] velocityCorrection = HelperFunctions.subtract(desiredVelocityVector, updatedVelocity);
        double[] requiredVelocity = HelperFunctions.addition(updatedVelocity, velocityCorrection);

        return requiredVelocity;
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
