package group1.project12group1;

import java.util.Random;

public class HillClimbingAlg {

    final int INITIAL = 0;
    final int XPLUS = 1;
    final int XMINUS = 2;
    final int YPLUS = 3;
    final int YMINUS = 4;
    final int ZPLUS = 5;
    final int ZMINUS = 6;

    public int[] HillClimbingAlg() {

        Random rn = new Random();

        int VelocityChange = 10;
        int[] DistancesToTitan = new int[7];
        int closestRocket = 0;

        int InitalXVelocity = rn.nextInt();
        int InitalYVelocity = rn.nextInt();
        int InitalZVelocity = rn.nextInt();

        int XVelocityPlus = InitalXVelocity + VelocityChange;
        int XVelocityMinus = InitalXVelocity - VelocityChange;

        int YVelocityPlus = InitalYVelocity + VelocityChange;
        int YVelocityMinus = InitalYVelocity - VelocityChange;

        int ZVelocityPlus = InitalZVelocity + VelocityChange;
        int ZVelocityMinus = InitalZVelocity - VelocityChange;

        while (true) {
            
            // launch seven rockets, one being the initial one, and the other 6 only
            // changing one velocity either plus or minus.
            // put distances of every rocket after 1 year in DistancesToTitan

            closestRocket = findSmallest(DistancesToTitan);

            switch (closestRocket) {
                case INITIAL:
                    return new int[] { InitalXVelocity, InitalYVelocity, InitalZVelocity };
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

    public int findSmallest(int[] distances) {
        int smallestIndex = 0;
        for (int i = 1; i < distances.length; i++) {
            if (distances[i] < distances[smallestIndex])
                smallestIndex = i;
        }
        return smallestIndex;
    }
}