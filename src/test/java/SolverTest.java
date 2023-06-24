import code.algorithms.solvers.ExplicitEuler;
import code.algorithms.solvers.RungeKutta;
import code.algorithms.solvers.Solver;

import java.util.Arrays;
import java.util.function.BiFunction;

public class SolverTest {

    public static double[] testSolver(Solver solver, BiFunction<Double, double[], double[]> function,
                                      double[] initialState, double initialTime, double timeStep, int steps) {
        double[] stateArray = initialState;
        double time = initialTime;
        for (int i = 0; i < steps; i++) {
            stateArray = solver.solve(function, stateArray, time, timeStep);
            time += timeStep;
        }
        return stateArray;
    }
    public static void main(String[] args) {
        // Test parameters
        double h = 0.0005;
        double t0 = 0;
        double[] w0 = {1.0};
        int steps = 2000;

        System.out.println("h               = " + h);
        System.out.println("Number of steps = " + steps);

        Solver explicitEuler = new ExplicitEuler(); //test ExplicitEuler solver
        double[] explicitEulerSolution = testSolver(explicitEuler, (t, y) -> y, w0, t0, h, steps);
        System.out.println("Explicit Euler: " + Arrays.toString(explicitEulerSolution));

        Solver rungeKutta = new RungeKutta(); //test RungeKutta solver
        double[] rungeKuttaSolution = testSolver(rungeKutta, (t, y) -> y, w0, t0, h, steps);
        System.out.println("RungeKutta: " + Arrays.toString(rungeKuttaSolution));
    }
}
//MARCELL's SOLVERS
// Explicit Euler: t0 = 0, w0 = 1, exact solution = e = 2.718281828459045 (16 sf, 15 dp)
// h = 0.1, 10 steps ==> y(1) = w10 = 2.5937424601 ==> absolute error = |2.718281828459045 - 2.5937424601| = 0.124539368359045
// h = 0.01, 100 steps ==> y(1) = w100 = 2.704813829421526 ==> absolute error = |2.718281828459045 - 2.704813829421526| = 0.013467999037519
// h = 0.001, 1,000 steps ==> y(1) = w1000 = 2.716923932235896 ==> absolute error = |2.718281828459045 - 2.716923932235896| = 0.001357896223149
// h = 0.0001, 10,000 steps ==> y(1) = w10,000 = 2.7181459268252266 ==> absolute error = |2.718281828459045 - 2.7181459268252266| = 0.0001359016338184

// h = 0.5, 2 steps ==> y(1) = w2 = 2.25 ==> absolute error = |2.718281828459045 - 2.25| = 0.468281828459045
// h = 0.05, 20 steps ==> y(1) = w20 = 2.6532977051444213 ==> absolute error = |2.718281828459045 - 2.6532977051444213| = 0.0649841233146237
// h = 0.005, 200 steps ==> y(1) = w200 = 2.7115171229293744 ==> absolute error = |2.718281828459045 - 2.7115171229293744| = 0.0067647055296706
// h = 0.0005, 2,000 steps ==> y(1) = w2000 = 2.7176025693231414 ==> absolute error = |2.718281828459045 - 2.7176025693231414| = 0.0006792591359036


// Runge-Kutta : t0 = 0, w0 = 1, exact solution = e = 2.718281828459045 (16 sf, 15 dp)
// h = 0.1, 10 steps ==> y(1) = w10  = 2.718279744135166 ==> absolute error = |2.718281828459045 - 2.718279744135166| = 0.000002084323879
// h = 0.01, 100 steps ==> y(1) = w100 = 2.718281828234404 ==> absolute error = |2.718281828459045 - 2.718281828234404| = 0.000000000224641
// h = 0.001, 1,000 steps ==> y(1) = w1000 = 2.7182818284590247 ==> absolute error = |2.718281828459045 - 2.7182818284590247| = 0.0000000000000203
// h = 0.0001, 10,000 steps ==> y(1) = w10,000 = 2.7182818284590566 ==> absolute error = |2.718281828459045 - 2.7182818284590566| = 0.0000000000000116

// h = 0.5, 2 steps ==> y(1) = w2 = 2.71734619140625 ==> absolute error = |2.718281828459045 - 2.71734619140625| = 0.000935637052795
// h = 0.05, 20 steps ==> y(1) = w20 = 2.718281692656335 ==> absolute error = |2.718281828459045 - 2.718281692656335| = 0.00000013580271
// h = 0.005, 200 steps ==> y(1) = w200 = 2.718281828444946 ==> absolute error = |2.718281828459045 - 2.718281828444946| = 0.000000000014099
// h = 0.0005, 2,000 steps ==> y(1) = w2000 = 2.7182818284590415 ==> absolute error = |2.718281828459045 - 2.7182818284590415| = 0.0000000000000035