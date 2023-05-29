package code.algorithms;

import code.model.objects.PlanetObject;
import code.utils.HelperFunctions;

public class Solvers {
    final static double G = PlanetObject.G;


    private Solvers() {
    }


    private static double accelerationFunction(double position1D, PlanetObject planetObject, PlanetObject otherObject, int i) {
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


    /**
     * @param acceleration is the all the accelerations that affect a planet
     * @param step is amount of time we move the planet 1 second is 1
     *
     * This is the Euler's solver that updates the planet position with step in time
     */
    public static void implicitEuler(PlanetObject planetObject, double[] acceleration, double step){
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        for(int i = 0; i < 3; i++)
        {
            velocityVector[i] += acceleration[i] * step;
            positionalVector[i] += velocityVector[i] * step;
        }
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
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


    public static void kutta3(PlanetObject planetObject, PlanetObject otherObject, double h){
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();
        double[] kv1 = new double[3];
        double[] kv2 = new double[3];
        double[] kv3 = new double[3];
        double[] kr1 = new double[3];
        double[] kr2 = new double[3];
        double[] kr3 = new double[3];

        for (int i = 0; i < 3; i++){
            kr1[i] = h * velocityVector[i];
            kv1[i] = acceleration[i] + accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            kr2[i] = h * (velocityVector[i] + kv1[i]/2);
            kv2[i] = acceleration[i] + accelerationFunction(kr1[i]/2, planetObject, otherObject, i);
            kr3[i] = h * (velocityVector[i] - kv1[i] + 2*kv2[i]);
            kv3[i] = acceleration[i] + accelerationFunction(positionalVector[i] - kv1[i] + 2*kv2[i], planetObject, otherObject, i);

            positionalVector[i] += (h/6)*(kr1[i] + 4*kr2[i] + kr3[i]);
            velocityVector[i] += (h/6)*(kv1[i] + 4*kv2[i] + kv3[i]);
        }

        double[] newAcceleration = new double[3];
        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));
        planetObject.setCoordinates(positionalVector);
        planetObject.setVelocity(velocityVector);
        planetObject.setAcceleration(newAcceleration);

    }


    public static void explicitEuler(PlanetObject planetObject, PlanetObject otherObject, double h){
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();

        for (int i = 0; i < 3; i++) {
            acceleration[i] += accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            positionalVector[i] += velocityVector[i] * h;
            velocityVector[i] += acceleration[i] * h;
        }

        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(acceleration);
    }


    public static void ralston2(PlanetObject planetObject, PlanetObject otherObject, double h){
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] acceleration = planetObject.getAcceleration();
        double kv1[] = new double[3];
        double kv2[] = new double[3];
        double kr1[] = new double[3];
        double kr2[] = new double[3];

        for (int i = 0; i < 3; i++){
            kv1[i] = acceleration[i] + accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            kr1[i] = velocityVector[i] * h;
            kv2[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kr1[i] * (2*h/3)), planetObject, otherObject, i);
            kr2[i] = velocityVector[i] * (kv1[i] * (2*h/3));

            velocityVector[i] = velocityVector[i] + (h/4) * (kv1[i] + 3*kv2[i]);
            positionalVector[i] = positionalVector[i] + (h/4) * (kr1[i] + 3*kr2[i]);
        }

        double newAcceleration[] = new double[3];
        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));

        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(newAcceleration);

    }

    public static double [][] rungeKutta4Generic(double[][] y, int dimY, int dimVariables, double h, PlanetObject planetObject, PlanetObject otherObject, double[] var){
        double[][] k1 = new double[dimY][dimVariables];
        double[][] k2 = new double[dimY][dimVariables];
        double[][] k3 = new double[dimY][dimVariables];
        double[][] k4 = new double[dimY][dimVariables];

        for (int i = 0; i < dimY; i++){
            for (int j = 0; j < dimVariables; j++){
                k1[i][j] = var[j] + accelerationFunction(y[i][j], planetObject, otherObject, j);
                k2[i][j] = var[j] + accelerationFunction(y[i][j]+(k1[i][j]/2), planetObject, otherObject, j);
                k3[i][j] = var[j] + accelerationFunction(y[i][j]+(k2[i][j]/2), planetObject, otherObject, j);
                k4[i][j] = var[j] + accelerationFunction(y[i][j]+k3[i][j]*h, planetObject, otherObject, j);

                y[i][j] = y[i][j] + (1/6)*(k1[i][j] + 2*k2[i][j] + 2*k3[i][j] + k4[i][j]);
            }
        }
        return y;
    }

    public static void heun3(PlanetObject planetObject, PlanetObject otherObject, double h){
        double [] velocityVector = planetObject.getVelocity();
        double [] positionalVector = planetObject.getCoordinates();
        double [] acceleration = planetObject.getAcceleration();
        double [] kr1 = new double [3];
        double [] kr2 = new double [3];
        double [] kr3 = new double [3];
        double [] kv1 = new double [3];
        double [] kv2 = new double [3];
        double [] kv3 = new double [3];

        for (int i = 0; i < 3; i++){
            kr1[i] = h * velocityVector[i];
            kv1[i] = acceleration[i] + accelerationFunction(positionalVector[i], planetObject, otherObject, i);
            kr2[i] = h * (velocityVector[i] * (1/3)*kv1[i]);
            kv2[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (kr1[i]/3), planetObject, otherObject, i);
            kr3[i] = h * (velocityVector[i] * (2/3)*kv2[i]);
            kv3[i] = acceleration[i] + accelerationFunction(positionalVector[i] + (2/3)*kr2[i], planetObject, otherObject, i);

            velocityVector[i] = velocityVector[i] + (h/4) * (kv1[i] + 3*kv3[i]);
            positionalVector[i] = positionalVector[i] + (h/4) * (kr1[i] + 3*kr3[i]);
        }

        double newAcceleration[] = new double[3];
        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));

        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(newAcceleration);
    }


    public static void midPoint(PlanetObject planetObject, PlanetObject otherObject, double h) {
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        double[] newPositionalVector = new double[3];
        double[] newVelocityVector = new double[3];
        double[] acceleration = planetObject.getAcceleration();
        double[] kv1 = new double [3];
        double[] newAcceleration = new double[3];


        for (int i = 0; i < 3; i++) {
            kv1[i] = acceleration[i] + (0.5 * accelerationFunction(positionalVector[i], planetObject, otherObject, i) * h);
            newPositionalVector[i] = positionalVector[i] + (0.5 * velocityVector[i] * h);
            newVelocityVector[i] = velocityVector[i] + kv1[i];
        }
        for (int i = 0; i < 3; i++) {
            kv1[i] = acceleration[i] + (accelerationFunction(newPositionalVector[i], planetObject, otherObject, i) * h);
            velocityVector[i] = velocityVector[i] + acceleration[i];
            positionalVector[i] = positionalVector[i] + (newVelocityVector[i] * h);
        }

        newAcceleration = HelperFunctions.addition(newAcceleration, planetObject.accelerationBetween(otherObject));

        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
        planetObject.setAcceleration(newAcceleration);
    }

    public static void fastEuler(PlanetObject planetObject, double[] acceleration, double step) {
        double[] velocityVector = planetObject.getVelocity();
        double[] positionalVector = planetObject.getCoordinates();
        for (int i = 0; i < 3; i++) {
            velocityVector[i] += acceleration[i] * step;
            positionalVector[i] += velocityVector[i] * step;
        }
        planetObject.setVelocity(velocityVector);
        planetObject.setCoordinates(positionalVector);
    }
}
