package apr.optimization.demo;

import apr.linear.vector.IVector;
import apr.linear.vector.Vector;
import apr.optimization.algorithms.*;
import apr.optimization.function.CostFunctions;
import apr.optimization.function.ICostFunction;

import java.util.List;

public class Lab {

    public static void main(String[] args) {
        zadatak1();
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
            IVector x = new Vector(point, 0., 0.);

            for (IOptimizationAlgorithm algorithm : algorithms) {
                System.out.println(algorithm.getName());
                System.out.println(algorithm.search(x));
                System.out.println();
            }
        }
    }

    private static void zadatak2() {
    }

    private static void zadatak3() {
    }

    private static void zadatak4() {
    }

    private static void zadatak5() {
    }
}
