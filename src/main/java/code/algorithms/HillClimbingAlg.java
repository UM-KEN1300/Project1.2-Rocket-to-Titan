package code.algorithms;

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

    public double[] HillClimbingAlg() {

        Random rn = new Random();

        double VelocityChange = 10;
        double[] DistancesToTitan = new double[7];
        int closestRocket = 0;

        double InitalXVelocity = rn.nextDouble();
        double InitalYVelocity = rn.nextDouble();
        double InitalZVelocity = rn.nextDouble();

        double XVelocityPlus = InitalXVelocity + VelocityChange;
        double XVelocityMinus = InitalXVelocity - VelocityChange;

        double YVelocityPlus = InitalYVelocity + VelocityChange;
        double YVelocityMinus = InitalYVelocity - VelocityChange;

        double ZVelocityPlus = InitalZVelocity + VelocityChange;
        double ZVelocityMinus = InitalZVelocity - VelocityChange;

        while (true) {
        LaunchRocketHC.launchOneRocket(new double [] {-40, -2, 60});
        LaunchRocketHC.launchOneRocket(new double [] {60, -2, -40});
        LaunchRocketHC.launchOneRocket(new double [] {30, -50, 50});

            closestRocket = findSmallest(DistancesToTitan);

            switch (closestRocket) {
                case INITIAL:
                    return new double[] { InitalXVelocity, InitalYVelocity, InitalZVelocity };
                case XPLUS:
                    InitalXVelocity = InitalXVelocity + VelocityChange;
                case XMINUS:
                    InitalXVelocity = InitalXVelocity - VelocityChange;
                case YPLUS:
                    InitalYVelocity = InitalYVelocity + VelocityChange;
                case YMINUS:
                    InitalYVelocity = InitalYVelocity - VelocityChange;
                case ZPLUS:
                    InitalZVelocity = InitalZVelocity + VelocityChange;
                case ZMINUS:
                    InitalZVelocity = InitalZVelocity - VelocityChange;
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