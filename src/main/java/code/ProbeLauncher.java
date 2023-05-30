package code;

import code.algorithms.ModelRunner;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Probe;
public class ProbeLauncher
{
    public static void main(String[] args)
    {
        Model.getInstance().loadData(new FileDataLoader());
        Probe probe= new Probe();
        Probe probe2= new Probe();
        Probe.Boost boost=new Probe.Boost(0,new double[]{68.7449, -44.1, -2.2176});
        Probe.Boost boost2=new Probe.Boost(0,new double[]{159.78470381490106, -97.12752946315422  , -8.151758278466753});
        probe.addBoost(boost);
        probe2.addBoost(boost2);
        System.out.println(boost.getFuel());
        System.out.println(boost2.getFuel());


        //Model.addProbe(probe);
        Model.addProbe(probe2);
        //Visualizer.main(args);
        ModelRunner.runnerForMultipleProbes(365,2,Model.getPlanetObjectsArrayList(),Model.getProbes());
//        System.out.println(probe.getShortestDistanceToTitan());
        System.out.println(probe2.getShortestDistanceToTitan());
    }
}
