package code.graphics.visuals.objects;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class TrailSphere extends Sphere {
    private double minRadius;
    private double maxRadius;


    public TrailSphere(double probeMinRadius, double probeMaxRadius) {
        setVisible(false);
        minRadius = probeMinRadius / 2;
        maxRadius = probeMaxRadius / 3;
        setMaterial(new PhongMaterial(Color.rgb(182, 255, 46)));
    }


    public double getMinRadius() {
        return minRadius;
    }

    public double getMaxRadius() {
        return maxRadius;
    }
}
