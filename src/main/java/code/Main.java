package code;


import code.algorithms.HillClimbingAlg;
import code.algorithms.HillClimbingAlgReturn;

import java.util.Arrays;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        HillClimbingAlg hcInstance = new HillClimbingAlg();
        double[] bestVelocitiesToTitan = hcInstance.hillClimbing(10,1,159.78470381490106, -97.12752946315422, -8.151758278466753);
        System.out.println("Best velocities to Titan: ");
        System.out.println("xVelocity = " + bestVelocitiesToTitan[0] + "; yVelocity = " + bestVelocitiesToTitan[1] + "; zVelocity = " + bestVelocitiesToTitan[2]);

        HillClimbingAlgReturn hcReturnInstance = new HillClimbingAlgReturn(hcInstance.getCoordinatesToMain());
        double[] bestVelocitiesToEarth = hcReturnInstance.runHillClimbingAlg(10, 1, 0, 0 ,0);
        System.out.println("Best velocities to Earth: ");
        System.out.println("xVelocity = " + bestVelocitiesToEarth[0] + "; yVelocity = " + bestVelocitiesToEarth[1] + "; zVelocity = " + bestVelocitiesToEarth[2]);

        System.out.println("Results: ");
        System.out.println("Best velocities to Titan: " + "xVelocity = " + bestVelocitiesToTitan[0] + "; yVelocity = " + bestVelocitiesToTitan[1] + "; zVelocity = " + bestVelocitiesToTitan[2]);
        System.out.println("Best velocities to Earth: " + "xVelocity = " + bestVelocitiesToEarth[0] + "; yVelocity = " + bestVelocitiesToEarth[1] + "; zVelocity = " + bestVelocitiesToEarth[2]);
        //        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("What simulation to run?");
//        System.out.println("1) From Earth to Titan");
//        System.out.println("2) From Titan to Earth");
//        System.out.println("Your choice: ");
//        int choice = scanner.nextInt();
//        if(choice == 1){
//            System.out.println(Arrays.toString(HillClimbingAlg(0.000000001,100,-7.431984185099234, -38.47640576315323, -2.8374480784666383)));
//        }else if (choice == 2) {
//            System.out.println(Arrays.toString(HillClimbingAlgReturn.runHillClimbingAlg(0.000000001, 100, 0, 0, 0)));
//        } else {
//            System.out.println("Invalid choice.");
//        }


        //Current best velocities: 78.00583413245792, -48.83110909866967, 5.512161606512603
        // Model.getInstance().loadData(new FileDataLoader());
//        Model.addProbe(new Probe(new double[]{68.7449, -44.1, -2.2176}));
//
//    Visualizer.main(args);
//       ModelRunner.runnerForMultipleProbes(366, 1, new ArrayList<>(Model.getPlanetObjects().values()), Model.getProbes());
    }
}
