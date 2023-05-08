package code.model.objects;

import code.model.Model;
import code.utils.HelperFunctions;

public class Probe extends PlanetObject {
    private double shortestDistanceToTitan;


    public Probe(double[] VELOCITY) {
        super(new double[]{0, 0, 0}, VELOCITY, 50_000);
        setCoordinates(initialPosition());
        shortestDistanceToTitan = getDistanceToTitan();
    }


    private double[] initialPosition() {
        double[] directionalVector = HelperFunctions.subtract(Model.getPlanetObjects().get("Earth").getCoordinates(),
                Model.getPlanetObjects().get("Titan").getCoordinates());

        double magnitude = HelperFunctions.getVectorMagnitude(directionalVector);
        for (int i = 0; i <= 2; i++)
            directionalVector[i] = directionalVector[i] * 6370 / magnitude;

        return directionalVector;
    }

    public double getDistanceToTitan() {
        return HelperFunctions.getDistanceBetween(this, Model.getPlanetObjects().get("Titan"));
    }

    private void checkDistance() {
        if (getDistanceToTitan() < shortestDistanceToTitan)
            shortestDistanceToTitan = getDistanceToTitan();
    }

    public double getShortestDistanceToTitan() {
        return shortestDistanceToTitan;
    }
}
