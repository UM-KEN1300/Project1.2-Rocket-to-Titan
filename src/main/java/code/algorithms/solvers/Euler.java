package code.algorithms.solvers;

import java.util.function.BiFunction;

public class Euler extends AbstractSolver {
    public Euler() {
    }


    @Override
    public double[] solve(BiFunction<Double, double[], double[]> function, double[] y, double t, double dt) {
        double[] dydt = function.apply(t, y);
        return addArrays(y, scaleArray(dt, dydt));
    }
}
