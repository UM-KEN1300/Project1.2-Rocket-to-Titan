package helperFunction;
import  group1.project12group1.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HelperFunctions
{

    public HelperFunctions()
    {}


    public static double getDistanceBetween(PlanetObject one, PlanetObject two)
    {
        return (Math.sqrt(
                Math.pow((two.getPositionalVector()[0]-one.getPositionalVector()[0]),2)
                +Math.pow((two.getPositionalVector()[1]-one.getPositionalVector()[1]),2)
                +Math.pow((two.getPositionalVector()[2]-one.getPositionalVector()[2]),2))
        );
    }

    public static double getDistanceBetweenWithVectors(double[] one, double[] two)
    {
        return (Math.sqrt(
                Math.pow((two[0]-one[0]),2)
                        +Math.pow((two[1]-one[1]),2)
                        +Math.pow((two[2]-one[2]),2))
        );
    }


    public static double[] subtract(double[]vectorOne, double[] vectorTwo)
    {
       double[] returnable=new double[3];
       returnable[0]=vectorOne[0]-vectorTwo[0];
        returnable[1]=vectorOne[1]-vectorTwo[1];
        returnable[2]=vectorOne[2]-vectorTwo[2];
        return returnable;
    }

    public static double[] addition(double[]vectorOne, double[] vectorTwo)
    {
        double[] returnable=new double[3];
        returnable[0]=vectorOne[0]+vectorTwo[0];
        returnable[1]=vectorOne[1]+vectorTwo[1];
        returnable[2]=vectorOne[2]+vectorTwo[2];
        return returnable;
    }
    //todo is this distance and check how we use it
    public double[] getDistanceBetweenPositionVectors(double[] vectorOne, double[] vectorTwo)
    {
        double[] returnable=new double[3];
        returnable[0]=vectorOne[0]-vectorTwo[0];
        returnable[1]=vectorOne[1]-vectorTwo[1];
        returnable[2]=vectorOne[2]-vectorTwo[2];
        return returnable;
    }
    public PlanetObject[] testing()
    {
        ArrayList<PlanetObject> listOfPlanets = new ArrayList<>();
        PlanetObject Sun = new PlanetObject(0, 0, 0, 0, 0, 0, 1.99e30);
        PlanetObject Mercury = new PlanetObject(199, 3.3e23);
        PlanetObject Venus = new PlanetObject(299, 4.87e24);
        PlanetObject Earth = new PlanetObject(399, 5.97E+24);
        PlanetObject Moon = new PlanetObject(301, 7.35e22);
        PlanetObject Mars = new PlanetObject(499, 6.42e23);
        PlanetObject Jupiter = new PlanetObject(599, 1.90e27);
        PlanetObject Saturn = new PlanetObject(699, 5.68e26);
        PlanetObject Titan = new PlanetObject(606, 1.35e23);
        PlanetObject Uranus = new PlanetObject(799, 8.68e25);
        PlanetObject Neptune = new PlanetObject(899, 1.02e26);
        PlanetObject Projectile= new PlanetObject(0,50000);
        listOfPlanets.add(Sun);
        listOfPlanets.add(Mercury);
        listOfPlanets.add(Venus);
        listOfPlanets.add(Earth);
        listOfPlanets.add(Moon);
        listOfPlanets.add(Mars);
        listOfPlanets.add(Jupiter);
        listOfPlanets.add(Saturn);
        listOfPlanets.add(Titan);
        listOfPlanets.add(Neptune);
        listOfPlanets.add(Uranus);
        listOfPlanets.add(Projectile);

        //end of default inputs
        //Input the start time and end time after set the string to number of days
        // between the dates example 20d if the two dates are 20 dates apart
        //follow the format below when changing
        String startDate = "2023-04-01";
        String endDate = "2023-04-2";
        String daysLong = "1d";
        String urlLoc = "https://ssd.jpl.nasa.gov/api/horizons.api?format=text&COMMAND='";
        BufferedReader br;
        //Sets the positions of the planet to this start frame
        for (int i = 1; i < listOfPlanets.size()-1; i++)
        {
            System.out.println("trying to connect with planet: " + i);
            urlLoc = urlLoc + listOfPlanets.get(i).getPlanetCode() + "&OBJ_DATA='NO'&MAKE_EPHEM='YES'&EPHEM_TYPE='VECTORS'&CENTER='@sun&START_TIME='" + startDate + "'&STOP_TIME='" + endDate + "'&STEP_SIZE='" + daysLong + "'&QUANTITIES='1,9,20,23,24,29'";
            try
            {
                URL locationHTTPS = new URL(urlLoc);
                HttpURLConnection connectionLoc = (HttpURLConnection) locationHTTPS.openConnection();
                connectionLoc.setRequestMethod("GET");
                connectionLoc.connect();
                System.out.println("Connection successful!");
                br = new BufferedReader(new InputStreamReader((connectionLoc.getInputStream())));

                String output = "";
                while (!output.equals("$$SOE"))
                {
                    output = br.readLine();

                }
                br.readLine();
                String positionalVector = br.readLine();
                listOfPlanets.get(i).setPositionalVector(stringToVector(positionalVector));
                String velocityVector = br.readLine() + "\n";
                listOfPlanets.get(i).setVelocityVector(stringToVector(velocityVector));

                br.readLine();
                br.readLine();
                String positinalVectorTarget = br.readLine() + "\n";
                listOfPlanets.get(i).setTargetPosition((stringToVector(positinalVectorTarget)));
                System.out.println("Data added to the planet");
            } catch (MalformedURLException e)
            {
                System.out.println("Problems 1");
                ;
            } catch (ProtocolException e)
            {
                System.out.println("Problems 2");
            } catch (IOException e)
            {
                System.out.println("Problems 3");
            }
            urlLoc = "https://ssd.jpl.nasa.gov/api/horizons.api?format=text&COMMAND='";
        }
        Projectile.setPositionalVector(Earth.getPositionalVector());

        PlanetObject[] re=listOfPlanets.toArray(new PlanetObject[listOfPlanets.size()]);
        return re;


    }

    public double getVectorMagnitude(double[] vector)
    {
        return Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
    }

    public static double[] stringToVector(String input)
    {
        input = (input.replace("X",""));
        input = (input.replace("V",""));
        input = (input.replace("Y",""));
        input = (input.replace("Z",""));
        input = (input.replace("=",""));

        String[] srt = input.split(" ");
        double[] returnable= new double[3];
        int counter=0;
        for (int i = 0; i < srt.length; i++)
        {
            if (!srt[i].isEmpty())
            {
                returnable[counter]=Double.valueOf(srt[i]);
                counter++;
            }

        }


        return returnable;
    }

}
