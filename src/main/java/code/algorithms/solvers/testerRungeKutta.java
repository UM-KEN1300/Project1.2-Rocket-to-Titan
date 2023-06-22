package code.algorithms.solvers;

import java.io.*;
public class testerRungeKutta {
    static double function(double t, double y){
        return y;
    }

    public static double testerRungeKutta(double h, double t0, double w0){
        int howManySteps = 2000;
        double[] k1 = new double[howManySteps];
        double[] k2 = new double[howManySteps];
        double[] k3 = new double[howManySteps];
        double[] k4 = new double[howManySteps];
        double[] t = new double[howManySteps + 1];
        double[] w = new double[howManySteps + 1];
        t[0] = t0;
        w[0] = w0;
        for(int i = 0; i < howManySteps; i++){
            k1[i] = h * function(t[i],w[i]);
            k2[i] = h * function(t[i] + h * 0.5, w[i] + k1[i] * 0.5);
            k3[i] = h * function(t[i] + h * 0.5, w[i] + k2[i] * 0.5);
            k4[i] = h * function(t[i] + h, w[i] + k3[i]);
            w[i + 1] = w[i] + (1.0 / 6.0) * (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]);
            t[i + 1] = t[i]  + h;
        }
        return w[howManySteps];
    }
    public static void main(String[] args) {
        double apprx = testerRungeKutta(0.0005,0, 1);
        System.out.println("4th-order Runge-Kutta: " + apprx);

    }
}
// t0 = 0; w0 = 1;
// h = 0.1, 10 steps ==> y(1) = w10 = 2.718279744135166 ==> absolute error = |2.718281828459045 - 2.718279744135166| = 0.000002084323879
// h = 0.01, 100 steps ==> y(1) = w100 = 2.7182818282344035 ==> absolute error = |2.718281828459045 - 2.7182818282344035| = 0.0000000002246415
// h = 0.001, 1000 steps ==> y(1) = w1000 = 2.7182818284590247 ==> absolute error = |2.718281828459045 - 2.7182818284590247| = 0.0000000000000203
// h = 0.0001, 10000 steps ==> y(1) = w10000 = 2.7182818284590566 ==> absolute error = |2.718281828459045 - 2.7182818284590566| = 0.0000000000000116

// h = 0.5, 2 steps ==> y(1) = w2 = 2.71734619140625 ==> absolute error = |2.718281828459045 - 2.71734619140625| = 0.000935637052795
// h = 0.05, 20 steps ==> y(1) = w20 = 2.718281692656335 ==> absolute error = |2.718281828459045 - 2.718281692656335| = 0.00000013580271
// h = 0.005, 200 steps ==> y(1) = w200 = 2.718281828444946 ==> absolute error = |2.718281828459045 - 2.718281828444946| = 0.000000000014099
// h = 0.0005, 2000 steps ==> y(1) = w2000 = 2.718281828459041 ==> absolute error = |2.718281828459045 - 2.718281828459041| = 0.000000000000004