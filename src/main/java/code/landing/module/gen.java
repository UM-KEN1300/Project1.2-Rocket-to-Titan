package code.landing.module;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class gen
{
        private   double mutationRate = 0.1;
        private final Random random = new Random();
        HardcodedValues[] population;
        public  void runs()
        {
            int popSize = 100;
            population = new HardcodedValues[popSize];


            // we initialize the population with random characters
            for (int i = 0; i < popSize; i++)
            {
                HardcodedValues hardcodedValues = new HardcodedValues(600, 600, 0, -0.3, 0);
                double time = 0;
                double u;
                double v;
                for (int j = 0; j < 5; j++)
                {
                    u = 0.01 + (13 - 0.01) * random.nextDouble();
                    v = 58 * random.nextDouble();
                    time += 200000 * random.nextDouble();
                    hardcodedValues.addBoost(time, u, v);
                }
                population[i]=hardcodedValues;

            }
            boolean stopper = true;
            int counters=0;
            while (stopper)
            {
                counters++;
                System.out.println("Gen: "+counters);
                System.out.println("Best: "+population[0].getFitnessValue());
                for (int i = 0; i < popSize; i++)
                { // goes over the population


                    //TODO if one reached landing


                    //set fitness
                    population[i].setFitness();
                    if (population[i].getFitnessValue() == 0)
                    {
                        stopper = false;
                        System.out.println("Found a probe that reached");
                    }

                }

                //TODO sort population
                Arrays.sort(population);

                //selector of the population
                int counter = 0;
                //removes 3/4 of with the first 1/4
                for (int i = 0; i < population.length / 4; i++)
                {
                    for (int j = 0; j < 3; j++)
                    {
                        population[popSize / 4 + counter] = population[i].clone();
                        counter++;
                    }
                }


//______________________________________________________________________________
                //crossover
                for (int i = 0; i < popSize; i++)
                {
                    int secondParent = randomNumber(popSize - 1);

                    HardcodedValues hardcodedValues = new HardcodedValues(600, 600, 0, -0.3, 0);


                    for (int j = 0; j < 3; j++)
                    {
                        double[] boost = population[i].getListOfBoost().get(j);
                        hardcodedValues.addBoost(boost[0], boost[1], boost[2]);
                    }
                    for (int j = 0; j < 2; j++)
                    {
                        double[] boost = population[secondParent].getListOfBoost().get(j);
                        hardcodedValues.addBoost(boost[0], boost[1], boost[2]);
                    }
                }
//______________________________________________________________________________
                //mutation
                for (int i = 0; i < population.length; i++)
                {
                    ArrayList<double[]> m = population[i].getListOfBoost();
                    m = mutation(m, 0.1, 0.1);
                    population[i].setListOfBoost(m);
                }

            }
        }



//______________________________________________________________________________
public ArrayList<double[]>  mutation(ArrayList<double[]> arrayList, double mutationRate, double skipRate) {
    Random random = new Random();

    // Iterate over each array in the ArrayList
    for (double[] array : arrayList)
    {
        // Determine if the array should be skipped
        if (random.nextDouble() < skipRate)
        {
            continue;
        }

        // Iterate over each variable in the array
        for (int i = 0; i < array.length; i++)
        {
            // Determine if the variable should be mutated
            if (random.nextDouble() < mutationRate)
            {
                // Generate a random number between -0.1 and 0.1
                double mutation = random.nextDouble() * 0.2 - 0.1;

                // Modify the variable by adding the mutation value
                array[i] += mutation;
            }
        }
    }
    return arrayList;}



//______________________________________________________________________________

        //random individual from the population
        public   int randomNumber(int last){
            int randomNumber = (int)(Math.random()*last + 1);
            return randomNumber;
        }


    public static void main(String[] args)
    {
        gen gen=new gen();
        gen.runs();;
    }

}
