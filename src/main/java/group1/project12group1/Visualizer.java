package group1.project12group1;

import helperFunction.HelperFunctions;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Hashtable;

import static group1.project12group1.SolarSystem.*;

public class Visualizer extends Application {
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public final double SCALE = 100;
    private HelperFunctions helperFunctions = new HelperFunctions();
    PlanetObject[] planets = new PlanetObject[]{Sun, Mercury, Venus, Earth, Moon, Mars, Jupiter, Saturn, Titan, Neptune, Uranus};
    private Sphere sunSphere;
    private Sphere mercurySphere;
    private Sphere venusSphere;
    private Sphere earthSphere;
    private Sphere moonSphere;
    private Sphere marsSphere;
    private Sphere jupiterSphere;
    private Sphere saturnSphere;
    private Sphere titanSphere;
    private Sphere neptuneSphere;
    private Sphere uranusSphere;
    private SolarCamera solarCamera;
    private Hashtable<PlanetObject, Sphere> planetDictionary;
    private Sphere currentFocus;
    Rotate rotateX, rotateZ;

    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();

        root.setTranslateX(WIDTH / 2);
        root.setTranslateY(HEIGHT / 2);

        solarCamera = new SolarCamera();

        initializeSpheres(root);

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setCamera(solarCamera);

        zoom2(stage);

        scene.setFill(Color.BLACK.brighter());

        rotateX = new Rotate(315, Rotate.X_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        setUpMouseRotation(scene, rotateX, rotateZ);
        root.getTransforms().addAll(rotateX, rotateZ);

        setUpKeyboardInput(scene);

        fullView();
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

                        int step = 10 * 30000;
                        for (int i = 0; i < step; i += 1) {

                            for (int j = 0; j < planets.length - 1; j++) {

                                double[] acc = new double[3];
                                for (int k = 0; k < planets.length; k++) {

                                    if (k != j) {
                                        if (j < k) {
                                            acc = HelperFunctions.addition(acc, planets[j].ForceCaluclatorNEW(planets[k]));
                                        } else {
                                            acc = HelperFunctions.addition(acc, planets[j].ForceCaluclatorNEWWithOld(planets[k]));
                                        }

                                    }

                                }
                                planets[j].setPrivousPosition(planets[j].getPositionalVector());
                                planets[j].updatePositionVelocity(acc, 0.1);
                            }
                        }
                        updateSpheres();

                    }
                },

                0, // Start immediately
                1 // Update every second
        );
    }

    private void initializeSpheres(Group group) {
        sunSphere = new Sphere(695_508 / SCALE);
        mercurySphere = new Sphere(2_439 / SCALE);
        venusSphere = new Sphere(6052 / SCALE);
        earthSphere = new Sphere(6_371 / SCALE);
        moonSphere = new Sphere(1_737 / SCALE);
        marsSphere = new Sphere(3_390 / SCALE);
        jupiterSphere = new Sphere(69_911 / SCALE);
        saturnSphere = new Sphere(58_232 / SCALE);
        titanSphere = new Sphere(2_574 / SCALE);
        neptuneSphere = new Sphere(24_622 / SCALE);
        uranusSphere = new Sphere(25_363 / SCALE);

        group.getChildren().add(sunSphere);
        group.getChildren().add(mercurySphere);
        group.getChildren().add(venusSphere);
        group.getChildren().add(earthSphere);
        group.getChildren().add(moonSphere);
        group.getChildren().add(marsSphere);
        group.getChildren().add(jupiterSphere);
        group.getChildren().add(saturnSphere);
        group.getChildren().add(titanSphere);
        group.getChildren().add(neptuneSphere);
        group.getChildren().add(uranusSphere);

        setFocus(sunSphere);
        updateSpheres();
        setTextures();
    }

    private void updateSpheres() {
        Sphere[] spheres = new Sphere[]{sunSphere, mercurySphere, venusSphere, earthSphere, moonSphere, marsSphere, jupiterSphere, saturnSphere, titanSphere, neptuneSphere, uranusSphere};
        for (int i = 0; i < spheres.length; i++) {
            spheres[i].setTranslateX(planets[i].getPositionalVector()[0] / SCALE);
            spheres[i].setTranslateY(planets[i].getPositionalVector()[1] / SCALE);
            spheres[i].setTranslateZ(planets[i].getPositionalVector()[2] / SCALE);
        }
    }

    static class SolarCamera extends PerspectiveCamera {
        SolarCamera() {
            setNearClip(0.1);
            setFarClip(100_000_000_000_000D);
        }
    }

    private void zoom2(Stage stage) {
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            if (solarCamera.getTranslateZ() > -5_000_000) {
                if (delta < 0)
                    solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);
            } else if (solarCamera.getTranslateZ() < -150_000_000) {
                if (delta > 0)
                    solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);
            } else {
                solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);
            }

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

    private void setUpKeyboardInput(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case DIGIT1 -> {
                    fullView();
                }
                case DIGIT2 -> {
                    setFocus(earthSphere);
                }
                case DIGIT3 -> {
                    setFocus(saturnSphere);
                }
            }
        });
    }

    private void fullView() {
        setFocus(sunSphere);
        solarCamera.setTranslateZ(-2_500_000_0);

        sunSphere.setRadius(sunSphere.getRadius() * 30);
        mercurySphere.setRadius(mercurySphere.getRadius() * SCALE * 40);
        venusSphere.setRadius(venusSphere.getRadius() * SCALE * 25);
        earthSphere.setRadius(earthSphere.getRadius() * SCALE * 35);
        marsSphere.setRadius(marsSphere.getRadius() * SCALE * 55);
        jupiterSphere.setRadius(jupiterSphere.getRadius() * SCALE * 10);
        saturnSphere.setRadius(saturnSphere.getRadius() * SCALE * 10);
        neptuneSphere.setRadius(neptuneSphere.getRadius() * SCALE * 35);
        uranusSphere.setRadius(uranusSphere.getRadius() * SCALE * 35);
    }

    private void setFocus(Sphere sphere) {
        currentFocus = sunSphere;

        solarCamera.setTranslateX(sphere.getTranslateX() / SCALE);
        solarCamera.setTranslateY(sphere.getTranslateY() / SCALE);
        solarCamera.setTranslateZ(sphere.getTranslateZ() / SCALE - currentFocus.getRadius() * 5);
    }

    private void setTextures() {
        String[] names = new String[]{"sun", "mercury", "venus", "earth", "moon", "mars", "jupiter", "saturn", "titan", "neptune", "uranus"};
        Sphere[] spheres = new Sphere[]{sunSphere, mercurySphere, venusSphere, earthSphere, moonSphere, marsSphere, jupiterSphere, saturnSphere, titanSphere, neptuneSphere, uranusSphere};
        for (int i = 0; i < names.length; i++) {
            PhongMaterial texture = new PhongMaterial();
            texture.setDiffuseMap(new Image(Paths.get("src/main/resources/" + names[i] + ".jpg").toUri().toString()));
            spheres[i].setMaterial(texture);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
