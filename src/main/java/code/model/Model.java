package code.model;

import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import org.apache.poi.ss.usermodel.Row;
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
    private final Map<String, PlanetObject> PLANET_OBJECT;

    private final List<Probe> PROBES;

    private Model() {
        PLANET_OBJECT = new HashMap<>();
        PROBES = new ArrayList<>();
        initializeFromFile();
    }

    private static final class InstanceHolder {
        private static final Model INSTANCE = new Model();
    }

    private static Model getInstance() {
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

    private void initializeFromFile() {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/initial_stats.xlsx")) {
            assert inputStream != null : "Initial stats resource not found";
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 1; index <= 11; index++) {
                    Row row = sheet.getRow(index);

                    String name = row.getCell(0).getStringCellValue();

                    double[] coordinates = new double[3];
                    for (int i = 0; i <= 2; i++)
                        coordinates[i] = row.getCell(i + 1).getNumericCellValue();

                    double[] velocity = new double[3];
                    for (int i = 0; i <= 2; i++)
                        velocity[i] = row.getCell(i + 4).getNumericCellValue();

                    long mass = Math.round(row.getCell(7).getNumericCellValue());

                    PLANET_OBJECT.put(name, new PlanetObject(coordinates, velocity, mass));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}
