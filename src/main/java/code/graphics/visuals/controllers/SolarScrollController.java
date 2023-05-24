package code.graphics.visuals.controllers;

import code.graphics.visuals.SolarCamera;
import code.graphics.visuals.SolarSubScene;
import javafx.scene.input.ScrollEvent;

public class SolarScrollController {
    public SolarScrollController(SolarSubScene solarSubScene) {
        SolarCamera camera = (SolarCamera) solarSubScene.getCamera();
        addScrollHandler(solarSubScene, camera);
    }


    private void addScrollHandler(SolarSubScene solarSubScene, SolarCamera camera) {
        solarSubScene.addEventHandler(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY();

            double zoomStep = 0.01;
            if (camera.getTranslateZ() - deltaY * camera.getTranslateZ() * zoomStep > camera.getMinZoom())
                camera.setTranslateZ(camera.getMinZoom());
            else
                camera.setTranslateZ(Math.max(camera.getTranslateZ() - deltaY * camera.getTranslateZ() * zoomStep, camera.getMaxZoom()));

            solarSubScene.rescaleObjects();
        });
    }
}
