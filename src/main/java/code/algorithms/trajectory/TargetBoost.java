package code.algorithms.trajectory;

import code.model.Model;
import code.model.objects.properties.Boost;

public class TargetBoost {
    private final double MAX_SPEED;
    private double[] distancesToTitan;
    private double[] destination;


    public TargetBoost(double[] destination) {
        this.destination = destination;
        MAX_SPEED = Math.min(Model.getProbes().get(0).getMaxSpeed(), (Math.pow(10, 7)) * Model.getTimeStep());
        distancesToTitan = new double[3];
        setDistances();
        scaleDistances(distancesToTitan);
        Model.getProbes().get(0).addBoost(new Boost(Model.getTime(), boostVelocities()));
        Model.getProbes().get(0).setTimeUntilNextBoost(60 * 60 * 24);
    }


    private void setDistances() {
        for (int i = 0; i < 3; i++)
            distancesToTitan[i] = destination[i] - Model.getProbes().get(0).getCoordinates()[i];
    }

    private void scaleDistances(double[] values) {
        double maxVal = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > maxVal) {
                maxVal = values[i];
            }
        }

        if (maxVal > MAX_SPEED) {
            double ratio = MAX_SPEED / maxVal;
            for (int i = 0; i < values.length; i++) {
                values[i] *= ratio;
            }
        }
    }

    private double[] boostVelocities() {
        double[] velocities = new double[3];

        for (int i = 0; i < 3; i++)
            velocities[i] = distancesToTitan[i] - Model.getProbes().get(0).getVelocity()[i];

        return velocities;
    }
}
