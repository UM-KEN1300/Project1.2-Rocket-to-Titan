package code.landing.module;

import code.model.objects.PlanetObject;
import code.model.objects.Probe;
import code.utils.HelperFunctions;

import java.util.Arrays;

public class SpaceCraft
{
    double XPosition;
    double YPosition;
    double XVelocity;
    double YVelocity;
   
    public SpaceCraft(PlanetObject Titan, Probe probe, double[] anotherPointInTheOrbit)
    {

        double[] A = {0, 0, 0};
        double[] B = HelperFunctions.subtract(probe.getCoordinates(), Titan.getCoordinates());
        double[] C = HelperFunctions.subtract(anotherPointInTheOrbit, Titan.getCoordinates());
        double[] velocity=HelperFunctions.subtract(probe.getVelocity(),Titan.getVelocity());
        converterFrom3Dto2D(A, B, C,velocity);

    }

    public void converterFrom3Dto2D(double[]A, double[]B, double[]C,double[] velocity)
    {
        //Plane
        double[] planeEquationConstants=new double[4];
        planeEquationConstants[0]=(B[1]-A[1])*(C[2]-A[2])-(C[1]-A[1])*(B[2]-A[2]);
        planeEquationConstants[1]=(B[2]-A[2])*(C[0]-A[0])-(C[2]-A[2])*(B[0]-A[0]);
        planeEquationConstants[2]=(B[0]-A[0])*(C[1]-A[1])-(C[0]-A[0])*(B[1]-A[1]);
        planeEquationConstants[3]=-(planeEquationConstants[0]*A[0]+planeEquationConstants[1]*A[1]+planeEquationConstants[2]*A[2]);



        System.out.println("The plane equations is "+Arrays.toString(planeEquationConstants));
        double[] firstUnit={0,0,0};
        if(planeEquationConstants[3]==0)
        {
            firstUnit[1]=planeEquationConstants[2];
            firstUnit[2]=-planeEquationConstants[1];
        }
        else
        {
           firstUnit[1]=1;
           firstUnit[2]=(planeEquationConstants[3]+planeEquationConstants[1])/planeEquationConstants[2];
        }
        double[] secondUnit={0,0,1};
        secondUnit[1]=-firstUnit[2]/firstUnit[1];
        secondUnit[0]=(-planeEquationConstants[1]*secondUnit[1]-planeEquationConstants[2])/planeEquationConstants[0];


        System.out.println("First unit vector is "+Arrays.toString(firstUnit));
        System.out.println("Second unit vector is "+Arrays.toString(secondUnit));

        firstUnit=turnVectorTo1(firstUnit);
        secondUnit=turnVectorTo1(secondUnit);


        double[] initial2DPosition=convertPoint(B,firstUnit,secondUnit);
        XPosition=initial2DPosition[0];
        YPosition=initial2DPosition[1];
        double[] initial2DVelocity=convertPoint(velocity,firstUnit,secondUnit);
        XVelocity=initial2DVelocity[0];
        YVelocity=initial2DVelocity[1];

    }







    public double[] convertPoint(double[] A,double[] firstUnit,double[] secondUnit)
    {
        double[] cord2D=new double[2];
        cord2D[0]=firstUnit[0]*A[0]+(firstUnit[1]*A[1])+(firstUnit[2]*A[2]);
        cord2D[1]=secondUnit[0]*A[0]+(secondUnit[1]*A[1])+(secondUnit[2]*A[2]);
        System.out.println(Arrays.toString(cord2D));
        return cord2D;
    }


    public double[] turnVectorTo1(double[ ]vector)
    {
        double magnitude= HelperFunctions.getVectorMagnitude(vector);
        for (int i = 0; i < vector.length; i++) {
            vector[i]=vector[i]/magnitude;
        }
        return vector;
    }

    public double[] crossProduct(double[] a,double[]b)
    {
        double[] returnable=new double[3];
        returnable[0]=a[1]*b[1]-(a[2]*b[1]);
        returnable[1]=a[2]*b[0]-(a[0]*b[2]);
        returnable[2]=a[0]*b[1]-(a[1]*b[0]);
        return returnable;
    }

    public double[] getVector(double[] pointA,double[] pointB)
    {
        double[] returnable=new double[3];
        for (int i = 0; i < pointA.length; i++) {
            returnable[i]=pointB[i]-pointA[i];
        }
        return returnable;
    }







}
