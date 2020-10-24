package apr.optimization.demo;

import apr.linear.vector.Vector;
import apr.optimization.algorithms.GoldenSectionSearch;
import apr.optimization.algorithms.HookeJeeves;
import apr.optimization.algorithms.NelderMeadMethod;
import apr.optimization.function.CostFunction;
import apr.optimization.function.CostFunctions;
import apr.optimization.function.ICostFunction;

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
        f3.setXMin(new Vector(3));

        for (int i = 10; i < 1000; i += 100) {
            System.out.println("Golden ratio search");
            System.out.println(GoldenSectionSearch.goldenRatio(f3, 1, i));
            System.out.println(f3.getCounter());
            f3.reset();

            System.out.println();

            System.out.println("unimodal interval search");
            System.out.println(GoldenSectionSearch.unimodalInterval(f3, 1, i));
            System.out.println(f3.getCounter());
            f3.reset();

            System.out.println();

            System.out.println("Hooke Jeeves");
            System.out.println(HookeJeeves.patternSearch(f3, new Vector(i)));
            System.out.println(f3.getCounter());
            f3.reset();

            System.out.println();

            System.out.println("Nelder Mead");
            System.out.println(NelderMeadMethod.simplex(f3, new Vector(i)));
            System.out.println(f3.getCounter());
            f3.reset();
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
