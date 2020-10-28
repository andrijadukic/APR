package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.*;
import apr.optimization.function.CostFunction;
import apr.optimization.function.CostFunctions;
import apr.optimization.function.ICostFunction;

import java.util.List;

public class Lab {

    public static void main(String[] args) {
//        zadatak1();
        zadatak2();
        zadatak3();
        zadatak4();
        zadatak5();
    }

    private static void zadatak1() {
        ICostFunction f3 = CostFunctions.f3();

        List<Double> startingPoints = List.of(10., 100., 200., 500., 1000.);
        List<IOptimizationAlgorithm> algorithms = List.of(new UnimodalInterval(f3),
                new GoldenSectionSearch(f3),
                new CoordinateDescent(f3),
                new HookeJeeves(f3),
                new NelderMeadMethod(f3));

        for (Double point : startingPoints) {
            IVector x = new Vector(point);

            for (IOptimizationAlgorithm algorithm : algorithms) {
                System.out.println(algorithm.getName());
                System.out.println(algorithm.search(x));
                System.out.println(f3.getCounter());
                System.out.println();

                f3.reset();
            }
        }
    }

    private static void zadatak2() {
        List<ICostFunction> functions = List.of(CostFunctions.f1(), CostFunctions.f2(), CostFunctions.f3(), CostFunctions.f4());
        List<IVector> points = List.of(new Vector(-1.9, 2), new Vector(0.1, 0.3), new Vector(0, 0, 0, 0, 0), new Vector(5.1, 1.1));
        List<IVector> minimums = List.of(new Vector(1., 1.), new Vector(4., 2.), new Vector(1, 2, 3, 4, 5), new Vector(0., 0.));

        for (int i = 0; i < 4; i++) {
            ICostFunction f = functions.get(i);
            IVector startingPoint = points.get(i);
            IVector minimum = minimums.get(i);

            List<IOptimizationAlgorithm> algorithms = List.of(new CoordinateDescent(f), new HookeJeeves(f), new NelderMeadMethod(f));

            for (IOptimizationAlgorithm algorithm : algorithms) {
                System.out.println("Function: f" + (i + 1));
                System.out.println("Algorithm: " + algorithm.getName());
                System.out.println("x0: " + startingPoint);
                System.out.println("min: " + minimum);
                System.out.println("min(aprox) : " + algorithm.search(startingPoint));
                System.out.println("Function calls: " + f.getCounter());
                System.out.println();

                f.reset();
            }
        }
    }

    private static void zadatak3() {
    }

    private static void zadatak4() {
    }

    private static void zadatak5() {
    }
}
