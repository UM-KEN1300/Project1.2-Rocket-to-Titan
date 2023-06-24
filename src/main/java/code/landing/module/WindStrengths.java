package code.landing.module;
import java.util.Arrays;
import java.util.Random;

class WindStrengths {
    double[] windValues;

    public WindStrengths()
    {
        windValues= RandomWindValues(1, 0.01, 1, 1, 600);
    }

    public static void main(String[] args)
    {
        WindStrengths windStrengths=new WindStrengths();
      double one=  windStrengths.getWindValues()[300];
    }

    public static double[] RandomWindValues(double bound, double speedChange, double heightDecrease, double maxWindSpeed, int startingHeight){

        double[] windStrenghts = new double[startingHeight+1];
        Random random = new Random();
        double currentWindSpeed = 0;
        double desiredSpeed = 0;
        double boundSize = 2*bound;
        double windSpeedIncrease = maxWindSpeed/startingHeight;

        double differenceGeneratedSpeedDesiredSpeed = (desiredSpeed + bound) - currentWindSpeed;
        double chanceOfIncrease = differenceGeneratedSpeedDesiredSpeed/boundSize;

        for (int i = startingHeight; i >= 120; i--) {
            if(random.nextDouble()<=chanceOfIncrease){
                currentWindSpeed += speedChange;
            }else{
                currentWindSpeed -= speedChange;
            }
            //System.out.println(currentWindSpeed);
            windStrenghts[i] = currentWindSpeed;
            differenceGeneratedSpeedDesiredSpeed = (desiredSpeed + bound) - currentWindSpeed;
            chanceOfIncrease = differenceGeneratedSpeedDesiredSpeed/boundSize;
            desiredSpeed += windSpeedIncrease;
            //System.out.println("Current height: " + i);
            //System.out.println(desiredSpeed);
        }

        windSpeedIncrease = windSpeedIncrease*4;
        speedChange = speedChange*4;
        for (int i = 120; i >= 0; i--) {
            if(random.nextDouble()<=chanceOfIncrease){
                currentWindSpeed += speedChange;
            }else{
                currentWindSpeed -= speedChange;
            }
            //System.out.println(currentWindSpeed);
            windStrenghts[i] = currentWindSpeed;
            differenceGeneratedSpeedDesiredSpeed = (desiredSpeed + bound) - currentWindSpeed;
            chanceOfIncrease = differenceGeneratedSpeedDesiredSpeed/boundSize;
            desiredSpeed -= windSpeedIncrease;
            //System.out.println("Current height: " + i);
            //System.out.println(desiredSpeed);
        }
        return windStrenghts;
    }

    public double[] getWindValues()
    {
        return windValues;
    }
}
