package code;


import code.graphics.Visualizer;
import code.model.Model;
import code.model.objects.Probe;

public class Main {
    public static void main(String[] args) {
        Model.addProbe(new Probe(new double[]{0, 0, 0}));
        Visualizer.main(args);
    }
}
