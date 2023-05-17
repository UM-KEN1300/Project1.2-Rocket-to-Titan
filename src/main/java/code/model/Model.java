package code.model;

import code.model.data.loaders.DataLoader;
import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<Probe> getProbes() {
        return getInstance().probes;
    }

    public static void addProbe(Probe probe) {
        getInstance().probes.add(probe);
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
                    long radius = (long) sheet.getRow(index).getCell(1).getNumericCellValue();

                    planetObjects.get(name).setRadius(radius);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void loadMass(){
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
