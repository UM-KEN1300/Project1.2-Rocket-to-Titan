package group1.project12group1;


import helperFunction.HelperFunctions;

import java.util.ArrayList;
import java.util.Arrays;

public class SolarSystem {
    public static PlanetObject Sun = new PlanetObject(0, 0, 0, 0, 0, 0, 1.99e30);
    public static PlanetObject Mercury = new PlanetObject(7.83e6, 4.49e7, 2.87e6, -5.75e1, 1.15e1, 6.22e0, 3.3e23);
    public static PlanetObject Venus = new PlanetObject(-2.82e7, 1.04e8, 3.01e6, -3.4e1, -8.97e0, 1.84e0, 4.87e24);
    public static PlanetObject Earth = new PlanetObject(-1.48e8, -2.78e7, 3.37e4, 5.05e0, -2.94e1, 1.71e-3, 5.97e24);
    public static PlanetObject Moon = new PlanetObject(-1.48e8, -2.75e7, 7.02e4, 4.34e0, -3.0e1, -1.16e2, 7.35e22);
    public static PlanetObject Mars = new PlanetObject(-1.59e8, 1.89e8, 7.87e6, -1.77e1, -1.35e1, 1.52e-1, 6.42e23);
    public static PlanetObject Jupiter = new PlanetObject(6.93e8, 2.59e8, -1.66e7, -4.71e0, 1.29e1, 5.22e-2, 1.90e27);
    public static PlanetObject Saturn = new PlanetObject(1.25e9, -7.60e8, -3.67e7, 4.47e0, 8.24e0, -3.21e1, 5.68e26);
    public static PlanetObject Titan = new PlanetObject(1.25e9, -7.61e8, -3.63e7, 9, 1.11e1, -2.25e0, 1.35e23);
    public static PlanetObject Neptune = new PlanetObject(4.45e9, -3.98e8, -9.45e7, 4.48e-1, 5.45e0, -1.23e1, 1.02e26);
    public static PlanetObject Uranus = new PlanetObject(1.96e9, 2.19e9, -1.72e7, -5.13e0, 4.22e0, 8.21e-2, 8.68e25);

    public static void main(String[] args) {
        Sun.setRadius(695_508);
        Mercury.setRadius(2439);
        Venus.setRadius(6052);
        Earth.setRadius(6371);
        Moon.setRadius(1737);
        Mars.setRadius(3390);
        Jupiter.setRadius(69_911);
        Saturn.setRadius(58_232);
        Titan.setRadius(2574);
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

        for (int i = 1; i < 25; i++)
        {
            double cordY=i;
            double cordX=(-262+29*cordY)/5;
            double[] velocity={cordX,cordY,0};
            Probe probe=new Probe(Earth,Titan,velocity);
            listOfPlanets.add(probe);

             cordY=-i;
             cordX=(-262+29*cordY)/5;
             double[] velocity2={cordX,cordY,0};
             Probe probe2=new Probe(Earth,Titan,velocity2);
             listOfPlanets.add(probe2);
        }




        double step=1*3600*24*365;
        for (int i = 0; i < step; i++)
        {
            if(i%70000==0)
            {
                System.out.println("Progress: "+i+"/"+step);
            }

            for (int j = 1; j <listOfPlanets.size() ; j++)
            {

                double[] acc=new double[3];
                for (int k = 0; k <listOfPlanets.size() ; k++)
                {

                    if(k!=j)
                    {

                        acc=helperFunctions.addition(acc, listOfPlanets.get(j).accelerationBetween(listOfPlanets.get(k)));
                    }

                }
                listOfPlanets.get(j).updatePosition(acc,1);
            }
        }

        Probe tester=(Probe) listOfPlanets.get(11);
        double closesDistance=tester.getDistanceToTitan();
        int remember=11;
        for (int i = 12; i <listOfPlanets.size() ; i++)
        {
             tester=(Probe) listOfPlanets.get(i);
            if(closesDistance>tester.getDistanceToTitan()){
                closesDistance=tester.getDistanceToTitan();
                remember=i;
            }


        }
        tester=(Probe) listOfPlanets.get(remember);
        System.out.println("The probe "+remember+"was the closes to titan with distance "+closesDistance);
        System.out.println("The probe had intial velosity of ");
        for (int i= 0; i <3 ; i++)
        {
            System.out.println(tester.getInitialVelocity()[i]);
        }


    }
}
