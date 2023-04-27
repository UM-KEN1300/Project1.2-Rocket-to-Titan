package new_graphics.model_from_file.objects;

/**
 * Class whose objects store information about celestial bodies represented in the new_graphics.model_from_file.
 */
public class CelestialBody {
    private final double[] COORDINATES;
    private final double[] VELOCITY;
    private final long MASS;


    public CelestialBody(double[] coordinates, double[] velocity, long mass) {
        COORDINATES = coordinates;
        VELOCITY = velocity;
        MASS = mass;
    }


    // coordinates
    public double getX() {
        return COORDINATES[0];
    }

    public void setX(double x) {
        COORDINATES[0] = x;
    }

    public double getY() {
        return COORDINATES[1];
    }

    public void setY(double y) {
        COORDINATES[1] = y;
    }

    public double getZ() {
        return COORDINATES[2];
    }

    public void setZ(double z) {
        COORDINATES[2] = z;
    }


    // velocity
    public double getVelocityX() {
        return VELOCITY[0];
    }

    public void setVelocityX(double x) {
        VELOCITY[0] = x;
    }

    public double getVelocityY() {
        return VELOCITY[1];
    }

    public void setVelocityY(double y) {
        VELOCITY[1] = y;
    }

    public double getVelocityZ() {
        return VELOCITY[2];
    }

    public void setVelocityZ(double z) {
        VELOCITY[2] = z;
    }


    // mass
    public long getMass() {
        return MASS;
    }
}
