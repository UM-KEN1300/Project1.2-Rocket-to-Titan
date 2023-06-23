package code.algorithms.solvers;

import java.util.function.BiFunction;

public class RungeKutta extends AbstractSolver {
    public RungeKutta() {
    }


    @Override
    public double[] solve(BiFunction<Double, double[], double[]> function, double[] stateArray, double time, double timeStep) {
        double[] k1 = function.apply(time, stateArray);
        double[] k2 = function.apply(time + 0.5 * timeStep, addArrays(stateArray, scaleArray(0.5 * timeStep, k1)));
        double[] k3 = function.apply(time + 0.5 * timeStep, addArrays(stateArray, scaleArray(0.5 * timeStep, k2)));
        double[] k4 = function.apply(time + timeStep, addArrays(stateArray, scaleArray(timeStep, k3)));

        double[] kSum = addArrays(addArrays(k1, scaleArray(2, k2)), addArrays(scaleArray(2, k3), k4));

        return addArrays(stateArray, scaleArray(timeStep / 6d, kSum));
    }
}
