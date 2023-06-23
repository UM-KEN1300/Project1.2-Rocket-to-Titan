package code.algorithms.solvers;

import code.model.Model;
import code.model.objects.PlanetObject;

import java.util.List;

public class AccelerationFunction {
    private final double G = 6.67430e-11;

//    public double[] calculate(int index) {
//        List<PlanetObject> allObjects = Model.getAllObjects();
//        PlanetObject target = allObjects.get(index);
//        double[] acceleration = new double[3];
//
//        for (PlanetObject other : allObjects) {
//            if (target == other || !other.affectsOthers()) continue;
//
//            double[] difference = HelperFunctions.subtract(target.getCoordinates(), other.getCoordinates());
//            double distanceCubed = Math.pow(HelperFunctions.getVectorMagnitude(difference), 3);
//
//            double scale = G * other.getMass() / distanceCubed;
//            for (int j = 0; j < 3; j++)
//                acceleration[j] -= scale * difference[j];
//        }
//
//        return acceleration;
//    }

    public double[] calculate(int index) {
        List<PlanetObject> allObjects = Model.getAllObjects();
        double[] acceleration = new double[]{0, 0, 0};
        PlanetObject evaluatedObject = allObjects.get(index);

        for (PlanetObject other : allObjects) {
            if (evaluatedObject == other || !other.affectsOthers()) continue;
            for (int i = 0; i < 3; i++)
                acceleration[i] += evaluatedObject.accelerationBetween(other)[i];
        }

        return acceleration;
    }
}
