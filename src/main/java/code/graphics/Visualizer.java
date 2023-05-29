package code.graphics;

import code.algorithms.ModelRunner;
import code.graphics.overlay.OverlayPane;
import code.graphics.visuals.SolarSubScene;
import code.graphics.visuals.controllers.SolarKeyController;
import code.graphics.visuals.controllers.SolarMouseController;
import code.graphics.visuals.controllers.SolarScrollController;
import code.model.Model;
import code.model.objects.PlanetObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Timer;

public class Visualizer extends Application {
    public static final int SCALE = 50; // don't change this
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    SolarSubScene solarSubScene;
    OverlayPane overlayPane;
    private final PlanetObject[] planets = new PlanetObject[]{
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
            Model.getProbes().get(0)
    };
    private Timer timer;
    private int count;


    @Override
    public void start(Stage stage) {
        stage.setTitle("Mission to Titan");
        stage.show();

        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane, WIDTH, HEIGHT, true);

        solarSubScene = new SolarSubScene(new Group(), WIDTH, HEIGHT);
        new SolarMouseController(solarSubScene, scene);
        new SolarScrollController(scene, solarSubScene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new SolarKeyController(solarSubScene));
        stackPane.getChildren().add(solarSubScene);

        overlayPane = new OverlayPane(Model.getProbes().get(0));
        stackPane.getChildren().add(overlayPane);

        stage.setScene(scene);

        timer = new Timer();
        count = 0;
        stage.setOnCloseRequest(e -> {
            timer.cancel();
            Platform.exit();
            System.exit(0);
        });
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> calculation());
        new Thread(sleeper).start();
    }


    private void calculation() {
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            ModelRunner.runnerForGUI(180, 1, planets);
                            Platform.runLater(() -> {
                                solarSubScene.updateObjects();
                                overlayPane.update();
                            });
                        }

                        count++;
                        if (count % 5 == 0)
                            Platform.runLater(() -> solarSubScene.addTrail());
                    }
                }, 0, 1);
    }

    public static void main(String[] args) {
        launch();
    }
}
