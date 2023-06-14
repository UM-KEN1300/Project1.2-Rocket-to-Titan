package code.algorithms.solvers;

public class SolverTester {

    public static void main(String[] args){
        MyFunction myFunction = new MyFunction();
        AnotherFunction anotherFunction = new AnotherFunction();
        Vector y = new Vector();
        y.addFunction(myFunction);
        y.addFunction(anotherFunction);
        double h = 0.5;
        double t = 1;
        double [] y0 = {0, 0};

        for (int i = 0; i < 6; i++){
            Solvers.eulerStep(y, y0, h, t);
            t += 0.5;
        }

    }
}
