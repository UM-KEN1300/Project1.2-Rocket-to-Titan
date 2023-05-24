package code.algorithms;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;

import java.util.ArrayList;

public class Solvers {
    final static double G = PlanetObject.G;


    private Solvers() {
    }


    public static double accelerationFunction(double position1D, PlanetObject planetObject, PlanetObject otherObject, int i) {
        double force = 0;
        double acceleration = 0;
        double M1 = planetObject.getMass();
        double M2 = otherObject.getMass();
        double positionalDifference = 0;
        double[] coordinates = planetObject.getCoordinates();
        double[] otherCoordinates = otherObject.getCoordinates();

        positionalDifference = position1D - otherCoordinates[i];

        double distance = 1 / HelperFunctions.getDistanceBetweenWithVectors(coordinates, otherCoordinates);

        force = -G * M1 * M2 * (Math.pow(distance, 3)) * positionalDifference;
        acceleration = force / M1;

        return acceleration;
    }

    public static void explicitEuler(PlanetObject planetObject, PlanetObject otherObject, double h) {
        double[] velocity = planetObject.getVelocity();
        double[] coordinates = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();

        for (int i = 0; i < 3; i++) {
            acceleration[i] += accelerationFunction(coordinates[i], planetObject, otherObject, i);
            coordinates[i] += velocity[i] * h;
            velocity[i] += acceleration[i] * h;
        }

        planetObject.setVelocity(velocity);
        planetObject.setCoordinates(coordinates);
        planetObject.setAcceleration(acceleration);
    }



    public static void rungeKutta4(PlanetObject planetObject, PlanetObject otherObject, double h) {
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();
        double[][] y = {positionalVector, velocityVector};

        double[] kr1 = new double[3];
        double[] kr2 = new double[3];
        double[] kr3 = new double[3];
        double[] kr4 = new double[3];
        double[] kv1 = new double[3];
        double[] kv2 = new double[3];
        double[] kv3 = new double[3];
        double[] kv4 = new double[3];

        for (int i = 0; i < 3; i++) {
            kv1[i] = acceleration[i] + accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            kr1[i] = velocityVector[i] * h;
            kv2[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kr1[i] * (h / 2)), planetObject, otherObject, i);
            kr2[i] = velocityVector[i] * (kv1[i] * (h / 2));
            kv3[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kr2[i] * (h / 2)), planetObject, otherObject, i);
            kr3[i] = velocityVector[i] * (kv2[i] * (h / 2));
            kv4[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kr3[i] * (h / 2)), planetObject, otherObject, i);
            kr4[i] = velocityVector[i] * (kv3[i] * h);

            velocityVector[i] = velocityVector[i] + (h / 6) * (kv1[i] + 2 * kv2[i] + 2 * kv3[i] + kv4[i]);
            positionalVector[i] = positionalVector[i] + (h / 6) * (kr1[i] + 2 * kr2[i] + 2 * kr3[i] + kr4[i]);
        }

        double newAcceleration[] = new double[3];
        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));

        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(newAcceleration);
    }

    public static void fastEuler(PlanetObject planetObject, double[] acceleration, double step){
        double[] velocityVector=planetObject.getVelocity();
        double[] positionalVector= planetObject.getCoordinates();
        for(int i = 0; i < 3; i++)
        {
            velocityVector[i] += acceleration[i] * step;
            positionalVector[i] += velocityVector[i] * step;
        }
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
    }

}