package code;


import code.algorithms.ModelRunner;
import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.data.loaders.NasaDataLoader;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;

import java.util.ArrayList;
import java.util.Arrays;

import static code.algorithms.HillClimbingAlg.HillClimbingAlg;
import static code.algorithms.LaunchRocketHC.launchSevenRockets;

public class Main {
    public static void main(String[] args) {


       System.out.println(Arrays.toString(HillClimbingAlg(0.000000001,1,-7.431984185099234, -38.47640576315323, -2.8374480784666383)));
        //Current best velocities: 78.00583413245792, -48.83110909866967, 5.512161606512603
        // Model.getInstance().loadData(new FileDataLoader());
//        Model.addProbe(new Probe(new double[]{68.7449, -44.1, -2.2176}));
//
//    Visualizer.main(args);
//       ModelRunner.runnerForMultipleProbes(366, 1, new ArrayList<>(Model.getPlanetObjects().values()), Model.getProbes());
    }
}
