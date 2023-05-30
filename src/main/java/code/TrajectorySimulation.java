package code;


import code.algorithms.HillClimbingAlg;
import code.algorithms.HillClimbingAlgReturn;


public class TrajectorySimulation {
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
