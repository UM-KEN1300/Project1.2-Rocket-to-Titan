package code.landing.module;
import java.lang.Math;
import java.util.*;

public class FeedbackController
{
    double XPosition;
    double YPosition;
    double rotationAngle;
    double rotationAngleVelocity;
    double XVelocity;
    double YVelocity;
    double distanceToLandingSpot;
    private final Queue<double[]> listOfBoost;
    boolean finished;
    public FeedbackController(double XPosition, double YPosition, double rotationAngle, double XVelocity, double YVelocity)
    {
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.rotationAngle = rotationAngle;
        this.XVelocity = XVelocity;
        this.YVelocity = YVelocity;
        listOfBoost=new LinkedList<>();
        finished = false;
    }


    public void controllerY() {
       // updater(0,0,1);


        double XTolerance = 10;
        if (YPosition <= 0.001) {
            finished = true;
        }
        else if (YPosition > 200000) {
            correctY(-150);
        }
        else if (YPosition > 100000) {
            correctY(-80);
        } else if (YPosition > 20000) {
            correctY(-30);
            }
         else if (YPosition > 10000) {
                correctY(-8);
        } else if (YPosition > 5000) {
                correctY(-1);
        } else if (YPosition > 10) {
                correctY(-0.1);
        }else if (YPosition > 5) {
            correctY(-0.01);
        }

        else {
            correctY(-0.001);
        }
    }


    public void controllerX() {
//        if(YPosition)


        if (XPosition <= 0.001) {
            finished = true;
        }
        else if (XPosition > 200000) {
            correctX(-750);
        }

        else if (XPosition > 100000) {
            correctX(-500);
        } else if (XPosition > 20000) {
            correctX(-100);
        }
        else if (XPosition > 10000) {
            correctX(-10);
        } else if (XPosition > 5000) {
            correctX(-1);
        } else if (XPosition > 10) {
            correctX(-0.1);
        } else {
            correctX(-0.01);
        }
    }

    private void correctX(double target) {
        double difference;
        if(target<XVelocity)
        {
            if (rotationAngle != 270)
            {
                turnProbeToAngle(270,0,1);
                runner(1,8);
                System.out.println(rotationAngle);
                rotationAngle=Math.round(rotationAngle);
                System.out.println(rotationAngle);
            }
            difference= target -XVelocity;
            if(Math.abs(difference)>13.52)
                difference=13.52;
            updaterX(difference,0,1);
        }
        else if(target>XVelocity)
        {
            if (rotationAngle != 90)
            {
                turnProbeToAngle(90,0,1);
                runner(1,8);
                System.out.println(rotationAngle);
                rotationAngle=Math.round(rotationAngle);
                System.out.println(rotationAngle);
            }
            difference= target -XVelocity;

            if(Math.abs(difference)>13.52)
                difference=13.52;
            updaterX(Math.abs(difference),0,1);
        }
        else {
            updaterX(0,0,1);
        }






    }

    private void correctY(double target) {
        if(target<YVelocity)
        {
            if (rotationAngle != 180)
            {
                turnProbeToAngle(180, 0, 1);
                runner(1, 8);
            }
            double difference = target - YVelocity;
            if(Math.abs(difference)>13.52)
                difference=13.52;
            updaterY(Math.abs(difference),0,1);
        }
        else if(target>YVelocity)
        {
            if (rotationAngle != 0)
            {
                turnProbeToAngle(0, 0, 1);
                runner(1, 8);
            }
            double difference = target - YVelocity;
            if(Math.abs(difference)>13.52)
                difference=13.52;
            updaterY(Math.abs(difference),0,1);
        }
        else updaterY(0,0,1);


    }





    public void runner(double stepSize, double numberOfIterations)
    {

        double[] currentBoost={0.0,0.0,0.0};
        if(!listOfBoost.isEmpty())
        {
            currentBoost = listOfBoost.poll();
        }
        for (int i = 0; i < numberOfIterations&&YPosition>0; i++)
        {
//            if(i%10==0)
//            {
//                System.out.println("X: "+XPosition+" Y: "+YPosition);
//               // System.out.println("Angle is "+rotationAngle+" at time "+i);
//            }

            if(currentBoost[0]==i)
            {
                updater(currentBoost[1],currentBoost[2],stepSize);
                if(!listOfBoost.isEmpty())
                {
                    currentBoost = listOfBoost.poll();
                }
            }
            else
            {
                updater(0, 0, 1);
            }
        }
    }

