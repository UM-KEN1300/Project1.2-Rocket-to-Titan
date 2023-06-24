package code.algorithms.solvers;

public class Ralstons2ndOrder {
    static double function(double t, double y){
        return y;
    }
    public static double Ralstons2ndOrder(double h, double t0, double w0) {
        int howManySteps = 2000;
        double[] k1 = new double[howManySteps];
        double[] k2 = new double[howManySteps];
        double[] t = new double[howManySteps + 1];
        double[] w = new double[howManySteps + 1];
        t[0] = t0; w[0] = w0;
        for(int i = 0; i < howManySteps; i++){
            k1[i] = h * function(t[i], w[i]);
            k2[i] = h * function(t[i] + (2.0 / 3.0) * h, w[i] + (2.0 / 3.0) * k1[i]);
            w[i+1] = w[i] + 0.25 * (k1[i] + 3.0 * k2[i]);
            t[i+1] = t[i] + h;
        }
        return w[howManySteps];
    }

    public static void main(String[] args) {
        double apprx = Ralstons2ndOrder(0.0005,0, 1);
        System.out.println("2nd order Ralston's: " + apprx);
    }
}
// Dragos' Ralston's 2nd order:
// t0 = 0, w0 = 1, exact solution = e = 2.718281828459045 (15 dp)
// h = 0.1, 10 steps ==> y(1) = w10 = 2.714080846608224 ==> absolute error = |2.718281828459045 - 2.714080846608224| = 0.004200981850821
// h = 0.01, 100 steps ==> y(1) = w100 = 2.7182368625599573 ==> absolute error = |2.718281828459045 - 2.7182368625599573| = 0.0000449658990877
// h = 0.001, 1,000 steps ==> y(1) = w1000 = 2.7182813757517628 ==> absolute error = |2.718281828459045 - 2.7182813757517628| = 0.0000004527072822
// h = 0.0001, 10,000 steps ==> y(1) = w10,000 = 2.718281823928888 ==> absolute error = |2.718281828459045 - 2.718281823928888| = 0.000000004530157

// h = 0.5, 2 steps ==> y(1) = w2 = 2.640625 ==> absolute error = |2.718281828459045 - 2.640625| = 0.077656828459045
// h = 0.05, 20 steps ==> y(1) = w20 = 2.7171910543548843 ==> absolute error = |2.718281828459045 - 2.7171910543548843| = 0.0010907741041607
// h = 0.005, 200 steps ==> y(1) = w200 = 2.7182705446963857 ==> absolute error = |2.718281828459045 - 2.7182705446963857| = 0.0000112837626593
// h = 0.0005, 2,000 steps ==> y(1) = w2000 = 2.7182817152397885 ==> absolute error = |2.718281828459045 - 2.7182817152397885| = 0.0000001132192565