package apr.integration.demo;

import apr.integration.algorithms.*;
import apr.integration.util.AbsoluteErrorAccumulator;
import apr.integration.util.NthIterationObserver;
import apr.integration.util.StandardOutputLogger;
import apr.linear.matrix.ArrayMatrix;
import apr.linear.matrix.Matrix;
import apr.linear.vector.ArrayVector;
import apr.linear.vector.Vector;

import java.util.Arrays;
import java.util.List;

public class Lab {

    private static final AbstractLinearSystemIntegrator EULER = new EulerMethod();
    private static final AbstractLinearSystemIntegrator BACKWARD_EULER = new BackwardEuler();
    private static final AbstractLinearSystemIntegrator RUNGE_KUTTA = new RungeKutta();
    private static final AbstractLinearSystemIntegrator TRAPEZOIDAL = new Trapezoidal();
    private static final AbstractLinearSystemIntegrator PECE = new PECE(EULER, TRAPEZOIDAL, 1);
    private static final AbstractLinearSystemIntegrator PECE2 = new PECE(EULER, BACKWARD_EULER, 2);


    public static void main(String[] args) {
        zadatak1();
    }

    private static void zadatak1() {
        var accumulator = new AbsoluteErrorAccumulator(t -> Vector.of(Math.cos(t) + Math.sin(t), Math.cos(t) - Math.sin(t)));

        Matrix A = new ArrayMatrix(new double[][]{{0, 1}, {-1, 0}});
        Matrix B = new ArrayMatrix(new double[][]{{0, 0}, {0, 0}});
        Vector x0 = new ArrayVector(new double[]{1, 1});

        double max = 10.;
        double T = 0.1;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(new NthIterationObserver(new StandardOutputLogger(), 10));
            integrator.addObserver(accumulator);

            integrator.solve(A, B, t -> Vector.of(0., 0.), T, max, x0);

            System.out.println("Accumulated error: " + accumulator.getAccumulatedError());

            accumulator.clear();

            System.out.println();
        }
    }
}
