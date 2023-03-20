package group1.project12group1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    final double WIDTH = Screen.getPrimary().getBounds().getWidth();
    final double HEIGHT = Screen.getPrimary().getBounds().getHeight();
    final int SCALE = 2_000_000;

    int[][] coordinates = new int[][]{
            {0, 0},                     //sun
            {-148186907, -27823159},    //earth
            {-148458048, -27524868},    //moon
            {-159116303, 189235672},    //mars
            {692722876, 258560761},     //jupiter
            {1253801724, -760453008},   //saturn
            {1254501625, -761340299}   //titan
    };

    String[] names = new String[]{
            "sun",
            "earth",
            "moon",
            "mars",
            "jupiter",
            "saturn",
            "titan"
    };

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(HEIGHT);

        Sphere sun = initializeSphere(0);
        sun.setRadius(30);
        Sphere earth = initializeSphere(1);
        Sphere moon = initializeSphere(2);
        moon.setRadius(10);
        Sphere mars = initializeSphere(3);
        Sphere jupiter = initializeSphere(4);
        Sphere saturn = initializeSphere(5);
        Sphere titan = initializeSphere(6);
        titan.setRadius(10);

        Label sunLabel = new Label("Sun");
        sunLabel.setTranslateX(sun.getTranslateX() - sun.getRadius() / 2);
        sunLabel.setTranslateY(sun.getTranslateY() + 40);
        sunLabel.setFont(new Font(20));

        Group root = new Group();
        root.getChildren().add(sun);
        root.getChildren().add(earth);
        root.getChildren().add(moon);
        root.getChildren().add(mars);
        root.getChildren().add(jupiter);
        root.getChildren().add(saturn);
        root.getChildren().add(titan);

        root.getChildren().add(sunLabel);

        drawPath(root, new int[][]{{-148186907 / SCALE, -27823159 / SCALE}, {1254501625 / SCALE, -761340299 / SCALE}});

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Visualization");
        stage.setScene(scene);
        stage.show();
    }

    private Sphere initializeSphere(int index) {
        Sphere sphere = new Sphere(20);
        sphere.setTranslateX(WIDTH / 2 + (float) coordinates[index][0] / SCALE - sphere.getRadius() / 2);
        sphere.setTranslateY(HEIGHT / 2 + (float) coordinates[index][1] / SCALE - sphere.getRadius() / 2);

        PhongMaterial texture = new PhongMaterial();
        texture.setDiffuseMap(new Image(Paths.get("src/main/resources/" + names[index] + ".jpg").toUri().toString()));

        sphere.setMaterial(texture);

        return sphere;
    }

    private void drawLine(Group group, int x1, int y1, int x2, int y2) {
        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        group.getChildren().add(line);
    }

    private void drawPath(Group group, int[][] coordinates) {
        for (int i = 0; i < coordinates.length - 1; i++)
            drawLine(group, coordinates[i][0], coordinates[i][1], coordinates[i + 1][0], coordinates[i + 1][1]);
    }

    public static void main(String[] args) {
        launch();
    }
}
