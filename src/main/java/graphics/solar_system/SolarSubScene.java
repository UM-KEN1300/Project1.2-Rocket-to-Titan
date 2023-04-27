package graphics.solar_system;

import com.graphics.solar_system.controllers.SolarScrollController;
import com.model.Model;
import com.model.objects.CelestialBody;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class responsible for creating and managing the movement of the 3D objects in the visualization.
 */
public class SolarSubScene extends SubScene implements SolarSubSceneInterface {
    private final Group ROOT;
    private final Map<String, Sphere> celestialBodyViews;
    private final Map<String, Double> CELESTIAL_BODY_SIZES;
    private final Map<String, CelestialBody> CELESTIAL_BODIES;
    private final Map<String, Polyline> TRAILS;


    public SolarSubScene(double width, double height) {
        this(new Group(), width, height);
    }

    private SolarSubScene(Group root, double width, double height) {
        super(root, width, height, true, SceneAntialiasing.BALANCED);
        ROOT = root;
        CELESTIAL_BODIES = Model.getInstance().getCelestialBodies();

        celestialBodyViews = new HashMap<>();
        CELESTIAL_BODY_SIZES = new HashMap<>();

        TRAILS = new HashMap<>();

        initializeSolarView();
    }

    /**
     * Creates the 3D spheres representing planets in the visualization and puts them into the root group
     * of the SubScene. Bases the radius on the second sheet of the Excel file in resources directory. Bases
     * coordinates of the celestial body objects from the HashMap of object of class Model.
     */
    private void initializeSolarView() {
        try (InputStream inputStream = getClass().getResourceAsStream("/model/radius.xlsx")) {
            assert inputStream != null;
            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int index = 0; index <= 10; index++) {
                    String name = sheet.getRow(index).getCell(0).getStringCellValue();
                    double radius = sheet.getRow(index).getCell(1).getNumericCellValue() / SCALE;
                    Sphere view = new Sphere(radius);

                    view.setTranslateX(CELESTIAL_BODIES.get(name).getX() / SCALE);
                    view.setTranslateY(CELESTIAL_BODIES.get(name).getY() / SCALE);
                    view.setTranslateZ(CELESTIAL_BODIES.get(name).getZ() / SCALE);

                    celestialBodyViews.put(name, view);
                    CELESTIAL_BODY_SIZES.put(name, radius);
                    ROOT.getChildren().add(celestialBodyViews.get(name));

                    Polyline trail = new Polyline();
                    trail.setStroke(Color.WHITE);
                    trail.setStrokeWidth(0.5);
                    TRAILS.put(name, trail);
                    ROOT.getChildren().add(trail);
                }

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        initializeSubScene();
        setTextures();
    }

    private void initializeSubScene() {
        setFill(Color.BLACK.brighter());
        initializeCamera();
        initializeFilters();
    }

    private void initializeCamera() {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.01);
        camera.setFarClip(1_000_000_000);
        camera.setTranslateZ(-celestialBodyViews.get("Sun").getRadius());
        setCamera(camera);
        updateSizes();
    }

    private void initializeFilters() {
        new SolarScrollController(this);

        setRotationPoint(0, 0, 0);
    }

    private void setTextures() {
        for (Map.Entry<String, Sphere> entry : celestialBodyViews.entrySet()) {
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseMap(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/textures/" + entry.getKey().toLowerCase() + ".jpg"))));
            celestialBodyViews.get(entry.getKey()).setMaterial(material);
        }
    }

    /**
     * Updates the positions of the 3D objects representations based on the values from the model.
     */
    public void updateCoordinates() {
        for (Map.Entry<String, CelestialBody> entry : CELESTIAL_BODIES.entrySet()) {
            celestialBodyViews.get(entry.getKey()).setTranslateX(entry.getValue().getX());
            celestialBodyViews.get(entry.getKey()).setTranslateY(entry.getValue().getX());
            celestialBodyViews.get(entry.getKey()).setTranslateZ(entry.getValue().getX());
        }
    }

    public void setRotationPoint(double x, double y, double z) {
        ROOT.getTransforms().setAll(new Translate(-x, -y, -z), new Rotate(), new Translate(x, y, z));
    }

    public void updateSizes() {
        double cameraZ = -getCamera().getTranslateZ(); // Make sure cameraZ is positive
        double scaleFactor;
        double zoomLevel = cameraZ / (2000.0 * SolarSubSceneInterface.SCALE);

        for (Map.Entry<String, Sphere> entry : celestialBodyViews.entrySet()) {
            String key = entry.getKey();
            Sphere view = entry.getValue();
            double minSize = CELESTIAL_BODY_SIZES.get(key) / SolarSubSceneInterface.SCALE; // Get the minimum size and apply the scaling factor

            scaleFactor = 1 + 2 * zoomLevel;
            scaleFactor = Math.max(1, scaleFactor); // Prevent the scaleFactor from going below 1

            if (key.equals("Sun")) {
                scaleFactor = scaleFactor * 0.03; // Apply a different scaling factor for the Sun
            }

            view.setRadius(minSize * scaleFactor);
        }
    }

    public void updateTrails() {
        for (Map.Entry<String, CelestialBody> entry : CELESTIAL_BODIES.entrySet()) {
            String key = entry.getKey();
            CelestialBody body = entry.getValue();
            Polyline trail = TRAILS.get(key);

            double x = body.getX() / SolarSubSceneInterface.SCALE;
            double y = body.getY() / SolarSubSceneInterface.SCALE;
            double z = body.getZ() / SolarSubSceneInterface.SCALE;

            trail.getPoints().addAll(x, y, z);

            int maxTrailPoints = 5000;
            if (trail.getPoints().size() > maxTrailPoints * 3) {
                trail.getPoints().remove(0, 3);
            }
        }
    }

    public Map<String, Sphere> getCelestialBodyViews(){
        return celestialBodyViews;
    }
}
