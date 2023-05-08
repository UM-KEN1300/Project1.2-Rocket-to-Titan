package code.algorithms;


import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;

public class ModelRunner {
    public ModelRunner() {
    }

    public static void runnerForGUI(int smoothness, double accuracy, PlanetObject[] planets) {
        for (int i = 0; i < smoothness; i += 1) {
            for (int j = 1; j < planets.length; j++) {
                double[] acc = new double[3];
                for (int k = 0; k < planets.length; k++) {
                    if (k != j) {
                        acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                    }
                }
                Solvers.eulerSolver(planets[j], acc, accuracy);
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
                Solvers.eulerSolver(planets[j], acc, accuracy);
            }
        }
    }
}
