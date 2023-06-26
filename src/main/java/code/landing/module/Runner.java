package code.landing.module;

import code.model.Model;
import code.model.data.loaders.FileDataLoader;
//import code.model.objects.Boost;
import code.model.objects.Probe;
import code.utils.Time;

public class Runner
{
    public static void main(String[] args)
    {
        Model.loadData(new FileDataLoader());
        Probe probe = new Probe();
        Time startTime=new Time(2023,4,1);
     //   Boost boost = new Boost(startTime, new double[]{67.73988800000001, -44.03988500000006, -4.258907});
    }
}
