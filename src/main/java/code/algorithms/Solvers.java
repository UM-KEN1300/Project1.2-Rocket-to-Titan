package code.algorithms;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;

import java.util.ArrayList;

public class Solvers {
    final static double G = PlanetObject.G;


    private Solvers() {
    }


    public static double accelerationForSolvers(double position1D, PlanetObject planetObject, PlanetObject otherObject, int i) {
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
            acceleration[i] += accelerationForSolvers(coordinates[i], planetObject, otherObject, i);
            coordinates[i] += velocity[i] * h;
            velocity[i] += acceleration[i] * h;
        }

        planetObject.setVelocity(velocity);
        planetObject.setCoordinates(coordinates);
        planetObject.setAcceleration(acceleration);
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


//    public static double accelerationForSolversV(double velocity1D, PlanetObject planetObject, PlanetObject otherObject, int i) {
//        double force = 0;
//        double acceleration = 0;
//        double M1 = planetObject.getMass();
//        double M2 = otherObject.getMass();
//        double velocityDifference = 0;
//        double[] velocity = planetObject.getVelocity();
//        double[] otherVelocity = otherObject.getVelocity();
//
//        velocityDifference = velocity1D - otherVelocity[i];
//
//        double velocity = 1 / HelperFunctions.getDistanceBetweenWithVectors(velocity, otherVelocity);
//
//        force = -G * M1 * M2 * (Math.pow(velocity, 3)) * velocityDifference;
//        acceleration = force / M1;
//
//        return acceleration;
//    }
//
//    /**
//     * @param acceleration is the all the accelerations that affect a planet
//     * @param step         is amount of time we move the planet 1 second is 1
//     *                     <p>
//     *                     This is the Euler's solver that updates the planet position with step in time
//     */
//    public static void implicitEuler(PlanetObject planetObject, double[] acceleration, double step) {
//        double[] velocity = planetObject.getVelocity();
//        double[] coordinates = planetObject.getCoordinates();
//        for (int i = 0; i < 3; i++) {
//            velocity[i] += acceleration[i] * step;
//            coordinates[i] += velocity[i] * step;
//        }
//        planetObject.setVelocity(velocity);
//        planetObject.setCoordinates(coordinates);
//    }
//
//
//    public static void rungeKutta4(PlanetObject planetObject, PlanetObject otherObject, double h) {
//        double[] velocity = planetObject.getVelocity();
//        double[] coordinates = planetObject.getCoordinates();
//        double[] acceleration = planetObject.getAcceleration();
//        double[] kr1 = new double[3];
//        double[] kr2 = new double[3];
//        double[] kr3 = new double[3];
//        double[] kr4 = new double[3];
//        double[] kv1 = new double[3];
//        double[] kv2 = new double[3];
//        double[] kv3 = new double[3];
//        double[] kv4 = new double[3];
//
//        for (int i = 0; i < 3; i++) {
//            kv1[i] = acceleration[i] + accelerationForSolvers(coordinates[i], planetObject, otherObject, i);
//            kr1[i] = velocity[i];
//            kv2[i] = acceleration[i] + accelerationForSolvers(coordinates[i] + (kr1[i] * (h / 2)), planetObject, otherObject, i);
//            kr2[i] = velocity[i] * (kv1[i] * (h / 2));
//            kv3[i] = acceleration[i] + accelerationForSolvers(coordinates[i] + (kr2[i] * (h / 2)), planetObject, otherObject, i);
//            kr3[i] = velocity[i] * (kv2[i] * (h / 2));
//            kv4[i] = acceleration[i] + accelerationForSolvers(coordinates[i] + (kr3[i] * (h / 2)), planetObject, otherObject, i);
//            kr4[i] = velocity[i] * (kv3[i] * h);
//
//            velocity[i] = velocity[i] + (h / 6) * (kv1[i] + 2 * kv2[i] + 2 * kv3[i] + kv4[i]);
//            coordinates[i] = coordinates[i] + (h / 6) * (kr1[i] + 2 * kr2[i] + 2 * kr3[i] + kr4[i]);
//        }
//        double newAcceleration[] = new double[3];
//        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));
//
//        planetObject.setVelocity(velocity);
//        planetObject.setCoordinates(coordinates);
//        //planetObject.setAcceleration(newAcceleration);
//
//        System.out.println("x: " + planetObject.getAcceleration(0));
//        System.out.println("y: " + planetObject.getAcceleration(1));
//        System.out.println("z: " + planetObject.getAcceleration(2));
//    }
//
//
//    public void adamsMoulton(PlanetObject planetObject, PlanetObject otherObject, double h) {
//        double[] velocity = planetObject.getVelocity();
//        double[] coordinates = planetObject.getCoordinates();
//        double[] acceleration = planetObject.getAcceleration();
//
//        for (int i = 1; i < 4; i++) {
//            acceleration[i] += accelerationForSolvers(coordinates[i], planetObject, otherObject, i);
//            coordinates[i] += (h / 2) * (3 * velocity[i] - velocity[i - 1]);
//        }
//
//    }
//
//    public static void kutta3(PlanetObject planetObject, PlanetObject otherObject, double h) {
//        double[] velocity = planetObject.getVelocity();
//        double[] coordinates = planetObject.getCoordinates();
//        double[] acceleration = planetObject.getAcceleration();
//        double[] kv1 = new double[3];
//        double[] kv2 = new double[3];
//        double[] kv3 = new double[3];
//        double[] kr1 = new double[3];
//        double[] kr2 = new double[3];
//        double[] kr3 = new double[3];
//
//        for (int i = 0; i < 3; i++) {
//            kr1[i] = h * velocity[i];
//            kv1[i] = acceleration[i] + (h * accelerationForSolvers(coordinates[i], planetObject, otherObject, i));
//            kr2[i] = h * (velocity[i] + kv1[i] / 2);
//            kv2[i] = acceleration[i] + (h * accelerationForSolvers(kr1[i] / 2, planetObject, otherObject, i));
//            kr3[i] = h * (velocity[i] - kv2[i] + 2 * kv1[i]);
//            kv3[i] = acceleration[i] + (h * accelerationForSolvers(coordinates[i] + kr2[i], planetObject, otherObject, i));
//
//            coordinates[i] += (1 / 6) * (kr1[i] + 4 * kr2[i] + kr3[i]);
//            velocity[i] += (1 / 6) * (kv1[i] + 4 * kv2[i] + kv3[i]);
//        }
//
//        double[] newAcceleration = new double[3];
//        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));
//        planetObject.setCoordinates(coordinates);
//        planetObject.setVelocity(velocity);
//        //planetObject.setAcceleration(newAcceleration);
//
//    }


//    public static void ralston2(PlanetObject planetObject, PlanetObject otherObject, double h) {
//        double[] velocity = planetObject.getVelocity();
//        double[] coordinates = planetObject.getCoordinates();
//        double[] acceleration = planetObject.getAcceleration();
//        double kv1[] = new double[3];
//        double kv2[] = new double[3];
//        double kr1[] = new double[3];
//        double kr2[] = new double[3];
//
//        for (int i = 0; i < 3; i++) {
//            kv1[i] = acceleration[i] + accelerationForSolvers(coordinates[i], planetObject, otherObject, i);
//            kr1[i] = velocity[i];
//            kv2[i] = acceleration[i] + accelerationForSolvers(coordinates[i] + (kr1[i] * (2 * h / 3)), planetObject, otherObject, i);
//            kr2[i] = velocity[i] * (kv1[i] * (2 * h / 3));
//
//            velocity[i] = velocity[i] + (h / 4) * (kv1[i] + 3 * kv2[i]);
//            coordinates[i] = coordinates[i] + (h / 4) * (kr1[i] + 3 * kr2[i]);
//        }
//
//        double newAcceleration[] = new double[3];
//        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));
//
//        planetObject.setVelocity(velocity);
//        planetObject.setCoordinates(coordinates);
//        planetObject.setAcceleration(newAcceleration);
//
//
//    }


//    public static void midPoint(PlanetObject planetObject, PlanetObject otherObject, double h) {
//        double[] velocity = planetObject.getVelocity();
//        double[] coordinates = planetObject.getCoordinates();
//        double[] newCoordinates = new double[3];
//        double[] newVelocity = new double[3];
//        double[] acceleration = planetObject.getAcceleration();
//        double[] kv1 = new double[3];
//        double[] newAcceleration = new double[3];
//
//
//        for (int i = 0; i < 3; i++) {
//            kv1[i] = acceleration[i] + (0.5 * accelerationForSolvers(coordinates[i], planetObject, otherObject, i) * h);
//            newCoordinates[i] = coordinates[i] + (0.5 * velocity[i] * h);
//            newVelocity[i] = velocity[i] + kv1[i];
//        }
//        for (int i = 0; i < 3; i++) {
//            kv1[i] = acceleration[i] + (accelerationForSolvers(newCoordinates[i], planetObject, otherObject, i) * h);
//            velocity[i] = velocity[i] + acceleration[i];
//            coordinates[i] = coordinates[i] + (newVelocity[i] * h);
//        }
//
//        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));
//
//        planetObject.setVelocity(velocity);
//        planetObject.setCoordinates(coordinates);
//
//        /*System.out.println("x: "+planetObject.getAcceleration(0));
//        System.out.println("y: "+planetObject.getAcceleration(1));
//        System.out.println("z: "+planetObject.getAcceleration(2));*/
//        planetObject.setAcceleration(newAcceleration);
//    }

        /*for (int i = 0; i < 3; i++){
            coordinates[i] += h * accelerationForSolvers(coordinates[i]+(1/2)*h*accelerationForSolvers(coordinates[i], planetObject, otherObject, i), planetObject, otherObject, i);
            velocity[i] += h * accelerationForSolvers(velocity[i]+(1/2)*h*accelerationForSolvers(velocity[i], planetObject, otherObject, i), planetObject, otherObject, i);
        }*/
}
