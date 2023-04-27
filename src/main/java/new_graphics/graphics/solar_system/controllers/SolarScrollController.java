package new_graphics.graphics.solar_system.controllers;

import new_graphics.graphics.solar_system.SolarSubScene;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.ScrollEvent;

public class SolarScrollController {
    private final PerspectiveCamera camera;

    public SolarScrollController(SolarSubScene solarSubScene) {
        this.camera = (PerspectiveCamera) solarSubScene.getCamera();
        addScrollHandler(solarSubScene);
    }

    private void addScrollHandler(SolarSubScene solarSubScene) {
        solarSubScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();

            camera.setTranslateZ(camera.getTranslateZ() - deltaY * camera.getTranslateZ() * 0.001);
            solarSubScene.updateSizes();
        });
    }
}
