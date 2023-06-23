package code.algorithms.solvers;

import java.util.function.BiFunction;

public interface Solver {
    double[] solve(BiFunction<Double, double[], double[]> function, double[] stateArray, double time, double timeStep);
}
