package code.model.objects;

/**
 * Defines basic functionality for celestial bodies and spaceships.
 */
public interface ModeledObject {



    double[] getCoordinates();


    void setCoordinates(double[] coordinates);


    double[] getVelocity();


    void setVelocity(double[] velocity);


    double getMass();


    double[] accelerationBetween(PlanetObject planetObject);
}
