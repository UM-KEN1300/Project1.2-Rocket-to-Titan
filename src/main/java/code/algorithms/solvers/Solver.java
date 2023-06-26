package code.algorithms.solvers;

import java.util.function.BiFunction;

/**
 * Interface whose implementation provide the functionality of mathematical solvers.
 */
public interface Solver {
    /**
     * Method to solve a given mathematical problem.
     *
     * @param function the function to be solved. It should accept a double and a double array and return a double array.
     * @param stateArray an array of initial states
     * @param time the initial time
     * @param timeStep the step size for each iteration
     * @return a double array representing the solution to the function
     */
    double[] solve(BiFunction<Double, double[], double[]> function, double[] stateArray, double time, double timeStep);
}
