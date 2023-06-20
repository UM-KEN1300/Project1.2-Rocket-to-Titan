package code;

import code.algorithms.ModelRunner;
import code.algorithms.OrbitCalculator;
import code.graphics.Visualizer;
import code.model.Model;
import code.model.data.loaders.FileDataLoader;
import code.model.objects.Boost;
import code.model.objects.Probe;
import code.utils.Time;
//BUGS:
//The "Days of simulation" counter in the visualizer resets at 32
public class Main {
    public static void main(String[] args) {
        Model.loadData(new FileDataLoader());
        Probe probe = new Probe();

        // velocities calculated by HillClimbingAlg
        Time startTime=new Time(2023,4,1);
        Time orbitTime = new Time(2023, 12, 4);
        Boost boost = new Boost(startTime, new double[]{67.73988800000001, -44.03988500000006, -4.258907});
        // velocities and time calculated by HillClimbingAlgReturn
       // Boost boost1 = new Boost(startTime, new double[]{-128.99164151418873, 46.449291805183115, 3.3594162321263057});
        Boost orbitBoost = new Boost(orbitTime,OrbitCalculator.enterTitanOrbit(probe, Model.getPlanetObjects().get("Titan"), 200));//this
        probe.addBoost(boost);
        probe.addBoost(orbitBoost); //this -> Where do I do this? I do it based on time, but its probably better to do an if(distanceToTitan<=1500)
      //  probe.addBoost(boost1);   // Currently, when I do the boost it stops. I got the date from 30*7+30 = 247 days after startTime
        Model.addProbe(probe);


        //ModelRunner modelRunner=new ModelRunner(startTime);
       // modelRunner.runnerForMultipleProbes(365,1,Model.getPlanetObjectsArrayList(),Model.getProbes());
      //  System.out.println(probe.getShortestDistanceToTitan());
        Visualizer.main(args);
    }
}
