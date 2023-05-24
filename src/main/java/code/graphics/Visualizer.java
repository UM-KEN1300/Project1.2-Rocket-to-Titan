package code.graphics;

import code.algorithms.ModelRunner;
import code.graphics.visuals.SolarSubScene;
import code.graphics.visuals.controllers.SolarKeyController;
import code.graphics.visuals.controllers.SolarMouseController;
import code.model.Model;
import code.model.objects.PlanetObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Visualizer extends Application {
    public static final int SCALE = 50;
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    private final Group GROUP = new Group();
    SolarSubScene solarSubScene;
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


    @Override
    public void start(Stage stage) {
        stage.setTitle("Mission to Titan");
        stage.show();

        Scene scene = new Scene(GROUP, WIDTH, HEIGHT, true);

        solarSubScene = new SolarSubScene(new Group(), WIDTH, HEIGHT);
        new SolarMouseController(solarSubScene, scene);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new SolarKeyController(solarSubScene));
        GROUP.getChildren().add(solarSubScene);

        stage.setScene(scene);

        calculation();
    }

    private void calculation() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            ModelRunner.runnerForGUI(180, 1, planets);
                            Platform.runLater(() -> {
                                solarSubScene.updateAndRescale();
                            });
                        }
                    }
                }, 0, 1);
    }

    public static void main(String[] args) {
        launch();
    }
}
