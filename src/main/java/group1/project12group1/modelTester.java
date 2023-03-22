package group1.project12group1;

import helperFunction.HelperFunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class modelTester
{

    public static void main(String[] args)
    {
        //default inputs
        HelperFunctions helperFunctions=new HelperFunctions();
        ArrayList<PlanetObject> listOfPlanets=new ArrayList<>();
        PlanetObject Sun = new PlanetObject(0, 0, 0, 0, 0, 0, 1.99e30);
        PlanetObject Mercury= new PlanetObject(199,3.3e23);
        PlanetObject Venus= new PlanetObject(299,4.87e24);
        PlanetObject Earth= new PlanetObject(399,5.97E+24);
        PlanetObject Moon= new PlanetObject(301,7.35e22);
        PlanetObject Mars= new PlanetObject(499,6.42e23);
        PlanetObject Jupiter= new PlanetObject(599,1.90e27);
        PlanetObject Saturn= new PlanetObject(699,5.68e26);
        PlanetObject Titan= new PlanetObject(606,1.35e23);
        PlanetObject Uranus= new PlanetObject(799,8.68e25);
        PlanetObject Neptune= new PlanetObject(899,1.02e26);
        listOfPlanets.add(Sun);
        listOfPlanets.add(Mercury);
        listOfPlanets.add(Venus);
        listOfPlanets.add(Earth);
        listOfPlanets.add(Moon);
        listOfPlanets.add(Mars);
        listOfPlanets.add(Jupiter);
        listOfPlanets.add(Saturn);
        listOfPlanets.add(Neptune);
        listOfPlanets.add(Uranus);

        //end of default inputs
        //Input the start time and end time after set the string to number of days
        // between the dates example 20d if the two dates are 20 dates apart
        //follow the format below when changing
        String startDate="2022-01-01";
        String endDate="2022-12-31";
        String daysLong="364d";
        String urlLoc = "https://ssd.jpl.nasa.gov/api/horizons.api?format=text&COMMAND='";
        BufferedReader br;
        //Sets the positions of the planet to this start frame
        for (int i = 0; i <listOfPlanets.size() ; i++)
        {
            System.out.println("trying to connect with planet: "+i);
            urlLoc=urlLoc+listOfPlanets.get(i).getPlanetCode()+"&OBJ_DATA='NO'&MAKE_EPHEM='YES'&EPHEM_TYPE='VECTORS'&CENTER='@sun&START_TIME='"+startDate+"'&STOP_TIME='"+endDate+"'&STEP_SIZE='"+daysLong+"'&QUANTITIES='1,9,20,23,24,29'";
            try
            {
                URL locationHTTPS = new URL(urlLoc);
                HttpURLConnection connectionLoc = (HttpURLConnection) locationHTTPS.openConnection();
                connectionLoc.setRequestMethod("GET");
                connectionLoc.connect();
                System.out.println("Connection successful!");
                 br = new BufferedReader(new InputStreamReader((connectionLoc.getInputStream())));

                String output="";
                while (!output.equals("$$SOE")  )
                {
                   output= br.readLine();

                }
                br.readLine();
                String positionalVector=br.readLine();
                listOfPlanets.get(i).setPositionalVector( helperFunctions.stringToVector(positionalVector));
                String velocityVector=br.readLine()+"\n";
                listOfPlanets.get(i).setVelocityVector( helperFunctions.stringToVector(velocityVector));

                br.readLine();
                br.readLine();
                String positinalVectorTarget=br.readLine()+"\n";
                listOfPlanets.get(i).setTargetPosition(( helperFunctions.stringToVector(positinalVectorTarget)));
                System.out.println("Data added to the planet");
            }
            catch (MalformedURLException e){System.out.println("Problems 1");;}
            catch (ProtocolException e) {System.out.println("Problems 2");}
            catch (IOException e) {System.out.println("Problems 3");}
            urlLoc="https://ssd.jpl.nasa.gov/api/horizons.api?format=text&COMMAND='";
        }


        //run for the same time as the initial value
        //accuracy
//        System.out.println("starting the simulation of solar system.");
//        double step0p1=10*3600*24*10;
//        double step=10*3600*24*365;
//        for (int i = 0; i < step; i++)
//        {
//           if(i%70000==0)
//           {
//               System.out.println("Progress: "+i+"/"+step);
//           }
//
//            for (int j = 0; j <listOfPlanets.size()-1 ; j++)
//            {
//
//                double[] acc=new double[3];
//                for (int k = 0; k <listOfPlanets.size() ; k++)
//                {
//
//                    if(k!=j)
//                    {
//                        acc=helperFunctions.addition(acc, listOfPlanets.get(j).ForceCaluclatorNEW(listOfPlanets.get(k)));
//                    }
//
//                }
//                listOfPlanets.get(j).updatePositionVelocity(acc,0.1);
//
//
//
////                listOfPlanets.get(j).setPrivousPosition(listOfPlanets.get(j).getPositionalVector());
////
//            }
//        }
//
//        System.out.println("The difference between the model and the nasa model is:");
//        for (int i = 0; i < listOfPlanets.size()-1; i++)
//        {
//
//            System.out.print("For planet with Planet Code: "+listOfPlanets.get(i).getPlanetCode()+" is: ");
//            System.out.print(helperFunctions.getDistanceBetweenWithVectors(listOfPlanets.get(i).getPositionalVector(),listOfPlanets.get(i).getTargetPosition())+"km difference");
//            double[] result=helperFunctions.getDistanceBetweenPositionVectors( listOfPlanets.get(i).getPositionalVector(),listOfPlanets.get(i).getTargetPosition());
//            System.out.println("Difference in coordinates is:");
//            for (int j = 0; j < result.length; j++)
//            {
//                System.out.print(result[j]+"  ");
//            }
//            System.out.println();
//        }






    }
}
