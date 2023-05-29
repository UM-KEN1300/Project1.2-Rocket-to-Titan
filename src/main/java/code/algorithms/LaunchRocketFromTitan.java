package code.algorithms;

import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

import java.util.ArrayList;
import java.util.Arrays;

import static code.algorithms.ModelRunner.runnerForMultipleProbes;
import static code.model.Model.addProbe;

public class LaunchRocketFromTitan {

    static final int INITIAL = 0;
    static final int XPLUS = 1;
    static final int XMINUS = 2;
    static final int YPLUS = 3;
    static final int YMINUS = 4;
    static final int ZPLUS = 5;
    static final int ZMINUS = 6;
    static final int X = 0;
    static final int Y = 1;
    static final int Z = 2;

    public static double[] launchSevenRocketsReturn(double[][] velocitiesOfRockets, double accuracySolvers) {
        System.out.println("Running...");
        Model.getInstance().loadData(new FileDataLoader());

        Probe initialRocket = new Probe(velocitiesOfRockets[INITIAL]);
        Probe xPlusRocket = new Probe(velocitiesOfRockets[XPLUS]);
        Probe xMinusRocket = new Probe(velocitiesOfRockets[XMINUS]);
        Probe yPlusRocket = new Probe(velocitiesOfRockets[YPLUS]);
        Probe yMinusRocket = new Probe(velocitiesOfRockets[YMINUS]);
        Probe zPlusRocket = new Probe(velocitiesOfRockets[ZPLUS]);
        Probe zMinusRocket = new Probe(velocitiesOfRockets[ZMINUS]);

        Model.addProbe(initialRocket);
        Model.addProbe(xPlusRocket);
        Model.addProbe(xMinusRocket);
        Model.addProbe(yPlusRocket);
        Model.addProbe(yMinusRocket);
        Model.addProbe(zPlusRocket);
        Model.addProbe(zMinusRocket);
        //HAVE TO ADD SOMETHING TO RUN THE SIM FOR 365 DAYS BEFORE LAUNCHING THE ROCKETS
        runnerForMultipleProbes(100, accuracySolvers, new ArrayList<>(Model.getPlanetObjects().values()), Model.getProbes());
        System.out.println("Distances from Titan: " + initialRocket.getDistanceToEarth() + "   " +
                xPlusRocket.getDistanceToEarth() + "   " +
                xMinusRocket.getDistanceToEarth() + "   " +
                yPlusRocket.getDistanceToEarth() + "   " +
                yMinusRocket.getDistanceToEarth() + "   " +
                zPlusRocket.getDistanceToEarth() + "   " +
                zMinusRocket.getDistanceToEarth());

        System.out.println("-------------------------------------------------------------------------------------------------------------");

        return new double[]{
                initialRocket.getDistanceToEarth(),
                xPlusRocket.getDistanceToEarth(),
                xMinusRocket.getDistanceToEarth(),
                yPlusRocket.getDistanceToEarth(),
                yMinusRocket.getDistanceToEarth(),
                zPlusRocket.getDistanceToEarth(),
                zMinusRocket.getDistanceToEarth()
        };
    }

}
