package code.model.objects;

import code.model.Model;
import code.utils.HelperFunctions;

public class Probe extends PlanetObject {
    private double shortestDistanceToTitan;


    public Probe(double[] velocity) {
        super(new double[3], velocity);
        setCoordinates(initialPosition());
        setMass(50_000);
        setRadius(Model.getPlanetObjects().get("Earth").getRadius() / 3);
        shortestDistanceToTitan = getDistanceToTitan();
    }


    @Override
    public void setCoordinates(double[] coordinates) {
        super.setCoordinates(coordinates);

        double distanceToTitan = getDistanceToTitan();
        if (distanceToTitan < shortestDistanceToTitan)
            shortestDistanceToTitan = distanceToTitan;
    }

    private double[] initialPosition() {
        double[] coordinates = HelperFunctions.subtract(Model.getPlanetObjects().get("Earth").getCoordinates(),
                Model.getPlanetObjects().get("Titan").getCoordinates());

        double magnitude = HelperFunctions.getVectorMagnitude(coordinates);
        for (int i = 0; i <= 2; i++)
            coordinates[i] = coordinates[i] * 6370 / magnitude;

        return HelperFunctions.addition(coordinates, Model.getPlanetObjects().get("Earth").getCoordinates());
    }

    public double getDistanceToTitan() {
        return HelperFunctions.getDistanceBetween(this, Model.getPlanetObjects().get("Titan"));
    }

    public double getShortestDistanceToTitan() {
        return shortestDistanceToTitan;
    }
}
