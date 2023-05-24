package code.graphics.visuals.objects;

import code.model.objects.PlanetObject;
import javafx.scene.shape.Sphere;

import static code.graphics.Visualizer.SCALE;

public class PlanetSphere extends Sphere {
    private final PlanetObject PLANET_OBJECT;
    private final double MIN_RADIUS;
    private double maxRadius;


    public PlanetSphere(PlanetObject planetObject) {
        super();
        PLANET_OBJECT = planetObject;
        MIN_RADIUS = PLANET_OBJECT.getRadius() / SCALE;
        setRadius(MIN_RADIUS);
        updateCoordinates();
    }


    /**
     * Updates the coordinates of the Sphere based on the associated PlanetObjects.
     */
    public void updateCoordinates() {
        setTranslateX(PLANET_OBJECT.getCoordinates()[0] / SCALE);
        setTranslateY(PLANET_OBJECT.getCoordinates()[1] / SCALE);
        setTranslateZ(PLANET_OBJECT.getCoordinates()[2] / SCALE);
    }


    // getters and setters

    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    public double getMinRadius() {
        return MIN_RADIUS;
    }

    public double getMaxRadius() {
        return maxRadius;
    }
}
