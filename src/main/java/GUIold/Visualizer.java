package GUIold;
import  Algorithms.ModelRunner;
import Model.PlanetObject;
import Model.Probe;
import Model.SolarSystemModel;
import helperFunction.HelperFunctions;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.ArrayList;



public class Visualizer extends Application {

    private static final HelperFunctions helperFunctions = new HelperFunctions();
    private final double WIDTH= Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT= Screen.getPrimary().getBounds().getHeight();;
    public final double SCALE= 100;;
    SolarSystemModel solarSystemModel=new SolarSystemModel();
   ArrayList<PlanetObject> listWithProjectiles=solarSystemModel.getListOfPlanets();
    PlanetObject[] planets = solarSystemModel.getListOfPlanets().toArray(new PlanetObject[0]);
            //new PlanetObject[]{Sun, Mercury, Venus, Earth, Moon, Mars, Jupiter, Saturn, Titan, Neptune, Uranus, Projectile};
    Sphere[] visualizedObjects = new Sphere[12];
    private SolarCamera solarCamera;
    private Sphere currentFocus = visualizedObjects[11];
    Rotate rotateX, rotateZ;
    int currentFocusIndex;
    int projectilePathIndex = 0;
    Group root, paths;
    ArrayList<Sphere> projectilePath = new ArrayList<>();
    double[] shift;
    double timePassed;
    double distanceToTitan;



    @Override
    public void start(Stage stage) throws IOException {
        Probe probe= (Probe)planets[11];
        distanceToTitan = probe.getDistanceToTitan();
        planets[0].setRadius(695_508);
        planets[1].setRadius(2439);
        planets[2].setRadius(6052);
        planets[3].setRadius(6371);
        planets[4].setRadius(1737);
        planets[5].setRadius(3390);
        planets[6].setRadius(69_911);
        planets[7].setRadius(58_232);
        planets[8].setRadius(2574);
        planets[9].setRadius(24_622);
        planets[10].setRadius(25_362);

        root = new Group();
        root.setTranslateX(WIDTH / 2);
        root.setTranslateY(HEIGHT / 2);
        solarCamera = new SolarCamera();
        initializeSpheres(root);

        paths = new Group();
        root.getChildren().add(paths);

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
        Probe probe= (Probe)planets[11];
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        int step = 10 * 3600;
                        double calculationStep = 1;
                        ModelRunner.runnerForGUI(step,calculationStep,planets);

                        double currentDistance = probe.getDistanceToTitan();
                        timePassed += calculationStep * step;
                        if (currentDistance < distanceToTitan) {
                            distanceToTitan = currentDistance;

                            System.out.println("\nLowest distance: " + currentDistance + " km");
                            System.out.println("Recorded at " + timePassed + " seconds passed (" + (int) (timePassed / (60 * 60 * 24)) + " days)");
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
        setFocus(11);
        setVisibleScale();

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
        Platform.runLater(() -> {
            for (Sphere path : projectilePath) {
                paths.setTranslateX(path.getTranslateX() + shift[0]);
                paths.setTranslateX(path.getTranslateY() + shift[1]);
                paths.setTranslateX(path.getTranslateZ() + shift[2]);
            }
        });
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
//            if (solarCamera.getTranslateZ() > -1000) {
//                if (delta < 0)
//                    solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);
//            } else if (solarCamera.getTranslateZ() < -150_000_000) {
//                if (delta > 0)
//                    solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);
//            } else {
//                solarCamera.setTranslateZ(solarCamera.getTranslateZ() - delta * solarCamera.getTranslateZ() * 0.005);
//            }
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
                    setFocus(8);
                }
                case DIGIT4 -> {
                    setFocus(11);
                }
                case P -> {
                    setVisibleScale();
                }
                case O -> {
                    setRealScale();
                }
            }
        });
    }

    private void setRealScale() {
        visualizedObjects[0].setRadius(planets[0].getRadius() / SCALE);
        visualizedObjects[1].setRadius(planets[1].getRadius() / SCALE);
        visualizedObjects[2].setRadius(planets[2].getRadius() / SCALE);
        visualizedObjects[3].setRadius(planets[3].getRadius() / SCALE);
        visualizedObjects[4].setRadius(planets[4].getRadius() / SCALE);
        visualizedObjects[5].setRadius(planets[5].getRadius() / SCALE);
        visualizedObjects[6].setRadius(planets[6].getRadius() / SCALE);
        visualizedObjects[7].setRadius(planets[7].getRadius() / SCALE);
        visualizedObjects[8].setRadius(planets[8].getRadius() / SCALE);
        visualizedObjects[9].setRadius(planets[9].getRadius() / SCALE);
        visualizedObjects[10].setRadius(planets[10].getRadius() / SCALE);
        visualizedObjects[11].setRadius(visualizedObjects[3].getRadius() / 10);
        solarCamera.setTranslateZ(-currentFocus.getRadius() * 5);
    }

    public void setVisibleScale() {
        visualizedObjects[0].setRadius(planets[0].getRadius() / SCALE * 50);
        visualizedObjects[1].setRadius(planets[1].getRadius() * 25);
        visualizedObjects[2].setRadius(planets[2].getRadius() * 10);
        visualizedObjects[3].setRadius(planets[3].getRadius() * 10);
        visualizedObjects[5].setRadius(planets[5].getRadius() * 20);
        visualizedObjects[6].setRadius(planets[6].getRadius() * 3);
        visualizedObjects[7].setRadius(planets[7].getRadius() * 3);
        visualizedObjects[9].setRadius(planets[9].getRadius() * 10);
        visualizedObjects[10].setRadius(planets[10].getRadius() * 10);
        visualizedObjects[11].setRadius(visualizedObjects[3].getRadius() / 10);

        solarCamera.setTranslateZ(-currentFocus.getRadius() * 5);
    }

    private void setFocus(int index) {
        currentFocusIndex = index;
        currentFocus = visualizedObjects[index];

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
