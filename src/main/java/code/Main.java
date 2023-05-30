package code;

import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Boost;
import code.model.objects.Probe;

public class Main {
    public static void main(String[] args) {
        Model.loadData(new FileDataLoader());
        Probe probe = new Probe();
        Boost boost = new Boost(0, new double[]{67.73988800000001, -44.03988500000006, -4.258907});
        probe.addBoost(boost);
        Model.addProbe(probe);

        Visualizer.main(args);
    }
}
