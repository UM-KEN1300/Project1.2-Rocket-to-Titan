package Algorithms;

import Model.PlanetObject;
import helperFunction.HelperFunctions;

public class Solvers
{
    final static double G = PlanetObject.G;
    public Solvers() {}




    /**
     * @param acceleration is the all the accelerations that affect a planet
     * @param step is amount of time we move the planet 1 second is 1
     *
     * This is the Euler's solver that updates the planet position with step in time
     */
    public static void eulerSolver(PlanetObject planetObject, double[] acceleration, double step){
        double[] velocityVector = planetObject.getVelocityVector();
        double[] positionalVector = planetObject.getPositionalVector();
        for(int i = 0; i < 3; i++)
        {
            velocityVector[i] += acceleration[i] * step;
            positionalVector[i] += velocityVector[i] * step;
        }
        planetObject.setVelocityVector(velocityVector);
        planetObject.setPositionalVector(positionalVector);
    }


    public static double magNadd(double[] vector, double k) {
        double [] newVector = new double[3];
        HelperFunctions helperFunctions = new HelperFunctions();
        for (int i = 0; i<3; i++){
            newVector[i] = vector[i] + k;
        }
        double vectorMag = helperFunctions.getVectorMagnitude(vector);
        return vectorMag;
    }

    /*private static double accelerationForSolvers(double positionalVectorValue, double [] positionalVector, PlanetObject other, int i){
        HelperFunctions helperFunctions = new HelperFunctions();
        double [] newPositionalVectorValue = helperFunctions.subtract(positionalVector, other.getPositionalVector());
        //double positionalVectorMagnitude = helperFunctions.getVectorMagnitude(positionalVector);
        double positionalVectorMagnitude = helperFunctions.getDistanceBetweenWithVectors(positionalVector, other.getPositionalVector());
        final double M = other.getMass();
        return (-G * M * newPositionalVectorValue[i]) / (Math.pow(positionalVectorMagnitude, 3)) ;
    }*/

    /*public static double accelerationForSolvers(double position1D, PlanetObject planetObject, PlanetObject otherObject, int i){
        HelperFunctions helperFunctions = new HelperFunctions();
        double force = 0;
        double acceleration = 0;
        double M1 = planetObject.getMass();
        double M2 = otherObject.getMass();
        double positionalDifference = 0;
        double [] positionalVector = planetObject.getPositionalVector();
        double [] otherPositionalVector = otherObject.getPositionalVector();


        positionalDifference = position1D - otherPositionalVector[i];

        double distance = 1/helperFunctions.getDistanceBetweenWithVectors(positionalVector, otherPositionalVector);

        force = -G * M1 * M2 *(Math.pow(distance, 3)) * positionalDifference;
        acceleration = force / M1;

        return acceleration;
    }*/



    public static void rungeKutta(PlanetObject planetObject, PlanetObject otherObject, double h) {
        double[] velocityVector = planetObject.getVelocityVector();
        double[] positionalVector = planetObject.getPositionalVector();
        double[] acceleration = planetObject.getAcceleration();
        double[] kr1 = new double[3];
        double[] kr2 = new double[3];
        double[] kr3 = new double[3];
        double[] kr4 = new double[3];
        double[] kv1 = new double[3];
        double[] kv2 = new double[3];
        double[] kv3 = new double[3];
        double[] kv4 = new double[3];

        final double M = otherObject.getMass();

      /* for (int i =  0; i<3; i++) {

            kr1[i] = h * velocityVector[i];
            kv1[i] = (-G*M*positionalVector[i])/(Math.pow(magNadd(positionalVector, 0),3));

            kr2[i] = h * (velocityVector[i] + kv1[i]/2);
            kv2[i] = (-G*M*(positionalVector[i]+(kr1[i]/2)))/(Math.pow(magNadd(positionalVector, kr1[i]/2),3));

            kr3[i] = h*(velocityVector[i] + (kv2[i]/2));
            kv3[i] = -G*M*(positionalVector[i] + kr2[i]/2)/(Math.pow(magNadd(positionalVector, kr2[i]/2),3));

            kr4[i] = h*(velocityVector[i] + kv3[i]);
            kv4[i] = -G*M*(positionalVector[i]+kr3[i])/(Math.pow(magNadd(positionalVector, kr3[i]),3)*h);

            velocityVector[i] = velocityVector[i] + (h / 6) * (kv1[i] + 2 * kv2[i] + 2 * kv3[i] + kv4[i]);
            positionalVector[i] = positionalVector[i] + (h / 6) * (kr1[i] + 2 * kr2[i] + 2 * kr3[i] + kr4[i]);
        }*/

        for (int i = 0; i < 3; i++) {
            kv1[i] = acceleration[i] + planetObject.accelerationForSolvers(positionalVector[i], otherObject, i);
            kr1[i] = velocityVector[i];
            kv2[i] = acceleration[i] + planetObject.accelerationForSolvers(positionalVector[i] + (kr1[i] * (h / 2)), otherObject, i);
            kr2[i] = velocityVector[i] * (kv1[i] * (h / 2));
            kv3[i] = acceleration[i] + planetObject.accelerationForSolvers(positionalVector[i] + (kr2[i] * (h / 2)), otherObject, i);
            kr3[i] = velocityVector[i] * (kv2[i] * (h / 2));
            kv4[i] = acceleration[i] + planetObject.accelerationForSolvers(positionalVector[i] + (kr3[i] * (h / 2)), otherObject, i);
            kr4[i] = velocityVector[i] * (kv3[i] * h);

            velocityVector[i] = velocityVector[i] + (h / 6) * (kv1[i] + 2 * kv2[i] + 2 * kv3[i] + kv4[i]);
            positionalVector[i] = positionalVector[i] + (h / 6) * (kr1[i] + 2 * kr2[i] + 2 * kr3[i] + kr4[i]);

        }
        double [] acc = new double[3];
        acc = HelperFunctions.addition(acc, planetObject.accelerationBetween(otherObject));


        planetObject.setVelocityVector(velocityVector);
        planetObject.setPositionalVector(positionalVector);
        planetObject.setAcceleration(acceleration);


        // Calculate kv1 and kr1
        /* for (int i = 0; i < 3; i++) {
             kv1[i] = accelerationForSolvers(positionalVector[i], positionalVector, otherObject);
             kr1[i] = velocityVector[i];
         }

         // Calculate kv2 and kr2
         double[] newPositionalVector = new double[3];
         double[] newVelocityVector = new double[3];
         for (int i = 0; i < 3; i++) {
             newPositionalVector[i] = positionalVector[i] + (kr1[i] * (h / 2));
         }
         for (int i = 0; i < 3; i++) {
             kv2[i] = accelerationForSolvers(newPositionalVector[i], newPositionalVector, otherObject);
             kr2[i] = velocityVector[i] + (kv1[i] * (h / 2));
         }

         // Calculate kv3 and kr3
         for (int i = 0; i < 3; i++) {
             newPositionalVector[i] = positionalVector[i] + (kr2[i] * (h / 2));
         }
         for (int i = 0; i < 3; i++) {
             kv3[i] = accelerationForSolvers(newPositionalVector[i], newPositionalVector, otherObject);
             kr3[i] = velocityVector[i] + (kv2[i] * (h / 2));
         }

         // Calculate kv4 and kr4
         for (int i = 0; i < 3; i++) {
            newPositionalVector[i] = positionalVector[i] + (kr3[i] * h);
            kr4[i] = velocityVector[i] + (kv3[i]*h);
         }

         for (int i = 0; i < 3; i++){
             newVelocityVector[i] = velocityVector[i] + (h / 6) * (kv1[i] + 2 * kv2[i] + 2 * kv3[i] + kv4[i]);
             newPositionalVector[i] = positionalVector[i] + (h / 6) * (kr1[i] + 2 * kr2[i] + 2 * kr3[i] + kr4[i]);
         }

        planetObject.setVelocityVector(newVelocityVector);
        planetObject.setPositionalVector(newPositionalVector);


    }*/
    }

