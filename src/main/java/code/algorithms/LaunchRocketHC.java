package code.algorithms;

import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Probe;

import java.util.ArrayList;
import java.util.Arrays;

import static code.algorithms.ModelRunner.runnerForMultipleProbes;
import static code.model.Model.addProbe;

public class LaunchRocketHC {

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

    public static double[] launchSevenRockets(double[][] velocitiesOfRockets, double accuracySolvers) {

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

        runnerForMultipleProbes(100, accuracySolvers, new ArrayList<>(Model.getPlanetObjects().values()), Model.getProbes());
        System.out.println("Distances to Titan: "+ initialRocket.getShortestDistanceToTitan()+"   " +
                xPlusRocket.getShortestDistanceToTitan()+ "   " +
                xMinusRocket.getShortestDistanceToTitan()+"   " +
                yPlusRocket.getShortestDistanceToTitan()+"   " +
                yMinusRocket.getShortestDistanceToTitan()+"   " +
                zPlusRocket.getShortestDistanceToTitan()+"   " +
                zMinusRocket.getShortestDistanceToTitan());

        return new double[]{initialRocket.getShortestDistanceToTitan(),
                xPlusRocket.getShortestDistanceToTitan(),
                xMinusRocket.getShortestDistanceToTitan(),
                yPlusRocket.getShortestDistanceToTitan(),
                yMinusRocket.getShortestDistanceToTitan(),
                zPlusRocket.getShortestDistanceToTitan(),
                zMinusRocket.getShortestDistanceToTitan()};
    }


    public static void main(String[] args) {
//
//        double[][] testVelocities = {{50, 50, 50},
//                {60, 50, 50},
//                {40, 50, 50},
//                {50, 60, 50},
//                {50, 40, 50},
//                {50, 50, 60},
//                {50, 50, 40}};
//
//        System.out.println(Arrays.toString(launchSevenRockets(testVelocities)));
    }
}
