package code.algorithms.solvers;

import java.util.function.BiFunction;

public class ExplicitEuler extends AbstractSolver {
    public ExplicitEuler() {
    }


    @Override
    public double[] solve(BiFunction<Double, double[], double[]> function, double[] y, double t, double dt) {
        double[] dydt = function.apply(t, y);
        return addArrays(y, scaleArray(dt, dydt));
    }
}
