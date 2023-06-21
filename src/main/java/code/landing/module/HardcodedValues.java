package code.landing.module;

public class HardcodedValues
{
    double XPosition;
    double YPosition;
    double rotationAngle;
    double XVelocity;
    double YVelocity;

    public void runner(double stepSize, double numberOfIterations)
    {
        for (int i = 0; i < numberOfIterations; i+=stepSize)
        {
            updater(0,0,numberOfIterations);
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
        rotationAngle+=v*stepSize*stepSize;

    }

    public HardcodedValues(double XPosition, double YPosition, double rotationAngle, double XVelocity, double YVelocity)
    {
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.rotationAngle = rotationAngle;
        this.XVelocity = XVelocity;
        this.YVelocity = YVelocity;
    }


    public static void main(String[] args)
    {
        HardcodedValues hardcodedValues=new HardcodedValues(600,600,0,1,1);
    }
}
