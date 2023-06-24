package code.graphics;

import code.graphics.overlay.OverlayPane;
import code.graphics.visuals.SolarSubScene;
import code.graphics.visuals.controllers.SolarKeyController;
import code.graphics.visuals.controllers.SolarMouseController;
import code.graphics.visuals.controllers.SolarScrollController;
import code.utils.Time;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class SolarStackPane extends StackPane {
    private final SolarSubScene SOLAR_SUB_SCENE;
    private final OverlayPane OVERLAY_PANE;


    public SolarStackPane(double width, double height) {
        super();
        SOLAR_SUB_SCENE = new SolarSubScene(new Group(), width, height);
        OVERLAY_PANE = new OverlayPane();
        getChildren().add(SOLAR_SUB_SCENE);
        getChildren().add(OVERLAY_PANE);
    }


    public void update(Time time) {
        SOLAR_SUB_SCENE.update();
        OVERLAY_PANE.update(time);
    }

    public void addTrail() {
        SOLAR_SUB_SCENE.addTrail();
    }

    public void addFilters(Scene scene) {
        new SolarMouseController(scene, SOLAR_SUB_SCENE);
        new SolarScrollController(scene, SOLAR_SUB_SCENE);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new SolarKeyController(SOLAR_SUB_SCENE));
    }
}
