package code;

import code.algorithms.ModelRunner;
import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Boost;
import code.model.objects.Probe;

/**
 * Main class of the project responsible for setting up variables and executing all methods in order.
 */
public class Main {
    public static void main(String[] args) {
        Model.getInstance().loadData(new FileDataLoader());
        System.out.println(Model.getPlanetObjects().get("Earth").getCoordinates()[0]);
        Probe probe = new Probe();
        Boost boost = new Boost(0, new double[]{68.7449, -44.1, -2.2176});
//        Boost boost = new Boost(0, new double[]{159.78470381490106, -97.12752946315422, -8.151758278466753});
        probe.addBoost(boost);
        Model.addProbe(probe);
          Visualizer.main(args);
//        ModelRunner.runnerForMultipleProbes(100, 1, Model.getPlanetObjectsArrayList(), Model.getProbes());
//        System.out.println(probe.getShortestDistanceToTitan());
    }
}
