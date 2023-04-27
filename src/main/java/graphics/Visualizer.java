package graphics;

import com.graphics.solar_system.SolarSubScene;
import com.graphics.solar_system.controllers.SolarMouseController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Sphere;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class Visualizer extends Application {
    private final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    private final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    private final Group GROUP = new Group();
    Map<String, Sphere> c;
    double translate = 0;
    int count = 0;
    SolarSubScene solarSubScene;


    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(GROUP, WIDTH, HEIGHT, true);

        solarSubScene = new SolarSubScene(WIDTH, HEIGHT);
        new SolarMouseController(solarSubScene, scene);
        GROUP.getChildren().add(solarSubScene);

        Label label = new Label("Days since launch: ");
        label.setStyle("-fx-font-size: 24; -fx-text-fill: white;");
        label.setLayoutX(10);
        label.setLayoutY(10);
        GROUP.getChildren().add(label);

        stage.setTitle("Mission to Titan");
        stage.setScene(scene);
        stage.show();

         c = solarSubScene.getCelestialBodyViews();
        updateThread.setDaemon(true);
        updateThread.start();
    }

    Thread updateThread = new Thread(() -> {
        while (true) {
            c.get("Sun").setTranslateX(translate += 100);
            count++;
            if (count % 5 == 0)
                solarSubScene.updateTrails();
            Platform.runLater(() -> {
                solarSubScene.updateTrails();
            });

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) {
        launch();
    }
}
