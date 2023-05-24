package code;


import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.data.loaders.NasaDataLoader;
import code.model.objects.Probe;

public class Main {
    public static void main(String[] args) {
        Model.getInstance().loadData(new FileDataLoader());
      //  Model.addProbe(new Probe(new double[]{68.7449, -44.1, -2.2176}));
        Visualizer.main(args);
    }
}