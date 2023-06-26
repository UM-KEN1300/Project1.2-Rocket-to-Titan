package code.graphics.landing;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class LandingScene extends Scene {
    private LandingPane landingPane;


    public LandingScene(double width, double height) {
        super(new LandingPane(width, height), width, height);
        landingPane = (LandingPane) getRoot();
        setFill(Color.BLACK.brighter());
    }


    public void moveSpaceship(double x, double y, double rotation) {
        landingPane.moveSpaceship(x, y, rotation);
    }
}
