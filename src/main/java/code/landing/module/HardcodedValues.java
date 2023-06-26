package code.landing.module;
import java.lang.Math;
import java.util.*;

public class HardcodedValues
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
    boolean lastPhase;
    int countX;
    public HardcodedValues(double XPosition, double YPosition, double rotationAngle, double XVelocity, double YVelocity)
    {
        this.XPosition = XPosition;
        this.YPosition = YPosition;
        this.rotationAngle = rotationAngle;
        this.XVelocity = XVelocity;
        this.YVelocity = YVelocity;
        listOfBoost=new LinkedList<>();
        lastPhase = false;
        finished = false;
        countX = 0;
    }


    public void controller() {
       // updater(0,0,1);
        //TODO add wind

        double XTolerance = 10;
        if (YPosition <= 0.001) {
            finished = true;
        }
        else if (YPosition > 200000) {
            correctY(750);
        }

        else if (YPosition > 100000) {
            correctY(500);
        } else if (YPosition > 20000) {
            correctY(100);
            }
         else if (YPosition > 10000) {
                correctY(10);
        } else if (YPosition > 5000) {
                correctY(1);
        } else if (YPosition > 10) {
                correctY(0.1);
        } else {
            lastPhase = true;
            correctY(0.01);
        }
    }


    public void controllerX() {
        updater(0,0,1);
        //TODO add wind


        if (XPosition <= 0.001) {
            finished = true;
        }
        else if (XPosition > 200000) {
            correctX(-750);
        }

        else if (XPosition > 100000) {
            correctX(500);
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
            lastPhase = true;
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
            }
            difference= target -XVelocity;
            if(Math.abs(difference)>13.52)
                difference=13.52;
            updater(difference,0,1);
        }
        else if(target>XVelocity)
        {
            if (rotationAngle != 90)
            {
                turnProbeToAngle(90,0,1);
                runner(1,8);
            }
            difference= target -XVelocity;

            if(Math.abs(difference)>13.52)
                difference=13.52;
            updater(Math.abs(difference),0,1);
        }
        else {
            updater(0,0,1);
        }






    }

    private void correctY(double target) {
        countX -= 1;
        if (rotationAngle != 0)
        {
           turnProbeToAngle(0,0,1);
           runner(1,8);
        }
        //-35.5398560637122 V: -28.48258400000952

//        System.out.println("In correctY boost");
        target = -target; // that's because we are going down, so we want negative velocity
        double difference = target -YVelocity;

//        if(Math.abs(difference)>14)
//        {
//            difference = -13.52;
//        }
        System.out.println("Diff"+difference);
        updater(difference,0,1);
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
        //X value update
        double XAcceleration=u*Math.sin(rotationAngle);
        XVelocity+=XAcceleration*stepSize;
        XPosition+=XVelocity*stepSize;
        //Y value update
        double YAcceleration=u*Math.cos(rotationAngle)-(1.352*Math.pow(10,-3));

        YVelocity+=YAcceleration*stepSize;
        YPosition+=YVelocity*stepSize;
        //rotation angle update

      // System.out.println("X: "+XPosition+" Y: "+YPosition+" V: "+YVelocity);
        //System.out.println(rotationAngle);


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
        System.out.println("X: "+XPosition+" Y: "+YPosition+" V: "+XVelocity);
    }
    public static void main(String[] args)
    {
     // HardcodedValues spaceCraft=new HardcodedValues(300000,300000,  0,-40,0);
      //spaceCraft.turnProbeToAngle(90,0,1);

//        spaceCraft.addBoost(0,10,0);
//        spaceCraft.turnProbeToAngle(90,100,1)

//        spaceCraft.addBoost(108,-13,0);
//       spaceCraft.runner(1,10);
//        spaceCraft.turnProbeToAngle(180,0,1);
//        spaceCraft.runner(1,10);
//        spaceCraft.turnProbeToAngle(0,0,1);
//        spaceCraft.runner(1,10);

        HardcodedValues spaceCraft=new HardcodedValues(200001,300000,  0,0,0);
        boolean stop = false;
        int couner=0;
        while (couner<100){
            spaceCraft.controllerX();
            spaceCraft.print();
            stop = spaceCraft.isFinished();
            couner++;
        }
    }

}
