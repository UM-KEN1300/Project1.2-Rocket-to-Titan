package new_graphics.model_from_file;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import new_graphics.model_from_file.objects.CelestialBody;

public class Model {
    private static Model instance;
    private final Map<String, CelestialBody> CELESTIAL_BODIES;


    public Model() {
        CELESTIAL_BODIES = new HashMap<>();
        initializeCelestialBodies();
    }


    public static Model getInstance() {
        if (instance == null)
            instance = new Model();

        return instance;
    }

    public Map<String, CelestialBody> getCelestialBodies() {
        return CELESTIAL_BODIES;
    }

    private void initializeCelestialBodies() {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/initial_stats.xlsx")) {
            assert inputStream != null: "Initial stats resource not found";
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 1; index <= 11; index++) {
                    Row row = sheet.getRow(index);

                    String name = row.getCell(0).getStringCellValue();

                    double[] coordinates = new double[3];
                    for (int i = 0; i < 3; i++)
                        coordinates[i] = row.getCell(i + 1).getNumericCellValue();

                    double[] velocity = new double[3];
                    for (int i = 0; i < 3; i++)
                        velocity[i] = row.getCell(i + 4).getNumericCellValue();

                    long mass = Math.round(row.getCell(7).getNumericCellValue());

                    CELESTIAL_BODIES.put(name, new CelestialBody(coordinates, velocity, mass));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
