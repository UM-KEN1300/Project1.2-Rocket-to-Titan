package code.algorithms.solvers;

import java.util.function.BiFunction;

public abstract class AbstractSolver implements Solver {
    @Override
    public abstract double[] solve(BiFunction<Double, double[], double[]> function, double[] stateArray, double time, double timeStep);

    double[] addArrays(double[] array1, double[] array2) {
        if (array1.length != array2.length)
            throw new IllegalArgumentException("Arrays must be the same length");

        double[] result = new double[array1.length];
        for (int i = 0; i < array1.length; i++)
            result[i] = array1[i] + array2[i];

        return result;
    }

    double[] scaleArray(double scalar, double[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
            result[i] = scalar * array[i];

        return result;
    }
}
