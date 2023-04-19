package helperFunction;
import Model.PlanetObject;


public class HelperFunctions
{

    public HelperFunctions()
    {}


    public static double getDistanceBetween(PlanetObject one, PlanetObject two)
    {
        return (Math.sqrt(
                Math.pow((two.getPositionalVector()[0]-one.getPositionalVector()[0]),2)
                +Math.pow((two.getPositionalVector()[1]-one.getPositionalVector()[1]),2)
                +Math.pow((two.getPositionalVector()[2]-one.getPositionalVector()[2]),2))
        );
    }

    public static double getDistanceBetweenWithVectors(double[] one, double[] two)
    {
        return (Math.sqrt(
                Math.pow((two[0]-one[0]),2)
                        +Math.pow((two[1]-one[1]),2)
                        +Math.pow((two[2]-one[2]),2))
        );
    }


    public static double[] subtract(double[]vectorOne, double[] vectorTwo)
    {
       double[] returnable=new double[3];
       returnable[0]=vectorOne[0]-vectorTwo[0];
        returnable[1]=vectorOne[1]-vectorTwo[1];
        returnable[2]=vectorOne[2]-vectorTwo[2];
        return returnable;
    }

    public static double[] addition(double[]vectorOne, double[] vectorTwo)
    {
        double[] returnable=new double[3];
        returnable[0]=vectorOne[0]+vectorTwo[0];
        returnable[1]=vectorOne[1]+vectorTwo[1];
        returnable[2]=vectorOne[2]+vectorTwo[2];
        return returnable;
    }

    public double getVectorMagnitude(double[] vector)
    {
        return Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
    }

    public static double[] stringToVector(String input)
    {
        input = (input.replace("X",""));
        input = (input.replace("V",""));
        input = (input.replace("Y",""));
        input = (input.replace("Z",""));
        input = (input.replace("=",""));

        String[] srt = input.split(" ");
        double[] returnable= new double[3];
        int counter=0;
        for (int i = 0; i < srt.length; i++)
        {
            if (!srt[i].isEmpty())
            {
                returnable[counter]=Double.valueOf(srt[i]);
                counter++;
            }

        }


        return returnable;
    }

}
