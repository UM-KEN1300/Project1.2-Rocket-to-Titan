package code.algorithms.trajectory;

import code.model.Model;
import code.model.objects.Probe;

public class OrbitTrajectory extends Trajectory {
    public OrbitTrajectory() {
        super();
        Model.getProbes().get(0).addBoost(findBestBoost());
    }


    private double objectiveFunction(Probe probe) {
        double distance = probe.getDistanceToTitan();
        double distanceAfterFiveDays, distanceAfterTenDays;

        super.modelCopy.addProbe(probe);
        double simulatedSeconds = 0;
        while (5 * 60 * 60 * 24 > simulatedSeconds) {
            modelCopy.step(Model.getTimeStep());
            simulatedSeconds += Model.getTimeStep();
        }
        distanceAfterFiveDays = probe.getDistanceToTitan();
        while (10 * 60 * 60 * 24 > simulatedSeconds) {
            modelCopy.step(Model.getTimeStep());
            simulatedSeconds += Model.getTimeStep();
        }
        distanceAfterTenDays = probe.getDistanceToTitan();

        return 2 * distance - (distanceAfterFiveDays + distanceAfterTenDays);
    }
}
