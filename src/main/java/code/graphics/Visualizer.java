package code.graphics;

import code.model.Model;
import code.model.objects.PlanetObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Visualizer extends Application {
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public final double SCALE = 100;

    PlanetObject[] planets = new PlanetObject[]{
            Model.getPlanetObjects().get("Sun"),
            Model.getPlanetObjects().get("Mercury"),
            Model.getPlanetObjects().get("Venus"),
            Model.getPlanetObjects().get("Earth"),
            Model.getPlanetObjects().get("Moon"),
            Model.getPlanetObjects().get("Mars"),
            Model.getPlanetObjects().get("Jupiter"),
            Model.getPlanetObjects().get("Saturn"),
            Model.getPlanetObjects().get("Titan"),
            Model.getPlanetObjects().get("Neptune"),
            Model.getPlanetObjects().get("Uranus"),
            Model.getProbes().get(0)};
    Sphere[] visualizedObjects = new Sphere[12];
    private SolarCamera solarCamera;
    private Sphere currentFocus = visualizedObjects[11];
    Rotate rotateX, rotateZ;
    int currentFocusIndex;
    Group root, paths;
    ArrayList<Sphere> projectilePath = new ArrayList<>();
    double[] shift;
    double distanceToTitan;
    int[] radius = new int[11];
    double test = 0;

    @Override
    public void start(Stage stage) throws IOException {
        distanceToTitan = Model.getProbes().get(0).getDistanceToTitan();

        radius[0] = (695_508);
        radius[1] = (2439);
        radius[2] = (6052);
        radius[3] = (6371);
        radius[4] = (1737);
        radius[5] = (3390);
        radius[6] = (69_911);
        radius[7] = (58_232);
        radius[8] = (2574);
        radius[9] = (24_622);
        radius[10] = (25_362);

        root = new Group();
        root.setTranslateX(WIDTH / 2);
        root.setTranslateY(HEIGHT / 2);
        solarCamera = new SolarCamera();
        initializeSpheres(root);

        paths = new Group();
        root.getChildren().add(paths);

        solarCamera.setTranslateZ(currentFocus.getTranslateZ() - 10_000_000);

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setCamera(solarCamera);

        zoom(stage);

        scene.setFill(Color.BLACK.brighter());

        rotateX = new Rotate(-15, Rotate.X_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        setUpMouseRotation(scene, rotateX, rotateZ);
        root.getTransforms().addAll(rotateX, rotateZ);
        calculation();

        stage.setTitle("Solar System");
        stage.setScene(scene);
        stage.show();
    }

    private void calculation() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        test += 50000;
                        Platform.runLater(() -> {
                            Model.getProbes().get(0).setCoordinates(new double[]{test, 0, 0});

                            updateSpheres();
                        });

                    }
                }, 0, 1);
    }


    private void initializeSpheres(Group group) {
        for (int i = 0; i < visualizedObjects.length; i++) {
            visualizedObjects[i] = new Sphere();
            group.getChildren().add(visualizedObjects[i]);
        }
        setFocus();
        setVisibleScale();

        updateSpheres();
        setTextures();
    }

    private void updateSpheres() {
        shift = new double[]{
                -planets[currentFocusIndex].getCoordinates()[0] / SCALE,
                -planets[currentFocusIndex].getCoordinates()[1] / SCALE,
                -planets[currentFocusIndex].getCoordinates()[2] / SCALE
        };

        for (int i = 0; i < visualizedObjects.length; i++) {
            visualizedObjects[i].setTranslateX(planets[i].getCoordinates()[0] / SCALE + shift[0]);
            visualizedObjects[i].setTranslateY(planets[i].getCoordinates()[1] / SCALE + shift[1]);
            visualizedObjects[i].setTranslateZ(planets[i].getCoordinates()[2] / SCALE + shift[2]);
        }
    }

    static class SolarCamera extends PerspectiveCamera {
        SolarCamera() {
            setNearClip(0.1);
            setFarClip(100_000_000_000_000D);
        }
    }

    private void zoom(Stage stage) {
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);
        });
    }

    private void setUpMouseRotation(Scene scene, Rotate rotateX, Rotate rotateZ) {
        final double[] anchorX = new double[1];
        final double[] anchorY = new double[1];

        final double[] anchorAngleX = new double[1];
        final double[] anchorAngleZ = new double[1];

        scene.setOnMousePressed(event -> {
            anchorX[0] = event.getSceneX();
            anchorY[0] = event.getSceneY();
            anchorAngleX[0] = rotateX.getAngle();
            anchorAngleZ[0] = rotateZ.getAngle();
        });

        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - anchorX[0];
            double deltaY = event.getSceneY() - anchorY[0];
            rotateX.setAngle(anchorAngleX[0] + deltaY * 0.15);
            if (anchorAngleX[0] > 90 && anchorAngleX[0] < 270)
                rotateZ.setAngle(anchorAngleZ[0] + deltaX * 0.15);
            else
                rotateZ.setAngle(anchorAngleZ[0] - deltaX * 0.15);
        });
    }

    public void setVisibleScale() {
        visualizedObjects[0].setRadius(radius[0] / SCALE * 50);
        visualizedObjects[1].setRadius(radius[1] * 25);
        visualizedObjects[2].setRadius(radius[2] * 10);
        visualizedObjects[3].setRadius(radius[3] * 20);
        visualizedObjects[4].setRadius(radius[4] * 20);
        visualizedObjects[5].setRadius(radius[5] * 20);
        visualizedObjects[6].setRadius(radius[6] * 10);
//        visualizedObjects[7].setRadius(radius[7] * 10);
        visualizedObjects[8].setRadius(radius[6] * 2);
        visualizedObjects[9].setRadius(radius[7] * 25);
        visualizedObjects[10].setRadius(radius[8] * 10);

        visualizedObjects[11].setRadius(visualizedObjects[3].getRadius());

        solarCamera.setTranslateZ(-currentFocus.getRadius() * 5);
    }

    private void setFocus() {
        currentFocusIndex = 11;
        currentFocus = visualizedObjects[11];

        updateSpheres();
    }

    private void setTextures() {
        String[] names = new String[]{"sun", "mercury", "venus", "earth", "moon", "mars", "jupiter", "saturn", "titan", "neptune", "uranus"};
        for (int i = 0; i < names.length; i++) {
            PhongMaterial texture = new PhongMaterial();
            texture.setDiffuseMap(new Image(Paths.get("src/main/resources/graphics/textures/" + names[i] + ".jpg").toUri().toString()));
            visualizedObjects[i].setMaterial(texture);
        }

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.PINK);
        material.setSpecularColor(Color.PINK);
        visualizedObjects[11].setMaterial(material);
    }

    public static void main(String[] args) {
        launch();
    }
}
