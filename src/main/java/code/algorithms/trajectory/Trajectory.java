package code.algorithms.trajectory;

import code.model.Model;
import code.model.ModelCopy;
import code.model.objects.Probe;
import code.model.objects.properties.Boost;
import code.utils.HelperFunctions;

import java.util.Arrays;

/**
 * Class used for planning the mission. It calculates the velocities of boosts at time intervals which allow
 * for reaching the target celestial body in a sufficiently short path, taking into consideration
 * the amount of used fuel and the speed at which the spacecraft needs to travel in order to reach its
 * destination at a given point in time.
 */
public class Trajectory {
    ModelCopy modelCopy;
    private final int TRAJECTORY_ACCURACY = 3;
    private final int SIMULATION_LENGTH = 1;
    private final double TIME_STEP;
    private double[] destination;
    private double[] bestVelocity, secondBestVelocity;
    private double[] bestEvaluation, secondBestEvaluation;
    private double multiplier;
    private final boolean[] REACHED;


    public Trajectory() {
        setDestination(Model.getProbes().get(0));
        modelCopy = new ModelCopy();
        TIME_STEP = Model.getTimeStep();
        REACHED = new boolean[]{false, false, false};
        bestVelocity = new double[3];
        secondBestVelocity = new double[3];
        bestEvaluation = new double[3];
        secondBestEvaluation = new double[3];
    }


    public Boost findBestBoost() {
        for (int i = 0; i < 3; i++)
            initialEvaluation(i);
        while (!REACHED[0] || !REACHED[1] || !REACHED[2]) {
            if (!REACHED[0])
                bestTwoValues(0);
            if (!REACHED[1])
                bestTwoValues(1);
            if (!REACHED[2])
                bestTwoValues(2);
        }
        for (int i = 0; i < TRAJECTORY_ACCURACY; i++)
            evaluationBetween(i);

        setNextBoostTime(Model.getProbes().get(0));
        return new Boost(Model.getTime(), bestVelocity);
    }

    /**
     * The function that helps evaluate how 'good' the current trajectory is taking into account the distance from Titan
     * and fuel consumed so far. The closer to earth or titan the probe gets the less the fuel optimization matters.
     *
     * @param probe The considered probe
     * @return The value of the objective function
     */
    private double objectiveFunction(Probe probe) {
        if (probe.getDistanceToEarth() < 15_000)
            return HelperFunctions.getDistanceBetweenWithVectors(probe.getCoordinates(), destination);
        else if (probe.getDistanceToTitan() < 600)
            return probe.getVelocity()[0] + probe.getVelocity()[1] + probe.getVelocity()[2];
        else {
            double distance = Math.min(probe.getDistanceToTitan(), probe.getDistanceToEarth());
            return distance * probe.getFuelUsed() * 0.0001 + HelperFunctions.getDistanceBetweenWithVectors(probe.getCoordinates(), destination);
        }
    }

    private void initialEvaluation(int index) {
        double negativeOneEvaluation = evaluateBoost(-1, index);
        modelCopy = new ModelCopy();
        double zeroEvaluation = evaluateBoost(0, index);
        modelCopy = new ModelCopy();
        double oneEvaluation = evaluateBoost(1, index);
        modelCopy = new ModelCopy();
        if (zeroEvaluation > negativeOneEvaluation) {
            bestVelocity[index] = -1;
            bestEvaluation[index] = negativeOneEvaluation;
            secondBestVelocity[index] = 0;
            secondBestEvaluation[index] = zeroEvaluation;
            multiplier = -1;
        } else if (zeroEvaluation > oneEvaluation) {
            bestVelocity[index] = 1;
            bestEvaluation[index] = negativeOneEvaluation;
            secondBestVelocity[index] = 0;
            secondBestEvaluation[index] = zeroEvaluation;
            multiplier = 1;
        } else {
            bestVelocity[index] = 0;
            bestEvaluation[index] = zeroEvaluation;
            if (oneEvaluation > negativeOneEvaluation) {
                secondBestVelocity[index] = -1;
                secondBestEvaluation[index] = negativeOneEvaluation;
                multiplier = -1;
            } else {
                secondBestVelocity[index] = 1;
                secondBestEvaluation[index] = oneEvaluation;
                multiplier = 1;
            }
        }
    }

    private void bestTwoValues(int index) {
        double newVelocity = bestVelocity[index] + multiplier;
        if (!HelperFunctions.isBoostVelocityAllowed(newVelocity, TIME_STEP)) {
            REACHED[index] = true;
            return;
        }
        double newEvaluation = evaluateBoost(newVelocity, index);
        modelCopy = new ModelCopy();
        if (newEvaluation == -1d) {
            REACHED[index] = true;
            return;
        }
        if (newEvaluation < bestEvaluation[index]) {
            secondBestVelocity[index] = bestVelocity[index];
            secondBestEvaluation[index] = bestEvaluation[index];
            bestVelocity[index] = newVelocity;
            bestEvaluation[index] = newEvaluation;
        } else if (newEvaluation < secondBestEvaluation[index]) {
            secondBestVelocity[index] = newVelocity;
            secondBestEvaluation[index] = newEvaluation;
        } else
            REACHED[index] = true;
    }

    private void evaluationBetween(int index) {
        double newVelocity = (bestVelocity[index] + secondBestVelocity[index]) / 2d;
        double newEvaluation = evaluateBoost(newVelocity, index);
        modelCopy = new ModelCopy();
        if (newEvaluation < bestEvaluation[index]) {
            secondBestVelocity[index] = bestVelocity[index];
            secondBestEvaluation[index] = bestEvaluation[index];
            bestVelocity[index] = newVelocity;
            bestEvaluation[index] = newEvaluation;
        } else {
            secondBestVelocity[index] = newVelocity;
            secondBestEvaluation[index] = newEvaluation;
        }
    }

    private double evaluateBoost(double velocity, int index) {
        Probe probeCopy = new Probe();
        double[] velocities = Arrays.copyOf(bestVelocity, bestVelocity.length);
        velocities[index] = velocity;
        probeCopy.addBoost(new Boost(Model.getTime(), velocities));
        if (!probeCopy.areBoostsValid(Model.getTimeStep()))
            return -1;

        modelCopy.addProbe(probeCopy);
        double simulatedSeconds = 0;
        while (SIMULATION_LENGTH * 24 * 60 * 60 > simulatedSeconds) {
            modelCopy.step(Model.getTimeStep());
            simulatedSeconds += Model.getTimeStep();
        }

        return objectiveFunction(probeCopy);
    }

    private void setDestination(Probe probe) {
        destination = Model.getPlanetObjects().get("Titan").getCoordinates();
    }

    private void setNextBoostTime(Probe probe) {
        probe.setTimeUntilNextBoost(60 * 60);
    }

    private int decimalPlaces(double d) {
        String s = Double.toString(d);
        int decimalIndex = s.indexOf(".");
        if (decimalIndex == -1)
            return 0;
        else
            return s.length() - decimalIndex - 1;
    }
}
