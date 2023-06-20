package code.algorithms.solvers;

public class AnotherSolvableFunction implements Function {

    public AnotherSolvableFunction(){
    }

    @Override
    public double evaluation(double y0, double t) {
        return 6 * t - 3; // ODE:   dy/dt = 6t - 3
    }

}
