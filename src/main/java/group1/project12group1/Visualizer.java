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
    private static HelperFunctions helperFunctions = new HelperFunctions();


    //Select which one you want to use by changing the name planets
    //Api version
    PlanetObject[] planetss = helperFunctions.testing();
    //Solar System project

    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public final double SCALE = 100;
    PlanetObject[] planets = new PlanetObject[]{Sun, Mercury, Venus, Earth, Moon, Mars, Jupiter, Saturn, Titan, Neptune, Uranus, Projectile};
    Sphere[] visualizedObjects = new Sphere[12];
    private SolarCamera solarCamera;
    private Sphere currentFocus = visualizedObjects[0];
    private double[] shift = new double[]{0, 0, 0};
    private double[][] originalCoordinates = new double[12][3];
    Rotate rotateX, rotateZ;
    int currentFocusIndex;

    @Override
    public void start(Stage stage) throws IOException {
        for (int i = 0; i < planets.length; i++)
            originalCoordinates[i] = new double[]{planets[i].getX() / SCALE, planets[i].getY() / SCALE, planets[i].getZ() / SCALE};

        Sun.setRadius(695_508);
        Mercury.setRadius(2439);
        Venus.setRadius(6052);
        Earth.setRadius(6371);
        Moon.setRadius(1737);
        Mars.setRadius(3390);
        Jupiter.setRadius(69_911);
        Saturn.setRadius(58_232);
        Titan.setRadius(2574);
        Neptune.setRadius(24_622);
        Uranus.setRadius(25_362);


        Group root = new Group();
        root.setTranslateX(WIDTH / 2);
        root.setTranslateY(HEIGHT / 2);
        solarCamera = new SolarCamera();
        initializeSpheres(root);

        solarCamera.setTranslateZ(currentFocus.getTranslateZ() - currentFocus.getRadius() * 5);

        Scene scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setCamera(solarCamera);

        zoom(stage);

        scene.setFill(Color.BLACK.brighter());

        rotateX = new Rotate(0, Rotate.X_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        setUpMouseRotation(scene, rotateX, rotateZ);
        root.getTransforms().addAll(rotateX, rotateZ);
        setUpKeyboardInput(scene);

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
                        int step = 10 * 6000;
                        for (int i = 0; i < step; i += 1) {

                            for (int j = 1; j < planets.length - 1; j++) {

                                double[] acc = new double[3];
                                for (int k = 0; k < planets.length ; k++) {

                                    if (k != j) {
                                        acc = HelperFunctions.addition(acc, planets[j].accelerationBetween(planets[k]));
                                    }
                                }
                                planets[j].updatePosition(acc, 0.1);
                            }
                        }
                        updateSpheres();
                    }
                }, 0, 1);
    }

    private void initializeSpheres(Group group) {
        for (int i = 0; i < visualizedObjects.length; i++) {
            visualizedObjects[i] = new Sphere();
            group.getChildren().add(visualizedObjects[i]);
        }

        setVisibleScale();
        currentFocus = visualizedObjects[0];
        setFocus(0);
        updateSpheres();
        setTextures();
    }

    private void updateSpheres() {
        shift = new double[]{
                -planets[currentFocusIndex].getPositionalVector()[0] / SCALE,
                -planets[currentFocusIndex].getPositionalVector()[1] / SCALE,
                -planets[currentFocusIndex].getPositionalVector()[2] / SCALE
        };

        for (int i = 0; i < visualizedObjects.length; i++) {
            visualizedObjects[i].setTranslateX(planets[i].getPositionalVector()[0] / SCALE + shift[0]);
            visualizedObjects[i].setTranslateY(planets[i].getPositionalVector()[1] / SCALE + shift[1]);
            visualizedObjects[i].setTranslateZ(planets[i].getPositionalVector()[2] / SCALE + shift[2]);
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
            if (solarCamera.getTranslateZ() > -500_000) {
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
                    setFocus(0);
                }
                case DIGIT2 -> {
                    setFocus(3);
                }
                case DIGIT3 -> {
                    setFocus(7);
                }
                case DIGIT4 -> {
                    setFocus(11);
                }
            }
        });
    }

    private void setRealScale() {
        visualizedObjects[0].setRadius(Sun.getRadius() / SCALE);
        visualizedObjects[1].setRadius(Mercury.getRadius() / SCALE);
        visualizedObjects[2].setRadius(Venus.getRadius() / SCALE);
        visualizedObjects[3].setRadius(Earth.getRadius() / SCALE);
        visualizedObjects[4].setRadius(Moon.getRadius() / SCALE);
        visualizedObjects[5].setRadius(Mars.getRadius() / SCALE);
        visualizedObjects[6].setRadius(Jupiter.getRadius() / SCALE);
        visualizedObjects[7].setRadius(Saturn.getRadius() / SCALE);
        visualizedObjects[8].setRadius(Titan.getRadius() / SCALE);
        visualizedObjects[9].setRadius(Neptune.getRadius() / SCALE);
        visualizedObjects[10].setRadius(Uranus.getRadius() / SCALE);

        visualizedObjects[11].setRadius(visualizedObjects[3].getRadius() / 10);
    }

    public void setVisibleScale() {
        visualizedObjects[0].setRadius(Sun.getRadius() / SCALE * 50);
        visualizedObjects[1].setRadius(Mercury.getRadius() * 25);
        visualizedObjects[2].setRadius(Venus.getRadius() * 10);
        visualizedObjects[3].setRadius(Earth.getRadius() * 10);
        visualizedObjects[5].setRadius(Mars.getRadius() * 20);
        visualizedObjects[6].setRadius(Jupiter.getRadius() * 3);
        visualizedObjects[7].setRadius(Saturn.getRadius() * 3);
        visualizedObjects[9].setRadius(Neptune.getRadius() * 10);
        visualizedObjects[10].setRadius(Uranus.getRadius() * 10);

        visualizedObjects[11].setRadius(visualizedObjects[3].getRadius() / 10);
    }

    private void setFocus(int index) {

        currentFocusIndex = index;
        updateSpheres();
    }

    private void setTextures() {
        String[] names = new String[]{"sun", "mercury", "venus", "earth", "moon", "mars", "jupiter", "saturn", "titan", "neptune", "uranus"};
        for (int i = 0; i < names.length; i++) {
            PhongMaterial texture = new PhongMaterial();
            texture.setDiffuseMap(new Image(Paths.get("src/main/resources/" + names[i] + ".jpg").toUri().toString()));
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
