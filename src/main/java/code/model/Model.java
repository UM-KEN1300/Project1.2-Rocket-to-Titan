package code.model;

import code.model.data.loaders.DataLoader;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Model {
    private Map<String, PlanetObject> planetObjects;
    private List<Probe> probes;


    private Model() {
    }


    private static final class InstanceHolder {
        private static final Model INSTANCE = new Model();
    }

    public static Model getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static Map<String, PlanetObject> getPlanetObjects() {
        return getInstance().planetObjects;
    }

    public static PlanetObject[] getPlanetObjectsArray() {
        Map<String, PlanetObject> planets = getInstance().planetObjects;

        return planets.values().toArray(new PlanetObject[planets.size()]);
    }

    public static ArrayList<PlanetObject> getPlanetObjectsArrayList() {
        ArrayList<PlanetObject> planets = new ArrayList<>();
        planets.add(Model.getPlanetObjects().get("Sun"));
        planets.add(Model.getPlanetObjects().get("Mercury"));
        planets.add(Model.getPlanetObjects().get("Venus"));
        planets.add(Model.getPlanetObjects().get("Earth"));
        planets.add(Model.getPlanetObjects().get("Moon"));
        planets.add(Model.getPlanetObjects().get("Mars"));
        planets.add(Model.getPlanetObjects().get("Jupiter"));
        planets.add(Model.getPlanetObjects().get("Saturn"));
        planets.add(Model.getPlanetObjects().get("Titan"));
        planets.add(Model.getPlanetObjects().get("Neptune"));
        planets.add(Model.getPlanetObjects().get("Uranus"));
        return planets;
    }


    public static List<Probe> getProbes() {
        return getInstance().probes;
    }

    public static void addProbe(Probe probe) {
        getInstance().probes.add(probe);
    }

    public static void chooseProbe(Probe probe){
        getInstance().probes = new ArrayList<>();
        addProbe(probe);
    }

    public void loadData(DataLoader dataLoader) {
        planetObjects = new HashMap<>();
        probes = new ArrayList<>();
        dataLoader.load(planetObjects);
        loadRadii();
        loadMass();
    }

    private void loadRadii() {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/radius.xlsx")) {
            assert inputStream != null;
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 0; index <= 10; index++) {
                    String name = sheet.getRow(index).getCell(0).getStringCellValue();
                    double radius = sheet.getRow(index).getCell(1).getNumericCellValue();

                    planetObjects.get(name).setRadius(radius);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void loadMass() {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/mass.xlsx")) {
            assert inputStream != null;
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 0; index <= 10; index++) {
                    String name = sheet.getRow(index).getCell(0).getStringCellValue();
                    double mass = sheet.getRow(index).getCell(1).getNumericCellValue();

                    planetObjects.get(name).setMass(mass);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
