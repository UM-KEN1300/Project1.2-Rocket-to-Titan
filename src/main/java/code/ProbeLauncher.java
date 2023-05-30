package code;
import code.algorithms.HillClimbingAlg;
import code.algorithms.HillClimbingAlgReturn;

public class ProbeLauncher {
    public static void main(String[] args) {
        HillClimbingAlg hcInstance = new HillClimbingAlg();
        double[] bestVelocitiesToTitan = hcInstance.hillClimbing(1,10,67.73988800000001, -44.03988500000006, -4.258907);
        System.out.println("Best velocities to Titan: ");
        System.out.println("xVelocity = " + bestVelocitiesToTitan[0] + "; yVelocity = " + bestVelocitiesToTitan[1] + "; zVelocity = " + bestVelocitiesToTitan[2]);

        HillClimbingAlgReturn hcReturnInstance = new HillClimbingAlgReturn(hcInstance.getCoordinatesToMain());
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("Switch to Return Trip");

        double[] bestVelocitiesToEarth = hcReturnInstance.runHillClimbingAlg(1, 10, bestVelocitiesToTitan[0], bestVelocitiesToTitan[1] ,bestVelocitiesToTitan[2]);
        System.out.println("Best velocities to Earth: ");
        System.out.println("xVelocity = " + bestVelocitiesToEarth[0] + "; yVelocity = " + bestVelocitiesToEarth[1] + "; zVelocity = " + bestVelocitiesToEarth[2]);

        System.out.println("Results: ");
        System.out.println("Best velocities to Titan: " + "xVelocity = " + bestVelocitiesToTitan[0] + "; yVelocity = " + bestVelocitiesToTitan[1] + "; zVelocity = " + bestVelocitiesToTitan[2]);
        System.out.println("Best velocities to Earth: " + "xVelocity = " + bestVelocitiesToEarth[0] + "; yVelocity = " + bestVelocitiesToEarth[1] + "; zVelocity = " + bestVelocitiesToEarth[2]);

    }
}


//package code;
//
//import code.graphics.Visualizer;
//import code.model.Model;
//import code.model.data.loaders.FileDataLoader;
//import code.model.objects.Probe;
//
//public class ProbeLauncher {
//    public static void main(String[] args) {
//        Model.getInstance().loadData(new FileDataLoader());
//        Probe probe = new Probe();
//        Probe.Boost boost = new Probe.Boost(0, new double[]{68.7449, -44.1, -2.2176});
//        probe.addBoost(boost);
//        Model.addProbe(probe);
////        ModelRunner.runnerForMultipleProbes(365, 2, Model.getPlanetObjectsArrayList(), Model.getProbes());
////        System.out.println(probe.getShortestDistanceToTitan());
//
//        Visualizer.main(args);
//    }
//}
