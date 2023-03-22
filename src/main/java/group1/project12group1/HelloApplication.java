package group1.project12group1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

import static group1.project12group1.SolarSystem.*;

public class HelloApplication extends Application {
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public final double SCALE = 100;
    PlanetObject[] planets = new PlanetObject[]{Sun, Mercury, Venus, Earth, Moon, Mars, Jupiter, Saturn, Titan};
    private Sphere sunSphere;
    private Sphere mercurySphere;
    private Sphere venusSphere;
    private Sphere earthSphere;
    private Sphere moonSphere;
    private Sphere marsSphere;
    private Sphere jupiterSphere;
    private Sphere saturnSphere;
    private Sphere titanSphere;
    private SolarCamera solarCamera;
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

//        zoom(stage);

        rotateX = new Rotate(-45, Rotate.X_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        setUpMouseRotation(scene, rotateX, rotateZ);
        root.getTransforms().addAll(rotateX, rotateZ);

        setUpKeyboardInput(scene);

        stage.setTitle("Solar System");
        stage.setScene(scene);
        stage.show();

        fullView();
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

        group.getChildren().add(sunSphere);
        group.getChildren().add(mercurySphere);
        group.getChildren().add(venusSphere);
        group.getChildren().add(earthSphere);
        group.getChildren().add(moonSphere);
        group.getChildren().add(marsSphere);
        group.getChildren().add(jupiterSphere);
        group.getChildren().add(saturnSphere);
        group.getChildren().add(titanSphere);

        setFocus(Sun);
        updateSpheres();
        setTextures();
    }

    private void updateSpheres() {
        Sphere[] spheres = new Sphere[]{sunSphere, mercurySphere, venusSphere, earthSphere, moonSphere, marsSphere, jupiterSphere, saturnSphere, titanSphere};
        for (int i = 0; i < spheres.length; i++) {
            spheres[i].setTranslateX(planets[i].getX() / SCALE);
            spheres[i].setTranslateY(planets[i].getY() / SCALE);
            spheres[i].setTranslateZ(planets[i].getZ() / SCALE);
        }

        solarCamera.setTranslateX(currentFocus.getTranslateX() / SCALE);
        solarCamera.setTranslateY(currentFocus.getTranslateY() / SCALE);
        solarCamera.setTranslateZ(currentFocus.getTranslateZ() / SCALE - currentFocus.getRadius() * 5);
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
            if (solarCamera.getTranslateZ() < currentFocus.getTranslateZ() - currentFocus.getRadius() * 5 || delta < 0)
                solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);

//            if (solarCamera.getTranslateZ() == -sunSphere.getRadius() * 5)
//                recalculateRadius();
        });
    }

//    private void recalculateRadius() {
//        double j = sunSphere.getRadius() / 1.1;
//        if (solarCamera.getTranslateZ() > -sunSphere.getRadius() * 30) {
//            sunSphere.setRadius(j);
//            mercurySphere.setRadius(j);
//            venusSphere.setRadius(j);
//            earthSphere.setRadius(j);
//            marsSphere.setRadius(j);
//            jupiterSphere.setRadius(j);
//            saturnSphere.setRadius(j);
//        } else {
//            double i = sunSphere.getRadius() * 1.1;
//            if (solarCamera.getTranslateZ() < -sunSphere.getRadius() * 50) {
//                sunSphere.setRadius(i);
//                mercurySphere.setRadius(i);
//                venusSphere.setRadius(i);
//                earthSphere.setRadius(i);
//                marsSphere.setRadius(i);
//                jupiterSphere.setRadius(i);
//                saturnSphere.setRadius(i);
//            }
//        }
//    }

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
            rotateZ.setAngle(anchorAngleZ[0] - deltaX * 0.15);
        });
    }

    private void setUpKeyboardInput(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case Z:
                    fullView();
            }
        });
    }

    private void fullView() {
        solarCamera.setTranslateZ(-2_500_000_0);

        sunSphere.setRadius(sunSphere.getRadius() * 30);
        mercurySphere.setRadius(mercurySphere.getRadius() * SCALE * 40);
        venusSphere.setRadius(venusSphere.getRadius() * SCALE * 30);
        earthSphere.setRadius(earthSphere.getRadius() * SCALE * 35);
        marsSphere.setRadius(marsSphere.getRadius() * SCALE * 45);
        jupiterSphere.setRadius(jupiterSphere.getRadius() * SCALE * 10);
        saturnSphere.setRadius(saturnSphere.getRadius() * SCALE * 10);
    }

    private void setFocus(PlanetObject planet) {
        currentFocus = sunSphere;

        solarCamera.setTranslateX(planet.getX() / SCALE);
        solarCamera.setTranslateY(planet.getY() / SCALE);
        solarCamera.setTranslateZ(planet.getZ() / SCALE - currentFocus.getRadius() * 5);
    }

    private void setTextures() {
        String[] names = new String[]{"sun", "mercury", "venus", "earth", "moon", "mars", "jupiter", "saturn", "titan"};
        Sphere[] spheres = new Sphere[]{sunSphere, mercurySphere, venusSphere, earthSphere, moonSphere, marsSphere, jupiterSphere, saturnSphere, titanSphere};
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
