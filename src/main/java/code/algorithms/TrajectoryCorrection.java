package code.algorithms;

import code.model.Model;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;

public class TrajectoryCorrection {
    private static final double G = PlanetObject.G;

    public static double[] performTrajectoryCorrection(Probe rocket, PlanetObject titan) {
        double[] titanPosition = titan.getCoordinates();
        //calculate the required velocity to enter Titan's orbit
        double[] requiredVelocity = calculateRequiredVelocity(rocket.getCoordinates(), rocket.getVelocity(), titanPosition);

        // return the new velocities for the rocket
        return requiredVelocity;
    }

    private static double[] calculateRequiredVelocity(double[] rocketPosition, double[] rocketVelocity, double[] titanPosition) {
        double[] directionVector = calculateDirectionVector(rocketPosition, titanPosition);
        double[] escapeVelocity = calculateEscapeVelocity(titanPosition);

        double[] requiredVelocity = new double[3];
        for (int i = 0; i < 3; i++) {
            requiredVelocity[i] = rocketVelocity[i] + directionVector[i] * escapeVelocity[i];
        }

        return requiredVelocity;
    }

    private static double[] calculateDirectionVector(double[] rocketPosition, double[] titanPosition) {
        double[] directionVector = new double[3];
        double distanceToTitan = calculateDistance(rocketPosition, titanPosition);

        for (int i = 0; i < 3; i++) {
            directionVector[i] = (titanPosition[i] - rocketPosition[i]) / distanceToTitan;
        }

        return directionVector;
    }

    private static double[] calculateEscapeVelocity(double[] titanPosition) {
        double[] escapeVelocity = new double[3];
        double titanMass = getTitanMass();  // Obtain the mass of Titan from your simulation

        double distanceToTitan = calculateDistance(titanPosition, new double[]{0, 0, 0});  // Assuming Earth is at the origin (0, 0, 0)

        for (int i = 0; i < 3; i++) {
            escapeVelocity[i] = Math.sqrt(2 * G * titanMass / distanceToTitan);
        }

        return escapeVelocity;
    }

    private static double calculateDistance(double[] position1, double[] position2) {
        double sum = 0.0;
        for (int i = 0; i < position1.length; i++) {
            double diff = position1[i] - position2[i];
            sum += Math.pow(diff, 2);
        }
        return Math.sqrt(sum);
    }

    private static double getTitanMass() {
        return Model.getPlanetObjects().get("Titan").getMass();
    }
}

