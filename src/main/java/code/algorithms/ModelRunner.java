package code.algorithms;


import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

public class ModelRunner {
    public ModelRunner() {
    }

    public static void runnerForFastEuler(int numberOfDays,double accuracy, PlanetObject[] planets)
    {
        Probe probe = (Probe) planets[11];
        for (int i = 0; i <(1/accuracy)*60*60*24*numberOfDays ; i += 1) {
            if(i%((1 / accuracy) * 60 * 60 * 24)==0)
            {
                double day=i /( (1 / accuracy) * 60 * 60 * 24);
                System.out.println("Day "+day);
                probe.BoosterMECH(day);
            }
            for (int j = 1; j < planets.length; j++) {

                double[] acc = new double[3];
                for (int k = 0; k < planets.length; k++) {

                    if (k != j) {
                        acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                    }
                }
                Solvers.fastEuler(planets[j],acc, accuracy);
            }
        }
    }






    public static void runnerForGUI(int smoothness, double accuracy, PlanetObject[] planets)
    {
        for (int i = 0; i < smoothness; i += 1)
        {

            for (int j = 1; j < planets.length; j++)
            {
                planets[j].initializeAcceleration();
                for (int k = 0; k < planets.length; k++)
                {
                    if (k != j)
                    {
                        Solvers.explicitEuler(planets[j], planets[k], accuracy);
                    }
                }
            }
        }
    }


    public static void guiRunnerOldButFast(int smoothness, double accuracy, PlanetObject[] planets) {
        for (int i = 0; i < smoothness; i += 1) {

            for (int j = 1; j < planets.length; j++) {
                double[] acc = new double[3];
                for (int k = 0; k < planets.length; k++) {

                    if (k != j) {
                        acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                    }
                }
                Solvers.fastEuler(planets[j],acc, accuracy);
                }
            }
        }


    public static void runnerForModel(int numberOfDays, double accuracy, PlanetObject[] planets) {
        for (int i = 0; i < (1 / accuracy) * 60 * 60 * 24 * numberOfDays; i += 1) {
            if(i%((1 / accuracy) * 60 * 60 * 24)==0)
            {
                System.out.println(i /( (1 / accuracy) * 60 * 60 * 24));
            }
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



}