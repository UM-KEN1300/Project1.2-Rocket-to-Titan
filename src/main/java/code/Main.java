package code;


import code.algorithms.HillClimbingAlgReturn;

import java.util.Arrays;
import java.util.Scanner;

import static code.algorithms.HillClimbingAlg.HillClimbingAlg;


public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(HillClimbingAlg(0.000000001,100,-7.431984185099234, -38.47640576315323, -2.8374480784666383)));



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
