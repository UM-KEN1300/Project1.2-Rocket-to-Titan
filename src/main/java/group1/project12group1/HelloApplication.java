package group1.project12group1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
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
            {1254501625, -761340299},   //titan
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
        Sphere earth = initializeSphere(1);
        Sphere moon = initializeSphere(2);
        Sphere mars = initializeSphere(3);
        Sphere jupiter = initializeSphere(4);
        Sphere saturn = initializeSphere(5);
        Sphere titan = initializeSphere(6);


        Group root = new Group();
        root.getChildren().add(sun);
        root.getChildren().add(earth);
        root.getChildren().add(moon);
        root.getChildren().add(mars);
        root.getChildren().add(jupiter);
        root.getChildren().add(saturn);
        root.getChildren().add(titan);

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

    public static void main(String[] args) {
        launch();
    }
}
