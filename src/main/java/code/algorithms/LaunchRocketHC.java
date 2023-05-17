package code.algorithms;

import code.model.Model;
import code.model.objects.Probe;

import static code.model.Model.addProbe;

public class LaunchRocketHC {
    public static void launchOneRocket(double[] VELOCITY){
        Probe rocket = new Probe(VELOCITY); //ask about initial position, as its supposed to launch from 63800 meters
        Model.addProbe(rocket); //is this adding the rocket to the model? Is the one probe that was already on the model launched in the Main?
                                        // is the runnerForModel (ModelRunner.java) method working?

    }
}
