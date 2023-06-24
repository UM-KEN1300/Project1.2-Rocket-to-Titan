package code.landing.module;
import java.lang.Math;
import java.util.ArrayList;

public class HardcodedValues implements Comparable<HardcodedValues>
{
    double XPosition;
    double YPosition;
    double rotationAngle;
    double rotationAngleVelocity;
    double XVelocity;
    double YVelocity;
    double distanceToLandingSpot;
    ArrayList<double[]> listOfBoost;

    public void runner(double stepSize, double numberOfIterations)
    {
        int counter=0;
        double[] currentBoost=listOfBoost.get(counter);
        for (int i = 0; i < numberOfIterations&&YPosition>0; i++)
        {
            if(currentBoost[0]==numberOfIterations)
            {
                updater(currentBoost[1],currentBoost[2],stepSize);
                counter++;
                currentBoost=listOfBoost.get(counter);
            }
            else
            {
                updater(0, 0, 1);
            }
        }
    }

    public void updater(double u,double v,double stepSize)
    {
        //X value update
        double XAcceleration=u*Math.sin(rotationAngle);
        XVelocity+=XAcceleration*stepSize;
        XPosition+=XVelocity*stepSize;
        //Y value update
        double YAcceleration=u*Math.sin(rotationAngle)-1.352*Math.pow(10,-3);
        YVelocity+=YAcceleration*stepSize;
        YPosition+=YVelocity*stepSize;
        //rotation angle update
        rotationAngleVelocity+=v*stepSize;
        rotationAngle+=rotationAngleVelocity*stepSize;

    }

    public HardcodedValues(double XPosition, double YPosition, double rotationAngle, double XVelocity, double YVelocity)
    {
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.rotationAngle = rotationAngle;
        this.XVelocity = XVelocity;
        this.YVelocity = YVelocity;
        listOfBoost=new ArrayList<>();
    }

//    public double getDirectionTowardsLandingSpot()
//    {
//     double tan=YPosition/XPosition;
//     double degs = Math.toDegrees(Math.atan(tan));
//     return 180-degs;
//    }

    public double getBoostForAngle(double angle)
    {
        return 0;
    }
    public void addBoost(double time,double u,double v)
    {
        //TODO add limits
        double[] boost={time,u,v};
        listOfBoost.add(boost);
    }

    public ArrayList<double[]> getListOfBoost()
    {
        return listOfBoost;
    }

    public void setListOfBoost(ArrayList<double[]> listOfBoost)
    {
        this.listOfBoost = listOfBoost;
    }
    //___________________________GEN PART

    private double fitnessValue;
    public void setFitness()
    {
        //TODO depending on the xy and the end of a run set a score
        runner(1,100000);
        fitnessValue=Math.sqrt(XPosition*XPosition+(YPosition*YPosition));
    }


    public double getFitnessValue()
    {
        return fitnessValue;
    }





    public HardcodedValues clone()
    {
        HardcodedValues hardcodedValues=new HardcodedValues(600,600,0,-0.1,-0.087);
        hardcodedValues.setListOfBoost(this.listOfBoost);
        return hardcodedValues;
    }


    public static void main(String[] args)
    {
        HardcodedValues hardcodedValues=new HardcodedValues(600,600,0,-0.1,-0.087);
        hardcodedValues.runner(1,100000);
    }

    @Override
    public int compareTo(HardcodedValues o)
    {
        return -(int) (this.fitnessValue-o.getFitnessValue());
    }
}
