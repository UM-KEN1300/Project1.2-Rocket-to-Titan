package code.model.objects;

import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.HelperFunctions;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class NASAMODELFIX {
        private final Map<String, PlanetObject> PLANET_OBJECT;

        private final List<Probe> PROBES;

        private NASAMODELFIX(String date) {
            PLANET_OBJECT = new HashMap<>();
            PROBES = new ArrayList<>();
            initializeFromFile(date);
        }

        private static final class InstanceHolder {
            private static final NASAMODELFIX INSTANCE = new NASAMODELFIX("2023-04-1");
        }

        private static NASAMODELFIX getInstance() {
            return InstanceHolder.INSTANCE;
        }

        public static Map<String, PlanetObject> getPlanetObjects() {
            return getInstance().PLANET_OBJECT;
        }

        public static List<Probe> getProbes() {
            return getInstance().PROBES;
        }

        public static void addProbe(Probe probe) {
            getInstance().PROBES.add(probe);
        }



        private void initializeFromFile(String startDate)
        {
            PlanetObject Sun = new PlanetObject(new double[]{0, 0, 0}, new double[]{0, 0, 0}, (long) 1.99e30);
            PlanetObject Mercury = new PlanetObject(199, (long) 3.3e23);
            PlanetObject Venus = new PlanetObject(299, (long) 4.87e24);
            PlanetObject Earth = new PlanetObject(399, (long) 5.97E+24);
            PlanetObject Moon = new PlanetObject(301, (long) 7.35e22);
            PlanetObject Mars = new PlanetObject(499, (long) 6.42e23);
            PlanetObject Jupiter = new PlanetObject(599, (long) 1.90e27);
            PlanetObject Saturn = new PlanetObject(699, (long) 5.68e26);
            PlanetObject Titan = new PlanetObject(606, (long) 1.35e23);
            PlanetObject Uranus = new PlanetObject(799, (long) 8.68e25);
            PlanetObject Neptune = new PlanetObject(899, (long) 1.02e26);
            PLANET_OBJECT.put("Sun", Sun);
            PLANET_OBJECT.put("Mercury", Mercury);
            PLANET_OBJECT.put("Venus", Venus);
            PLANET_OBJECT.put("Earth", Earth);
            PLANET_OBJECT.put("Moon", Moon);
            PLANET_OBJECT.put("Mars", Mars);
            PLANET_OBJECT.put("Jupiter", Jupiter);
            PLANET_OBJECT.put("Saturn", Saturn);
            PLANET_OBJECT.put("Titan", Titan);
            PLANET_OBJECT.put("Neptune", Neptune);
            PLANET_OBJECT.put("Uranus", Uranus);
            String endDate = "2023-04-2";
            String daysLong = "1d";
            String urlLoc = "https://ssd.jpl.nasa.gov/api/horizons.api?format=text&COMMAND='";
            BufferedReader br;
            //Sets the positions of the planet to this start frame


            for (Map.Entry<String, PlanetObject> entry : PLANET_OBJECT.entrySet())
            {
                System.out.println("trying to connect with planet: " + entry.getKey());
                urlLoc = urlLoc + entry.getValue().getPlanetCode() + "&OBJ_DATA='NO'&MAKE_EPHEM='YES'&EPHEM_TYPE='VECTORS'&CENTER='@sun&START_TIME='" + startDate + "'&STOP_TIME='" + endDate + "'&STEP_SIZE='" + daysLong + "'&QUANTITIES='1,9,20,23,24,29'";
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
                    entry.getValue().setCoordinates(HelperFunctions.stringToVector(positionalVector));
                    String velocityVector = br.readLine() + "\n";
                    entry.getValue().setVelocity(HelperFunctions.stringToVector(velocityVector));


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
    }


