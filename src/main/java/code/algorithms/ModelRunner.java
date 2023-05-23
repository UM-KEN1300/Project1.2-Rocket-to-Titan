package code.algorithms;


import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

import java.util.ArrayList;
import java.util.List;

public class ModelRunner {
    public ModelRunner() {
    }

    public static void runnerForGUI(int smoothness, double accuracy, PlanetObject[] planets) {
        for (int i = 0; i < smoothness; i += 1) {
            for (int j = 1; j < planets.length; j++) {
                planets[j].initializeAcceleration();
                for (int k = 0; k < planets.length; k++) {
                    if (k != j) {
                        Solvers.explicitEuler(planets[j], planets[k], accuracy);
                    }
                }
            }
        }
    }

    public static void runnerForModel(int numberOfDays, double accuracy, PlanetObject[] planets) {
        for (int i = 0; i < (1 / accuracy) * 60 * 60 * 24 * numberOfDays; i += 1) {
            for (int j = 1; j < planets.length; j++) {
                double[] acc = new double[3];
                for (int k = 0; k < planets.length; k++) {
                    if (k != j) {
                        acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                    }
                }
                //Solvers.eulerSolver(planets[j], acc, accuracy);
            }
        }
    }

    public static void runnerForMultipleProbes(int numberOfDays, double accuracy, List<PlanetObject> planets, List<Probe> probes) {
        ArrayList<PlanetObject> allObjects = new ArrayList<>(planets);
        allObjects.addAll(probes);

        for (int i = 0; i < (1 / accuracy) * 60 * 60 * 24 * numberOfDays; i += 1) {
            for (PlanetObject evaluatedObject : allObjects.subList(1, allObjects.size())) {
                evaluatedObject.initializeAcceleration();
                for (PlanetObject otherPlanet : planets) {
                    if (!evaluatedObject.equals(otherPlanet))
                        Solvers.explicitEuler(evaluatedObject, otherPlanet, accuracy);
                }
            }
        }
    }
}
