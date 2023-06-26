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


    public void controller() {
       // updater(0,0,1);
        //TODO add wind

        double XTolerance = 10;
        if (YPosition <= 0.001) {
            finished = true;
        }


        else if (YPosition > 200000) {
            if (Math.abs(XPosition) > XTolerance && countX <= 0) {
                correctX();
            } else {
                correctY(750);
            }
        }

        else if (YPosition > 100000) {
            if (Math.abs(XPosition) > XTolerance && countX <= 0) {
                correctX();
            } else {
                correctY(500);
            }
        } else if (YPosition > 20000) {
            if (Math.abs(XPosition) > XTolerance && countX <= 0) {
                correctX();
            } else {
                correctY(100);
            }
        } else if (YPosition > 10000) {
            if (Math.abs(XPosition) > XTolerance && countX <= 0) {
                correctX();
            } else {
                correctY(10);
            }
        } else if (YPosition > 5000) {
            correctY(1);
        } else if (YPosition > 10) {
            correctY(0.1);
            lastPhase = true;
        } else {
            lastPhase = true;
            correctY(0.01);
        }
    }

    private void correctX() {
        double angle=0;
        if(XPosition>0)angle=270;
        if(XPosition<0)angle=90;


        if (rotationAngle != angle)
            {
                turnProbeToAngle(angle, 0, 1);
                runner(1, 8);
            }
            countX = 3;
           // updater(XVelocity, 0, 1);
            updater(0.1, 0, 1);
    }

    private void correctY(double target) {
        countX -= 1;
        if (rotationAngle != 0)
        {
           turnProbeToAngle(0,0,1);
           runner(1,8);
        }
        //-35.5398560637122 V: -28.48258400000952

        //System.out.println("In correctY boost");
        target = -target; // that's because we are going down, so we want negative velocity
        double difference = target -YVelocity;
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
        rotationAngleVelocity+=v*stepSize;
        rotationAngle+=rotationAngleVelocity*stepSize;
        //X value update
        double XAcceleration=u*Math.sin(rotationAngle);
        XVelocity+=XAcceleration*stepSize;
        XPosition+=XVelocity*stepSize;
        //Y value update
        double YAcceleration=u*Math.cos(rotationAngle)-1.352*Math.pow(10,-3);
        YVelocity+=YAcceleration*stepSize;
        YPosition+=YVelocity*stepSize;
        //rotation angle update

      // System.out.println("X: "+XPosition+" Y: "+YPosition+" V: "+YVelocity);
        //System.out.println(rotationAngle);


    }

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

    public double getDirectionTowardsLandingSpot()
    {
     double tan=YPosition/XPosition;
     double degs = Math.toDegrees(Math.atan(tan));
     return 270-degs;
    }

    public void turnProbeToAngle(double angle,double currentTime,double step)
    {
        double turnAngle=angle-rotationAngle;
//        System.out.println("Angle before boost: "+rotationAngle);
//        System.out.println("Angle"+turnAngle);
        addBoost(currentTime,0,turnAngle);
        addBoost(currentTime+step,0,-turnAngle);

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

    public void print()
    {
        System.out.println("X: "+XPosition+" Y: "+YPosition+" V: "+YVelocity);
    }
    public static void main(String[] args)
    {
      HardcodedValues spaceCraft=new HardcodedValues(300000,300000,  0,-40,0);
      //spaceCraft.turnProbeToAngle(90,0,1);

//        spaceCraft.addBoost(0,10,0);
//        spaceCraft.turnProbeToAngle(90,100,1)

//        spaceCraft.addBoost(108,-13,0);
//       spaceCraft.runner(1,10);
//        spaceCraft.turnProbeToAngle(180,0,1);
//        spaceCraft.runner(1,10);
//        spaceCraft.turnProbeToAngle(0,0,1);
//        spaceCraft.runner(1,10);
        boolean stop = false;
//        int counter=0;
        while (!stop){
            spaceCraft.controller();
            spaceCraft.print();
            stop = spaceCraft.isFinished();
        }
    }

}
