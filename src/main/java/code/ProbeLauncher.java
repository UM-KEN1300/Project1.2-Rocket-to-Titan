package code;

import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Probe;

public class ProbeLauncher {
    public static void main(String[] args) {
        Model.getInstance().loadData(new FileDataLoader());
        Probe probe = new Probe();
        Probe.Boost boost = new Probe.Boost(0, new double[]{68.7449, -44.1, -2.2176});
        probe.addBoost(boost);
        Model.addProbe(probe);
//        ModelRunner.runnerForMultipleProbes(365, 2, Model.getPlanetObjectsArrayList(), Model.getProbes());
//        System.out.println(probe.getShortestDistanceToTitan());

        Visualizer.main(args);
    }
}
