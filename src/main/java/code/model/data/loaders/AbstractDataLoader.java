package code.model.data.loaders;

import code.model.objects.PlanetObject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public abstract class AbstractDataLoader implements DataLoader {
    @Override
    public abstract void load(Map<String, PlanetObject> planetObjects);

    /**
     * Loads the radii of each celestial body from the xlsx file in resources and assigns it to the
     * PlanetObjects in the model.
     */
    void loadRadii(Map<String, PlanetObject> planetObjects) {
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

    /**
     * Loads the mass of each celestial body from the xlsx file in resources and assigns it to the
     * PlanetObjects in the model.
     */
    void loadMass(Map<String, PlanetObject> planetObjects) {
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
