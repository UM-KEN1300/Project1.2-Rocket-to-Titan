package code;


import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.data.loaders.NasaDataLoader;
import code.model.objects.Probe;

public class Main {
    public static void main(String[] args) {
        // uncomment one of the following lines before running the program
        Model.getInstance().loadData(new FileDataLoader());
//        Model.getInstance().loadData(new NasaDataLoader());

        Model.addProbe(new Probe(new double[]{0, 0, 0}));

        System.out.println(Model.getPlanetObjects().get("Moon").getCoordinates()[0]);

        Visualizer.main(args);
    }
}
