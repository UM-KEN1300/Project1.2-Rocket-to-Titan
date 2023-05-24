package code.graphics.visuals;

import javafx.scene.PerspectiveCamera;

public class SolarCamera extends PerspectiveCamera {
    private double minZoom;
    private final double MAX_ZOOM;


    public SolarCamera(){
        super();
        setNearClip(0.1);
        setFarClip(1_000_000_000);

        setMinZoom(-500);
        MAX_ZOOM = -1_000_000_00d;

        setTranslateZ(-1_000);
    }


    public void setMinZoom(double minZoom) {
        this.minZoom = minZoom;
    }

    public double getMinZoom() {
        return minZoom;
    }

    public double getMaxZoom() {
        return MAX_ZOOM;
    }
}
