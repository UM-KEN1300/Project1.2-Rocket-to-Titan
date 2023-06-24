package code.graphics;

import code.utils.Time;
import javafx.scene.Scene;

public class SolarScene extends Scene {
    private final SolarStackPane SOLAR_STACK_PANE;


    public SolarScene(double width, double height) {
        super(new SolarStackPane(width, height), width, height, true);
        SOLAR_STACK_PANE = (SolarStackPane) getRoot();
        SOLAR_STACK_PANE.addFilters(this);
    }


    public void update(Time time) {
        SOLAR_STACK_PANE.update(time);
    }

    public void addTrail(){
        SOLAR_STACK_PANE.addTrail();
    }
}
