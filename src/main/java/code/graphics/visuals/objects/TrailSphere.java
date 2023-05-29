package code.graphics.visuals.objects;

import javafx.scene.shape.Sphere;

public class TrailSphere extends Sphere {
    private double minRadius;
    private double maxRadius;


    public TrailSphere(double probeMinRadius, double probeMaxRadius) {
        minRadius = probeMinRadius / 2;
        maxRadius = probeMaxRadius /3;
    }


    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }
}
