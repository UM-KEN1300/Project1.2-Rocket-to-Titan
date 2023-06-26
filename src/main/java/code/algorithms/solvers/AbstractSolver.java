package code.algorithms.solvers;

import java.util.function.BiFunction;

/**
 * AbstractSolver is an abstract class that provides a framework for implementing mathematical solvers.
 * This class provides some basic array manipulation methods that may be useful for concrete Solver implementations.
 */
public abstract class AbstractSolver implements Solver {
    @Override
    public abstract double[] solve(BiFunction<Double, double[], double[]> function, double[] stateArray, double time, double timeStep);

    /**
     * Method to add two arrays element-wise.
     * The two input arrays must be of the same length.
     *
     * @param array1 the first array to be added
     * @param array2 the second array to be added
     * @return a double array where each element is the sum of the corresponding elements in the input arrays
     * @throws IllegalArgumentException if the input arrays are not the same length
     */
    double[] addArrays(double[] array1, double[] array2) {
        if (array1.length != array2.length)
            throw new IllegalArgumentException("Arrays must be the same length");

        double[] result = new double[array1.length];
        for (int i = 0; i < array1.length; i++)
            result[i] = array1[i] + array2[i];

        return result;
    }

    /**
     * Method to scale an array by a scalar value.
     * This is performed element-wise: each element in the array is multiplied by the scalar.
     *
     * @param scalar the scalar to multiply each element in the array by
     * @param array  the array to be scaled
     * @return a double array where each element is the original element multiplied by the scalar
     */
    double[] scaleArray(double scalar, double[] array) {
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++)
            result[i] = scalar * array[i];

        return result;
    }
}
