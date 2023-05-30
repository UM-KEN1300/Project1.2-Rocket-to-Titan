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

        Probe initialRocket = new Probe();
        Probe.Boost boost1 = new Probe.Boost(0,velocitiesOfRockets[INITIAL]);
        initialRocket.addBoost(boost1);

        Probe xPlusRocket = new Probe();
        Probe.Boost boost2 = new Probe.Boost(0,velocitiesOfRockets[XPLUS]);
        xPlusRocket.addBoost(boost2);

        Probe xMinusRocket = new Probe();
        Probe.Boost boost3 = new Probe.Boost(0,velocitiesOfRockets[XMINUS]);
        xMinusRocket.addBoost(boost3);

        Probe yPlusRocket = new Probe();
        Probe.Boost boost4 = new Probe.Boost(0,velocitiesOfRockets[YPLUS]);
        yPlusRocket.addBoost(boost4);

        Probe yMinusRocket = new Probe();
        Probe.Boost boost5 = new Probe.Boost(0,velocitiesOfRockets[YMINUS]);
        yMinusRocket.addBoost(boost5);

        Probe zPlusRocket = new Probe();
        Probe.Boost boost6 = new Probe.Boost(0,velocitiesOfRockets[ZPLUS]);
        zPlusRocket.addBoost(boost6);

        Probe zMinusRocket = new Probe();
        Probe.Boost boost7 = new Probe.Boost(0,velocitiesOfRockets[ZMINUS]);
        zMinusRocket.addBoost(boost7);


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
