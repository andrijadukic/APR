package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.fminbnd.GoldenSectionSearch;
import apr.optimization.algorithms.fminbnd.ISingleVariableOptimizationAlgorithm;
import apr.optimization.algorithms.fminbnd.UnimodalInterval;
import apr.optimization.algorithms.fminsearch.CoordinateDescent;
import apr.optimization.algorithms.fminsearch.HookeJeeves;
import apr.optimization.algorithms.fminsearch.IMultivariableOptimizationAlgorithm;
import apr.optimization.algorithms.fminsearch.NelderMeadMethod;
import apr.optimization.function.MultivariableCostFunctions;
import apr.optimization.function.IMultivariableCostFunction;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Lab {

    public static void main(String[] args) {
//        zadatak1();
        zadatak2();
//        zadatak3();
//        zadatak4();
//        zadatak5();
    }

    private static void zadatak1() {
        IMultivariableCostFunction f3 = MultivariableCostFunctions.f3();

        List<Double> startingPoints = List.of(10., 100., 200., 500., 1000.);
        List<ISingleVariableOptimizationAlgorithm> singleVariableOptimizationAlgorithms = List.of(new UnimodalInterval(x -> f3.valueAt(new Vector(x))),
                new GoldenSectionSearch(x -> f3.valueAt(new Vector(x))));
        List<IMultivariableOptimizationAlgorithm> multivariableOptimizationAlgorithms = List.of(new CoordinateDescent(f3), new HookeJeeves(f3), new NelderMeadMethod(f3));

        for (Double point : startingPoints) {
            IVector x = new Vector(point);

            for (var algorithm : singleVariableOptimizationAlgorithms) {
                System.out.println(algorithm.getName());
                System.out.println(algorithm.search(point));
                System.out.println(f3.getCounter());
                System.out.println();

                f3.reset();
            }

            for (var algorithm : multivariableOptimizationAlgorithms) {
                System.out.println(algorithm.getName());
                System.out.println(algorithm.search(x));
                System.out.println(f3.getCounter());
                System.out.println();

                f3.reset();
            }
        }
    }

    private static void zadatak2() {
        List<IMultivariableCostFunction> functions = List.of(MultivariableCostFunctions.f1(), MultivariableCostFunctions.f2(), MultivariableCostFunctions.f3(), MultivariableCostFunctions.f4());
        List<IVector> points = List.of(new Vector(-1.9, 2), new Vector(0.1, 0.3), new Vector(0, 0, 0, 0, 0), new Vector(5.1, 1.1));
        List<IVector> minimums = List.of(new Vector(1., 1.), new Vector(4., 2.), new Vector(1, 2, 3, 4, 5), new Vector(0., 0.));

        for (int i = 0; i < 4; i++) {
            IMultivariableCostFunction f = functions.get(i);
            List<IMultivariableOptimizationAlgorithm> algorithms = List.of(new CoordinateDescent(f), new HookeJeeves(f), new NelderMeadMethod(f));
            for (var algorithm : algorithms) {
                System.out.println("Function: f" + (i + 1));
                test(f, points.get(i), minimums.get(i), algorithm);
                f.reset();
            }
        }
    }

    private static void zadatak3() {
        IMultivariableCostFunction f = MultivariableCostFunctions.f4();
        IVector x0 = new Vector(5.5, 5.5);
        IVector min = new Vector(0., 0.);

        for (var algorithm : List.of(new HookeJeeves(f), new NelderMeadMethod(f))) {
            test(f, x0, min, algorithm);
        }
    }

    private static void zadatak4() {
        IMultivariableCostFunction f = MultivariableCostFunctions.f1();
        IVector min = new Vector(1., 1.);

        IVector startingPoint1 = new Vector(0.5, 0.5);
        IntStream.range(1, 20).forEach(step -> {
            System.out.println("Step: " + step);
            var hookeJeeves = new HookeJeeves(f);
            hookeJeeves.setDelta(step);
            test(f, startingPoint1, min, hookeJeeves);

            var nelderMead = new NelderMeadMethod(f);
            nelderMead.setStep(step);
            test(f, startingPoint1, min, nelderMead);
        });

        IVector startingPoint2 = new Vector(0.5, 0.5);
        IntStream.range(1, 20).forEach(step -> {
            System.out.println("Step: " + step);
            var hookeJeeves = new HookeJeeves(f);
            hookeJeeves.setDelta(step);
            test(f, startingPoint2, min, hookeJeeves);

            var nelderMead = new NelderMeadMethod(f);
            nelderMead.setStep(step);
            test(f, startingPoint2, min, nelderMead);
        });
    }

    private static void zadatak5() {
        IMultivariableCostFunction f = MultivariableCostFunctions.f6();
        double min = f.valueAt(new Vector(0., 0.));
        IMultivariableOptimizationAlgorithm algorithm = new NelderMeadMethod(f);

        int count = 0;
        int maxIter = 1000;
        Random random = new Random();
        for (int i = 0; i < maxIter; i++) {
            double x = -50 + (50 - (-50)) * random.nextDouble();
            double y = -50 + (50 - (-50)) * random.nextDouble();
            IVector startingPoint = new Vector(x, y);
            if (Math.abs(f.valueAt(algorithm.search(startingPoint)) - min) < 1e-4) {
                count++;
            }
            f.reset();
        }

        System.out.printf("%.6f%%", (double) count / maxIter * 100);
    }

    private static void test(IMultivariableCostFunction f, IVector startingPoint, IVector minimum, IMultivariableOptimizationAlgorithm algorithm) {
        System.out.println("Algorithm: " + algorithm.getName());
        System.out.println("x0: " + startingPoint);
        System.out.println("min: " + minimum);
        System.out.println("min(aprox) : " + algorithm.search(startingPoint));
        System.out.println("Function calls: " + f.getCounter());
        System.out.println();
    }
}
