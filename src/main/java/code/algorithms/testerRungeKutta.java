package code.algorithms;

import java.io.*;
public class testerRungeKutta {
    private double[] k1,k2,k3,k4;
    double function(double x, double y){
        return 6 * x -3;
    }

    public double testerRungeKutta(double h, double t0, double w0) {
        int howManySteps = 3;
        double[] t = new double[howManySteps + 1];
        double[] w = new double[howManySteps + 1];
        t[0] = t0; w[0] = w0;
        for(int i = 0; i <= howManySteps; i++){
            k1[i] = h * function(t[i],w[i]);
            k2[i] = h * function(t[i] + h * 0.5, w[i] + k1[i] * 0.5);
            k3[i] = h * function(t[i] + h * 0.5, w[i] + k2[i] * 0.5);
            k4[i] = h * function(t[i] + h, w[i] + k3[i]);
            w[i+1] = w[i] + (1.0 / 6.0) * (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]);
        }
        return w[howManySteps + 1];
    }
    public static void main(String[] args) {

    }
}
