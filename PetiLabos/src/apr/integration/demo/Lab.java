package apr.integration.demo;

import apr.functions.UnivariateVectorFunction;
import apr.integration.algorithms.*;
import apr.integration.util.AbsoluteErrorAccumulator;
import apr.integration.util.NthIterationObserver;
import apr.integration.util.StandardOutputLogger;
import apr.linear.matrix.ArrayMatrix;
import apr.linear.matrix.Matrix;
import apr.linear.vector.ArrayVector;
import apr.linear.vector.Vector;

import java.util.List;

public class Lab {

    private static final AbstractLinearSystemIntegrator EULER = new EulerMethod();
    private static final AbstractLinearSystemIntegrator REVERSE_EULER = new ReverseEulerMethod();
    private static final AbstractLinearSystemIntegrator RUNGE_KUTTA = new RungeKutta();
    private static final AbstractLinearSystemIntegrator TRAPEZOIDAL = new Trapezoidal();
    private static final AbstractLinearSystemIntegrator PREDICTOR_CORRECTOR = new PredictorCorrector(EULER, TRAPEZOIDAL);


    public static void main(String[] args) {
        zadatak1();
    }

    private static void zadatak1() {
        var accumulator = new AbsoluteErrorAccumulator(t -> Vector.of(Math.cos(t) + Math.sin(t), Math.cos(t) - Math.sin(t)));

        Matrix A = new ArrayMatrix(new double[][]{{0, 1}, {-1, 0}});
        Vector B = new ArrayVector(new double[]{0, 0});
        Vector x0 = new ArrayVector(new double[]{1, 1});

        double tMax = 10.;
        double T = 0.1;

        for (var integrator : List.of(EULER, REVERSE_EULER, RUNGE_KUTTA, TRAPEZOIDAL, PREDICTOR_CORRECTOR)) {
            integrator.addObserver(new NthIterationObserver(new StandardOutputLogger(), 10));
            integrator.addObserver(accumulator);

            integrator.solve(x0, A, B, T, tMax);

            System.out.println(accumulator.getAccumulatedError());

            accumulator.clear();
        }
    }
}
