package code;


import code.algorithms.ModelRunner;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.data.loaders.NasaDataLoader;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

import java.util.Arrays;

public class NasaRunner
{
    public static void main(String[] args)
    {

        Model.getInstance().loadData(new FileDataLoader());
        Probe probe= new Probe();
        Probe.Boost boost=new Probe.Boost(0,new double[]{68.7449, -44.1, -2.2176});
        probe.addBoost(boost);
        Model.addProbe(probe);

        ModelRunner.runnerForFastEuler(365,2,Model.getPlanetObjectsArray());
//        double[] target=Model.getPlanetObjectsArray()[3].getTargetPosition();
//        double[] model=Model.getPlanetObjectsArray()[3].getCoordinates();
//        System.out.println(Arrays.toString(model));
        System.out.println(probe.getShortestDistanceToTitan());
//        System.out.println(HelperFunctions.getDistanceBetweenWithVectors(model,target));
        //7.492931555658273E8
    }
}
