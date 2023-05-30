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

    private final int INITIAL = 0;
    private final int XPLUS = 1;
    private final int XMINUS = 2;
    private final int YPLUS = 3;
    private final int YMINUS = 4;
    private final int ZPLUS = 5;
    private final int ZMINUS = 6;
    private final int X = 0;
    private final int Y = 1;
    private final int Z = 2;
    private double[] bestProbeCoordinates;
    
    public LaunchRocketFromTitan(double[] bestCoordinates){
        this.bestProbeCoordinates = bestCoordinates;
    }

    public double[] launchSevenRocketsReturn(double[][] velocitiesOfRockets, double accuracySolvers) {
        System.out.println("Running...");

//        double[] bestCoordinates = bestProbeCoordinates; //coordinates of best rocket

        Probe initialRocket = new Probe();
        initialRocket.setCoordinates(bestProbeCoordinates);
        Probe.Boost boost1 = new Probe.Boost(0,velocitiesOfRockets[INITIAL]);
        initialRocket.addBoost(boost1);

        Probe xPlusRocket = new Probe();
        xPlusRocket.setCoordinates(bestProbeCoordinates);
        Probe.Boost boost2 = new Probe.Boost(0,velocitiesOfRockets[XPLUS]);
        xPlusRocket.addBoost(boost2);

        Probe xMinusRocket = new Probe();
        xMinusRocket.setCoordinates(bestProbeCoordinates);
        Probe.Boost boost3 = new Probe.Boost(0,velocitiesOfRockets[XMINUS]);
        xMinusRocket.addBoost(boost3);

        Probe yPlusRocket = new Probe();
        yPlusRocket.setCoordinates(bestProbeCoordinates);
        Probe.Boost boost4 = new Probe.Boost(0,velocitiesOfRockets[YPLUS]);
        yPlusRocket.addBoost(boost4);

        Probe yMinusRocket = new Probe();
        yMinusRocket.setCoordinates(bestProbeCoordinates);
        Probe.Boost boost5 = new Probe.Boost(0,velocitiesOfRockets[YMINUS]);
        yMinusRocket.addBoost(boost5);

        Probe zPlusRocket = new Probe();
        zPlusRocket.setCoordinates(bestProbeCoordinates);
        Probe.Boost boost6 = new Probe.Boost(0,velocitiesOfRockets[ZPLUS]);
        zPlusRocket.addBoost(boost6);

        Probe zMinusRocket = new Probe();
        zMinusRocket.setCoordinates(bestProbeCoordinates);
        Probe.Boost boost7 = new Probe.Boost(0,velocitiesOfRockets[ZMINUS]);
        zMinusRocket.addBoost(boost7);


        Model.addProbe(initialRocket);
        Model.addProbe(xPlusRocket);
        Model.addProbe(xMinusRocket);
        Model.addProbe(yPlusRocket);
        Model.addProbe(yMinusRocket);
        Model.addProbe(zPlusRocket);
        Model.addProbe(zMinusRocket);
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
