package code.algorithms;

import code.model.Model;
import code.model.objects.Probe;

import static code.model.Model.addProbe;

public class LaunchRocketHC {

    final int INITIAL = 0;
    final int XPLUS = 1;
    final int XMINUS = 2;
    final int YPLUS = 3;
    final int YMINUS = 4;
    final int ZPLUS = 5;
    final int ZMINUS = 6;
    final int X = 0;
    final int Y = 1;
    final int Z = 2;
    public double[] launchSevenRockets(double[][] velocitiesOfRockets){

        Probe initialRocket = new Probe(velocitiesOfRockets[INITIAL]);
        Probe xPlusRocket = new Probe(velocitiesOfRockets[XPLUS]);
        Probe xMinusRocket = new Probe(velocitiesOfRockets[XMINUS]);
        Probe yPlusRocket = new Probe(velocitiesOfRockets[YPLUS]);
        Probe yMinusRocket = new Probe(velocitiesOfRockets[YMINUS]);
        Probe zPlusRocket = new Probe(velocitiesOfRockets[ZPLUS]);
        Probe zMinusRocket = new Probe(velocitiesOfRockets[ZMINUS]);

        runnerForModel(365, 1, );

        return new double[] { initialRocket.getShortestDistanceToTitan(),
                                xPlusRocket.getShortestDistanceToTitan(),
                                xMinusRocket.getShortestDistanceToTitan(),
                                yPlusRocket.getShortestDistanceToTitan(),
                                yMinusRocket.getShortestDistanceToTitan(),
                                zPlusRocket.getShortestDistanceToTitan(),
                                zMinusRocket.getShortestDistanceToTitan()};
    }
}
