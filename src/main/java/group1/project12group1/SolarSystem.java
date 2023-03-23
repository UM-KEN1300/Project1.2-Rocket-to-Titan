package group1.project12group1;


import helperFunction.HelperFunctions;

import java.util.ArrayList;
import java.util.Arrays;

public class SolarSystem {
    public static PlanetObject Sun = new PlanetObject(0, 0, 0, 0, 0, 0, 1.99e30);
    public static PlanetObject Mercury = new PlanetObject(7.83e6, 4.49e7, 2.87e6, -5.75e1, 1.15e1, 6.22e0, 3.3e23);
    public static PlanetObject Venus = new PlanetObject(-2.82e7, 1.04e8, 3.01e6, -3.4e1, -8.97e0, 1.84e0, 4.87e24);
    public static PlanetObject Earth = new PlanetObject(-1.48e8, -2.78e7, 3.37e4, 5.05e0, -2.94e1, 1.71e-3, 5.97e24);
    public static PlanetObject Moon = new PlanetObject(-1.48e8, -2.75e7, 7.02e4, 4.34e0, -3.0e1, -1.16e-2, 7.35e22);
    public static PlanetObject Mars = new PlanetObject(-1.59e8, 1.89e8, 7.87e6, -1.77e1, -1.35e1, 1.52e-1, 6.42e23);
    public static PlanetObject Jupiter = new PlanetObject(6.93e8, 2.59e8, -1.66e7, -4.71e0, 1.29e1, 5.22e-2, 1.90e27);
    public static PlanetObject Saturn = new PlanetObject(1253801723.95465, -760453007.810989, -36697431.1565206, 4.46781341335014, 8.23989540475628, -0.320745376969732, 5.68E+26);
    public static PlanetObject Titan = new PlanetObject(1.25e9, -7.61e8, -3.63e7, 9.e0, 1.11e1, -2.25e0, 1.35e23);
    public static PlanetObject Neptune = new PlanetObject(4.45e9, -3.98e8, -9.45e7, 4.48e-1, 5.45e0, -1.23e-1, 1.02e26);
    public static PlanetObject Uranus = new PlanetObject(1.96e9, 2.19e9, -1.72e7, -5.13e0, 4.22e0, 8.21e-2, 8.68e25);
    static double[] arr = {42, -43, -3};
    public static Probe Projectile = new Probe(Earth, Titan, arr);

    public static void main(String[] args) {
        ArrayList<PlanetObject> listOfPlanets = new ArrayList<>();
        listOfPlanets.add(Sun);
        listOfPlanets.add(Mercury);
        listOfPlanets.add(Venus);
        listOfPlanets.add(Earth);
        listOfPlanets.add(Moon);
        listOfPlanets.add(Mars);
        listOfPlanets.add(Jupiter);
        listOfPlanets.add(Titan);
        listOfPlanets.add(Saturn);
        listOfPlanets.add(Neptune);
        listOfPlanets.add(Uranus);
        HelperFunctions helperFunctions = new HelperFunctions();

//        for (int i = 1; i < 25; i++)
//        {
//            double cordY=i;
//            double cordX=(-262+29*cordY)/5;
//            double[] velocity={cordX,cordY,0};
//            Probe probe=new Probe(Earth,Titan,velocity);
//            listOfPlanets.add(probe);
//             cordY=-i;
//             cordX=(-262+29*cordY)/5;
//             double[] velocity2={cordX,cordY,0};
//             Probe probe2=new Probe(Earth,Titan,velocity2);
//             listOfPlanets.add(probe2);
//        }
        double[] velocity = {0, 0, 0};
        Probe probes = new Probe(Earth, Titan, velocity);
        double[] direction = helperFunctions.subtract(probes.getPositionalVector(), Sun.getVelocityVector());
        velocity = direction;

        for (int i = 0; i < 3; i++) {
            velocity[i] = -velocity[i] * 53 / helperFunctions.getVectorMagnitude(direction);
            System.out.println(velocity[i]);
        }
        System.out.println(helperFunctions.getVectorMagnitude(velocity));

        double[] arr = {42, -43, -3};
        double[] initPos = probes.getPosition();
        double[] titanPos = new double[3];
        titanPos[0] = 1.4487665253803456 * Math.pow(10, 9);
        titanPos[1] = -5.616509545202004 * Math.pow(10, 8);
        titanPos[2] = 2207623.640760994;
        double accel[] = new double[3];
        listOfPlanets.add(probes);
        for (int b = 0; b < listOfPlanets.size(); b++) {

            if (b != 11) {

                accel = helperFunctions.addition(accel, listOfPlanets.get(11).accelerationBetween(listOfPlanets.get(b)));
            }

            probes.setVelocityVector(probes.initialVelocity(initPos, titanPos, accel));

        }
            double step = 1 * 3600 * 24 * 365;
            for (int i = 0; i < step; i++) {
                if (i % 70000 == 0) {
                    System.out.println("Progress: " + i + "/" + step);
                }

                for (int j = 1; j < listOfPlanets.size(); j++) {

                    double[] acc = new double[3];
                    for (int k = 0; k < listOfPlanets.size(); k++) {

                        if (k != j) {

                            acc = helperFunctions.addition(acc, listOfPlanets.get(j).accelerationBetween(listOfPlanets.get(k)));
                        }

                    }
                    listOfPlanets.get(j).updatePosition(acc, 1);
                }
            }

            for (int i = 0; i < 3; i++) {
                System.out.println(Titan.getPositionalVector()[i]);
            }


            System.out.println(probes.getDistanceToTitan());
            System.out.println(helperFunctions.getDistanceBetween(probes, Titan));
//        Probe tester=(Probe) listOfPlanets.get(11);
//        double closesDistance=tester.getDistanceToTitan();
//        int remember=11;
//        for (int i = 12; i <listOfPlanets.size() ; i++)
//        {
//             tester=(Probe) listOfPlanets.get(i);
//            if(closesDistance>tester.getDistanceToTitan()){
//                closesDistance=tester.getDistanceToTitan();
//                remember=i;
//            }
//
//
//        }
//        tester=(Probe) listOfPlanets.get(remember);
//        System.out.println("The probe "+remember+"was the closes to titan with distance "+closesDistance);
//        System.out.println("The probe had intial velosity of ");
//        for (int i= 0; i <3 ; i++)
//        {
//            System.out.println(tester.getInitialVelocity()[i]);
//        }


        }
    }

