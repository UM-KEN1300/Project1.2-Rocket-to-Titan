package code.algorithms;

import code.model.Model;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

import java.util.ArrayList;
import java.util.List;

public class ModelRunner {
    private ModelRunner() {
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

    public static void runnerForFastEuler(int numberOfDays, double accuracy, PlanetObject[] planets) {
        Probe probe = (Probe) planets[11];
        for (int i = 0; i < (1 / accuracy) * 60 * 60 * 24 * numberOfDays; i += 1) {
            if (i % ((1 / accuracy) * 60 * 60 * 24) == 0) {
                double day = i / ((1 / accuracy) * 60 * 60 * 24);
                System.out.println("Day " + day);
                probe.BoosterMECH(day);
            }
            for (int j = 1; j < planets.length; j++) {

                double[] acc = new double[3];
                for (int k = 0; k < planets.length; k++) {

                    if (k != j) {
                        acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                    }
                }
                Solvers.fastEuler(planets[j], acc, accuracy);
            }
        }
    }

    public static void runnerForMultipleProbes(int numberOfDays, double accuracy, List<PlanetObject> planetss, List<Probe> probes) {
        ArrayList<PlanetObject> allObjects = new ArrayList<>(planetss);
        allObjects.addAll(probes);
        PlanetObject[] planets = allObjects.toArray(new PlanetObject[allObjects.size()]);
        for (int i = 0; i < (1 / accuracy) * 60 * 60 * 24 * numberOfDays; i += 1) {
            if (i % ((1 / accuracy) * 60 * 60 * 24) == 0) {
                double day = i / ((1 / accuracy) * 60 * 60 * 24);
                for (Probe probe : probes) {
                    probe.BoosterMECH(day);
                }
            }
            for (int j = 1; j < planets.length; j++) {

                double[] acc = new double[3];
                for (int k = 0; k < planets.length - probes.size(); k++) {

                    if (k != j) {
                        acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                    }
                }
                Solvers.fastEuler(planets[j], acc, accuracy);
            }
        }

    }


}
