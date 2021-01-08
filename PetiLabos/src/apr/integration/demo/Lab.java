package apr.integration.demo;

import apr.integration.algorithms.*;
import apr.integration.util.*;
import apr.linear.matrix.ArrayMatrix;
import apr.linear.matrix.Matrix;
import apr.linear.vector.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lab {

    private static final AbstractExplicitLinearSystemIntegrator EULER = new EulerMethod();
    private static final AbstractImplicitLinearSystemIntegrator BACKWARD_EULER = new BackwardEuler();
    private static final AbstractExplicitLinearSystemIntegrator RUNGE_KUTTA = new RungeKutta();
    private static final AbstractImplicitLinearSystemIntegrator TRAPEZOIDAL = new Trapezoidal();
    private static final AbstractLinearSystemIntegrator PECE = new PECE(EULER, TRAPEZOIDAL, 1);
    private static final AbstractLinearSystemIntegrator PECE2 = new PECE(EULER, BACKWARD_EULER, 2);


    public static void main(String[] args) throws IOException {
        zadatak1();
        System.out.println();

        zadatak2();
        System.out.println();

        zadatak3();
        System.out.println();

        zadatak4();
    }

    private static void zadatak1() throws IOException {
        System.out.println("Zadatak 1");
        System.out.println();

        var logger = new NthIterationObserver(new StandardOutputLogger(), 10);
        var accumulator = new AbsoluteErrorAccumulator(t -> Vector.of(Math.cos(t) + Math.sin(t), Math.cos(t) - Math.sin(t)));
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad1.txt");
        Matrix B = loadMatrix("data/B_zad1.txt");
        Vector x0 = loadMatrix("data/x0_zad1.txt").columns()[0];

        double max = 10.;
        double T = 0.1;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(logger);
            integrator.addObserver(accumulator);
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(0., 0.), T, max, x0);

            System.out.println("Accumulated error: " + accumulator.getAccumulatedError());

            accumulator.clear();
            writeToFile(integrator.getName() + "_zad1.txt", collector.getStatistics());
            collector.clear();

            integrator.removeObserver(logger);
            integrator.removeObserver(accumulator);
            integrator.removeObserver(collector);

            System.out.println();
        }
    }

    private static void zadatak2() throws IOException {
        System.out.println("Zadatak 2");
        System.out.println();

        var logger = new StandardOutputLogger();
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad2.txt");
        Matrix B = loadMatrix("data/B_zad2.txt");
        Vector x0 = loadMatrix("data/x0_zad2.txt").columns()[0];

        double max = 1.;
        double T = 0.1;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(logger);
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(0., 0.), T, max, x0);

            writeToFile(integrator.getName() + "_zad2.txt", collector.getStatistics());
            collector.clear();

            integrator.removeObserver(logger);
            integrator.removeObserver(collector);

            System.out.println();
        }
    }

    private static void zadatak3() throws IOException {
        System.out.println("Zadatak 3");
        System.out.println();

        var logger = new NthIterationObserver(new StandardOutputLogger(), 100);
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad3.txt");
        Matrix B = loadMatrix("data/B_zad3.txt");
        Vector x0 = loadMatrix("data/x0_zad3.txt").columns()[0];

        double max = 10.;
        double T = 0.01;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(logger);
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(1., 1.), T, max, x0);

            writeToFile(integrator.getName() + "_zad3.txt", collector.getStatistics());
            collector.clear();

            integrator.removeObserver(logger);
            integrator.removeObserver(collector);

            System.out.println();
        }
    }

    private static void zadatak4() throws IOException {
        System.out.println("Zadatak 4");
        System.out.println();

        var logger = new NthIterationObserver(new StandardOutputLogger(), 100);
        var collector = new StateCollector();

        Matrix A = loadMatrix("data/A_zad4.txt");
        Matrix B = loadMatrix("data/B_zad4.txt");
        Vector x0 = loadMatrix("data/x0_zad4.txt").columns()[0];

        double max = 1.;
        double T = 0.01;

        for (var integrator : List.of(EULER, BACKWARD_EULER, TRAPEZOIDAL, RUNGE_KUTTA, PECE, PECE2)) {
            System.out.println(integrator.getName());

            integrator.addObserver(logger);
            integrator.addObserver(collector);

            integrator.solve(A, B, t -> Vector.of(t, t), T, max, x0);

            writeToFile(integrator.getName() + "_zad4.txt", collector.getStatistics());
            collector.clear();

            integrator.removeObserver(logger);
            integrator.removeObserver(collector);

            System.out.println();
        }
    }

    private static Matrix loadMatrix(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        int rowDimension = lines.size();
        double[][] array = new double[rowDimension][];
        for (int i = 0; i < rowDimension; i++) {
            array[i] = Arrays.stream(lines.get(i).split("\\s+")).mapToDouble(Double::parseDouble).toArray();
        }

        return new ArrayMatrix(array);
    }

    private static void writeToFile(String fileName, List<StateStatistics> states) throws IOException {
        Files.write(
                Path.of("data/" + fileName.replace(" ", "_")),
                states.stream()
                        .map(state -> state.t() + ";" + state.x().toString().replace(" ", ";"))
                        .collect(Collectors.toUnmodifiableList())
        );
    }
}
