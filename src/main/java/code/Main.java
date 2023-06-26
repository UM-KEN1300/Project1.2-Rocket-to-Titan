package code;

import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Probe;

public class Main {
    public static void main(String[] args) {
        System.out.println(Math.round(3.49));
        Model.loadData(new FileDataLoader());
        Probe probe = new Probe();
//        probe.addBoost(new Boost(new Time(2023, 4, 1, 0, 0, 0), new double[]{159.78470381490106, -97.12752946315422, -8.151758278466753}));
        Model.addProbe(probe);
        Visualizer.main(args);
    }
}
