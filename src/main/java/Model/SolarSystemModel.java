package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static helperFunction.HelperFunctions.stringToVector;

public class SolarSystemModel
{

    ArrayList<PlanetObject> listOfPlanets = new ArrayList<>();

    /**
     * Uses the coordinates given by the examiners
     */
    public SolarSystemModel()
    {
        usePlanetsWithGivenCoordinates();
    }

    /**
     *
     * @param startDate the data of which we will take the planets position
     * Follow for date this format " 2023-04-2 "
     * Use this coordinates for the solarSystem
     *
     */
    public SolarSystemModel(String startDate)
    {
        useNasaCoordinates(startDate);
    }


    /**
     * Add the planets to the list
     * The coordinates are the given ones from the file
     */
    public void usePlanetsWithGivenCoordinates()
    {
        PlanetObject Sun = new PlanetObject(0, 0, 0, 0, 0, 0, 1.99e30);
        PlanetObject Mercury = new PlanetObject(7.83e6, 4.49e7, 2.87e6, -5.75e1, 1.15e1, 6.22e0, 3.3e23);
        PlanetObject Venus = new PlanetObject(-2.82e7, 1.04e8, 3.01e6, -3.4e1, -8.97e0, 1.84e0, 4.87e24);
        PlanetObject Earth = new PlanetObject(-1.48e8, -2.78e7, 3.37e4, 5.05e0, -2.94e1, 1.71e-3, 5.97e24);
        PlanetObject Moon = new PlanetObject(-1.48e8, -2.75e7, 7.02e4, 4.34e0, -3.0e1, -1.16e-2, 7.35e22);
        PlanetObject Mars = new PlanetObject(-1.59e8, 1.89e8, 7.87e6, -1.77e1, -1.35e1, 1.52e-1, 6.42e23);
        PlanetObject Jupiter = new PlanetObject(6.93e8, 2.59e8, -1.66e7, -4.71e0, 1.29e1, 5.22e-2, 1.90e27);
        PlanetObject Saturn = new PlanetObject(1253801723.95465, -760453007.810989, -36697431.1565206, 4.46781341335014, 8.23989540475628, -0.320745376969732, 5.68E+26);
        PlanetObject Titan = new PlanetObject(1.25e9, -7.61e8, -3.63e7, 9.e0, 1.11e1, -2.25e0, 1.35e23);
        PlanetObject Neptune = new PlanetObject(4.45e9, -3.98e8, -9.45e7, 4.48e-1, 5.45e0, -1.23e-1, 1.02e26);
        PlanetObject Uranus = new PlanetObject(1.96e9, 2.19e9, -1.72e7, -5.13e0, 4.22e0, 8.21e-2, 8.68e25);
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
        double[] arr = {68.7449, -44.1, -2.2176};
        createProbe(Earth,Titan,arr);

    }


    /**
     *
     * @param startDate is the data of which we will take the planets position
     *  Follow for date this format " 2023-04-2 "
     */
    public void useNasaCoordinates(String startDate)
    {
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
    }


    //TODO add file reader method of importing a solar system
    public void addPlanetsFromFile()
    {}


    public void createProbe(PlanetObject Earth,PlanetObject Titan, double[] arr)
    {
        Probe projectile = new Probe(Earth,Titan,arr);
        listOfPlanets.add(projectile);
    }

    /**
     * Add already created and finished object to the System
     */
    public void addPlanet(PlanetObject planetObject)
    {
        listOfPlanets.add(planetObject);
    }





//_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
    //Getter and Setters
    public ArrayList<PlanetObject> getListOfPlanets() {return listOfPlanets;}
    public void setListOfPlanets(ArrayList<PlanetObject> listOfPlanets) {this.listOfPlanets = listOfPlanets;}
}