    public void updater(double u,double v,double stepSize)
    {
        System.out.println("Angle: "+rotationAngle+" while u: "+u+" and YVelocity: "+YVelocity);

        rotationAngleVelocity+=v*stepSize;
        rotationAngle+=rotationAngleVelocity*stepSize;
        double XAcceleration;
        double YAcceleration;
        if(u>0)
        {
            rotationAngle=Math.round(rotationAngle);
           XAcceleration = u * Math.sin(rotationAngle);
           YAcceleration  = u * Math.cos(rotationAngle) - 1.352;
        }
        else
        {
            XAcceleration = u * Math.sin(rotationAngle);
            YAcceleration  = u * Math.cos(rotationAngle) - 1.352;
        }

        XVelocity+=XAcceleration*stepSize;
        XPosition+=XVelocity*stepSize;
        //Y value update


        YVelocity+=YAcceleration*stepSize;
        YPosition+=YVelocity*stepSize;
        //rotation angle update

      // System.out.println("X: "+XPosition+" Y: "+YPosition+" V: "+YVelocity);
        //System.out.println(rotationAngle);


    }

    public void updaterX(double u,double v,double stepSize)
    {
        //System.out.println("Angle: "+rotationAngle+" while u: "+u+" and YVelocity: "+YVelocity);
        rotationAngleVelocity+=v*stepSize;
        rotationAngle+=rotationAngleVelocity*stepSize;
        //X value update
        double XAcceleration=u*Math.sin(rotationAngle);
        XVelocity+=XAcceleration*stepSize;
        XPosition+=XVelocity*stepSize;
        //Y value update
        double YAcceleration=-1.352;
        YVelocity+=YAcceleration*stepSize;
        YPosition+=YVelocity*stepSize;
    }
    public void updaterY(double u,double v,double stepSize)
    {
        //System.out.println("Angle: "+rotationAngle+" while u: "+u+" and YVelocity: "+YVelocity);
        rotationAngleVelocity+=v*stepSize;
        rotationAngle+=rotationAngleVelocity*stepSize;
        //X value update
        double XAcceleration=u*Math.sin(rotationAngle);
        XVelocity+=XAcceleration*stepSize;
        XPosition+=XVelocity*stepSize;
        //Y value update
        double YAcceleration=-1.352;
        YVelocity+=YAcceleration*stepSize;
        YPosition+=YVelocity*stepSize;

    }




    public void turnProbeToAngle(double angle,double currentTime,double step)
    {
        double turnAngle=angle-rotationAngle;
//        System.out.println("Angle before boost: "+rotationAngle);
//        System.out.println("Angle"+turnAngle);
        addBoost(currentTime,0,turnAngle/7);
        addBoost(currentTime+7*step,0,-turnAngle/7);

    }






    public void addBoost(double time,double u,double v)
    {
        //TODO add limits
        double[] boost={time,u,v};
        listOfBoost.add(boost);
    }


    public double getDistanceToLandingSpot()
    {
      distanceToLandingSpot  =Math.sqrt(XPosition*XPosition+(YPosition*YPosition));
      return distanceToLandingSpot;
    }

    public boolean isFinished()
    {
        return finished;
    }


    public double getXPosition()
    {
        return XPosition;
    }

    public double getYPosition()
    {
        return YPosition;
    }

    public double getRotationAngle()
    {
        return rotationAngle;
    }

    public void print()
    {
        System.out.println("X: "+XPosition+" Y: "+YPosition+" VY: "+YVelocity);
    }
    public static void main(String[] args)
    {
        FeedbackController spaceCraft=new FeedbackController(30000,300000,  0,0,0);

        boolean stop = false;
        while (!stop){
            spaceCraft.controllerY();
            spaceCraft.print();
            stop = spaceCraft.isFinished();
        }
        spaceCraft.print();

//        int couner=0;
//        while (couner<100){
//            spaceCraft.controllerX();
//            spaceCraft.print();
//            couner++;
//        }
    }

}
