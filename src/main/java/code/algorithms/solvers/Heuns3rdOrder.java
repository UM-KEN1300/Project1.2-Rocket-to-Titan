package code.algorithms.solvers;

public class Heuns3rdOrder {
    static double function(double t, double y){
        return y;
    }
    public static double Heuns3rdOrder(double h, double t0, double w0, int steps) {
        int howManySteps = steps;
        double[] k1 = new double[howManySteps];
        double[] k2 = new double[howManySteps];
        double[] k3 = new double[howManySteps];
        double[] t = new double[howManySteps + 1];
        double[] w = new double[howManySteps + 1];
        t[0] = t0; w[0] = w0;
        for(int i = 0; i < howManySteps; i++){
            k1[i] = h * function(t[i], w[i]);
            k2[i] = h * function(t[i] + (1.0 / 3.0) * h, w[i] + (1.0 / 3.0) * k1[i]);
            k3[i] = h * function(t[i] + (2.0 / 3.0) * h, w[i] + (2.0 / 3.0) * k2[i]);
            w[i+1] = w[i] + 0.25 * (k1[i] + 3 * k3[i]) ;
            t[i+1] = t[i] + h;
        }
        return w[howManySteps];
    }

    public static void main(String[] args) {
        double timeStep_h = 0.0005;
        int numberOfSteps = 2000;
        double apprx = Heuns3rdOrder(timeStep_h,0, 1, numberOfSteps);
        System.out.println("h = " + timeStep_h + ", number of steps = " + numberOfSteps);
        System.out.println("Heun's 3rd order: " + apprx);
    }
}
// Dragos' Heun's 3rd order:
// t0 = 0, w0 = 1, exact solution = e = 2.718281828459045 (15 dp)
// h = 0.1, 10 steps ==> y(1) = w10 = 2.7181772624816096 ==> absolute error = |2.718281828459045 - 2.7181772624816096| = 0.0001045659774354
// h = 0.01, 100 steps ==> y(1) = w100 = 2.7182817160996344 ==> absolute error = |2.718281828459045 - 2.7182817160996344| = 0.0000001123594106
// h = 0.001, 1,000 steps ==> y(1) = w1000 = 2.7182818283458743 ==> absolute error = |2.718281828459045 - 2.7182818283458743| = 0.0000000001131707
// h = 0.0001, 10,000 steps ==> y(1) = w10,000 = 2.7182818284589323 ==> absolute error = |2.718281828459045 - 2.7182818284589323| = 0.0000000000001127

// h = 0.5, 2 steps ==> y(1) = w2 = 2.708767361111111 ==> absolute error = |2.718281828459045 - 2.708767361111111| = 0.009514467347934
// h = 0.05, 20 steps ==> y(1) = w20 = 2.7182682254508572 ==> absolute error = |2.718281828459045 - 2.7182682254508572| = 0.0000136030081878
// h = 0.005, 200 steps ==> y(1) = w200 = 2.7182818143578387 ==> absolute error = |2.718281828459045 - 2.7182818143578387| = 0.0000000141012063
// h = 0.0005, 2,000 steps ==> y(1) = w2000 = 2.718281828444897 ==> absolute error = |2.718281828459045 - 2.718281828444897| =0.000000000014148