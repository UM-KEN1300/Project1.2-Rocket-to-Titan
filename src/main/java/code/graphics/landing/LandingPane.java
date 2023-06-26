package code.graphics.landing;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class LandingPane extends Pane {
    double shift = 40;
    private ImageView spaceship;


    public LandingPane(double width, double height) {
        super();
        setPrefWidth(width);
        setPrefHeight(height);
        setStyle("-fx-background-color: black;");

        Line yAxis = new Line(width / 2, 0, width / 2, height);
        yAxis.setStroke(Color.WHITE);
        yAxis.setStrokeWidth(4);
        getChildren().add(yAxis);

        for (int i = 1; i <= 6; i++) {
            Line heightLine = new Line(0, i * 130 - shift, width, i * 130 - shift);
            heightLine.setStroke(Color.WHITE);
            heightLine.setStrokeWidth(0.5);
            getChildren().add(heightLine);

            Label heightLabel = new Label(7 - i + "00 km");
            heightLabel.setLayoutX(width / 2 + 10);
            heightLabel.setLayoutY(heightLine.getStartY());
            heightLabel.setTextFill(Color.WHITE);
            getChildren().add(heightLabel);
        }

        Rectangle titanSurface = new Rectangle(0, 7 * 130 - shift, width, 1000);
        titanSurface.setFill(Color.rgb(191, 167, 101));
        getChildren().add(titanSurface);

        Image spaceshipIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/textures/spaceship.png")));
        spaceship = new ImageView(spaceshipIcon);
        spaceship.setFitWidth(100);
        spaceship.setFitHeight(100);
        getChildren().add(spaceship);
        moveSpaceship(0, 0, 90);
    }


    public void moveSpaceship(double x, double y, double rotation) {
        double proportion = (x + 1_000_000) / (1_000_000 + 1_000_000);
        spaceship.setTranslateX(proportion * (1605 - 5) + 5);
        spaceship.setTranslateY(((600_000 - y) / 600_000) * 780);
        spaceship.setRotate(rotation-45);
    }
}