    public void kuttaThree(double h, PlanetObject planetObject, PlanetObject otherObject){
        double[] velocityVector = planetObject.getVelocityVector();
        double[] positionalVector = planetObject.getPositionalVector();
        double[] kr1 = new double[3];
        double[] kr2 = new double[3];
        double[] kr3 = new double[3];
        double[] kv1 = new double[3];
        double[] kv2 = new double[3];
        double[] kv3 = new double[3];


    }

    public static void explicitEuler(PlanetObject planetObject, double [] acceleration, double h){
        double[] velocityVector = planetObject.getVelocityVector();
        double[] positionalVector = planetObject.getPositionalVector();
        for (int i = 0; i < 3; i++) {
            positionalVector[i] += velocityVector[i] * h;
            velocityVector[i] += acceleration[i] * h;

            planetObject.setVelocityVector(velocityVector);
            planetObject.setPositionalVector(positionalVector);
        }

    }

    public static void explicitEuler(PlanetObject planetObject, PlanetObject otherObject, double h){
        double[] velocityVector = planetObject.getVelocityVector();
        double[] positionalVector = planetObject.getPositionalVector();
        double[] acceleration = planetObject.getAcceleration();
        //acceleration = HelperFunctions.addition(acceleration, planetObject.accelerationBetween(otherObject));
        for (int i = 0; i < 3; i++) {
            acceleration[i] += planetObject.accelerationForSolvers(positionalVector[i], otherObject, i);
            positionalVector[i] += velocityVector[i] * h;
            //velocityVector[i] += planetObject.accelerationForSolvers(positionalVector[i], otherObject, i) * h;
            velocityVector[i] += acceleration[i] * h;
            //velocityVector[i] += acc[i] * h;

        }
        planetObject.setVelocityVector(velocityVector);
        planetObject.setPositionalVector(positionalVector);
        planetObject.setAcceleration(acceleration);

    }


    public static void midPoint(PlanetObject planetObject, PlanetObject other, double h) {
        double[] velocityVector = planetObject.getVelocityVector();
        double[] positionalVector = planetObject.getPositionalVector();
        double[] newPositionalVector = new double[3];
        double[] newVelocityVector = new double[3];
        double[] acceleration = planetObject.getAcceleration();

        for (int i = 0; i < 3; i++) {
            acceleration[i] += (0.5 * planetObject.accelerationForSolvers(positionalVector[i], other, i) * h);
            newPositionalVector[i] = positionalVector[i] + (0.5 * velocityVector[i] * h);
            newVelocityVector[i] = velocityVector[i] + acceleration[i];
        }
        for (int i = 0; i < 3; i++) {
            acceleration[i] += (planetObject.accelerationForSolvers(newPositionalVector[i], other, i) * h);
            velocityVector[i] = velocityVector[i] + acceleration[i];
            positionalVector[i] = positionalVector[i] + (newVelocityVector[i] * h);
        }

        planetObject.setVelocityVector(velocityVector);
        planetObject.setPositionalVector(positionalVector);
        planetObject.setAcceleration(acceleration);
    }
}
