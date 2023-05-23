package code.algorithms;

import java.util.Arrays;
import java.util.Random;
import code.algorithms.LaunchRocketHC;

public class HillClimbingAlg {

    final int INITIAL = 0;
    final int XPLUS = 1;
    final int XMINUS = 2;
    final int YPLUS = 3;
    final int YMINUS = 4;
    final int ZPLUS = 5; 
    final int ZMINUS = 6;
    final int X = 0;
    final int Y = 1;
    final int Z = 2;

    public double[] HillClimbingAlg() {

        Random rn = new Random();

        double VelocityChange = 10;
        double[] DistancesToTitan = new double[7];
        int closestRocket = 0;

        double[][] velocitiesOfRockets = new double[7][3];

        velocitiesOfRockets[INITIAL][X] = rn.nextDouble();
        velocitiesOfRockets[INITIAL][Y] = rn.nextDouble();
        velocitiesOfRockets[INITIAL][Z] = rn.nextDouble();

        while (true) {
            System.out.println(Arrays.toString(velocitiesOfRockets[INITIAL]));
            for(int i=1; i < velocitiesOfRockets.length; i++) {                 //Loads the velocitiesOfRockets 2D array with respecting velocities.
                velocitiesOfRockets[i][X] = velocitiesOfRockets[INITIAL][X];
                velocitiesOfRockets[i][Y] = velocitiesOfRockets[INITIAL][Y];
                velocitiesOfRockets[i][Z] = velocitiesOfRockets[INITIAL][Z];
            }
            velocitiesOfRockets[XPLUS][X] = velocitiesOfRockets[INITIAL][X] + VelocityChange;
            velocitiesOfRockets[XMINUS][X] = velocitiesOfRockets[INITIAL][X] - VelocityChange;
            velocitiesOfRockets[YPLUS][Y] = velocitiesOfRockets[INITIAL][Y] + VelocityChange;
            velocitiesOfRockets[YMINUS][Y] = velocitiesOfRockets[INITIAL][Y] - VelocityChange;
            velocitiesOfRockets[ZPLUS][Z] = velocitiesOfRockets[INITIAL][Z] + VelocityChange;
            velocitiesOfRockets[ZMINUS][Z] = velocitiesOfRockets[INITIAL][Z] - VelocityChange;



            closestRocket = findSmallest(DistancesToTitan);

            switch (closestRocket) {
                case INITIAL:
                    return new double[] { velocitiesOfRockets[INITIAL][X], velocitiesOfRockets[INITIAL][Y], velocitiesOfRockets[INITIAL][Z] };
                case XPLUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[INITIAL][X] + VelocityChange;
                case XMINUS:
                    velocitiesOfRockets[INITIAL][X] = velocitiesOfRockets[INITIAL][X] - VelocityChange;
                case YPLUS:
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[INITIAL][Y] + VelocityChange;
                case YMINUS:
                    velocitiesOfRockets[INITIAL][Y] = velocitiesOfRockets[INITIAL][Y] - VelocityChange;
                case ZPLUS:
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[INITIAL][Z] + VelocityChange;
                case ZMINUS:
                    velocitiesOfRockets[INITIAL][Z] = velocitiesOfRockets[INITIAL][Z] - VelocityChange;
            }
        }
    }

    public int findSmallest(double[] distances) {
        int smallestIndex = 0;
        for (int i = 1; i < distances.length; i++) {
            if (distances[i] < distances[smallestIndex])
                smallestIndex = i;
        }
        return smallestIndex;
    }
}