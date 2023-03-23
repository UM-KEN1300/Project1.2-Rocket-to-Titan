package group1.project12group1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class gpt extends Application {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int RADIUS = 30;
    private static final int MAX_X = SCREEN_WIDTH - RADIUS * 2;
    private static final int MAX_Y = SCREEN_HEIGHT - RADIUS * 2;

    private final Group root = new Group();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> addRandomSphere()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void addRandomSphere() {
        Random random = new Random();
        double x = random.nextInt(MAX_X) + RADIUS;
        double y = random.nextInt(MAX_Y) + RADIUS;
        double z = 0;
        Sphere sphere = new Sphere(RADIUS);
        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(z);
        root.getChildren().add(sphere);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
