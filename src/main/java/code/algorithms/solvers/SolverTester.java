package code.algorithms.solvers;

public class SolverTester {


    public static double actualSolutionAnotherSolvableFunction(double t){
        return 3*(t*t) - (3*t) + 4; // solution: 3t^2 - 3t + 4
    }
    public static void main(String[] args){
        MyFunction myFunction = new MyFunction();
        AnotherFunction anotherFunction = new AnotherFunction();
        SolvableFunction solvableFunction = new SolvableFunction();
        AnotherSolvableFunction anotherSolvableFunction = new AnotherSolvableFunction();
        Vector y = new Vector();
        //y.addFunction(myFunction);
        //y.addFunction(anotherFunction);
        
        y.addFunction(anotherSolvableFunction);

        double h = 0.5;
        double t = 1;
        double [] y0 = {0};
        double actualResult = actualSolutionAnotherSolvableFunction(4);

        for (int i = 0; i < 6; i++) {
            Solvers.eulerStep(y, y0, h, t);
            t += h;
        }
