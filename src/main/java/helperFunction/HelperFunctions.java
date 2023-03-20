package helperFunction;
import  group1.project12group1.*;
public class HelperFunctions
{

    public static double getDistanceBetween(PlanetObject one, PlanetObject two)
    {
        return (Math.sqrt(
                Math.pow((two.getX()-one.getX()),2)
                +Math.pow((two.getY()-one.getY()),2)
                +Math.pow((two.getZ()-one.getZ()),2))
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

    public double[] getDistanceBetweenPositionVectors(double[] vectorOne, double[] vectorTwo)
    {
        double[] returnable=new double[3];
        returnable[0]=vectorOne[0]-vectorTwo[0];
        returnable[1]=vectorOne[1]-vectorTwo[1];
        returnable[2]=vectorOne[2]-vectorTwo[2];
        return returnable;
    }


    public double getVectorMagnitude(double[] vector)
    {
        return Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
    }

}
