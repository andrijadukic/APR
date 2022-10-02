package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.fminbnd.GoldenSectionSearch;
import apr.optimization.algorithms.fminsearch.CoordinateDescent;
import apr.optimization.algorithms.fminsearch.HookeJeeves;
import apr.optimization.algorithms.fminsearch.NelderMead;
import apr.optimization.functions.CostFunctions;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Lab {

    public static void main(String[] args) {
        zadatak1();
        zadatak2();
        zadatak3();
        zadatak4();
        zadatak5();
    }

    private static void zadatak1() {
        System.out.println("Zadatak 1");
        System.out.println("-----------------------------------");
        System.out.println();

        var f3 = CostFunctions.f3();

        var goldenSectionSearch = new GoldenSectionSearch(x -> f3.valueAt(new Vector(x)));
        var multivariableOptimizationAlgorithms = List.of(new CoordinateDescent(f3), new HookeJeeves(f3), new NelderMead(f3));

        Tester tester = new Tester();
        for (double point = 10; point < 1000; point += 100) {
            tester.test(f3, goldenSectionSearch, point);

            for (var algorithm : multivariableOptimizationAlgorithms) {
                tester.test(f3, algorithm, new Vector(point));
            }
        }
        tester.printResults();
    }

    private static void zadatak2() {
        System.out.println("Zadatak 2");
        System.out.println("-----------------------------------");
        System.out.println();

        var functions = List.of(CostFunctions.f1(), CostFunctions.f2(), CostFunctions.f3(), CostFunctions.f4());
        var points = List.of(new Vector(-1.9, 2.), new Vector(0.1, 0.3), new Vector(0, 0, 0, 0, 0), new Vector(5.1, 1.1));
        var minimums = List.of(new Vector(1., 1.), new Vector(4., 2.), new Vector(1, 2, 3, 4, 5), new Vector(0., 0.));

        Tester tester = new Tester();
        for (int i = 0; i < 4; i++) {
            System.out.println("Function: f" + (i + 1));
            System.out.println("x0: " + points.get(i));
            System.out.println("xMin: " + minimums.get(i));
            System.out.println();
            var f = functions.get(i);
            for (var algorithm : List.of(new CoordinateDescent(f), new HookeJeeves(f), new NelderMead(f))) {
                tester.test(f, algorithm, points.get(i));
            }
            tester.printResults();
            tester.reset();
        }
    }

    private static void zadatak3() {
        System.out.println("Zadatak 3");
        System.out.println("-----------------------------------");
        System.out.println();

        var f = CostFunctions.f4();
        IVector x0 = new Vector(5.5, 5.5);

        Tester tester = new Tester();
        for (var algorithm : List.of(new HookeJeeves(f), new NelderMead(f))) {
            tester.test(f, algorithm, x0);
        }
        tester.printResults();
    }

    private static void zadatak4() {
        System.out.println("Zadatak 4");
        System.out.println("-----------------------------------");
        System.out.println();

        var f = CostFunctions.f1();
        IVector startingPoint1 = new Vector(0.5, 0.5);
        IVector startingPoint2 = new Vector(0.5, 0.5);

        Tester tester = new Tester();

        IntStream.range(1, 20).forEach(step -> {
            var hookeJeeves = new HookeJeeves(f);
            hookeJeeves.setDelta(step);
            tester.test(f, hookeJeeves, startingPoint1);

            var nelderMead = new NelderMead(f);
            nelderMead.setStep(step);
            tester.test(f, nelderMead, startingPoint1);
        });

        tester.printResults();
        tester.reset();

        IntStream.range(1, 20).forEach(step -> {
            var hookeJeeves = new HookeJeeves(f);
            hookeJeeves.setDelta(step);
            tester.test(f, hookeJeeves, startingPoint2);

            var nelderMead = new NelderMead(f);
            nelderMead.setStep(step);
            tester.test(f, nelderMead, startingPoint2);
        });
        tester.printResults();
    }

    private static void zadatak5() {
        System.out.println("Zadatak 5");
        System.out.println("-----------------------------------");
        System.out.println();

        var f = CostFunctions.f6();
        double fmin = f.valueAt(new Vector(0., 0.));
        var algorithm = new NelderMead(f);

        int count = 0;
        int maxIter = 10000;
        Random random = new Random();
        for (int i = 0; i < maxIter; i++) {
            double x = -50 + (50 - (-50)) * random.nextDouble();
            double y = -50 + (50 - (-50)) * random.nextDouble();
            IVector startingPoint = new Vector(x, y);
            if (Math.abs(f.valueAt(algorithm.search(startingPoint)) - fmin) < 1e-4) {
                count++;
            }
            f.reset();
        }
        System.out.println((double) count / maxIter * 100 + "%");
    }
}
