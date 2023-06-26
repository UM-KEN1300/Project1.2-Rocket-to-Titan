package code.model.objects;

public class LandingProbe {
    private double[] coordinates;
    private double xVelocity, yVelocity;
    private double rotation;
    private double rotationVelocity;


    public LandingProbe(double x, double y) {
        coordinates = new double[]{x, y};
        xVelocity = 0;
        yVelocity = 0;
        rotation = 0;
        rotationVelocity = 0;
    }



}
